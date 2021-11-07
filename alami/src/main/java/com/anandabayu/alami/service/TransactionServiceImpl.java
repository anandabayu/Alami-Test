package com.anandabayu.alami.service;

import com.anandabayu.alami.dao.TransactionDAO;
import com.anandabayu.alami.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionDAO transactionDAO;

    @Autowired
    public TransactionServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public List<Transaction> findAll(String start, String end, Integer userId) throws ParseException {
        return transactionDAO.findAll(start, end, userId);
    }

    @Override
    public void createTransaction(Transaction transaction) {
        transactionDAO.createTransaction(transaction);
    }

    @Override
    public int getCurrentBalance() {
        return transactionDAO.getCurrentBalance();
    }
}
