package com.sysdesign.commonevents.event;

public class EmailEvent {
    private Long campaignId;
    private Long userId;
    private String email;
    private String template;

    public EmailEvent() {}

    public EmailEvent(Long campaignId, Long userId, String email, String template) {
        this.campaignId = campaignId;
        this.userId = userId;
        this.email = email;
        this.template = template;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getTemplate() {
        return template;
    }
}
