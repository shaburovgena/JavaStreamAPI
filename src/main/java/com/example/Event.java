package com.example;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "timeTag", "description"})
public class Event {
    private UUID id;
    private LocalDateTime timeTag;
    private String description;


}