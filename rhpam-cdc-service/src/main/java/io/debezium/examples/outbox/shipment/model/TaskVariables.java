package io.debezium.examples.outbox.shipment.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TaskVariables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private long taskId;
    private String name;
    private String value;
    private LocalDateTime changeDate;
    private long proceInstanceId;

    TaskVariables() {
    }

    public TaskVariables(Long taskId, String name, String value, LocalDateTime date, long pId) {
        this.taskId = taskId;
        this.name = name;
        this.value = value;
        this.changeDate = date;
        this.proceInstanceId = pId;

    }


    public Long getId() {
        return id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String orderId) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String orderId) {
        this.value = value;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }

    public Long getProceInstanceId() {
        return proceInstanceId;
    }

    public void setProceInstanceId(Long proceInstanceId) {
        this.taskId = proceInstanceId;
    }
}
