package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.dto.*;

import javax.servlet.http.HttpServletRequest;

public interface ProcessService {
    AuthorizationResponse getAuthorization(String clientId, String clientSecret, String grantType);
    PaymentResponse getPayment(String reference, String country, String currency, String msisdn, String amount, String id, String token);
    RefundResponse getRefund(String airtelMoneyId, String country, String currency, String token);
    EnquiryResponse getEnquiry(String partnerId, String country, String currency, String token);
    ResponsePaymentCallback getCallbackPayment(HttpServletRequest request);
    DisbursementResponse getDisbursement(String reference, String country, String currency, String pin, String msisdn, String amount, String id, String token);
}
