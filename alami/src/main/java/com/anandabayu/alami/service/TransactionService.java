package com.anandabayu.alami.service;

import com.anandabayu.alami.entity.Transaction;

import java.text.ParseException;
import java.util.List;

public interface TransactionService {
    public List<Transaction> findAll(String start, String end, Integer userId) throws ParseException;

    public void createTransaction(Transaction transaction);

    public int getCurrentBalance();
}
