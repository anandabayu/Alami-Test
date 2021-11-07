package com.anandabayu.alami.dao;

import com.anandabayu.alami.entity.Transaction;

import java.text.ParseException;
import java.util.List;

public interface TransactionDAO {
    public List<Transaction> findAll(String start, String end, Integer userId) throws ParseException;

    public void createTransaction(Transaction transaction);

    public int getCurrentBalance();
}
