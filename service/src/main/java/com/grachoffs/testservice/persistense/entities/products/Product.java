package com.grachoffs.testservice.persistense.entities.products;

import com.grachoffs.testservice.persistense.entities.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(indexes = {
        @Index(columnList = "code")
})
public class Product extends BaseEntity {
    @Column(length = 13, unique = true)
    @NotNull
    private String code;

    @NotNull
    private String name;
}
