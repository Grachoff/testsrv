package com.grachoffs.testservice.persistense.repositories.products;

import com.grachoffs.testservice.persistense.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {
    List<Product> findAllByCodeIn(List<String> code);
}
