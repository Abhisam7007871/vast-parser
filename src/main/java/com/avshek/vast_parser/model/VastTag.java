package com.avshek.vast_parser.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "vast_tags")
public class Vastg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vast_id", unique = true) // Ensure it's unique
    private String vastId;

    private String title;
    private String description;
    private String version;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "impression_id") // Foreign key column in vast_tags table
    private Impression impression;

    @OneToMany(mappedBy = "vastTag", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Creative> creatives;

    // Getters and Setters...


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Impression getImpression() {
        return impression;
    }

    public void setImpression(Impression impression) {
        this.impression = impression;
    }

    public List<Creative> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<Creative> creatives) {
        this.creatives = creatives;
    }
}

