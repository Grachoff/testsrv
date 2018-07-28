package com.grachoffs.testservice.persistense.mappers.transactions;

import com.grachoffs.testservice.persistense.entities.products.Product;
import com.grachoffs.testservice.persistense.entities.transactions.Transaction;
import dtos.products.ProductForTransactionDto;
import dtos.transactions.TransactionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TransactionMapper {
    ProductForTransactionDto toProductDto(Product product);
    List<ProductForTransactionDto> toProductDtos(List<Product> products);
    Product toProductEntity(ProductForTransactionDto productDto);
    List<Product> toProductEntities(List<ProductForTransactionDto> productDto);

    TransactionDto toTransactionDto(Transaction transaction);
    List<TransactionDto> toTransactionDtos(List<Transaction> transactions);
    Transaction toTransactionEntity(TransactionDto transaction);
    List<Transaction> toTransactionEntities(List<TransactionDto> productDto);
}
