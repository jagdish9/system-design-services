package com.sysdesign.commonevents.event;

public class CampaignStatusEvent {
    private Long campaignId;
    private String status;

    public CampaignStatusEvent() {}

    public CampaignStatusEvent(Long campaignId, String status) {
        this.campaignId = campaignId;
        this.status = status;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
