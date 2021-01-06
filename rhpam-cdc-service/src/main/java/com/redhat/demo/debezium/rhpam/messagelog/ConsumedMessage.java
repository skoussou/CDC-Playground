package com.redhat.demo.debezium.rhpam.messagelog;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ConsumedMessage {

    @Id
    private UUID eventId;

    private Instant timeOfReceiving;

    ConsumedMessage() {
    }

    public ConsumedMessage(UUID eventId, Instant timeOfReceiving) {
        this.eventId = eventId;
        this.timeOfReceiving = timeOfReceiving;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Instant getTimeOfReceiving() {
        return timeOfReceiving;
    }

    public void setTimeOfReceiving(Instant timeOfReceiving) {
        this.timeOfReceiving = timeOfReceiving;
    }
}
