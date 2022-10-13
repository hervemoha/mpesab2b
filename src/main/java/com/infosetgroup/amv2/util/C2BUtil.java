package com.infosetgroup.amv2.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by Administrator on 5/10/2019.
 */
public class C2BUtil {

    public static Map<String,List<String>> processXml(String path){
        Map<Integer,String> values = new HashMap();

        Map<String,List<String>> valuesFinal = new HashMap();

        try{

            /*
             * Etape 1 : récupération d'une instance de la classe "DocumentBuilderFactory"
             */
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            try {
                /*
                 * Etape 2 : création d'un parseur
                 */
                final DocumentBuilder builder = factory.newDocumentBuilder();
                InputSource inputSource = new InputSource();
                inputSource.setCharacterStream(new StringReader(path));

                /*
                 * Etape 3 : création d'un Document
                 */
                final Document document= builder.parse(inputSource);

                //Affichage du prologue
                System.out.println("*************PROLOGUE************");
                System.out.println("version : " + document.getXmlVersion());
                System.out.println("encodage : " + document.getXmlEncoding());
                System.out.println("standalone : " + document.getXmlStandalone());

                /*
                 * Etape 4 : récupération de l'Element racine
                 */
                final Element racine = document.getDocumentElement();

                //Affichage de l'élément racine
                System.out.println("\n*************RACINE************");
                System.out.println(racine.getNodeName());

                /*
                 * Etape 5 : récupération des personnes
                 */
                final NodeList racineNoeuds = racine.getChildNodes();
                final int nbRacineNoeuds = racineNoeuds.getLength();

                for (int i = 0; i<nbRacineNoeuds; i++) {
                    if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        final Element personne = (Element) racineNoeuds.item(i);


                        System.out.println("\n*************DATA************");


                        final NodeList telephones = personne.getElementsByTagName("dataItem");
                        final int nbTelephonesElements = telephones.getLength();

                        for(int j = 0; j<nbTelephonesElements; j++) {
                            final Element data = (Element) telephones.item(j);
                            NodeList list = data.getElementsByTagName("name");
                            String name = list.item(0).getTextContent();

                            NodeList list1 = data.getElementsByTagName("value");
                            String value = list1.item(0).getTextContent();

                            NodeList list2 = data.getElementsByTagName("type");
                            String type = list2.item(0).getTextContent();

                            System.out.println();
                            String ct = name+"#"+value+"#"+type;
                            System.out.println(j + " : " + ct);


                            //String content = data.getTextContent().trim().replace("\n","#");
                            //System.out.println(j + " : " + content);
                            //values.put(j,content);
                            values.put(j,ct);
                        }
                    }
                }
                System.out.println("==========================================================================");

                for (Map.Entry<Integer, String> entry : values.entrySet()) {

                    String result = entry.getValue();
                    // System.out.println("\t\tresult == " + result);

                    String delim = "#";
                    List<String> parametres = new ArrayList();
                    String key="";
                    StringTokenizer st = new StringTokenizer(result, delim);
                    int index=0;
                    while(st.hasMoreElements()){
                        //  System.out.println("Token=="+st.nextToken().trim());
                        if(index==0){
                            key = st.nextToken().trim();
                        }else{
                            parametres.add(st.nextToken().trim());
                        }
                        index++;
                    }

                    valuesFinal.put(key,parametres);
                }
            }

            catch (final ParserConfigurationException e) {
                e.printStackTrace();
            }
            catch (final SAXException e) {
                e.printStackTrace();
            }
            catch (final IOException e) {
                e.printStackTrace();
            }


        }catch (Exception x){
            x.printStackTrace();
        }

        return valuesFinal;
    }

    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    public static String proccessLoginB2B(String msg, String urlTo) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        SslUtil.disableSslVerification();

        //final URL url = new URL("http://41.78.195.169:8107/insight/B2BLogin");
        final URL url = new URL(urlTo);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);

        // Send post request
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        //final String msg = "<?xml version='1.0' encoding='utf-8' ?><TKKPG><Request><Operation>CreateOrder</Operation><Language>FR</Language><Order><Merchant>SCPT</Merchant><Amount>10</Amount><Currency>840</Currency><Description>Facture</Description><ApproveURL>http://</ApproveURL><CancelURL>http</CancelURL> <DeclineURL>http</DeclineURL> <OrderType>Purchase</OrderType> </Order> </Request> </TKKPG>";
        //final String msg = "<?xml version='1.0' encoding='utf-8' ?><Request><Username>"+login.getUsername()+"</Username><Password>"+login.getPassword()+"</Password></Request>";
        System.out.println(msg);
        wr.writeBytes(msg);
        // send request
        wr.flush();
        // close
        wr.close();

        // read response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str;
        while ((str = in.readLine()) != null) {
            content.append(str);
        }
        in.close();

        return content.toString();
    }

    public static String proccessLoginB2C(String msg, String urlToLogin) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        SslUtil.disableSslVerification();

        //final URL url = new URL("http://41.78.195.169:8107/insight/B2BLogin");
        final URL url = new URL(urlToLogin);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);

        // Send post request
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        System.out.println(msg);
        wr.writeBytes(msg);
        // send request
        wr.flush();
        // close
        wr.close();

        // read response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str;
        while ((str = in.readLine()) != null) {
            content.append(str);
        }
        in.close();

        return content.toString();
    }

    public static String getSessionId(Document doc)
    {
        try {
            NodeList studentNodes = doc.getElementsByTagName("Response");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    String status = studentElement.getElementsByTagName("SessionID").item(0).getTextContent();
                    System.out.println("Session id = " + status);

                    return status;
                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getResultCode(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Response");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ResultCode").item(0).getTextContent();
                System.out.println("Result code = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getLoginCodeResponseB2C(Document doc)
    {
        try {
            NodeList studentNodes = doc.getElementsByTagName("eventInfo");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    if (studentElement.getElementsByTagName("code") != null) {
                        String status = studentElement.getElementsByTagName("code").item(0).getTextContent();
                        System.out.println("Code = " + status);
                        return status;
                    }

                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getRequestCodeResponseB2C(Document doc)
    {
        try {
            NodeList studentNodes = doc.getElementsByTagName("eventInfo");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    if (studentElement.getElementsByTagName("code") != null) {
                        String status = studentElement.getElementsByTagName("code").item(0).getTextContent();
                        System.out.println("Code = " + status);
                        return status;
                    }

                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getDescriptionResponseB2C(Document doc)
    {
        try {
            NodeList studentNodes = doc.getElementsByTagName("eventInfo");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    if (studentElement.getElementsByTagName("description") != null) {
                        String status = studentElement.getElementsByTagName("description").item(0).getTextContent();
                        System.out.println("Code = " + status);
                        return status;
                    }

                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getRequestDescriptionResponseB2C(Document doc)
    {
        try {
            NodeList studentNodes = doc.getElementsByTagName("eventInfo");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    if (studentElement.getElementsByTagName("description") != null) {
                        String status = studentElement.getElementsByTagName("description").item(0).getTextContent();
                        System.out.println("Code = " + status);
                        return status;
                    }

                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getDetailResponseB2C(Document doc)
    {
        try{
            NodeList studentNodes = doc.getElementsByTagName("eventInfo");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    if (studentElement.getElementsByTagName("detail") != null) {
                        String status = studentElement.getElementsByTagName("detail").item(0).getTextContent();
                        System.out.println("Code = " + status);
                        return status;
                    }

                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getRequestDetailResponseB2C(Document doc)
    {
        try {
            NodeList studentNodes = doc.getElementsByTagName("eventInfo");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    if (studentElement.getElementsByTagName("detail") != null) {
                        String status = studentElement.getElementsByTagName("detail").item(0).getTextContent();
                        System.out.println("Code = " + status);
                        return status;
                    }

                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getTransactionIdResponseB2C(Document doc)
    {
        try {
            NodeList studentNodes = doc.getElementsByTagName("eventInfo");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    if (studentElement.getElementsByTagName("transactionID") != null) {
                        String status = studentElement.getElementsByTagName("transactionID").item(0).getTextContent();
                        System.out.println("Code = " + status);
                        return status;
                    }

                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getRequestTransactionIdResponseB2C(Document doc)
    {
        try {
            NodeList studentNodes = doc.getElementsByTagName("eventInfo");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    if (studentElement.getElementsByTagName("transactionID") != null) {
                        String status = studentElement.getElementsByTagName("transactionID").item(0).getTextContent();
                        System.out.println("Code = " + status);
                        return status;
                    }

                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getSessionIdResponseB2C(Document doc)
    {
        try {
            NodeList studentNodes = doc.getElementsByTagName("response");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    if (studentElement.getElementsByTagName("dataItem") != null) {
                        NodeList dataItemNodes =  studentElement.getElementsByTagName("dataItem");
                        for (int j = 0; j < dataItemNodes.getLength(); j++) {
                            Node dataItemNode = dataItemNodes.item(j);
                            if (dataItemNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element dataItemElement = (Element) dataItemNode;
                                if (dataItemElement.getElementsByTagName("value") != null) {
                                    String status = studentElement.getElementsByTagName("value").item(0).getTextContent();
                                    System.out.println("*****SessionID = " + status);
                                    return status;
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getRequestSessionIdResponseB2C(Document doc)
    {
        try {
            NodeList responseNodes = doc.getElementsByTagName("response");
            System.out.println("Length : " + responseNodes.getLength());

            for(int i=0; i<responseNodes.getLength(); i++)
            {
                Node responseNode = responseNodes.item(i);
                System.out.println(responseNode.getNodeName());
                NodeList nodeList = responseNode.getChildNodes();
                System.out.println(nodeList.getLength());
                for (int j = 0; j < nodeList.getLength(); j++) {
                    Node node = nodeList.item(j);
                    System.out.println(node.getNodeName());
                    if(node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        NodeList nodeList1 = node.getChildNodes();
                        for (int k = 0; k < nodeList1.getLength(); k++) {
                            Element nodeElement = (Element) node;
                            if (nodeElement.getElementsByTagName("name") != null) {
                                if (nodeElement.getElementsByTagName("name").item(0).getTextContent().equalsIgnoreCase("Insight_txid")) {
                                    String val = nodeElement.getElementsByTagName("value").item(0).getTextContent();
                                    System.out.println(val);
                                    return val;
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            return "777";
        }
        return "777";
    }

    public static String getResultDesc(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Response");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ResultDesc").item(0).getTextContent();
                System.out.println("Result desc = " + status);

                return status;
            }
        }
        return "777";
    }

    public static String proccessRequestC2B(String msg, String urlTo) {
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{

            SslUtil.disableSslVerification();
            //final URL url = new URL("https://uatipg.m-pesa.vodacom.cd:8091/insight");
            final URL url = new URL(urlTo);
            //final URL url = new URL("http://41.78.195.169:8097/insight/SOAPIn");
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(msg);
            wr.writeBytes(msg);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();

            return content.toString();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "777";

    }

    public static String proccessRequestB2B(String msg, String urlTo) {
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{

            SslUtil.disableSslVerification();
            final URL url = new URL(urlTo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            //final String msg = "<?xml version='1.0' encoding='utf-8' ?><Request><SessionID>"+session+"</SessionID><Command>OnlineB2B</Command><ServiceProviderCode>"+provider+"</ServiceProviderCode><Currency>"+currency+"</Currency><Amount>"+amount+"</Amount><TransactionDateTime>"+date+"</TransactionDateTime><ThirdPartyReference>"+reference+"</ThirdPartyReference><CommandID>"+commandId+"</CommandID><AgentMSISDN>"+msisdn+"</AgentMSISDN><Language>"+language+"</Language><AgentName>"+agentName+"</AgentName><CallBackChannel>"+callback+"</CallBackChannel><CallBackDestination>"+destination+"</CallBackDestination></Request>";
            System.out.println(msg);
            wr.writeBytes(msg);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();

            return content.toString();
        }catch (Exception e){
            System.out.println(e.getMessage());
            //return "666";
        }
        return "777";

    }

    public static String proccessRequestB2C(String msg, String urlTo) {
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{

            SslUtil.disableSslVerification();
            final URL url = new URL(urlTo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(msg);
            wr.writeBytes(msg);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();

            return content.toString();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "777";

    }

    public static String proccessRequestOmC2B(String token,String ref, String merMsisdn,
                                              String CustomerMSISDN, String partnId, String amount,
                                              String currency, String description, String apiUrl, String callbaclUrl) {

        final String msg = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://services.ws1.com/'>" +
                "<soapenv:Header/><soapenv:Body><ser:doS2M>\n" +
                "<subsmsisdn>"+CustomerMSISDN+"</subsmsisdn>\n" +
                "<PartnId>"+partnId+"</PartnId>\n" +
                "<mermsisdn>"+merMsisdn+"</mermsisdn>\n" +
                "<transid>"+ref+"</transid>\n" +
                "<currency>"+currency+"</currency>\n" +
                "<amount>"+amount+"</amount>\n" +
                "<callbackurl>"+callbaclUrl+"</callbackurl>\n" +
                "<message_s2m>"+description+"</message_s2m>\n" +
                "</ser:doS2M>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{
            SslUtil.disableSslVerification();
            //final URL url = new URL("https://41.77.223.184:8088/apigatewayom/apigwomService");
            final URL url = new URL(apiUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession sslSession) {
                    return true;
                }
            });
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("Authorization", token);
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(msg);
            System.out.println("*************************************************************");
            wr.writeBytes(msg);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();
            return content.toString();
        }catch (Exception e){
            System.out.println("******Orange Money********");
            System.out.println(e.getMessage());
        }
        return "777";
    }

    public static String proccessRequestOmB2C(String token,String ref, String merMsisdn,
                                              String CustomerMSISDN, String partnId, String amount,
                                              String currency, String apiUrl) {

        final String msg = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://services.ws1.com/'>" +
                "<soapenv:Header/><soapenv:Body><ser:doM2S>\n" +
                "<subsmsisdn>"+CustomerMSISDN+"</subsmsisdn>\n" +
                "<PartnId>"+partnId+"</PartnId>\n" +
                "<mermsisdn>"+merMsisdn+"</mermsisdn>\n" +
                "<transid>"+ref+"</transid>\n" +
                "<currency>"+currency+"</currency>\n" +
                "<amount>"+amount+"</amount>\n" +
                //"<callbackurl>"+configuration.getCallBackDestinationOmB2C()+"</callbackurl>\n" +
                //"<message_s2m>"+PaymentDesc+"</message_s2m>\n" +
                "</ser:doM2S>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";

        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{

            SslUtil.disableSslVerification();
            //final URL url = new URL("https://41.77.223.184:8088/apigatewayom/apigwomService");
            final URL url = new URL(apiUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession sslSession) {
                    return true;
                }
            });
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("Authorization", token);
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(msg);
            System.out.println("*************************************************************");
            wr.writeBytes(msg);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();

            return content.toString();
        }catch (Exception e){
            System.out.println("******Orange Money********");
            System.out.println(e.getMessage());
        }
        return "777";
    }

    public static String proccessRequestOmB2B(String token,String ref, String merMsisdn,
                                              String CustomerMSISDN, String partnId, String amount,
                                              String currency, String apiUrl) {

        final String msg = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://services.ws1.com/'>" +
                "<soapenv:Header/><soapenv:Body><ser:doM2M>\n" +
                "<mermsisdn1>"+merMsisdn+"</mermsisdn1>\n" +
                "<PartnId>"+partnId+"</PartnId>\n" +
                "<mermsisdn2>"+CustomerMSISDN+"</mermsisdn2>\n" +
                "<transid>"+ref+"</transid>\n" +
                "<currency>"+currency+"</currency>\n" +
                "<amount>"+amount+"</amount>\n" +
                //"<callbackurl>"+configuration.getCallBackDestinationOmB2B()+"</callbackurl>\n" +
                //"<message_s2m>"+PaymentDesc+"</message_s2m>\n" +
                "</ser:doM2M>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";

        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{

            SslUtil.disableSslVerification();
            //final URL url = new URL("https://41.77.223.184:8088/apigatewayom/apigwomService");
            final URL url = new URL(apiUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession sslSession) {
                    return true;
                }
            });
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("Authorization", token);
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(msg);
            System.out.println("*************************************************************");
            wr.writeBytes(msg);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();

            return content.toString();
        }catch (Exception e){
            System.out.println("******Orange Money********");
            System.out.println(e.getMessage());
        }
        return "777";
    }

    public static String proccessRequestAiC2B(String login, String password, String ref, String msisdn,
                                              String merchant, String merNickName, String amount, String interfaceId,
                                              String currency, String apiUrl, String language) {
        String lang = "2";
        if (language.equalsIgnoreCase("EN"))
            lang = "1";

        String cur1 = "";

        if (currency.equalsIgnoreCase("101") || currency.equalsIgnoreCase("USD")) {
            cur1 = "101";
        }else {
            cur1 = "102";
        }

        String data = new StringBuilder()
                .append("<?xml version=\"1.0\"?>")
                .append("<COMMAND>")
                .append("<TYPE>CALLBCKREQ</TYPE>")
                .append("<NOTIFYTYPE>CMPREQ</NOTIFYTYPE>")
                .append("<INTERFACEID>USSDPUSH</INTERFACEID>")
                .append("<interfaceId>").append(interfaceId).append("</interfaceId>")
                //.append("<interfaceId>0113863</interfaceId>")
                .append("<MERCHANT_TXN_ID>").append(ref).append("</MERCHANT_TXN_ID>")
                .append("<MSISDN>").append(msisdn).append("</MSISDN>")
                .append("<MSISDN2>").append(merchant).append("</MSISDN2>")
                .append("<AMOUNT>").append(amount).append("</AMOUNT>")
                .append("<EXTTRID>").append(ref).append("</EXTTRID>")
                .append("<REFERENCE_NO>").append(ref).append("</REFERENCE_NO>")
                .append("<REFERENCE>").append(ref).append("</REFERENCE>")
                .append("<IS_3PP_INITIATED>Y</IS_3PP_INITIATED>")
                .append("<IS_SPECIFIC_PUSH_MSG_REQ>Y</IS_SPECIFIC_PUSH_MSG_REQ>")
                .append("<MERNICKNAME>").append(merNickName).append("</MERNICKNAME>")
                .append("<SUB_TYPE>").append(merchant).append("</SUB_TYPE>")
                .append("<LANGUAGE1>"+lang+"</LANGUAGE1>")
                .append("<LANGUAGE2>"+lang+"</LANGUAGE2>")
                //.append("<CURRENCY>"+cur2+"</CURRENCY>")
                .append("<PROVIDER>").append(cur1).append("</PROVIDER>")
                .append("<PROVIDER2>").append(cur1).append("</PROVIDER2>")
                .append("<PAYID>12</PAYID>").append("<PAYID2>12</PAYID2>")
                .append("<PAYERINS>WALLET</PAYERINS>")
                .append("<PAYEEINS>WALLET</PAYEEINS>")
                .append("<IS_MERCHANT_INITIATED>Y</IS_MERCHANT_INITIATED>")
                .append("</COMMAND>").toString();

        System.out.println(data);

        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{
            SslUtil.disableSslVerification();
            //final URL url = new URL("https://41.77.223.184:8088/apigatewayom/apigwomService");
            //final URL url = new URL(apiUrl+"/"+login+"?LOGIN="+login+"&PASSWORD="+password);
            //String urlTo = apiUrl+"/"+login+"?LOGIN="+password;
            String urlTo = apiUrl+"/"+login+"?LOGIN="+login+"&PASSWORD="+password;
            System.out.println("URL AM : " + urlTo);
            final URL url = new URL(urlTo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(data);
            System.out.println("*************************************************************");
            wr.writeBytes(data);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();
            System.out.println(content.toString());

            return content.toString();
        }catch (Exception e){
            System.out.println("******Airtel Money exception ********");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "777";
        }
        //return "777";
    }

    public static String proccessRequestAiC2BV2(String transactionId, String reference, String country, String currency,
                                              String msisdn, String amount, String authorization, String apiUrl) {

        System.out.println("Process request airtel money v2");
        String data = new StringBuilder()
                .append("{\n")
                .append("\"reference\":\""+reference+"\",\n")
                .append("\"subscriber\":{\n")
                .append("\"country\":\""+country+"\",\n")
                .append("\"currency\":\""+currency+"\",\n")
                .append("\"msisdn\":"+msisdn+"\n")
                .append("},\n")
                .append("\"transaction\":{\n")
                .append("\"amount\":"+amount+",\n")
                .append("\"country\":\""+country+"\",\n")
                .append("\"currency\":\""+currency+"\",\n")
                .append("\"id\":\""+transactionId+"\"\n")
                .append("}\n")
                .append("}").toString();

        System.out.println(data);

        final String USER_AGENT = "Mozilla/5.0";
        final StringBuffer content = new StringBuffer();

        try{
            //SslUtil.disableSslVerification();
            final URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("X-Country", country);
            conn.setRequestProperty("X-Currency", currency);
            conn.setRequestProperty("Authorization", authorization);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(data);
            System.out.println("*************************************************************");
            wr.writeBytes(data);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    content.append(str);
                }
                in.close();
                System.out.println(content.toString());
                return content.toString();
            }else {
                System.out.println("Response is not 200 or 201");
                return "777";
            }
        }catch (Exception e){
            System.out.println("******Airtel V2 Money exception ********");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "777";
        }
    }

    public static String proccessRequestAiB2C(String login, String password, String ref, String msisdn,
                                              String merchant, String amount,String interfaceId,String pin,
                                              String currency, String apiUrl, String language) {
        String lang = "2";
        if (language.equalsIgnoreCase("EN"))
            lang = "1";

        String cur1 = "";
        if (currency.equalsIgnoreCase("101") || currency.equalsIgnoreCase("USD")) {
            cur1 = "101";
        }else {
            cur1 = "102";
        }

        String data = new StringBuilder()
                .append("<?xml version=\"1.0\"?>")
                .append("<COMMAND>")
                .append("<SERVICETYPE>RCIREQ</SERVICETYPE>")
                .append("<INTERFACEID>").append(interfaceId).append("</INTERFACEID>")
                .append("<MSISDN>").append(merchant).append("</MSISDN>")
                .append("<MSISDN2>").append(msisdn).append("</MSISDN2>")
                .append("<AMOUNT>").append(amount).append("</AMOUNT>")
                .append("<EXTTRID>").append(ref).append("</EXTTRID>")
                .append("<REFERENCE>").append(ref).append("</REFERENCE>")
                .append("<LANGUAGE1>"+lang+"</LANGUAGE1>")
                .append("<LANGUAGE2>"+lang+"</LANGUAGE2>")
                .append("<PROVIDER>").append(cur1).append("</PROVIDER>")
                .append("<PROVIDER2>").append(cur1).append("</PROVIDER2>")
                .append("<SNDPROVIDER>").append(cur1).append("</SNDPROVIDER>")
                .append("<SNDINSTRUMENT>12</SNDINSTRUMENT>")
                .append("<RCVINSTRUMENT>12</RCVINSTRUMENT>")
                .append("<RCVPROVIDER>").append(cur1).append("</RCVPROVIDER>")
                .append("<BPROVIDER>").append(cur1).append("</BPROVIDER>")
                .append("<PIN>").append(pin).append("</PIN>")
                .append("<PAYID>12</PAYID>")
                .append("<PAYID2>12</PAYID2>")
                .append("</COMMAND>").toString();

        System.out.println(data);

        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{
            SslUtil.disableSslVerification();
            //final URL url = new URL("https://41.77.223.184:8088/apigatewayom/apigwomService");
            //final URL url = new URL(apiUrl+"/"+login+"?LOGIN="+login+"&PASSWORD="+password);
            //String urlTo = apiUrl+"/"+login+"?LOGIN="+password;
            String urlTo = apiUrl+"/"+login+"?LOGIN="+login+"&PASSWORD="+password;
            System.out.println("URL AM : " + urlTo);
            final URL url = new URL(urlTo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(data);
            System.out.println("*************************************************************");
            wr.writeBytes(data);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();
            System.out.println(content.toString());

            return content.toString();
        }catch (Exception e){
            System.out.println("******Airtel Money exception B2C ********");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "777";
        }
        //return "777";
    }

    public static String getResonseCode(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Response");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ResponseCode").item(0).getTextContent();
                System.out.println("Response code = " + status);

                return status;
            }
        }
        return "777";
    }

    public static String getResonseDesc(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Response");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ResponseDesc").item(0).getTextContent();
                System.out.println("Response desc b2b = " + status);

                return status;
            }
        }
        return "777";
    }

    public static String getInsightTxID(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Response");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                if (studentElement.getElementsByTagName("InsightTxID") != null) {
                    String status = studentElement.getElementsByTagName("InsightTxID").item(0).getTextContent();
                    System.out.println("InsightTxID = " + status);
                    return status;
                }

            }
        }
        return "777";
    }

    // Send Mpesa C2B
    public static String getCodeResponseC2BRequest(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("eventInfo");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                if (studentElement.getElementsByTagName("code") != null) {
                    String status = studentElement.getElementsByTagName("code").item(0).getTextContent();
                    System.out.println("Code = " + status);
                    return status;
                }

            }
        }
        return "777";
    }

    public static String getTransactionIDResponseC2BRequest(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("eventInfo");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                if (studentElement.getElementsByTagName("transactionID") != null) {
                    String status = studentElement.getElementsByTagName("transactionID").item(0).getTextContent();
                    System.out.println("TransactionID = " + status);
                    return status;
                }

            }
        }
        return "777";
    }

    public static String getDescriptionResponseC2BRequest(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("eventInfo");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                if (studentElement.getElementsByTagName("description") != null) {
                    String status = studentElement.getElementsByTagName("description").item(0).getTextContent();
                    System.out.println("Description = " + status);
                    return status;
                }

            }
        }
        return "777";
    }

    public static String getDetailResponseC2BRequest(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("eventInfo");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                if (studentElement.getElementsByTagName("detail") != null) {
                    String status = studentElement.getElementsByTagName("detail").item(0).getTextContent();
                    System.out.println("Detail = " + status);
                    return status;
                }

            }
        }
        return "777";
    }

    public static String getInsightReferenceResponseC2BRequest(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("response");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node reponseNode = studentNodes.item(i);
            NodeList nodeList = reponseNode.getChildNodes();
            for (int j = 0; j < nodeList.getLength(); j++) {
                Node node = nodeList.item(j);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) node;
                    if (studentElement.getNodeName().contains("dataItem")) {
                        if (studentElement.getElementsByTagName("name").item(0).getTextContent().equalsIgnoreCase("InsightReference"))
                        {
                            String insightReference = studentElement.getElementsByTagName("value").item(0).getTextContent();
                            return insightReference;
                        }

                    }
                }
            }
        }
        return "777";
    }



    //Notification callback

    public static String getB2BNotificationResultCode(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Request");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                if (studentElement.getElementsByTagName("ResultCode") != null){
                    String status = studentElement.getElementsByTagName("ResultCode").item(0).getTextContent();
                    System.out.println("ResultCode = " + status);
                    return status;
                }

            }
        }
        return "777";
    }

    public static String getB2BWallet2BankNotificationResultCode(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Result");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                if (studentElement.getElementsByTagName("ResultCode") != null){
                    String status = studentElement.getElementsByTagName("ResultCode").item(0).getTextContent();
                    System.out.println("ResultCode = " + status);
                    return status;
                }

            }
        }
        return "777";
    }

    public static String getBankToWalletNotificationResultCode(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Request");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                if (studentElement.getElementsByTagName("ResultCode") != null){
                    String status = studentElement.getElementsByTagName("ResultCode").item(0).getTextContent();
                    System.out.println("ResultCode = " + status);
                    return status;
                }

            }
        }
        return "777";
    }

    public static String getB2BNotificationResultDesc(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Request");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ResultDesc").item(0).getTextContent();
                System.out.println("ResultDesc = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getB2BWallet2BankNotificationResultDesc(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Result");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ResultDesc").item(0).getTextContent();
                System.out.println("ResultDesc = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getBankToWalletNotificationResultDesc(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Result");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ResultDesc").item(0).getTextContent();
                System.out.println("ResultDesc = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getB2BNotificationThirdPartyReference(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Request");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ThirdPartyReference").item(0).getTextContent();
                System.out.println("ThirdPartyReference = " + status);
                return status;
            }
        }
        return "666";
    }

    public static String getB2BWallet2BankNotificationThirdPartyReference(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Result");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ThirdPartyReference").item(0).getTextContent();
                System.out.println("ThirdPartyReference = " + status);
                return status;
            }
        }
        return "666";
    }

    public static String getBankToWalletNotificationThirdPartyReference(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Result");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ThirdPartyReference").item(0).getTextContent();
                System.out.println("ThirdPartyReference = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getB2BNotificationTransactionTime(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Request");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("TransactionTime").item(0).getTextContent();
                System.out.println("TransactionTime = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getB2BNotificationResultType(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Request");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("ResultType").item(0).getTextContent();
                System.out.println("ResultType = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getB2BNotificationTransactionID(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Request");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("TransactionID").item(0).getTextContent();
                System.out.println("TransactionID = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getB2BWallet2BankNotificationTransactionID(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Result");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("TransactionID").item(0).getTextContent();
                System.out.println("TransactionID = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getBankToWalletNotificationTransactionID(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Result");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("TransactionID").item(0).getTextContent();
                System.out.println("TransactionID = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getB2BNotificationInsightTxID(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Request");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("InsightTxID").item(0).getTextContent();
                System.out.println("InsightTxID = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getB2BWallet2BankNotificationInsightTxID(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Result");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("InsightReference").item(0).getTextContent();
                System.out.println("InsightReference = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String getBankToWalletNotificationInsightTxID(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Result");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("InsightTxID").item(0).getTextContent();
                System.out.println("InsightTxID = " + status);

                return status;
            }
        }
        return "666";
    }

    public static String proccessRequestQuipu(String merchantUrl, String merchantCode,
                                               String amount, String currency,
                                               String description, String approveUrl, String cancelUrl, String declineUrl) {
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try {
            //final URL url = new URL("http://192.168.2.143:60005/Exec");
            final URL url = new URL("http://"+merchantUrl+"/Exec");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

            //final String msg = "<?xml version='1.0' encoding='utf-8' ?><TKKPG><Request><Operation>CreateOrder</Operation><Language>FR</Language><Order><Merchant>SCPT</Merchant><Amount>10</Amount><Currency>840</Currency><Description>Facture</Description><ApproveURL>http://</ApproveURL><CancelURL>http</CancelURL> <DeclineURL>http</DeclineURL> <OrderType>Purchase</OrderType> </Order> </Request> </TKKPG>";
            final String msg = "<?xml version='1.0' encoding='utf-8' ?><TKKPG><Request><Operation>CreateOrder</Operation><Language>FR</Language><Order><Merchant>" + merchantCode + "</Merchant><Amount>"+amount+"00</Amount><Currency>" + currency + "</Currency><Description>" + description + "</Description><ApproveURL>" + approveUrl + "</ApproveURL><CancelURL>" + cancelUrl + "</CancelURL> <DeclineURL>" + declineUrl + "</DeclineURL> <OrderType>Purchase</OrderType> </Order> </Request> </TKKPG>";
            wr.writeBytes(msg);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();

            return content.toString();
        }catch (Exception e){
            System.out.println("Error proccessing Quipu : " + e.getMessage());
            return "";
        }
    }

    public static Map<String, String> getOrderRequestQuipu(Document doc)
    {
        Map<String, String> order = new HashMap<>();
        NodeList studentNodes = doc.getElementsByTagName("Order");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String orderId = studentElement.getElementsByTagName("OrderID").item(0).getTextContent();
                String sessionId = studentElement.getElementsByTagName("SessionID").item(0).getTextContent();
                String url = studentElement.getElementsByTagName("URL").item(0).getTextContent();
                System.out.println("Order Id = " + orderId);
                System.out.println("Session Id = " + sessionId);
                System.out.println("Url = " + url);
                order.put("order_id", orderId);
                order.put("session_id", sessionId);
               // if (orderId.equalsIgnoreCase("00")) {
                    String ucl = url+"?ORDERID="+orderId+"&SESSIONID="+sessionId;
                    order.put("url", ucl);
                //}
            }
        }
        return order;
    }

    public static String getStatusRequestQuipu(Document doc)
    {
        NodeList studentNodes = doc.getElementsByTagName("Response");
        for(int i=0; i<studentNodes.getLength(); i++)
        {
            Node studentNode = studentNodes.item(i);
            if(studentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element studentElement = (Element) studentNode;
                String status = studentElement.getElementsByTagName("Status").item(0).getTextContent();
                System.out.println("Status = " + status);

                return status;
            }
        }
        return "66";
    }

    public static String getOmC2BTransId(Document doc)
    {
        try{
            NodeList studentNodes = doc.getElementsByTagName("return");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    String status = studentElement.getElementsByTagName("transId").item(0).getTextContent();
                    System.out.println("transId OM c2b = " + status);

                    return status;
                }
            }
        }catch (Exception e){
            System.out.println("Traitement trans Id erreur : " + e.getMessage());
        }
        return "Trans id error";
    }

    public static String getOmC2BSystemId(Document doc)
    {
        try{
            NodeList studentNodes = doc.getElementsByTagName("return");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    String status = studentElement.getElementsByTagName("systemId").item(0).getTextContent();
                    System.out.println("transId OM c2b = " + status);

                    return status;
                }
            }
        }catch (Exception e){
            System.out.println("Traitement system Id erreur : " + e.getMessage());
        }
        return "system id error";
    }

    public static String getOmBalance(Document doc)
    {
        try{
            NodeList studentNodes = doc.getElementsByTagName("return");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    String status = studentElement.getElementsByTagName("balance").item(0).getTextContent();
                    System.out.println("balance OM  = " + status);
                    return status;
                }
            }
        }catch (Exception e){
            System.out.println("Traitement system Id erreur : " + e.getMessage());
        }
        return "error";
    }

    public static String getAiC2BResult(Document doc, String tag)
    {
        try{
            NodeList studentNodes = doc.getElementsByTagName("COMMAND");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    String status = studentElement.getElementsByTagName(tag).item(0).getTextContent();
                    System.out.println( tag + " = " + status);
                    return status;
                }
            }
        }catch (Exception e){
            System.out.println("Traitement result code erreur : " + e.getMessage());
        }

        return "0";
    }

    public static String getOmC2BResultCode(Document doc)
    {
        try{
            NodeList studentNodes = doc.getElementsByTagName("return");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    String status = studentElement.getElementsByTagName("resultCode").item(0).getTextContent();
                    System.out.println("resultCode OM c2b = " + status);

                    return status;
                }
            }
        }catch (Exception e){
            System.out.println("Traitement result code erreur : " + e.getMessage());
        }

        return "Result code error";
    }

    public static String getOmC2BResultDesc(Document doc)
    {
        try{
            NodeList studentNodes = doc.getElementsByTagName("return");
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    String status = studentElement.getElementsByTagName("resultDesc").item(0).getTextContent();
                    System.out.println("resultDesc OM c2b = " + status);

                    return status;
                }
            }
        }catch (Exception e){
            System.out.println("Traitement result desc error : " + e.getMessage());
        }
        return "777";
    }

    public static String getOmNotificationC2B(Document doc, String node, String elt)
    {
        try{
            NodeList studentNodes = doc.getElementsByTagName(node);
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    String status = studentElement.getElementsByTagName(elt).item(0).getTextContent();
                    System.out.println("Notification "+elt+" OM c2b = " + status);

                    return status;
                }
            }
        }catch (Exception e){
            System.out.println("Traitement result desc error : " + e.getMessage());
        }
        return "400";
    }

    public static String getXmlRequest(String sessionId, String CustomerMSISDN, String merchantCode, String Currency, String Amount, String strDate, String ref, String commandId,
                                       String Language, String callBackChannel, String callBackDestination, String merchantName, String paymentDesc) {
        StringBuilder requestStringC2B = new StringBuilder();
        requestStringC2B.append("<?xml version='1.0' encoding='UTF-8'?>")
                .append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:soap='http://www.4cgroup.co.za/soapauth' xmlns:gen='http://www.4cgroup.co.za/genericsoap'>")
                .append("<soapenv:Header>")
                .append("<soap:Token xmlns:soap='http://www.4cgroup.co.za/soapauth'>"+sessionId+"</soap:Token>")
                .append("<soap:EventID>80049</soap:EventID>")
                .append("</soapenv:Header>")
                .append("<soapenv:Body>")
                .append("<gen:getGenericResult>")
                .append("<Request>")
                .append("<dataItem><name>CustomerMSISDN</name><type>String</type><value>"+CustomerMSISDN+"</value></dataItem>")
                .append("<dataItem><name>ServiceProviderCode</name><type>String</type><value>"+merchantCode+"</value></dataItem>")
                .append("<dataItem><name>Currency</name><type>String</type><value>"+Currency+"</value></dataItem>")
                .append("<dataItem><name>Amount</name><type>String</type><value>"+Amount+"</value></dataItem>")
                .append("<dataItem><name>Date</name><type>String</type><value>"+strDate+"</value></dataItem>")
                .append("<dataItem><name>ThirdPartyReference</name><type>String</type><value>"+ref+"</value></dataItem>")
                .append("<dataItem><name>CommandId</name><type>String</type><value>"+commandId+"</value></dataItem>")
                .append("<dataItem><name>Language</name><type>String</type><value>"+Language+"</value></dataItem>")
                .append("<dataItem><name>CallBackChannel</name><type>String</type><value>"+callBackChannel+"</value></dataItem>")
                .append("<dataItem><name>CallBackDestination</name><type>String</type><value>"+callBackDestination+"</value></dataItem>")
                .append("<dataItem><name>Surname</name><type>String</type><value>"+merchantName+"</value></dataItem>")
                .append("<dataItem><name>Initials</name><type>String</type><value>"+paymentDesc+"</value></dataItem>")
                .append("</Request>")
                .append("</gen:getGenericResult>")
                .append("</soapenv:Body>")
                .append("</soapenv:Envelope>")
        ;
        return requestStringC2B.toString();
    }

    public static String getXmlRequestB2C(String sessionId, String CustomerMSISDN, String merchantCode, String Currency, String Amount, String strDate, String ref, String commandId,
                                       String Language, String callBackChannel, String callBackDestination, String merchantName, String paymentDesc) {
        StringBuilder requestStringC2B = new StringBuilder();
        requestStringC2B.append("<?xml version='1.0' encoding='UTF-8'?>")
                .append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>")
                .append("<soapenv:Header>")
                .append("<soap:EventID xmlns:soap='http://www.4cgroup.co.za/soapauth'>12001</soap:EventID>")
                .append("<soap:Token xmlns:soap='http://www.4cgroup.co.za/soapauth'>"+sessionId+"</soap:Token>")
                .append("</soapenv:Header>")
                .append("<soapenv:Body>")
                .append("<gen:getGenericResult xmlns:gen='http://www.4cgroup.co.za/genericsoap'>")
                .append("<Request>")
                .append("<dataItem><name>ServiceProviderName</name><type>String</type><value>ONE4ALL</value></dataItem>")
                .append("<dataItem><name>CustomerMSISDN</name><type>String</type><value>"+CustomerMSISDN+"</value></dataItem>")
                .append("<dataItem><name>Currency</name><type>String</type><value>"+Currency+"</value></dataItem>")
                .append("<dataItem><name>Amount</name><type>String</type><value>"+Amount+"</value></dataItem>")
                .append("<dataItem><name>TransactionDateTime</name><type>String</type><value>"+strDate+"</value></dataItem>")
                .append("<dataItem><name>Shortcode</name><type>String</type><value>"+merchantCode+"</value></dataItem>")
                .append("<dataItem><name>Language</name><type>String</type><value>"+Language+"</value></dataItem>")
                .append("<dataItem><name>ThirdPartyReference</name><type>String</type><value>"+ref+"</value></dataItem>")
                .append("<dataItem><name>CallBackChannel</name><type>String</type><value>"+callBackChannel+"</value></dataItem>")
                .append("<dataItem><name>CallBackDestination</name><type>String</type><value>"+callBackDestination+"</value></dataItem>")
                .append("<dataItem><name>CommandId</name><type>String</type><value>"+commandId+"</value></dataItem>")
                //.append("<dataItem><name>Surname</name><type>String</type><value>"+merchantName+"</value></dataItem>")
                //.append("<dataItem><name>Initials</name><type>String</type><value>"+paymentDesc+"</value></dataItem>")
                .append("</Request>")
                .append("</gen:getGenericResult>")
                .append("</soapenv:Body>")
                .append("</soapenv:Envelope>")
        ;
        return requestStringC2B.toString();
    }

    public static String proccessRequestOMCheckBalance(String token,String ref,
                                              String CustomerMSISDN, String partnId,
                                              String currency, String apiUrl) {

        final String msg = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://services.ws1.com/'>" +
                "<soapenv:Header/><soapenv:Body><ser:TcheckBal>\n" +
                "<subsmsisdn>"+CustomerMSISDN+"</subsmsisdn>\n" +
                "<PartnId>"+partnId+"</PartnId>\n" +
                "<transid>"+ref+"</transid>\n" +
                "<currency>"+currency+"</currency>\n" +
                "</ser:TcheckBal>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{
            SslUtil.disableSslVerification();
            //final URL url = new URL("https://41.77.223.184:8088/apigatewayom/apigwomService");
            final URL url = new URL(apiUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession sslSession) {
                    return true;
                }
            });
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("Authorization", token);
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(msg);
            System.out.println("*************************************************************");
            wr.writeBytes(msg);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();
            return content.toString();
        }catch (Exception e){
            System.out.println("******Orange Money********");
            System.out.println(e.getMessage());
        }
        return "777";
    }

    public static Map<String, String> proccessRequestOMCheckTransactionStatus(String token,String transactionCode,
                                                       String mermsisdn, String partnId, String apiUrl) {

        final String msg = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://services.ws1.com/'>" +
                "<soapenv:Header/><soapenv:Body><ser:doCheckTrans>\n" +
                "<mermsisdn>"+mermsisdn+"</mermsisdn>\n" +
                "<PartnId>"+partnId+"</PartnId>\n" +
                "<transid>"+transactionCode+"</transid>\n" +
                "</ser:doCheckTrans>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        Map<String, String> result  = new HashMap<>();

        try{
            SslUtil.disableSslVerification();
            //final URL url = new URL("https://41.77.223.184:8088/apigatewayom/apigwomService");
            final URL url = new URL(apiUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession sslSession) {
                    return true;
                }
            });
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("Authorization", token);
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(msg);
            System.out.println("*************************************************************");
            wr.writeBytes(msg);
            // send request
            wr.flush();
            // close
            wr.close();

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            in.close();

            DocumentBuilder dbrequest = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document docRequest = dbrequest.parse(new InputSource(new StringReader(content.toString())));
            String resultDesc = getOmC2BResultDesc(docRequest);
            String resultCode = getOmC2BResultCode(docRequest);
            String omRef = "00000";
            if (!resultCode.equalsIgnoreCase("")) {
                if (resultCode.equalsIgnoreCase("200")) {
                    if (resultDesc.contains("Ref:")) {
                        String[] tab = resultDesc.split("Ref:");
                        omRef = tab[1].trim();
                        System.out.println(omRef);
                        result.put("reference", omRef);
                    }
                }else {
                    if (resultDesc.contains("Ref:")) {
                        String[] tab = resultDesc.split("ID de transaction:");
                        omRef = tab[1].trim();
                        System.out.println(omRef);
                        result.put("reference", omRef);
                    }
                }
                result.put("description", content.toString());
                String transId = C2BUtil.getOmC2BTransId(docRequest);
                result.put("transId", transId);
            }
            result.put("resultCode", resultCode);
            result.put("resultDesc", resultDesc);
            return result;
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            result.put("resultCode", "xxx");
            result.put("resultDesc", "xxx");
            return result;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            result.put("resultCode", "xxx");
            result.put("resultDesc", "xxx");
            return result;
        } catch (SAXException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            result.put("resultCode", "xxx");
            result.put("resultDesc", "xxx");
            return result;
        }
    }

    public static String proccessRequestAiCheckTransactionStatus(String login, String password, String currency, String reference, String apiUrl) {
        String cur1 = "";
        if (currency.equalsIgnoreCase("101") || currency.equalsIgnoreCase("USD")) {
            cur1 = "101";
        }else {
            cur1 = "102";
        }

        String data = new StringBuilder()
                .append("<?xml version=\"1.0\"?>")
                .append("<COMMAND>")
                .append("<TYPE>TXNEQREQ</TYPE>")
                .append("<INTERFACEID>07910</INTERFACEID>")
                .append("<PROVIDER>"+cur1+"</PROVIDER>")
                .append("<EXTTRID>"+reference+"</EXTTRID>")
                .append("</COMMAND>").toString();

        System.out.println(data);

        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();

        try{
            SslUtil.disableSslVerification();
            //final URL url = new URL("https://41.77.223.184:8088/apigatewayom/apigwomService");
            //final URL url = new URL(apiUrl+"/"+login+"?LOGIN="+login+"&PASSWORD="+password);
            //String urlTo = apiUrl+"/"+login+"?LOGIN="+password;
            String urlTo = apiUrl+"/"+login+"?LOGIN="+login+"&PASSWORD="+password;
            System.out.println("URL AM : " + urlTo);
            final URL url = new URL(urlTo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(data);
            System.out.println("*************************************************************");
            wr.writeBytes(data);
            // send request
            wr.flush();
            // close
            wr.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                // read response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    content.append(str);
                }
                in.close();
                System.out.println(content.toString());
                return content.toString();
            }else {
                return "777";
            }
        }catch (Exception e){
            System.out.println("******Airtel Money exception ********");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "999";
        }
    }

    public static String getValueFromXml(Document doc, String parent, String title)
    {
        try{
            NodeList studentNodes = doc.getElementsByTagName(parent);
            for(int i=0; i<studentNodes.getLength(); i++)
            {
                Node studentNode = studentNodes.item(i);
                if(studentNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element studentElement = (Element) studentNode;
                    String status = studentElement.getElementsByTagName(title).item(0).getTextContent();
                    System.out.println("Title = " + status);
                    return status;
                }
            }
        }catch (Exception e){
            System.out.println("Traitement result desc error : " + e.getMessage());
        }
        return "777";
    }

    public static String checkAirtelEnquiryV2(String reference, String authorization, String country, String currency,String url) {
        System.out.println("Check airtel enquity V2");
        System.out.println("Reference : " + reference);
        System.out.println("Country : " + country);
        System.out.println("Currency : " + currency);
        System.out.println("Url : " + url);
        System.out.println("Authorization : " + authorization);

        try {
            //URL obj = new URL("/openapiuat.airtel.africa/standard/v1/payments/{id}");
            URL obj = new URL(url + reference);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Country", country);
            con.setRequestProperty("X-Currency", currency);
            con.setRequestProperty("Authorization", authorization);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            int responseCode = con.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                return response.toString();
            }else {
                System.out.println("Response code is not 200 or 201");
                return null;
            }

        }catch (Exception exception) {
            System.out.println("Exception : " + exception.getMessage());
            return null;
        }
    }

    public static String refundAirtelTransactionV2(String airtelMoneyId, String authorization, String country, String currency,String url) {
        System.out.println("Refund airtel transaction V2");
        System.out.println("Airtel Money ID : " + airtelMoneyId);
        System.out.println("Country : " + country);
        System.out.println("Currency : " + currency);
        System.out.println("Url : " + url);
        System.out.println("Authorization : " + authorization);

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Country", country);
            con.setRequestProperty("X-Currency", currency);
            con.setRequestProperty("Authorization", authorization);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            con.setDoOutput(true);
            con.setConnectTimeout(5000);

            // Content
            String data = "{\n" +
                    "  \"transaction\":{\n" +
                    "  \"airtel_money_id\": \""+airtelMoneyId+"\"\n" +
                    "}\n" +
                    "}";

            // Send post request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(data.getBytes());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                return response.toString();
            }else {
                System.out.println("Response code is not 200 or 201");
                return null;
            }
        }catch (Exception exception) {
            System.out.println("Exception : " + exception.getMessage());
            return null;
        }
    }
}
