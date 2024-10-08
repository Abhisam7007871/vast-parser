package com.avshek.vast_parser.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Creative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vast_tag_id")
    private VastData vastTag;

    private int duration;

    @OneToMany(mappedBy = "creative", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompanionBanner> companionBanners;

    // Getters and Setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VastData getVastTag() {
        return vastTag;
    }

    public void setVastTag(VastData vastTag) {
        this.vastTag = vastTag;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<CompanionBanner> getCompanionBanners() {
        return companionBanners;
    }

    public void setCompanionBanners(List<CompanionBanner> companionBanners) {
        this.companionBanners = companionBanners;
    }
}
