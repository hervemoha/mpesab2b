package com.infosetgroup.amv2.util;

public class ErrorCodesUtil {

    public static ErrorCode collectionApi(String code) {
        ErrorCode errorCode = new ErrorCode();
        switch (code){
            case "DP00800001001":
                errorCode.setCode(code);
                errorCode.setReason("Success");
                errorCode.setDescription("Transaction is successful");
                break;
            case "DP00800001002":
                errorCode.setCode(code);
                errorCode.setReason("Incorrect Pin");
                errorCode.setDescription("Incorrect Pin has been entered");
                break;
            case "DP00800001003":
                errorCode.setCode(code);
                errorCode.setReason("Exceeds withdrawal amount limit(s) / Withdrawal amount limit exceeded");
                errorCode.setDescription("The User has exceeded their wallet allowed transaction limit");
                break;
            case "DP00800001004":
                errorCode.setCode(code);
                errorCode.setReason("Invalid Amount");
                errorCode.setDescription("The amount User is trying to transfer is less than the minimum amount allowed");
                break;
            case "DP00800001005":
                errorCode.setCode(code);
                errorCode.setReason("Transaction ID is invalid");
                errorCode.setDescription("User didn't enter the pin");
                break;
            case "DP00800001006":
                errorCode.setCode(code);
                errorCode.setReason("In process");
                errorCode.setDescription("Transaction in pending state. Please check after sometime");
                break;
            case "DP00800001007":
                errorCode.setCode(code);
                errorCode.setReason("Not enough balance");
                errorCode.setDescription("User wallet does not have enough money to cover the payable amount");
                break;
            case "DP00800001008":
                errorCode.setCode(code);
                errorCode.setReason("Refused");
                errorCode.setDescription("The transaction was refused");
                break;
            case "DP00800001009":
                errorCode.setCode(code);
                errorCode.setReason("Do not honor");
                errorCode.setDescription("This is a generic refusal that has several possible causes");
                break;
            case "DP00800001010":
                errorCode.setCode(code);
                errorCode.setReason("Transaction not permitted to Payee");
                errorCode.setDescription("Payee is already initiated for churn or barred or not registered on Airtel Money platform");
                break;
            default:
                errorCode.setCode(code);
                errorCode.setReason("Error");
                errorCode.setDescription("Code not found");
        }
        return errorCode;
    }

    public static ErrorCode disbursementApi(String code) {
        ErrorCode errorCode = new ErrorCode();
        switch (code){
            case "DP00900001001":
                errorCode.setCode(code);
                errorCode.setReason("Success");
                errorCode.setDescription("Transaction is successful");
                break;
            case "DP00900001003":
                errorCode.setCode(code);
                errorCode.setReason("Maximum transaction limit reached");
                errorCode.setDescription("Maximum transaction limit reached for the day");
                break;
            case "DP00900001004":
                errorCode.setCode(code);
                errorCode.setReason("Invalid Amount");
                errorCode.setDescription("Amount entered is out of range with respect to defined limits");
                break;
            case "DP00900001007":
                errorCode.setCode(code);
                errorCode.setReason("Insufficient Funds");
                errorCode.setDescription("Not enough funds in account to complete the transaction");
                break;
            case "DP00900001009":
                errorCode.setCode(code);
                errorCode.setReason("Invalid Initiatee");
                errorCode.setDescription("Initiatee of the transaction is invalid");
                break;
            case "DP00900001010":
                errorCode.setCode(code);
                errorCode.setReason("User not allowed");
                errorCode.setDescription("Payer is not an allowed user");
                break;
            case "DP00900001011":
                errorCode.setCode(code);
                errorCode.setReason("Transaction not allowed");
                errorCode.setDescription("Transaction with same information already exists in this system");
                break;
            case "DP00900001012":
                errorCode.setCode(code);
                errorCode.setReason("Invalid mobile number");
                errorCode.setDescription("Mobile number entered is incorrect");
                break;
            case "DP00900001013":
                errorCode.setCode(code);
                errorCode.setReason("Refused");
                errorCode.setDescription("The transaction was refused");
                break;
            default:
                errorCode.setCode(code);
                errorCode.setReason("Error");
                errorCode.setDescription("Code not found");
        }
        return errorCode;
    }
}
