package com.infosetgroup.amv2.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String callBackChannel;

    @Column(name = "call_back_channel_b2b")
    private String callBackChannelB2B;

    @Column(name = "call_back_channel_b2c")
    private String callBackChannelB2C;

    @Column(name = "call_back_channel_bank_to_wallet")
    private String callBackChannelBankToWallet;

    private String callBackDestination;

    @Column(name = "call_back_destination_b2b")
    private String callBackDestinationB2B;

    @Column(name = "call_back_destination_b2c")
    private String callBackDestinationB2C;

    @Column(name = "call_back_destination_bank_to_wallet")
    private String callBackDestinationBankToWallet;

    @Column(name = "call_back_destination_om")
    private String callBackDestinationOm;

    @Column(name = "call_back_destination_om_b2b")
    private String callBackDestinationOmB2B;

    @Column(name = "call_back_destination_om_b2c")
    private String callBackDestinationOmB2C;

    @Column(name = "call_back_success_url_equity")
    private String callBackSuccessUrlEquity;

    @Column(name = "call_back_cancel_url_equity")
    private String callBackCancelUrlEquity;

    @Column(name = "call_back_decline_url_equity")
    private String callBackDeclineUrlEquity;

    private String apiMpesaLoginC2b;
    private String apiMpesaLoginB2b;
    private String apiMpesaLoginB2c;
    private String apiMpesaLoginBankToWallet;
    private String apiMpesaRequestC2b;
    private String apiMpesaRequestB2b;
    private String apiMpesaRequestB2c;
    private String apiMpesaRequestBankToWallet;
    private String apiOmRequest;
    private String apiAiRequest;
    private String callBackChannelAiC2B;
    private String apiRequestFlocash;
    private String apiCallbackFlocash;
    private String apiCallbackCancelFlocash;
    private String apiCallbackNotifFlocash;

    private String urlPepeleSession;
    private String urlPepeleSessionMobile;
    private String urlPepeleBack;
    private String urlPepeleBackMobile;

    //AfriMoney
    private String apiAfriMoneyToken;
    private String apiAfriMoneyCashIn;
    private String apiAfriMoneyCashOut;
    private String apiAfriMoneyEnquiry;
    private String apiAfriMoneyFtxId;

    private boolean enabled;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;


    //RawBank
    private String apiRawbankUrl;

    // Check transaction URL
    private String checkTransactionMpesa;
    private String checkTransactionOm;
    private String checkTransactionAirtel;
    private String checkTransactionAfri;

    // Login mpesa check transaction
    private String checkMpesaUsername;
    private String checkMpesaPassword;

    // Refund airtel url
    private String refundAirtelLink;


    public Configuration() {
        this.enabled = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallBackChannel() {
        return callBackChannel;
    }

    public void setCallBackChannel(String callBackChannel) {
        this.callBackChannel = callBackChannel;
    }

    public String getCallBackDestination() {
        return callBackDestination;
    }

    public void setCallBackDestination(String callBackDestination) {
        this.callBackDestination = callBackDestination;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getCallBackChannelB2B() {
        return callBackChannelB2B;
    }

    public void setCallBackChannelB2B(String callBackChannelB2B) {
        this.callBackChannelB2B = callBackChannelB2B;
    }

    public String getCallBackDestinationB2B() {
        return callBackDestinationB2B;
    }

    public void setCallBackDestinationB2B(String callBackDestinationB2B) {
        this.callBackDestinationB2B = callBackDestinationB2B;
    }

    public String getCallBackDestinationOm() {
        return callBackDestinationOm;
    }

    public void setCallBackDestinationOm(String callBackDestinationOm) {
        this.callBackDestinationOm = callBackDestinationOm;
    }

    public String getCallBackSuccessUrlEquity() {
        return callBackSuccessUrlEquity;
    }

    public void setCallBackSuccessUrlEquity(String callBackSuccessUrlEquity) {
        this.callBackSuccessUrlEquity = callBackSuccessUrlEquity;
    }

    public String getCallBackCancelUrlEquity() {
        return callBackCancelUrlEquity;
    }

    public void setCallBackCancelUrlEquity(String callBackCancelUrlEquity) {
        this.callBackCancelUrlEquity = callBackCancelUrlEquity;
    }

    public String getCallBackDeclineUrlEquity() {
        return callBackDeclineUrlEquity;
    }

    public void setCallBackDeclineUrlEquity(String callBackDeclineUrlEquity) {
        this.callBackDeclineUrlEquity = callBackDeclineUrlEquity;
    }

    public String getCallBackDestinationOmB2B() {
        return callBackDestinationOmB2B;
    }

    public void setCallBackDestinationOmB2B(String callBackDestinationOmB2B) {
        this.callBackDestinationOmB2B = callBackDestinationOmB2B;
    }

    public String getCallBackDestinationOmB2C() {
        return callBackDestinationOmB2C;
    }

    public void setCallBackDestinationOmB2C(String callBackDestinationOmB2C) {
        this.callBackDestinationOmB2C = callBackDestinationOmB2C;
    }

    public String getApiMpesaLoginC2b() {
        return apiMpesaLoginC2b;
    }

    public void setApiMpesaLoginC2b(String apiMpesaLoginC2b) {
        this.apiMpesaLoginC2b = apiMpesaLoginC2b;
    }

    public String getApiMpesaLoginB2b() {
        return apiMpesaLoginB2b;
    }

    public void setApiMpesaLoginB2b(String apiMpesaLoginB2b) {
        this.apiMpesaLoginB2b = apiMpesaLoginB2b;
    }

    public String getApiMpesaRequestC2b() {
        return apiMpesaRequestC2b;
    }

    public void setApiMpesaRequestC2b(String apiMpesaRequestC2b) {
        this.apiMpesaRequestC2b = apiMpesaRequestC2b;
    }

    public String getApiMpesaRequestB2b() {
        return apiMpesaRequestB2b;
    }

    public void setApiMpesaRequestB2b(String apiMpesaRequestB2b) {
        this.apiMpesaRequestB2b = apiMpesaRequestB2b;
    }

    public String getApiOmRequest() {
        return apiOmRequest;
    }

    public void setApiOmRequest(String apiOmRequest) {
        this.apiOmRequest = apiOmRequest;
    }

    public String getApiAiRequest() {
        return apiAiRequest;
    }

    public void setApiAiRequest(String apiAiRequest) {
        this.apiAiRequest = apiAiRequest;
    }

    public String getCallBackChannelAiC2B() {
        return callBackChannelAiC2B;
    }

    public void setCallBackChannelAiC2B(String callBackChannelAiC2B) {
        this.callBackChannelAiC2B = callBackChannelAiC2B;
    }

    public String getApiRequestFlocash() {
        return apiRequestFlocash;
    }

    public void setApiRequestFlocash(String apiRequestFlocash) {
        this.apiRequestFlocash = apiRequestFlocash;
    }

    public String getApiCallbackFlocash() {
        return apiCallbackFlocash;
    }

    public void setApiCallbackFlocash(String apiCallbackFlocash) {
        this.apiCallbackFlocash = apiCallbackFlocash;
    }

    public String getCallBackChannelB2C() {
        return callBackChannelB2C;
    }

    public void setCallBackChannelB2C(String callBackChannelB2C) {
        this.callBackChannelB2C = callBackChannelB2C;
    }

    public String getCallBackDestinationB2C() {
        return callBackDestinationB2C;
    }

    public void setCallBackDestinationB2C(String callBackDestinationB2C) {
        this.callBackDestinationB2C = callBackDestinationB2C;
    }

    public String getApiMpesaLoginB2c() {
        return apiMpesaLoginB2c;
    }

    public void setApiMpesaLoginB2c(String apiMpesaLoginB2c) {
        this.apiMpesaLoginB2c = apiMpesaLoginB2c;
    }

    public String getApiMpesaRequestB2c() {
        return apiMpesaRequestB2c;
    }

    public void setApiMpesaRequestB2c(String apiMpesaRequestB2c) {
        this.apiMpesaRequestB2c = apiMpesaRequestB2c;
    }

    public String getApiCallbackCancelFlocash() {
        return apiCallbackCancelFlocash;
    }

    public void setApiCallbackCancelFlocash(String apiCallbackCancelFlocash) {
        this.apiCallbackCancelFlocash = apiCallbackCancelFlocash;
    }

    public String getApiCallbackNotifFlocash() {
        return apiCallbackNotifFlocash;
    }

    public void setApiCallbackNotifFlocash(String apiCallbackNotifFlocash) {
        this.apiCallbackNotifFlocash = apiCallbackNotifFlocash;
    }

    public String getUrlPepeleSession() {
        return urlPepeleSession;
    }

    public void setUrlPepeleSession(String urlPepeleSession) {
        this.urlPepeleSession = urlPepeleSession;
    }

    public String getUrlPepeleBack() {
        return urlPepeleBack;
    }

    public void setUrlPepeleBack(String urlPepeleBack) {
        this.urlPepeleBack = urlPepeleBack;
    }

    public String getCallBackDestinationBankToWallet() {
        return callBackDestinationBankToWallet;
    }

    public void setCallBackDestinationBankToWallet(String callBackDestinationBankToWallet) {
        this.callBackDestinationBankToWallet = callBackDestinationBankToWallet;
    }

    public String getApiMpesaLoginBankToWallet() {
        return apiMpesaLoginBankToWallet;
    }

    public void setApiMpesaLoginBankToWallet(String apiMpesaLoginBankToWallet) {
        this.apiMpesaLoginBankToWallet = apiMpesaLoginBankToWallet;
    }

    public String getApiMpesaRequestBankToWallet() {
        return apiMpesaRequestBankToWallet;
    }

    public void setApiMpesaRequestBankToWallet(String apiMpesaRequestBankToWallet) {
        this.apiMpesaRequestBankToWallet = apiMpesaRequestBankToWallet;
    }

    public String getCallBackChannelBankToWallet() {
        return callBackChannelBankToWallet;
    }

    public void setCallBackChannelBankToWallet(String callBackChannelBankToWallet) {
        this.callBackChannelBankToWallet = callBackChannelBankToWallet;
    }

    public String getApiAfriMoneyToken() {
        return apiAfriMoneyToken;
    }

    public void setApiAfriMoneyToken(String apiAfriMoneyToken) {
        this.apiAfriMoneyToken = apiAfriMoneyToken;
    }

    public String getApiAfriMoneyCashIn() {
        return apiAfriMoneyCashIn;
    }

    public void setApiAfriMoneyCashIn(String apiAfriMoneyCashIn) {
        this.apiAfriMoneyCashIn = apiAfriMoneyCashIn;
    }

    public String getApiAfriMoneyCashOut() {
        return apiAfriMoneyCashOut;
    }

    public void setApiAfriMoneyCashOut(String apiAfriMoneyCashOut) {
        this.apiAfriMoneyCashOut = apiAfriMoneyCashOut;
    }

    public String getApiAfriMoneyEnquiry() {
        return apiAfriMoneyEnquiry;
    }

    public void setApiAfriMoneyEnquiry(String apiAfriMoneyEnquiry) {
        this.apiAfriMoneyEnquiry = apiAfriMoneyEnquiry;
    }

    public String getApiAfriMoneyFtxId() {
        return apiAfriMoneyFtxId;
    }

    public void setApiAfriMoneyFtxId(String apiAfriMoneyFtxId) {
        this.apiAfriMoneyFtxId = apiAfriMoneyFtxId;
    }

    public String getApiRawbankUrl() {
        return apiRawbankUrl;
    }

    public void setApiRawbankUrl(String apiRawbankUrl) {
        this.apiRawbankUrl = apiRawbankUrl;
    }

    public String getUrlPepeleSessionMobile() {
        return urlPepeleSessionMobile;
    }

    public void setUrlPepeleSessionMobile(String urlPepeleSessionMobile) {
        this.urlPepeleSessionMobile = urlPepeleSessionMobile;
    }

    public String getUrlPepeleBackMobile() {
        return urlPepeleBackMobile;
    }

    public void setUrlPepeleBackMobile(String urlPepeleBackMobile) {
        this.urlPepeleBackMobile = urlPepeleBackMobile;
    }

    public String getCheckTransactionMpesa() {
        return checkTransactionMpesa;
    }

    public void setCheckTransactionMpesa(String checkTransactionMpesa) {
        this.checkTransactionMpesa = checkTransactionMpesa;
    }

    public String getCheckTransactionOm() {
        return checkTransactionOm;
    }

    public void setCheckTransactionOm(String checkTransactionOm) {
        this.checkTransactionOm = checkTransactionOm;
    }

    public String getCheckTransactionAirtel() {
        return checkTransactionAirtel;
    }

    public void setCheckTransactionAirtel(String checkTransactionAirtel) {
        this.checkTransactionAirtel = checkTransactionAirtel;
    }

    public String getCheckTransactionAfri() {
        return checkTransactionAfri;
    }

    public void setCheckTransactionAfri(String checkTransactionAfri) {
        this.checkTransactionAfri = checkTransactionAfri;
    }

    public String getCheckMpesaUsername() {
        return checkMpesaUsername;
    }

    public void setCheckMpesaUsername(String checkMpesaUsername) {
        this.checkMpesaUsername = checkMpesaUsername;
    }

    public String getCheckMpesaPassword() {
        return checkMpesaPassword;
    }

    public void setCheckMpesaPassword(String checkMpesaPassword) {
        this.checkMpesaPassword = checkMpesaPassword;
    }

    public String getRefundAirtelLink() {
        return refundAirtelLink;
    }

    public void setRefundAirtelLink(String refundAirtelLink) {
        this.refundAirtelLink = refundAirtelLink;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setModified(LocalDateTime.now());
    }
}
