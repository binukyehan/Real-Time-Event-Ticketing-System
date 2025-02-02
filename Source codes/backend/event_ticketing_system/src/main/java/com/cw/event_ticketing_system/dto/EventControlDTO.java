package com.cw.event_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventControlDTO {
    private Long id;
    private boolean eventStatus;
}
