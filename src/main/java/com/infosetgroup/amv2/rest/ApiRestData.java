package com.infosetgroup.amv2.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosetgroup.amv2.dto.*;
import com.infosetgroup.amv2.entity.*;
import com.infosetgroup.amv2.repository.LoginRepository;
import com.infosetgroup.amv2.service.*;
import com.infosetgroup.amv2.util.C2BUtil;
import com.infosetgroup.amv2.util.RSAUtil;
import com.infosetgroup.amv2.util.SslUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/rest")
public class ApiRestData {

    @Autowired
    private ProcessService processService;

    @Autowired
    private TransactionAiService transactionAiService;

    @Autowired
    private TransactionAiRequestService transactionAiRequestService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private TransactionAiResponseService transactionAiResponseService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private MerchantAiService merchantAiService;

    @Autowired
    private LogService logService;

    @Autowired
    private NotificationAiService notificationAiService;

    @Autowired
    private MerchantNotificationAiService merchantNotificationAiService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private LoginRequestService loginRequestService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private LoginResponseService loginResponseService;

    @Autowired
    private SalesRequestService salesRequestService;

    @Autowired
    private SalesResponseService salesResponseService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MerchantNotificationService merchantNotificationService;

    @Autowired
    private LoginRepository loginRepository;

    @PostMapping(value = "/v1/b2b", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<MessageResponse> apiB2BV1(
            @RequestParam(value = "Currency", required = false) String Currency,
            @RequestParam(value = "Amount", required = false) String Amount,
            @RequestParam(value = "Reference", required = false) String Reference,
            @RequestParam(value = "AppLogin", required = false) String AppLogin,
            @RequestParam(value = "MerchantName", required = false) String MerchantName,
            @RequestParam(value = "PaymentDesc", required = false) String PaymentDesc,
            @RequestParam(value = "AppCallBackURL", required = false) String AppCallBackURL,
            @RequestParam(value = "AppPassword", required = false) String AppPassword,
            @RequestParam(value = "ShortCode", required = false) String ShortCode,
            @RequestParam(value = "BankShortCode", required = false) String BankShortCode,
            HttpServletRequest httpServletRequest
    )
    {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("/api/mpesa/b2b : " + formatter.format(now));

        String ipClient = httpServletRequest.getRemoteAddr();
        Map<String, String> map = C2BUtil.getHeadersInfo(httpServletRequest);
        String others = "User-Agent: " + map.get("User-Agent") + " Host: " + map.get("Host") + " X-Forwarded-For: " + map.get("X-Forwarded-For");

        Log log = new Log();
        log.setClientIp(ipClient);
        log.setLevel(1);
        log.setDescription("Client request for API");
        log.setOthers(others);
        logService.saveOrUpdate(log);


        MessageResponse object = new MessageResponse();
        if(BankShortCode.isEmpty()){
            System.out.println("BankShortCode is empty");
            object.setCode("09");
            object.setMessage("BankShortCode is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(Currency.isEmpty()){
            System.out.println("Currency is empty");
            object.setCode("09");
            object.setMessage("Currency is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(Amount.isEmpty()){
            System.out.println("Amount is empty");
            object.setCode("09");
            object.setMessage("Amount is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(Reference.isEmpty()){
            System.out.println("Reference is empty");
            object.setCode("09");
            object.setMessage("Currency is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }


        if(AppLogin.isEmpty()){
            System.out.println("AppLogin is empty");
            object.setCode("09");
            object.setMessage("AppLogin is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(MerchantName.isEmpty()){
            System.out.println("Merchant name is empty");
            object.setCode("09");
            object.setMessage("Merchant name is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(PaymentDesc.isEmpty()){
            System.out.println("Payment description is null");
            object.setCode("09");
            object.setMessage("Currency is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(AppCallBackURL.isEmpty()){
            System.out.println("Callback url is empty");
            object.setCode("09");
            object.setMessage("AppCallBackURL is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(AppPassword.isEmpty()){
            object.setCode("09");
            object.setMessage("AppPassword is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }

        if(ShortCode.isEmpty()){
            object.setCode("09");
            object.setMessage("ShortCode is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }

        //Find merchant
        //Merchant merchant = merchantService.getByShortCode(ShortCode);
        Merchant merchant = merchantService.getByCode(ShortCode);
        if (merchant == null) {
            object.setCode("09");
            object.setMessage("Short code merchant not found");
            object.setOrderNumber("");
            System.out.println("Short code merchant not found");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }

        //Application
        Application application = applicationService.getByCodeAndPassword(AppLogin, AppPassword);
        if (application == null) {
            object.setCode("09");
            object.setMessage("Application not found");
            return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
        }

        //Login
        //Login login = merchant.getLogin();
        Login login = this.loginRepository.findByName(ShortCode);
        if (login == null) {
            object.setCode("09");
            object.setMessage("Login not performed. Contact admin");
            System.out.println("Error merchant login, login not performed. Contact the admin " );
            return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
        }

        //Request
        String[] cdList = UUID.randomUUID().toString().split("-");
        int myNumber = new Random().nextInt(11) + 100;

        Sales sale = new Sales();
        sale.setCustomerMsisdn(BankShortCode);
        sale.setCurrency(Currency);
        sale.setAmount(Amount);
        sale.setReference(Reference);
        //sale.setLanguage(Language);
        sale.setLoginApp(AppLogin);
        sale.setMerchantName(MerchantName);
        sale.setDescription(PaymentDesc);
        sale.setCallBackUrl(AppCallBackURL);
        sale.setPasswordApp(AppPassword);
        sale.setShortCode(ShortCode);
        //sale.setProviderId(ProviderID);
        sale.setMerchant(merchant);
        sale.setApplication(application);
        sale.setCodeValue("09");
        sale.setType(2);
        int random = (int)(Math.random() * 50 + 1);
        String[] uniqueID = UUID.randomUUID().toString().split("-");
        String ref = "REQ_" + uniqueID[0] + BankShortCode + RandomStringUtils.randomAlphanumeric(5);
        sale.setCode(ref );
        salesService.saveOrUpdate(sale);

        //Conf
        Configuration configuration = configurationService.findByEnabled();

        //Login Request

        //final String msg = "<?xml version='1.0' encoding='utf-8' ?><Request><Username>"+login.getUsername()+"</Username><Password>"+login.getPassword()+"</Password></Request>";
        final String msg = "<Request><Username>"+login.getUsername()+"</Username><Password>"+login.getPassword()+"</Password></Request>";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSale(sale);
        loginRequest.setUsername(login.getUsername());
        loginRequest.setPassword(login.getPassword());
        loginRequest.setDescription(msg);
        loginRequestService.saveOrUpdate(loginRequest);

        try{
            //invoke business method
            String urlLogin = configuration.getApiMpesaLoginB2b();
            System.out.println("Url : " + urlLogin);
            System.out.println(msg);
            String response = C2BUtil.proccessLoginB2B(msg, urlLogin);
            System.out.println(response);

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(response)));


            String codeResponse = C2BUtil.getResultCode(doc);
            String descResponse = C2BUtil.getResultDesc(doc);
            String detailResponse = C2BUtil.getResultDesc(doc);
            String transIdResponse = C2BUtil.getSessionId(doc);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setCode(codeResponse);
            loginResponse.setTransactionId(transIdResponse);
            loginResponse.setDescription(descResponse);
            loginResponse.setDetail(detailResponse);
            loginResponse.setLoginRequest(loginRequest);
            loginResponse.setSale(sale);
            loginResponse.setDescriptionResponse(response);
            loginResponseService.saveOrUpdate(loginResponse);

            if (codeResponse.equalsIgnoreCase("0")){
                String sessionId = C2BUtil.getSessionId(doc);

                // Payment request

                //Transaction date
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
                String dateString = format.format( new Date()   );
                System.out.println(dateString);

                String shortCode  = merchant.getShortCode();
                String commandId  = merchant.getCommandId();

                final String msgRequest = "<?xml version='1.0' encoding='utf-8' ?><Request><Command>BankB2W</Command><SessionID>"+sessionId+"</SessionID><Shortcode>"+BankShortCode+"</Shortcode><BankShortcode>"+shortCode+"</BankShortcode><Currency>"+Currency+"</Currency><Amount>"+Amount+"</Amount><DateTime>"+dateString+"</DateTime><ThirdPartyReference>"+ref+"</ThirdPartyReference><TransactionID>"+ref+"</TransactionID><CommandID>"+commandId+"</CommandID><TransactionType>VA</TransactionType><CallBackChannel>"+configuration.getCallBackChannelB2B()+"</CallBackChannel><CallBackDestination>"+configuration.getCallBackDestinationB2B()+"</CallBackDestination></Request>";
                System.out.println("*******************************************************************");
                System.out.println(msgRequest);

                SalesRequest salesRequest = new SalesRequest();
                salesRequest.setCallBackChannel(configuration.getCallBackChannelB2B());
                salesRequest.setCallBackDestination(configuration.getCallBackDestinationB2B());
                salesRequest.setShortCode(ShortCode);
                salesRequest.setSale(sale);
                salesRequest.setCodeRequest(ref);
                salesRequest.setCommandId(merchant.getCommandId());
                //salesRequest.setDescription(msgRequest);
                salesRequestService.saveOrUpdate(salesRequest);



                //Process request
                String urlRequest = configuration.getApiMpesaRequestB2b();
                String responseRequest = C2BUtil.proccessRequestB2B(msgRequest, urlRequest);
                System.out.println(responseRequest);
                DocumentBuilder dbrequest = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document docRequest = dbrequest.parse(new InputSource(new StringReader(responseRequest)));

                String descT = C2BUtil.getResonseDesc(docRequest);
                String transT = C2BUtil.getInsightTxID(docRequest);
                String codeT = C2BUtil.getResonseCode(docRequest);
                String dT = C2BUtil.getResonseDesc(docRequest);

                SalesResponse salesResponse = new SalesResponse();
                salesResponse.setCode(codeT);
                salesResponse.setDescription(descT);
                salesResponse.setDetail(dT);
                salesResponse.setTransactionId(transT);
                salesResponse.setSale(sale);
                salesResponse.setSalesRequest(salesRequest);
                //salesResponse.setDescriptionResponse(responseRequest);
                salesResponseService.saveOrUpdate(salesResponse);

                sale.setSalesRequest(salesRequest);
                sale.setSalesResponse(salesResponse);
                sale.setLoginResponse(loginResponse);
                salesService.saveOrUpdate(sale);


                if (codeT.equalsIgnoreCase("0")){
                    System.out.println("Description response : " + descT);
                    System.out.println("Transaction response : " + transT);
                    System.out.println("Code response : " + codeT);
                    System.out.println("Veuillez confirmer sur votre téléphone pour valider le paiement");

                    object.setCode("0");
                    object.setMessage("Veuillez confirmer sur votre téléphone pour valider le paiement");
                    object.setOrderNumber(ref);
                    return new ResponseEntity<>(object, HttpStatus.OK);

                }else {
                    System.out.println("Erreur lors du paiement");

                    sale.setCompleted(true);
                    sale.setErrorDescription("Request error. Status is not 0");
                    salesService.saveOrUpdate(sale);

                    object.setCode("09");
                    object.setMessage("Erreur lors du paiement");
                    object.setOrderNumber(ref);
                    return new ResponseEntity<>(object, HttpStatus.OK);
                }
            }else {
                System.out.println("******************* Login error ********************");

                sale.setCompleted(true);
                sale.setErrorDescription("Login error. Status is not 0");
                salesService.saveOrUpdate(sale);

                object.setCode("09");
                object.setMessage("Error login");
                object.setOrderNumber(ref);
                return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
            }


        }catch (Exception e){
            e.printStackTrace();
            object.setCode("09");
            object.setMessage("Error processing");
            return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);

        }

    }

    @PostMapping(value = "/v2/b2b", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<MessageResponse> apiB2BV2(
            @RequestParam(value = "Currency", required = false) String Currency,
            @RequestParam(value = "Amount", required = false) String Amount,
            @RequestParam(value = "Reference", required = false) String Reference,
            @RequestParam(value = "AppLogin", required = false) String AppLogin,
            @RequestParam(value = "MerchantName", required = false) String MerchantName,
            @RequestParam(value = "PaymentDesc", required = false) String PaymentDesc,
            @RequestParam(value = "AppCallBackURL", required = false) String AppCallBackURL,
            @RequestParam(value = "AppPassword", required = false) String AppPassword,
            @RequestParam(value = "ShortCode", required = false) String ShortCode,
            @RequestParam(value = "BankShortCode", required = false) String BankShortCode,
            HttpServletRequest httpServletRequest
    )
    {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("/api/mpesa/b2b : " + formatter.format(now));

        String ipClient = httpServletRequest.getRemoteAddr();
        Map<String, String> map = C2BUtil.getHeadersInfo(httpServletRequest);
        String others = "User-Agent: " + map.get("User-Agent") + " Host: " + map.get("Host") + " X-Forwarded-For: " + map.get("X-Forwarded-For");

        Log log = new Log();
        log.setClientIp(ipClient);
        log.setLevel(1);
        log.setDescription("Client request for API");
        log.setOthers(others);
        logService.saveOrUpdate(log);


        MessageResponse object = new MessageResponse();
        if(BankShortCode.isEmpty()){
            System.out.println("BankShortCode is empty");
            object.setCode("09");
            object.setMessage("BankShortCode is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(Currency.isEmpty()){
            System.out.println("Currency is empty");
            object.setCode("09");
            object.setMessage("Currency is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(Amount.isEmpty()){
            System.out.println("Amount is empty");
            object.setCode("09");
            object.setMessage("Amount is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(Reference.isEmpty()){
            System.out.println("Reference is empty");
            object.setCode("09");
            object.setMessage("Currency is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }


        if(AppLogin.isEmpty()){
            System.out.println("AppLogin is empty");
            object.setCode("09");
            object.setMessage("AppLogin is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(MerchantName.isEmpty()){
            System.out.println("Merchant name is empty");
            object.setCode("09");
            object.setMessage("Merchant name is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(PaymentDesc.isEmpty()){
            System.out.println("Payment description is null");
            object.setCode("09");
            object.setMessage("Currency is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(AppCallBackURL.isEmpty()){
            System.out.println("Callback url is empty");
            object.setCode("09");
            object.setMessage("AppCallBackURL is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }

        if(AppPassword.isEmpty()){
            object.setCode("09");
            object.setMessage("AppPassword is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }

        if(ShortCode.isEmpty()){
            object.setCode("09");
            object.setMessage("ShortCode is null");
            object.setOrderNumber("");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }

        //Find merchant
        //Merchant merchant = merchantService.getByShortCode(ShortCode);
        Merchant merchant = merchantService.getByCode(ShortCode);
        if (merchant == null) {
            object.setCode("09");
            object.setMessage("Short code merchant not found");
            object.setOrderNumber("");
            System.out.println("Short code merchant not found");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }

        //Application
        Application application = applicationService.getByCodeAndPassword(AppLogin, AppPassword);
        if (application == null) {
            object.setCode("09");
            object.setMessage("Application not found");
            return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
        }

        //Login
        //Login login = merchant.getLogin();
        Login login = this.loginRepository.findByName(ShortCode);
        if (login == null) {
            object.setCode("09");
            object.setMessage("Login not performed. Contact admin");
            System.out.println("Error merchant login, login not performed. Contact the admin " );
            return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
        }

        //Request
        String[] cdList = UUID.randomUUID().toString().split("-");
        int myNumber = new Random().nextInt(11) + 100;

        Sales sale = new Sales();
        sale.setCustomerMsisdn(BankShortCode);
        sale.setCurrency(Currency);
        sale.setAmount(Amount);
        sale.setReference(Reference);
        //sale.setLanguage(Language);
        sale.setLoginApp(AppLogin);
        sale.setMerchantName(MerchantName);
        sale.setDescription(PaymentDesc);
        sale.setCallBackUrl(AppCallBackURL);
        sale.setPasswordApp(AppPassword);
        sale.setShortCode(ShortCode);
        //sale.setProviderId(ProviderID);
        sale.setMerchant(merchant);
        sale.setApplication(application);
        sale.setCodeValue("09");
        sale.setType(2);
        int random = (int)(Math.random() * 50 + 1);
        String[] uniqueID = UUID.randomUUID().toString().split("-");
        String ref = "REQ_" + uniqueID[0] + BankShortCode + RandomStringUtils.randomAlphanumeric(5);
        sale.setCode(ref );
        salesService.saveOrUpdate(sale);

        //Conf
        Configuration configuration = configurationService.findByEnabled();

        //Login Request

        //final String msg = "<?xml version='1.0' encoding='utf-8' ?><Request><Username>"+login.getUsername()+"</Username><Password>"+login.getPassword()+"</Password></Request>";
        final String msg = "<Request><Username>"+login.getUsername()+"</Username><Password>"+login.getPassword()+"</Password></Request>";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSale(sale);
        loginRequest.setUsername(login.getUsername());
        loginRequest.setPassword(login.getPassword());
        loginRequest.setDescription(msg);
        loginRequestService.saveOrUpdate(loginRequest);

        try{
            //invoke business method
            String urlLogin = configuration.getApiMpesaLoginB2b();
            System.out.println("Url : " + urlLogin);
            System.out.println(msg);
            String response = C2BUtil.proccessLoginB2B(msg, urlLogin);
            System.out.println(response);

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(response)));


            String codeResponse = C2BUtil.getResultCode(doc);
            String descResponse = C2BUtil.getResultDesc(doc);
            String detailResponse = C2BUtil.getResultDesc(doc);
            String transIdResponse = C2BUtil.getSessionId(doc);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setCode(codeResponse);
            loginResponse.setTransactionId(transIdResponse);
            loginResponse.setDescription(descResponse);
            loginResponse.setDetail(detailResponse);
            loginResponse.setLoginRequest(loginRequest);
            loginResponse.setSale(sale);
            loginResponse.setDescriptionResponse(response);
            loginResponseService.saveOrUpdate(loginResponse);

            if (codeResponse.equalsIgnoreCase("0")){
                String sessionId = C2BUtil.getSessionId(doc);

                // Payment request

                //Transaction date
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
                String dateString = format.format( new Date()   );
                System.out.println(dateString);

                String shortCode  = merchant.getShortCode();
                String commandId  = merchant.getCommandId();

                final String msgRequest = "<?xml version='1.0' encoding='utf-8' ?><Request><Command>BankB2W</Command><SessionID>"+sessionId+"</SessionID><Shortcode>"+BankShortCode+"</Shortcode><BankShortcode>"+shortCode+"</BankShortcode><Currency>"+Currency+"</Currency><Amount>"+Amount+"</Amount><DateTime>"+dateString+"</DateTime><ThirdPartyReference>"+ref+"</ThirdPartyReference><TransactionID>"+ref+"</TransactionID><CommandID>"+commandId+"</CommandID><TransactionType>VA</TransactionType><CallBackChannel>"+configuration.getCallBackChannelB2B()+"</CallBackChannel><CallBackDestination>"+configuration.getCallBackDestinationB2B()+"</CallBackDestination></Request>";
                System.out.println("*******************************************************************");
                System.out.println(msgRequest);

                SalesRequest salesRequest = new SalesRequest();
                salesRequest.setCallBackChannel(configuration.getCallBackChannelB2B());
                salesRequest.setCallBackDestination(configuration.getCallBackDestinationB2B());
                salesRequest.setShortCode(ShortCode);
                salesRequest.setSale(sale);
                salesRequest.setCodeRequest(ref);
                salesRequest.setCommandId(merchant.getCommandId());
                //salesRequest.setDescription(msgRequest);
                salesRequestService.saveOrUpdate(salesRequest);



                //Process request
                String urlRequest = configuration.getApiMpesaRequestB2b();
                String responseRequest = C2BUtil.proccessRequestB2B(msgRequest, urlRequest);
                System.out.println(responseRequest);
                DocumentBuilder dbrequest = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document docRequest = dbrequest.parse(new InputSource(new StringReader(responseRequest)));

                String descT = C2BUtil.getResonseDesc(docRequest);
                String transT = C2BUtil.getInsightTxID(docRequest);
                String codeT = C2BUtil.getResonseCode(docRequest);
                String dT = C2BUtil.getResonseDesc(docRequest);

                SalesResponse salesResponse = new SalesResponse();
                salesResponse.setCode(codeT);
                salesResponse.setDescription(descT);
                salesResponse.setDetail(dT);
                salesResponse.setTransactionId(transT);
                salesResponse.setSale(sale);
                salesResponse.setSalesRequest(salesRequest);
                //salesResponse.setDescriptionResponse(responseRequest);
                salesResponseService.saveOrUpdate(salesResponse);

                sale.setSalesRequest(salesRequest);
                sale.setSalesResponse(salesResponse);
                sale.setLoginResponse(loginResponse);
                salesService.saveOrUpdate(sale);


                if (codeT.equalsIgnoreCase("0")){
                    System.out.println("Description response : " + descT);
                    System.out.println("Transaction response : " + transT);
                    System.out.println("Code response : " + codeT);
                    System.out.println("Veuillez confirmer sur votre téléphone pour valider le paiement");

                    object.setCode("0");
                    object.setMessage("Veuillez confirmer sur votre téléphone pour valider le paiement");
                    object.setOrderNumber(ref);
                    return new ResponseEntity<>(object, HttpStatus.OK);

                }else {
                    System.out.println("Erreur lors du paiement");

                    sale.setCompleted(true);
                    sale.setErrorDescription("Request error. Status is not 0");
                    salesService.saveOrUpdate(sale);

                    object.setCode("09");
                    object.setMessage("Erreur lors du paiement");
                    object.setOrderNumber(ref);
                    return new ResponseEntity<>(object, HttpStatus.OK);
                }
            }else {
                System.out.println("******************* Login error ********************");

                sale.setCompleted(true);
                sale.setErrorDescription("Login error. Status is not 0");
                salesService.saveOrUpdate(sale);

                object.setCode("09");
                object.setMessage("Error login");
                object.setOrderNumber(ref);
                return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            object.setCode("09");
            object.setMessage("Error processing");
            return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);

        }

    }

    @PostMapping("/v1/notification")
    public ResponseEntity<MessageResponse> retourb2B(HttpServletRequest request) throws IOException {

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("/api/mpesa/b2b/notification : " + formatter.format(now));

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        System.out.println(data);


        /*

        Map<String, List<String>> datas = C2BUtil.processXml(data);
        System.out.println("******************************************************");
        System.out.println(data);
        System.out.println("******************************************************");
        //Map<String,List<String>> datas = process2(path);
        String code = datas.get("ThirdPartyReference").get(0);
        String resultType = datas.get("TransactionDateTime").get(0);
        String resultCode = datas.get("ResponseCode").get(0);
        String resultDesc = datas.get("ResponseDesc").get(0);
        String transactionID = datas.get("TransactionID").get(0);
        String insightReference = datas.get("InsightTxID").get(0);

        */

        try{

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(data)));

            String code = C2BUtil.getB2BWallet2BankNotificationThirdPartyReference(doc);
            //String resultType = C2BUtil.getB2BNotificationResultType(doc);
            String resultCode = C2BUtil.getB2BWallet2BankNotificationResultCode(doc);
            String resultDesc = C2BUtil.getB2BWallet2BankNotificationResultDesc(doc);
            String transactionID = C2BUtil.getB2BWallet2BankNotificationTransactionID(doc);
            String insightReference = C2BUtil.getB2BWallet2BankNotificationInsightTxID(doc);

            System.out.println("Data to send");
            System.out.println("Référence b2b : " + code);
            System.out.println("Code b2b : " + resultCode);
            System.out.println("result desc : " + resultDesc);
            System.out.println("transaction id : " + transactionID);
            System.out.println("Insight reference : " + insightReference);

            Sales sales = salesService.getByCode(code);
            if (sales != null && !sales.isCompleted()) {
                Notification notification = new Notification();
                notification.setInsightReference(insightReference);
                notification.setResultCode(resultCode);
                notification.setResultDesc(resultDesc);
                //notification.setResultType(resultType);
                notification.setTransactionId(transactionID);
                notification.setReference(code);
                notification.setSale(sales);
                //notification.setDescription(data);
                notificationService.saveOrUpdate(notification);

                sales.setCompleted(true);
                sales.setCodeValue(resultCode);
                sales.setNotification(notification);
                salesService.saveOrUpdate(sales);

                //Message to client
                MerchantNotification merchantNotification = new MerchantNotification();
                merchantNotification.setNotification(notification);
                merchantNotification.setResultCode(resultCode);
                merchantNotification.setResultDesc(resultDesc);
                merchantNotificationService.saveOrUpdate(merchantNotification);

                String url = sales.getCallBackUrl();
                URL obj = new URL(url);
                //HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                //con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                String urlParameters = "code="+resultCode+"&reference="+sales.getReference()+"&mpesa_reference="+insightReference+"&telephone="+sales.getCustomerMsisdn()+"&amount="+sales.getAmount();

                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending b2b 'POST' request to URL : " + url);
                System.out.println("Post parameters b2b : " + urlParameters);
                System.out.println("Response Code b2b : " + responseCode);
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            MessageResponse object = new MessageResponse();
            object.setCode("96");
            object.setMessage("Exception B2B callback");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
        MessageResponse object = new MessageResponse();
        object.setCode("0");
        object.setMessage("Transaction successfull");
        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
