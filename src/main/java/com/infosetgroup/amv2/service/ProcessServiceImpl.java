package com.infosetgroup.amv2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosetgroup.amv2.dto.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ProcessServiceImpl implements ProcessService{
    @Override
    public AuthorizationResponse getAuthorization(String clientId, String clientSecret, String grantType) {
        System.out.println("**************getAuthorization******************");
        AuthorizationResponse response = new AuthorizationResponse();
        String urlParameters = "{\"client_id\": \""+clientId+"\", \"client_secret\": \""+clientSecret+"\", \"grant_type\": \""+grantType+"\"}";
        System.out.println(urlParameters);
        try{
            URL obj = new URL("https://openapi.airtel.africa/auth/oauth2/token");
            //URL obj = new URL("https://openapiuat.airtel.africa/auth/oauth2/token");
            //URL obj = new URL("https://openapi.airtel.africa/auth/oauth2/token");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json; utf-8");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("Response code : " + responseCode);
            if (responseCode == 200 || responseCode == 201) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println("Auth response : " + content.toString());
                response = new ObjectMapper().readValue(content.toString(), AuthorizationResponse.class);
                response.setCode("0");
            }else {
                response.setCode("1");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            response.setCode("1");
        }
        return response;
    }

    @Override
    public PaymentResponse getPayment(String reference, String country, String currency, String msisdn, String amount, String id, String token) {
        System.out.println("**************getPayment******************");
        PaymentResponse response = new PaymentResponse();
        StringBuilder urlParameters = new StringBuilder();
        urlParameters.append("{");
        urlParameters.append("\"reference\":\"" + reference + "\",");
        urlParameters.append("\"subscriber\":{");
        urlParameters.append("\"country\":\"" + country + "\",");
        urlParameters.append("\"currency\":\"" + currency + "\",");
        urlParameters.append("\"msisdn\":\"" + msisdn + "\"");
        urlParameters.append("},");
        urlParameters.append("\"transaction\":{");
        urlParameters.append("\"amount\":" + amount +",");
        urlParameters.append("\"country\":\"" + country + "\",");
        urlParameters.append("\"currency\":\"" + currency + "\",");
        urlParameters.append("\"id\":\"" + id + "\"");
        urlParameters.append("}");
        urlParameters.append("}");

        System.out.println(urlParameters);

        try{
            //URL obj = new URL("https://openapiuat.airtel.africa/merchant/v1/payments/");
            URL obj = new URL("https://openapi.airtel.africa/merchant/v1/payments/");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("X-Country", country);
            con.setRequestProperty("X-Currency", currency);
            con.setRequestProperty("Authorization", token);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters.toString());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println(content.toString());
                response = new ObjectMapper().readValue(content.toString(), PaymentResponse.class);
                response.setCode("0");
            }else {
                response.setCode("1");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            response.setCode("1");
        }
        return response;
    }

    @Override
    public RefundResponse getRefund(String airtelMoneyId, String country, String currency, String token) {
        System.out.println("**************getRefund******************");
        RefundResponse response = new RefundResponse();
        StringBuilder urlParameters = new StringBuilder();
        urlParameters.append("{");
        urlParameters.append("\"transaction\":{");
        urlParameters.append("\"airtel_money_id\":\"" + airtelMoneyId +"\"");
        urlParameters.append("}");
        urlParameters.append("}");

        System.out.println(urlParameters);

        try{
            //URL obj = new URL("https://openapiuat.airtel.africa/standard/v1/payments/refund");
            URL obj = new URL("https://openapi.airtel.africa/standard/v1/payments/refund");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("X-Country", country);
            con.setRequestProperty("X-Currency", currency);
            con.setRequestProperty("Authorization", token);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters.toString());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println(content.toString());
                response = new ObjectMapper().readValue(content.toString(), RefundResponse.class);
                response.setCode("0");
            }else {
                response.setCode("1");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            response.setCode("1");
        }
        return response;
    }

    @Override
    public EnquiryResponse getEnquiry(String partnerId, String country, String currency, String token) {
        System.out.println("**************getEnquiry******************");
        EnquiryResponse response = new EnquiryResponse();

        try{
            //URL obj = new URL("https://openapiuat.airtel.africa/standard/v1/payments/"+partnerId);
            URL obj = new URL("https://openapi.airtel.africa/standard/v1/payments/"+partnerId);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("X-Country", country);
            con.setRequestProperty("X-Currency", currency);
            con.setRequestProperty("Authorization", token);

            int responseCode = con.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println(content.toString());
                response = new ObjectMapper().readValue(content.toString(), EnquiryResponse.class);
                response.setCode("0");
            }else {
                response.setCode("1");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            response.setCode("1");
        }
        return response;
    }

    @Override
    public ResponsePaymentCallback getCallbackPayment(HttpServletRequest request) {
        System.out.println("**************getCallbackPayment********************");
        ResponsePaymentCallback response = new ResponsePaymentCallback();
        try {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String data = buffer.toString();
            response = new ObjectMapper().readValue(buffer.toString(), ResponsePaymentCallback.class);
            response.setCode("0");
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            response.setCode("0");
        }
        return response;
    }

    @Override
    public DisbursementResponse getDisbursement(String reference, String country, String currency, String pin, String msisdn, String amount, String id, String token) {
        System.out.println("**************getDisbursement******************");
        DisbursementResponse response = new DisbursementResponse();
        StringBuilder urlParameters = new StringBuilder();
        urlParameters.append("{");
        urlParameters.append("\"payee\":{");
        urlParameters.append("\"msisdn\":\"" + msisdn + "\"");
        urlParameters.append("},");
        urlParameters.append("\"reference\":\"" + reference +"\",");
        urlParameters.append("\"pin\":\"" + pin +"\",");
        urlParameters.append("\"transaction\":{");
        urlParameters.append("\"amount\":" + amount +",");
        urlParameters.append("\"id\":\"" + id + "\"");
        urlParameters.append("}");
        urlParameters.append("}");

        System.out.println(urlParameters);

        try{
            URL obj = new URL("https://openapiuat.airtel.africa/merchant/v1/payments/");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("X-Country", country);
            con.setRequestProperty("X-Currency", currency);
            con.setRequestProperty("Authorization", token);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters.toString());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println(content.toString());
                response = new ObjectMapper().readValue(content.toString(), DisbursementResponse.class);
                response.setCode("0");
            }else {
                response.setCode("1");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            response.setCode("1");
        }
        return response;
    }
}
