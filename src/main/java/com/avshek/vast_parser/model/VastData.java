package com.avshek.vast_parser.model;

import jakarta.persistence.*;
import java.util.List;
         
@Entity
@Table(name = "vast_data")
public class VastData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String version;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vast_data_id")
    private List<Impression> impressions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vast_data_id")
    private List<Creative> creatives;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Impression> getImpressions() {
        return impressions;
    }

    public void setImpressions(List<Impression> impressions) {
        this.impressions = impressions;
    }

    public List<Creative> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<Creative> creatives) {
        this.creatives = creatives;
    }

    @Entity
    @Table(name = "impression")
    public static class Impression {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String impressionId;
        private String url;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getImpressionId() {
            return impressionId;
        }

        public void setImpressionId(String impressionId) {
            this.impressionId = impressionId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Entity
    @Table(name = "creative")
    public static class Creative {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String creativeId;
        private String duration;

        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name = "creative_id")
        private List<CompanionBanner> companionBanners;

        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name = "creative_id")
        private List<TrackingEvent> trackingEvents;

        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name = "creative_id")
        private List<MediaFile> mediaFiles;

        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name = "creative_id")
        private List<VideoClick> videoClicks;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCreativeId() {
            return creativeId;
        }

        public void setCreativeId(String creativeId) {
            this.creativeId = creativeId;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public List<CompanionBanner> getCompanionBanners() {
            return companionBanners;
        }

        public void setCompanionBanners(List<CompanionBanner> companionBanners) {
            this.companionBanners = companionBanners;
        }

        public List<TrackingEvent> getTrackingEvents() {
            return trackingEvents;
        }

        public void setTrackingEvents(List<TrackingEvent> trackingEvents) {
            this.trackingEvents = trackingEvents;
        }

        public List<MediaFile> getMediaFiles() {
            return mediaFiles;
        }

        public void setMediaFiles(List<MediaFile> mediaFiles) {
            this.mediaFiles = mediaFiles;
        }

        public List<VideoClick> getVideoClicks() {
            return videoClicks;
        }

        public void setVideoClicks(List<VideoClick> videoClicks) {
            this.videoClicks = videoClicks;
        }
    }

    @Entity
    @Table(name = "companion_banner")
    public static class CompanionBanner {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String bannerId;
        private int width;
        private int height;
        private String type;
        private String source;
        private String clickThroughUrl;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getBannerId() {
            return bannerId;
        }

        public void setBannerId(String bannerId) {
            this.bannerId = bannerId;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }

    @Entity
    @Table(name = "tracking_event")
    public static class TrackingEvent {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String eventType;
        private String eventUrl;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getEventUrl() {
            return eventUrl;
        }

        public void setEventUrl(String eventUrl) {
            this.eventUrl = eventUrl;
        }
    }

    @Entity
    @Table(name = "media_file")
    public static class MediaFile {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String type;
        private int bitrate;
        private int width;
        private int height;
        private String source;

        // Getters and Setters
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

        public int getBitrate() {
            return bitrate;
        }

        public void setBitrate(int bitrate) {
            this.bitrate = bitrate;
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
    }

    @Entity
    @Table(name = "video_click")
    public static class VideoClick {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String clickId;
        private String url;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getClickId() {
            return clickId;
        }

        public void setClickId(String clickId) {
            this.clickId = clickId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
