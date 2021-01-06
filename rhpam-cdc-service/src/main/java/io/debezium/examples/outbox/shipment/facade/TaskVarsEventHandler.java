package io.debezium.examples.outbox.shipment.facade;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.debezium.examples.outbox.shipment.messagelog.MessageLog;
import io.debezium.examples.outbox.shipment.service.TaskDetailsService;

@ApplicationScoped
public class TaskVarsEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TaskVarsEventHandler.class);

    @Inject
    MessageLog log;

    @Inject
    TaskDetailsService taskDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void onTaskEvent(UUID eventUUID, String key, String event, Instant ts) {

        LOG.info("In onOrderEvent event -- key: {}, event id: '{}', ts: '{}'", key,  eventUUID,  ts);

        // TODO - Bring in the end to ensure no message re-processing happens
//            if (log.alreadyProcessed( eventUUID)) {
//                LOG.info("Event with UUID {} was already retrieved, ignoring it", eventId);
//                return;
//            }

        try {
            JsonNode eventPayload = deserialize(event);

            LOG.info("Processing 'TaskCreated' event: {}", eventPayload.toString());

            LOG.info("pre taskVariablesCreatedUpdated");
            taskDetailsService.taskVariablesCreatedUpdated(eventPayload);

            LOG.info("post taskVariablesCreatedUpdated");


//        if (eventType.equals("OrderCreated")) {
//            shipmentService.orderCreated(eventPayload);
//        }
//        else if (eventType.equals("OrderLineUpdated")) {
//            shipmentService.orderLineUpdated(eventPayload);
//        }
//        else {
//            LOGGER.warn("Unkown event type");
//        }

       // TODO - Bring in the end to ensure no message re-processing happens
       // log.processed(eventId);

        } catch (Exception e) {
            LOG.info("onTaskEvent problem");
            LOG.info(e.toString());
            LOG.info(e.getCause().toString());
            LOG.info(e.getMessage().toString());
            e.printStackTrace();
        }

    }

        private JsonNode deserialize(String event) {
            JsonNode eventPayload;

            LOG.info("deserialize 1");

            try {
                LOG.info("deserialize 2");

                LOG.info("deserialize 3");

                eventPayload = objectMapper.readTree(event);
                LOG.info("deserialize 4");

            }
            catch (IOException e) {
                LOG.info("deserialize 5");
                LOG.info(e.toString());
                LOG.info(e.getCause().toString());
                LOG.info(e.getMessage().toString());
                throw new RuntimeException("Couldn't deserialize event", e);
            }

            LOG.info("deserialize 6");

            return eventPayload.has("schema") ? eventPayload.at("/payload/after") : eventPayload;
        }
}
