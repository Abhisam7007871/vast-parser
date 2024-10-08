package com.avshek.vast_parser.model;

public class VastResponse {
    private String vastContent;
    private String message;
    private boolean success;

    public VastResponse(String vastContent, String message, boolean success) {
        this.vastContent = vastContent;
        this.message = message;
        this.success = success;
    }

    // Getters and setters

    public String getVastContent() {
        return vastContent;
    }

    public void setVastContent(String vastContent) {
        this.vastContent = vastContent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
