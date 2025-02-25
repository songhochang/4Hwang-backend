package com.green.sahwang.payment.service.impl;

import com.green.sahwang.config.AvroToDBSerializer;
import com.green.sahwang.dto.request.CartProductPurchaseReadyReqDto;
import com.green.sahwang.payment.dto.req.CancelPaymentReqDto;
import com.green.sahwang.payment.dto.req.PaymentCompleteRequest;
import com.green.sahwang.payment.dto.req.externalapi.ExternalPaymentReqDto;
import com.green.sahwang.payment.dto.req.externalapi.ExternalPurchasePaymentReqDto;
import com.green.sahwang.payment.dto.res.BuyerInfoResDto;
import com.green.sahwang.dto.response.CartProductPurchaseReadyResDto;
import com.green.sahwang.entity.*;
import com.green.sahwang.entity.enumtype.OutboxStatus;
import com.green.sahwang.entity.enumtype.PaymentType;
import com.green.sahwang.entity.enumtype.PurchaseStatus;
import com.green.sahwang.entity.enumtype.SystemLogicType;
import com.green.sahwang.payment.dto.req.externalapi.ExternalPrePaymentReqDto;
import com.green.sahwang.payment.dto.res.externalapi.BuyerResDto;
import com.green.sahwang.payment.entity.externalapi.PostPaymentEntity;
import com.green.sahwang.payment.entity.externalapi.PrePaymentEntity;
import com.green.sahwang.exception.BizException;
import com.green.sahwang.exception.ErrorCode;
import com.green.sahwang.exception.outbox.OutboxSerializeEventException;
import com.green.sahwang.exception.PurchaseDomainException;
import com.green.sahwang.exception.payment.PaymentDomainException;
import com.green.sahwang.model.payment.avro.PaymentAvroMethod;
import com.green.sahwang.model.payment.avro.PaymentAvroStatus;
import com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel;
import com.green.sahwang.payment.entity.Payment;
import com.green.sahwang.payment.repository.PaymentRepository;
import com.green.sahwang.payment.repository.externalapi.ExternalPostPaymentApiRepository;
import com.green.sahwang.repository.*;
import com.green.sahwang.payment.repository.externalapi.ExternalPrePaymentApiRepository;
import com.green.sahwang.payment.service.PaymentService;
import com.green.sahwang.payment.service.helper.PaymentServiceHelper;
import com.green.sahwang.service.ProductService;
import com.green.sahwang.service.impl.ProductServiceImpl;
import com.green.sahwang.usedproduct.dto.response.GeneralUsedProductResDto;
import com.green.sahwang.usedproduct.entity.UsedProduct;
import com.green.sahwang.usedproduct.repository.UsedProductRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final String MEMBER_PREFIX = "member:";

    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final OutboxRepository outboxRepository;

    private final PaymentServiceHelper paymentServiceHelper;
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final PurchasePaymentRepository purchasePaymentRepository;
    private final SalePaymentRepository salePaymentRepository;

    private final ProductRepository productRepository;
    private final UsedProductRepository usedProductRepository;

    private final ExternalPostPaymentApiRepository externalPostPaymentApiRepository;
    private final ProductServiceImpl productService;

    private IamportClient paymentExternalApi;
    private final ExternalPrePaymentApiRepository prePaymentApiRepository;

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.apiSecret}")
    private String apiSecret;

    @PostConstruct
    public void init() {
        this.paymentExternalApi = new IamportClient(apiKey, apiSecret);
    }

    @Override
    @Transactional
    public void prepareForPost(ExternalPrePaymentReqDto externalPrePaymentReqDto) throws IamportResponseException, IOException {
        PrepareData prepareData =
                new PrepareData(externalPrePaymentReqDto.getMerchantUid(), externalPrePaymentReqDto.getAmount());

        paymentExternalApi.postPrepare(prepareData); // 사전 등록 API

        PrePaymentEntity prePaymentEntity = PrePaymentEntity.builder()
                .merchantUid(externalPrePaymentReqDto.getMerchantUid())
                .amount(externalPrePaymentReqDto.getAmount())
                .purchaseId(externalPrePaymentReqDto.getPurchaseId())
                .build();

        prePaymentApiRepository.save(prePaymentEntity);
    }

    @Override
    @Transactional
    public com.siot.IamportRestClient.response.Payment postValidateOrder(PaymentCompleteRequest paymentCompleteRequest) throws IamportResponseException, IOException {
        PrePaymentEntity prePayment = prePaymentApiRepository.findByMerchantUid(paymentCompleteRequest.getMerchantUid())
                .orElseThrow(() -> new PurchaseDomainException("외부결제 ID가 존재하지 않습니다"));
        BigDecimal preAmount = prePayment.getAmount(); // DB에 저장된 data

        IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse =
                paymentExternalApi.paymentByImpUid(paymentCompleteRequest.getImpUid());

        BigDecimal paidAmount = iamportResponse.getResponse().getAmount(); // 실제 사용자가 결제한 금액

        log.info("preAmount : {}, paidAmount : {}", preAmount, paidAmount);
        // 데이터 포맷에 따른 오류가 발생 가능
        if (preAmount.intValue() != paidAmount.intValue()) {
            log.info("cancel payment in external payment API");
            CancelData cancelData = cancelPayment(iamportResponse);
            paymentExternalApi.cancelPaymentByImpUid(cancelData);
        }

        return iamportResponse.getResponse();
    }

    @Override
    @Transactional
    public void saveUserInfoForPayment(ExternalPaymentReqDto externalPaymentReqDto, String email) {

        log.info("externalPaymentReqDto {}", externalPaymentReqDto.toString());
//        기존 회원과 구매자의 배송지는 다를 수 있음
        Member member = memberRepository.findByEmail(email);

        Payment payment = Payment.builder()
                .member(member)
                .status(com.green.sahwang.entity.enumtype.PaymentStatus.valueOf(externalPaymentReqDto.getStatus().toUpperCase()))
                .systemLogicType(SystemLogicType.PURCHASE)
                .paymentType(PaymentType.CREDIT_CARD)
                .embPgProvider(externalPaymentReqDto.getEmbPgProvider())
                .paidAt(externalPaymentReqDto.getPaidAt())
                .impUid(externalPaymentReqDto.getImpUid())
                .pgProvider(externalPaymentReqDto.getPgProvider())
                .payMethod(externalPaymentReqDto.getPayMethod())
                .totalPrice(externalPaymentReqDto.getAmount().intValue())
                .merchantUid(externalPaymentReqDto.getMerchantUid())
                .build();

        paymentRepository.save(payment);
    }

    /**
     * 결제 완료 시 시스템 내 purchasePayment 저장 후
     * 카프카 이벤트 전송
     *
     * @param externalPurchasePaymentReqDto
     * @param email
     */
    @Override
    @Transactional
    public void savePurchaseInfoForPayment(ExternalPurchasePaymentReqDto externalPurchasePaymentReqDto, String email) {
        log.info("externalPurchasePaymentReqDto : {}", externalPurchasePaymentReqDto.toString());

        Purchase resPurchase = purchaseRepository.findById(externalPurchasePaymentReqDto.getPurchaseId())
                .orElseThrow(() -> new BizException(ErrorCode.NO_PURCHASE));
        List<PurchaseProduct> purchaseProducts = purchaseProductRepository.findAllByPurchase(resPurchase);

        Payment payment = paymentRepository.findByImpUid(externalPurchasePaymentReqDto.getImpUid())
                .orElseThrow(() -> new PaymentDomainException("해당 " + externalPurchasePaymentReqDto.getImpUid()
                        + " 외부결제가 존재하지 않습니다."));

        Purchase purchase = null;
        for (PurchaseProduct purchaseProduct : purchaseProducts) {
            PurchasePayment purchasePayment = PurchasePayment.builder()
                    .purchaseProduct(purchaseProduct)
                    .payment(payment)
                    .tradePrice(purchaseProduct.getUsedProduct().getVerifiedSale().getPendingSale().getExceptedSellingPrice())
                    .tradeSize(purchaseProduct.getUsedProduct().getVerifiedSale().getProductSize())
                    .createdDate(payment.getPaidAt())
                    .build();
            purchasePaymentRepository.save(purchasePayment);

            SalePayment salePayment = SalePayment.builder()
                    .usedProduct(purchaseProduct.getUsedProduct())
                    .payment(payment)
                    .originalPrice(purchasePayment.getTradePrice())
                    .finalPrice((int) (purchasePayment.getTradePrice() - (purchasePayment.getTradePrice() * 0.05)))
                    .createdDate(payment.getPaidAt())
                    .build();
            salePaymentRepository.save(salePayment);

            purchase = purchaseProduct.getPurchase();
        }
        PostPaymentEntity postPaymentEntity = PostPaymentEntity.builder()
                .purchaseId(purchase.getId())
                .buyerEmail(externalPurchasePaymentReqDto.getBuyerEmail())
                .buyerName(externalPurchasePaymentReqDto.getBuyerName())
                .buyerAddr(externalPurchasePaymentReqDto.getBuyerAddr())
                .buyerPostcode(externalPurchasePaymentReqDto.getBuyerPostcode())
                .buyerPhone(externalPurchasePaymentReqDto.getBuyerPhone())
                .merchantUid(externalPurchasePaymentReqDto.getMerchantUId())
                .impUid(externalPurchasePaymentReqDto.getImpUid())
                .payMethod(externalPurchasePaymentReqDto.getPayMethod())
                .amount(externalPurchasePaymentReqDto.getAmount())
                .build();

        externalPostPaymentApiRepository.save(postPaymentEntity);

        // 결제 완료
        purchase.setPurchaseStatus(PurchaseStatus.PAID);
        purchaseRepository.save(purchase);
        // 이벤트 준비
        createPaymentPaidOutboxMessage(purchase, payment, externalPurchasePaymentReqDto);
    }

    @Override
    public BuyerInfoResDto getBuyerInfo(String email) {
        Member member = memberRepository.findByEmail(email);

        if (Objects.isNull(member)) {
            throw new BizException(ErrorCode.NO_MEMBER);
        }

        return BuyerInfoResDto.builder()
                .buyerEmail(member.getEmail())
                .buyerName(member.getName())
                .buyerTel(member.getPhoneNum())
                .buyerAddress(Address.builder()
                        .addr(member.getAddress().getAddr())
                        .postCode(member.getAddress().getPostCode())
                        .build())
                .build();
    }

    @Override
    @Transactional
    public List<CartProductPurchaseReadyResDto> getCartProductInfo(List<CartProductPurchaseReadyReqDto>
                                                                           cartProductPurchaseReadyReqDtos) {

        return cartProductPurchaseReadyReqDtos.stream()
                .map(cartProductPurchaseReadyReqDto -> {
                    UsedProduct usedProduct = usedProductRepository.findById(cartProductPurchaseReadyReqDto.getUsedProductId())
                            .orElseThrow(() -> new BizException(ErrorCode.NO_USED_PRODUCT));

                    return CartProductPurchaseReadyResDto.builder()
                            .usedProductId(usedProduct.getId())
                            .productName(usedProduct.getVerifiedSale().getProductName())
                            .price(usedProduct.getVerifiedSale().getPendingSale().getExceptedSellingPrice() * cartProductPurchaseReadyReqDto.getQuantity())
                            .size(usedProduct.getVerifiedSale().getPendingSale().getProductSize())
                            .content(usedProduct.getVerifiedSale().getPendingSale().getProduct().getContent())
                            .mainImage(usedProduct.getVerifiedSale().getPendingSale().getUserSaleImages() != null ?
                                    usedProduct.getVerifiedSale().getPendingSale().getUserSaleImages().get(0).getFilename() : null)
                            .quantity(cartProductPurchaseReadyReqDto.getQuantity())
                            .build();
                }).toList();
    }

    @Override
    @Transactional
    public String cancelPayment(CancelPaymentReqDto cancelPaymentReqDto) {
        try {
            // CancelData 생성 (imp_uid와 취소 사유)
            CancelData cancelData = new CancelData(cancelPaymentReqDto.getImpUid(), true);
            cancelData.setReason(cancelPaymentReqDto.getReason());

            // 결제 취소 API 호출
            IamportResponse<com.siot.IamportRestClient.response.Payment> cancelResponse =
                    paymentExternalApi.cancelPaymentByImpUid(cancelData);

            if (cancelResponse.getResponse() != null) {
                log.info("결제 취소 성공: imp_uid = {}", cancelPaymentReqDto.getImpUid());
                return "success";
            } else {
                log.error("결제 취소 실패: {}", cancelResponse.getMessage());
                return "fail";
            }
        } catch (Exception e) {
            log.error("결제 취소 중 오류 발생", e);
            return "error";
        }
    }


    private CancelData cancelPayment(IamportResponse<com.siot.IamportRestClient.response.Payment>
                                       response) {
        return new CancelData(response.getResponse().getImpUid(), true);
    }

    @Transactional
    private void createPaymentPaidOutboxMessage(Purchase purchase, Payment payment,
                                                  ExternalPurchasePaymentReqDto externalPurchasePaymentReqDto) {
        log.info("created purchaseId : {}", purchase.getId());
        log.info("purchase : {}", purchase.getPurchaseStatus());

        payment.setStatus(com.green.sahwang.entity.enumtype.PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        PurchasePaidEventAvroModel purchasePaidEventAvroModel = PurchasePaidEventAvroModel.newBuilder()
                .setPurchaseId(String.valueOf(purchase.getId()))
                .setMemberId(MEMBER_PREFIX + purchase.getMember().getId())
                .setPaymentAvroStatus(PaymentAvroStatus.valueOf(payment.getStatus().toString().toUpperCase()))
                .setPaymentAvroMethod(PaymentAvroMethod.CREDIT_CARD)
                .setTransactionId(payment.getImpUid())
                .setTimestamp(System.currentTimeMillis())
                .setAmount(payment.getTotalPrice())
                .setShippingAddress(externalPurchasePaymentReqDto.getBuyerPostcode()
                + " " + externalPurchasePaymentReqDto.getBuyerAddr())
                .build();

        log.info("purchasePaidEventAvroModel : {}", purchasePaidEventAvroModel.toString());

        OutboxMessage outboxMessage;
        try {
            outboxMessage = OutboxMessage.builder()
//                    .aggregateId(PURCHASE_PREFIX + purchase.getId())
                    .aggregateId(MEMBER_PREFIX + purchase.getMember().getId())
                    .avroModel(purchasePaidEventAvroModel.getClass().getName())
                    .eventType("Payment")
                    .topicName("payment-paid")
                    .payload(AvroToDBSerializer.serialize(purchasePaidEventAvroModel))
                    .sequenceNumber(System.currentTimeMillis())
                    .status(OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            throw new OutboxSerializeEventException("Failed to serialize outboxMessage: " + e.getMessage());
        }

        outboxRepository.save(outboxMessage);
        log.info("outboxMessage = {}", outboxMessage);
    }
    @Override
    @Transactional
    public BuyerResDto getPaymentDetails(String merchantUid) {
        PostPaymentEntity postPaymentEntity = externalPostPaymentApiRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new BizException(ErrorCode.NO_ACCEPT_PAYMENT));

        List<PurchaseProduct> purchaseProducts = purchaseProductRepository.findAllByPurchase(purchaseRepository.findById(postPaymentEntity.getPurchaseId())
                .orElseThrow(() -> new BizException(ErrorCode.NO_PURCHASE)));

        return BuyerResDto.builder()
                .purchaseId(postPaymentEntity.getPurchaseId())
                .buyerEmail(postPaymentEntity.getBuyerEmail())
                .buyerName(postPaymentEntity.getBuyerName())
                .buyerAddr(postPaymentEntity.getBuyerAddr())
                .buyerPostcode(postPaymentEntity.getBuyerPostcode())
                .buyerPhone(postPaymentEntity.getBuyerPhone())
                .payMethod(postPaymentEntity.getPayMethod())
                .amount(postPaymentEntity.getAmount().intValue())
                .usedProductResDtos(purchaseProducts
                        .stream()
                        .map(purchaseProduct -> productService.getUsedProductResDto(purchaseProduct.getUsedProduct()))
                        .toList())
                .build();
    }
}
