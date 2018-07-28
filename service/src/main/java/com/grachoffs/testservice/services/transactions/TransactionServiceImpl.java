package com.grachoffs.testservice.services.transactions;

import com.grachoffs.testservice.persistense.entities.products.Product;
import com.grachoffs.testservice.persistense.entities.transactions.Transaction;
import com.grachoffs.testservice.persistense.mappers.transactions.TransactionMapper;
import com.grachoffs.testservice.persistense.repositories.products.ProductDao;
import com.grachoffs.testservice.persistense.repositories.transactions.TransactionDao;
import com.grachoffs.testservice.services.common.BaseService;
import dtos.products.ProductForTransactionDto;
import dtos.transactions.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Service
@Slf4j
public class TransactionServiceImpl extends BaseService implements TransactionService {
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    TransactionDao transactionDao;
    @Autowired
    ProductDao productDao;

    @Override
    public void save(List<TransactionDto> transactionsForProcessing) {
        log.info("Storing transactions: {}", transactionsForProcessing);
        if (isEmpty(transactionsForProcessing)) return;

        Map<String, Product> preparedProducts = prepareProductsForTransactions(transactionsForProcessing);
        List<Transaction> transactions = createTransactionEntitiesWithPreparedProducts(transactionsForProcessing, preparedProducts);
        transactionDao.saveAll(transactions);

    }

    private List<Transaction> createTransactionEntitiesWithPreparedProducts(List<TransactionDto> transactionsForProcessing, Map<String, Product> preparedProducts) {
        List<Transaction> transactionEntities = new ArrayList<>(transactionsForProcessing.size());
        for (TransactionDto transactionDto : transactionsForProcessing) {
            List<Product> productsForTransaction = new ArrayList<>(transactionDto.getProducts().size());
            transactionDto.getProducts().forEach(item -> productsForTransaction.add(preparedProducts.get(item.getCode())));

            Transaction newTransaction = Transaction
                    .builder()
                    .customer(transactionDto.getCustomer())
                    .seller(transactionDto.getSeller())
                    .products(productsForTransaction)
                    .build();

            transactionEntities.add(newTransaction);
        }

        return transactionEntities;
    }

    private Map<String, Product> prepareProductsForTransactions(List<TransactionDto> transactions) {
        List<ProductForTransactionDto> allProducts = transactions
                        .stream()
                        .map(item -> item.getProducts())
                        .flatMap(products -> products.stream())
                        .distinct()
                        .collect(Collectors.toList());

        List<Product> existingProducts = productDao.findAllByCodeIn(
                allProducts
                        .stream()
                        .map(item->item.getCode())
                        .collect(Collectors.toList())
        );

        Map<String, Product> preparedProducts = new HashMap<>(allProducts.size());
        existingProducts.forEach(item -> preparedProducts.put(item.getCode(), item));

        if (preparedProducts.size() == allProducts.size()) return preparedProducts;

        for (ProductForTransactionDto productDto : allProducts) {
            if (!preparedProducts.containsKey(productDto.getCode())) {
                Product product = transactionMapper.toProductEntity(productDto);
                preparedProducts.put(productDto.getCode(), productDao.save(product));
            }
        }
        return preparedProducts;
    }
}
