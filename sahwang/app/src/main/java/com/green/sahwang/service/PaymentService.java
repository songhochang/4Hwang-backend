package com.green.sahwang.service;

import com.green.sahwang.dto.request.CartProductPurchaseReadyReqDto;
import com.green.sahwang.dto.request.PaymentCompleteRequest;
import com.green.sahwang.dto.request.externalapi.ExternalPaymentReqDto;
import com.green.sahwang.dto.request.externalapi.ExternalPurchasePaymentReqDto;
import com.green.sahwang.dto.response.BuyerInfoResDto;
import com.green.sahwang.dto.response.CartProductPurchaseReadyResDto;
import com.green.sahwang.entity.externalapi.ExternalPrePaymentReqDto;
import com.green.sahwang.model.purchase.avro.PurchaseCreatedEventAvroModel;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;

import java.io.IOException;
import java.util.List;

public interface PaymentService {

//    void processPayment(List<String> purchaseIds, List<PurchaseCreatedEventAvroModel> messages);
//
//    void processAsyncPayment(PaymentCompleteRequest paymentCompleteRequest);

//    void postValidateOrder(List<String> purchaseIds, List<PurchaseCreatedEventAvroModel> messages);

    void prepareForPost(ExternalPrePaymentReqDto externalPrePaymentReqDto) throws IamportResponseException, IOException;

    Payment postValidateOrder(PaymentCompleteRequest paymentCompleteRequest) throws IamportResponseException, IOException;

    void saveUserInfoForPayment(ExternalPaymentReqDto externalPaymentReqDto, String email);

    void savePurchaseInfoForPayment(ExternalPurchasePaymentReqDto buyerReqDto, String email);

    BuyerInfoResDto getBuyerInfo(String email);

    List<CartProductPurchaseReadyResDto> getCartProductInfo(List<CartProductPurchaseReadyReqDto> cartProductPurchaseReadyReqDtos);
}
