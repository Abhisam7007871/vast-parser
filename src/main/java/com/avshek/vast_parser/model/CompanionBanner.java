package com.avshek.vast_parser.model;

import jakarta.persistence.*;

@Entity
public class CompanionBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private int width;
    private int height;
    private String source;
    private String clickThroughUrl;

    @ManyToOne
    @JoinColumn(name = "creative_id")
    private Creative creative;

    // Getters and Setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getClickThroughUrl() {
        return clickThroughUrl;
    }

    public void setClickThroughUrl(String clickThroughUrl) {
        this.clickThroughUrl = clickThroughUrl;
    }

    public Creative getCreative() {
        return creative;
    }

    public void setCreative(Creative creative) {
        this.creative = creative;
    }
}
