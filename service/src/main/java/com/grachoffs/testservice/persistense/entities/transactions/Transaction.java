package com.grachoffs.testservice.persistense.entities.transactions;

import com.grachoffs.testservice.persistense.entities.common.BaseEntity;
import com.grachoffs.testservice.persistense.entities.products.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@Entity
@Table(indexes = {
        @Index(columnList = "seller"),
        @Index(columnList = "customer")
})
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {
    @Column(length = 9)
    @NotNull
    private String seller;

    @Column(length = 9)
    @NotNull
    private String customer;

    @ManyToMany
    @CollectionTable(
            indexes = {
                    @Index(columnList = "transaction_id"),
                    @Index(columnList = "products_id"),
                    @Index(columnList = "transaction_id, products_id", unique = true)
            }
    )
    @NotNull
    private List<Product> products;
}
