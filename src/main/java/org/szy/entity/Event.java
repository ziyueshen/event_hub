package org.szy.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Event {
    private Long id;
    private String name;
    // 0 online, 1 offline
    private Integer status;
    private Integer capacity;
    private Integer numSignedUp;
    private String detail;
    private String image;
    private String category;
    private String format;
    private String state;
    private String city;
    private String address;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long creatorId;
}
