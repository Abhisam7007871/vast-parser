package com.avshek.vast_parser.model;

import jakarta.persistence.*;

@Entity
@Table(name = "impressions")
public class Impression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    private String vastId;
    private String url;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
