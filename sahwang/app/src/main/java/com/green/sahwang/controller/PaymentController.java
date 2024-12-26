package com.green.sahwang.controller;

import com.green.sahwang.dto.request.CartProductPurchaseReadyReqDto;
import com.green.sahwang.dto.request.PaymentCompleteRequest;
import com.green.sahwang.dto.request.externalapi.ExternalPaymentReqDto;
import com.green.sahwang.dto.request.externalapi.ExternalPurchasePaymentReqDto;
import com.green.sahwang.dto.response.BuyerInfoResDto;
import com.green.sahwang.dto.response.CartProductPurchaseReadyResDto;
import com.green.sahwang.entity.externalapi.ExternalPrePaymentReqDto;
import com.green.sahwang.service.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "구매자 정보 조회", description = "결제 전후로 구매자 정보를 보여주기 위함")
    @GetMapping("buyer-info")
    public ResponseEntity<BuyerInfoResDto> getBuyerInfo(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(paymentService.getBuyerInfo(userDetails.getUsername()));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "카트상품 정보 조회", description = "카트에 담긴 상품ID를 받아서 재검증 후 제품정보 반환")
    @PostMapping("cart-product-info")
    public ResponseEntity<List<CartProductPurchaseReadyResDto>> getCartProductInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody List<CartProductPurchaseReadyReqDto> cartProductPurchaseReadyReqDtos) {
        if (userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(paymentService.getCartProductInfo(cartProductPurchaseReadyReqDtos));
    }

    @PostMapping("/prepare")
    @Operation(summary = "사전검증", description = "외부결제 API와 연관된 사전검증")
    public void preparePayment(@RequestBody ExternalPrePaymentReqDto externalPrePaymentReqDto)
            throws IamportResponseException, IOException {

        log.info("ExternalPrePaymentReqDto : {}", externalPrePaymentReqDto);
        paymentService.prepareForPost(externalPrePaymentReqDto);
    }

    @PostMapping("/validate")
    @Operation(summary = "사후검증", description = "외부결제 API와 연관된 사후검증")
    public ResponseEntity<Payment> validatePayment(@RequestBody PaymentCompleteRequest paymentCompleteRequest) throws IamportResponseException, IOException {
        log.info("paymentCompleteRequest = {}", paymentCompleteRequest.toString());
        Payment payment = paymentService.postValidateOrder(paymentCompleteRequest);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/save-user-info")
    @Operation(summary = "결제 후 구매자 정보 저장", description = "사후검증 후 사용자 정보 저장")
    public ResponseEntity<String> saveUserInfo(@RequestBody ExternalPaymentReqDto externalPaymentReqDto,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        paymentService.saveUserInfoForPayment(externalPaymentReqDto, userDetails.getUsername());
        return ResponseEntity.ok("success save user info");
    }

    @PostMapping("/save-purchase-info")
    @Operation(summary = "결제 후 구매상품 정보 저장", description = "사후검증 후 구매상품(결제) 정보 저장")
    public ResponseEntity<String> savePurchaseInfo(@RequestBody ExternalPurchasePaymentReqDto externalPurchasePaymentReqDto,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        paymentService.savePurchaseInfoForPayment(externalPurchasePaymentReqDto, userDetails.getUsername());
        return ResponseEntity.ok(externalPurchasePaymentReqDto.getMerchantUId());
    }
}
