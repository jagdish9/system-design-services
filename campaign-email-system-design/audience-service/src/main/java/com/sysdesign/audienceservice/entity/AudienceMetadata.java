package com.sysdesign.audienceservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_audience_metadata")
@Getter
@Setter
public class AudienceMetadata {

    @Id
    @Column(name = "m_key")
    private String key;

    @Column(name = "m_value")
    private boolean value;
}
