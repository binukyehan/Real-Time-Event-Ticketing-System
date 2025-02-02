package com.cw.event_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDeleteDTO {
    private long id;
    private String confirmation;
}
