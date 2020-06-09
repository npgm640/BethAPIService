package com.beth.infy.domain;

public class CommonResponse {
    public String responseCode;  //success or error
    public int reasonCode;     //success or error
    public String messageText;

    public CommonResponse() {
    }

    public CommonResponse(String responseCode, int reasonCode) {
        this.responseCode = responseCode;
        this.reasonCode = reasonCode;
    }

    public CommonResponse(String responseCode, int reasonCode, String messageText) {
        this.responseCode = responseCode;
        this.reasonCode = reasonCode;
        this.messageText = messageText;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public int getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(int reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
