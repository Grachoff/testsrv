package com.grachoffs.testservice.services.transactions;

import com.grachoffs.testservice.services.common.BaseService;
import common.RestResult;
import dtos.transactions.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class TransactionProcessorServiceImpl extends BaseService implements TransactionProcessorService {
    private final int PACKET_SIZE;
    private final String FAILSAFE_FILE_NAME;
    private final AtomicBoolean isInterrupted = new AtomicBoolean(false);
    private final BlockingQueue<TransactionDto> queue;
    private final TransactionService transactionService;

    public TransactionProcessorServiceImpl(
            @Value("${app.queueSize}") int queueSize,
            @Value("${app.packetSize}") int packetSize,
            @Value("${app.workersCount}") int workersCount,
            @Value("${app.failsafeFileName}") String failsafeFileName,
            TransactionService transactionService,
            ExecutorService executorService) {

        this.PACKET_SIZE = packetSize;
        this.transactionService = transactionService;
        this.FAILSAFE_FILE_NAME = failsafeFileName;

        this.queue = createQueue(queueSize);
        for (int i=0; i< workersCount; i++) executorService.submit(this::worker);
    }

    private BlockingQueue<TransactionDto> createQueue(int queueSize) {
        File file = new File(FAILSAFE_FILE_NAME);
        if (file.exists() && !file.isDirectory()) {
            try (FileInputStream fin = new FileInputStream(file)) {
                ObjectInputStream ois = new ObjectInputStream(fin);
                BlockingQueue<TransactionDto> tmpQueue = (BlockingQueue<TransactionDto>) ois.readObject();
                if (!file.delete()) {
                    log.error("Error deleting file: {}", FAILSAFE_FILE_NAME);
                }
                return tmpQueue;
            } catch (IOException e) {
                log.error("Error reading from file", e);
            } catch (ClassNotFoundException e) {
                log.error("Class not found in file", e);
            }
        }
        return new ArrayBlockingQueue<>(queueSize);
    }

    @Override
    public RestResult receiveData(TransactionDto transactionDto) {
        queue.add(transactionDto);
        return RestResult.OK;
    }

    private void worker() {
        List<TransactionDto> transactionsForProcessing = new ArrayList<>(PACKET_SIZE);
        TransactionDto tmpTransactionDto;

        while (!isInterrupted.get()) {
            try {
                transactionsForProcessing.clear();
                transactionsForProcessing.add(queue.take());
                do {
                    tmpTransactionDto = queue.poll();
                    if (tmpTransactionDto != null) transactionsForProcessing.add(tmpTransactionDto);
                } while (transactionsForProcessing.size() < PACKET_SIZE && tmpTransactionDto != null);
                transactionService.save(transactionsForProcessing);
            } catch (InterruptedException e) {
                log.info("Worker was interrupted: {}", e);
                isInterrupted.set(true);
            }
        }
    }

    @PreDestroy
    public void waitForQueue() {
        log.info("Stopping workers...");
        try {
            int counter = 1000;
            while (queue.size()>0 && counter > 0) {
                Thread.sleep(33L);
                counter--;
            }
        } catch (InterruptedException e) {
            isInterrupted.set(true);
            log.error("Abnormal worker termination!");
        }
        if (queue.size()>0) writeQueueToDisk(queue);

    }

    private void writeQueueToDisk(BlockingQueue<TransactionDto> queue) {
        log.info("Writing queue ti disk!");
        try (FileOutputStream fout = new FileOutputStream(FAILSAFE_FILE_NAME)) {
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(queue);
        } catch (IOException e) {
            log.error("Error writing to file", e);
        }
    }

}
