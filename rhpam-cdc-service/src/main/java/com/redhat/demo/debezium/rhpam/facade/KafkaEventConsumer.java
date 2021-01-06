package com.redhat.demo.debezium.rhpam.facade;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


import org.apache.kafka.common.header.Header;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped
public class KafkaEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaEventConsumer.class);

    @Inject
    TaskVarsEventHandler taskEventHandlerr;

    @Incoming("taskdetails")
    @Transactional
    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) throws IOException {
        return CompletableFuture.runAsync(() -> {
                LOG.info("Kafka message with key = {} arrived", message.getKey());
                LOG.info("Kafka message with payload = {} arrived", message.getPayload());

                // TODO - Pass the correlatin ID
                //String eventId = getHeaderAsString(message, "id");
                // This comes from register-postgres.json transformer     "transforms.outbox.table.fields.additional.placement" : "type:header:eventType"
                //String eventType = getHeaderAsString(message, "eventType");

            try {

                //String eventId = "ht-task-even-"+System.currentTimeMillis();
                LOG.debug("Creating Random UUID - prefereable if it was not Random to avoid reprocessing messages");
                UUID eventUUID = UUID.randomUUID();

                taskEventHandlerr.onTaskEvent(
                        //UUID.fromString(eventId),
                        eventUUID,
                        message.getKey(),
                        message.getPayload(),
                        message.getTimestamp()
                );
                LOG.debug("post taskEventHandlerr.onOrderEvent");

            } catch (Exception e){
                LOG.info("Handling problem");
                LOG.info(e.toString());
                LOG.info(e.getCause().toString());
                LOG.info(e.getMessage().toString());
            }

        });
    }

    private String getHeaderAsString(KafkaRecord<?, ?> record, String name) {
        LOG.info("Message Headers {}", record.getHeaders());
        Header header = record.getHeaders().lastHeader(name);
        if (header == null) {
            throw new IllegalArgumentException("Expected record header '" + name + "' not present");
        }

        return new String(header.value(), Charset.forName("UTF-8"));
    }

}
