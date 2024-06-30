package com.decagon.karrigobe.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackingLocationResponse {
    private Long trackId;
    private String location;
    private LocalDateTime time;
}