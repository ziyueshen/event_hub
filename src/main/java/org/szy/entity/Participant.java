package org.szy.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Participant {
    private Long userID;
    private Long activityID;
    private LocalDateTime signUpTime;
}
