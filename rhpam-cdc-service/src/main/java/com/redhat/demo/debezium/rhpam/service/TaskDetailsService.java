package com.redhat.demo.debezium.rhpam.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import com.redhat.demo.debezium.rhpam.model.TaskVariables;

@ApplicationScoped
public class TaskDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskDetailsService.class);

    @PersistenceContext
    EntityManager entityManager;

    /**
     *
     * @param event
     * Payload
    {
    "schema": {
    "type": "struct",
    "fields": [
    {
    "type": "struct",
    "fields": [
    {
    "type": "int64",
    "optional": false,
    "field": "id"
    },
    {
    "type": "int64",
    "optional": true,
    "name": "io.debezium.time.Timestamp",
    "version": 1,
    "field": "modificationDate"
    },
    {
    "type": "string",
    "optional": true,
    "field": "name"
    },
    {
    "type": "string",
    "optional": true,
    "field": "processId"
    },
    {
    "type": "int64",
    "optional": true,
    "field": "processInstanceId"
    },
    {
    "type": "int64",
    "optional": true,
    "field": "taskId"
    },
    {
    "type": "int32",
    "optional": true,
    "field": "type"
    },
    {
    "type": "string",
    "optional": true,
    "field": "value"
    }
    ],
    "optional": true,
    "name": "rhpam6.inventory.TaskVariableImpl.Value",
    "field": "before"
    },
    {
    "type": "struct",
    "fields": [
    {
    "type": "int64",
    "optional": false,
    "field": "id"
    },
    {
    "type": "int64",
    "optional": true,
    "name": "io.debezium.time.Timestamp",
    "version": 1,
    "field": "modificationDate"
    },
    {
    "type": "string",
    "optional": true,
    "field": "name"
    },
    {
    "type": "string",
    "optional": true,
    "field": "processId"
    },
    {
    "type": "int64",
    "optional": true,
    "field": "processInstanceId"
    },
    {
    "type": "int64",
    "optional": true,
    "field": "taskId"
    },
    {
    "type": "int32",
    "optional": true,
    "field": "type"
    },
    {
    "type": "string",
    "optional": true,
    "field": "value"
    }
    ],
    "optional": true,
    "name": "rhpam6.inventory.TaskVariableImpl.Value",
    "field": "after"
    },
    {
    "type": "struct",
    "fields": [
    {
    "type": "string",
    "optional": false,
    "field": "version"
    },
    {
    "type": "string",
    "optional": false,
    "field": "connector"
    },
    {
    "type": "string",
    "optional": false,
    "field": "name"
    },
    {
    "type": "int64",
    "optional": false,
    "field": "ts_ms"
    },
    {
    "type": "string",
    "optional": true,
    "name": "io.debezium.data.Enum",
    "version": 1,
    "parameters": {
    "allowed": "true,last,false"
    },
    "default": "false",
    "field": "snapshot"
    },
    {
    "type": "string",
    "optional": false,
    "field": "db"
    },
    {
    "type": "string",
    "optional": true,
    "field": "table"
    },
    {
    "type": "int64",
    "optional": false,
    "field": "server_id"
    },
    {
    "type": "string",
    "optional": true,
    "field": "gtid"
    },
    {
    "type": "string",
    "optional": false,
    "field": "file"
    },
    {
    "type": "int64",
    "optional": false,
    "field": "pos"
    },
    {
    "type": "int32",
    "optional": false,
    "field": "row"
    },
    {
    "type": "int64",
    "optional": true,
    "field": "thread"
    },
    {
    "type": "string",
    "optional": true,
    "field": "query"
    }
    ],
    "optional": false,
    "name": "io.debezium.connector.mysql.Source",
    "field": "source"
    },
    {
    "type": "string",
    "optional": false,
    "field": "op"
    },
    {
    "type": "int64",
    "optional": true,
    "field": "ts_ms"
    },
    {
    "type": "struct",
    "fields": [
    {
    "type": "string",
    "optional": false,
    "field": "id"
    },
    {
    "type": "int64",
    "optional": false,
    "field": "total_order"
    },
    {
    "type": "int64",
    "optional": false,
    "field": "data_collection_order"
    }
    ],
    "optional": true,
    "field": "transaction"
    }
    ],
    "optional": false,
    "name": "rhpam6.inventory.TaskVariableImpl.Envelope"
    },
    "payload": {
    "before": null,
    "after": {
    "id": 1,
    "modificationDate": 1607445118000,
    "name": "tImportantVarIn",
    "processId": "ht-basics.simple-ht",
    "processInstanceId": 52,
    "taskId": 10,
    "type": 0,
    "value": "Level-0"
    },
    "source": {
    "version": "1.3.1.Final",
    "connector": "mysql",
    "name": "rhpam6",
    "ts_ms": 1607445118000,
    "snapshot": "false",
    "db": "inventory",
    "table": "TaskVariableImpl",
    "server_id": 223344,
    "gtid": null,
    "file": "mysql-bin.000004",
    "pos": 24200,
    "row": 0,
    "thread": null,
    "query": null
    },
    "op": "c",
    "ts_ms": 1607445118555,
    "transaction": null
    }
    }
     */
    @Transactional(value=TxType.MANDATORY)
    public void taskVariablesCreatedUpdated(JsonNode event) {

        try {
            LOGGER.info("Saving 'TaskCreated' event: {}", event);

            final long taskId = event.get("taskId").asLong();
            LOGGER.info("taskId {}", taskId);

            final String varName = event.get("name").asText();
            LOGGER.info("varName {}", varName);

            final String varValue = event.get("value").asText();
            LOGGER.info("varValue {}", varValue);

            LOGGER.info("date {}", event.get("modificationDate").asLong());

            LocalDateTime date =
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(event.get("modificationDate").asLong()), ZoneId.systemDefault());

            final long pId = event.get("processInstanceId").asLong();
            LOGGER.info("pId {}", pId);

            entityManager.persist(new TaskVariables(taskId, varName, varValue, date, pId));

        } catch (Exception e) {
            LOGGER.info("taskVariablesCreatedUpdated problem");
            LOGGER.info(e.toString());
            LOGGER.info(e.getCause().toString());
            LOGGER.info(e.getMessage().toString());
            e.printStackTrace();
        }
    }
}
