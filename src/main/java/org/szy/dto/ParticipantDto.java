package org.szy.dto;

import lombok.Data;
import org.szy.entity.Participant;

import java.time.LocalDateTime;

@Data
public class ParticipantDto extends Participant {
    private String name;
    private String description;
    private LocalDateTime startTime;
}
