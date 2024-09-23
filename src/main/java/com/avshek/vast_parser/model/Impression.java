package com.avshek.vast_parser.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Impression {
    @Column(name = "impression_vast_id") // Rename to avoid conflict
    private String vastId;

    private String url;

    // Getters and Setters...


    public String getVastId() {
        return vastId;
    }

    public void setVastId(String vastId) {
        this.vastId = vastId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
