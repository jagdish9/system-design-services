package com.sysdesign.commonevents.dto;

import java.time.LocalDateTime;

public class CampaignDto {
    private Long id;

    private String name;

    private LocalDateTime startTime;

    private String query;

    private String template;

    private String status;

    public CampaignDto() {}

    public CampaignDto(Long id, String name, LocalDateTime startTime, String query, String template, String status) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.query = query;
        this.template = template;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
