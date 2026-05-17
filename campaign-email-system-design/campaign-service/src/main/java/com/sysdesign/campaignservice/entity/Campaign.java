package com.sysdesign.campaignservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_campaign")
@Getter
@Setter
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime startTime;

    @Column(length = 2000)
    private String query;

    @Column(length = 5000)
    private String template;

    private String status; //CREATED, RUNNING, COMPLETED
}
