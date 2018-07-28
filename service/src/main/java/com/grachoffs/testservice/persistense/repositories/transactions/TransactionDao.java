package com.grachoffs.testservice.persistense.repositories.transactions;

import com.grachoffs.testservice.persistense.entities.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<Transaction, Long> {
}
