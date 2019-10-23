package com.example;
import java.time.LocalDateTime;
import java.util.UUID;

public class Event {
    private UUID id;
    private LocalDateTime timeTag;
    private String description;

    public Event(UUID id, LocalDateTime timeTag, String description) {
        this.id = id;
        this.timeTag = timeTag;
        this.description = description;

        System.out.printf("Generated %s", id.toString());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(LocalDateTime timeTag) {
        this.timeTag = timeTag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}