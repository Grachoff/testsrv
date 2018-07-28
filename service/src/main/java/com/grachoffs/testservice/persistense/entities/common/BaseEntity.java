package com.grachoffs.testservice.persistense.entities.common;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.Calendar;

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Calendar created;

    @PrePersist
    public void prePersist() {
        created = Calendar.getInstance();
    }

}
