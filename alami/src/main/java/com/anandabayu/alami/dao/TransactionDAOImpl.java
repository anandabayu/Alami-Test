package com.anandabayu.alami.dao;

import com.anandabayu.alami.entity.Transaction;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

    private EntityManager entityManager;

    public TransactionDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Transaction> findAll(String start, String end, Integer userId) throws ParseException {
        Session s = entityManager.unwrap(Session.class);

        Integer uid = null;
        if (userId != 0) {
            uid = userId;
        }

        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start + " 00:00:00");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end + " 23:59:59");

        String q = "from Transaction where (:uId is null or userId = :uId) and transactionDate BETWEEN :startDate AND :endDate ORDER BY transactionDate DESC ";
        Query<Transaction> query = s.createQuery(q, Transaction.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("uId", uid);

        List<Transaction> transactions = query.getResultList();

        return transactions;
    }

    @Override
    @Transactional
    public void createTransaction(Transaction transaction) {
        Session s = entityManager.unwrap(Session.class);
        s.saveOrUpdate(transaction);
    }

    @Override
    public int getCurrentBalance() {
        Session s = entityManager.unwrap(Session.class);
        Query q1 = s.createNativeQuery("select sum(nominal) from transactions where type in (1, 3)");
        Query q2 = s.createNativeQuery("select sum(nominal) from transactions where type in (2, 4)");

        Number nval1 = (Number) q1.getSingleResult();
        Number nval2 = (Number) q2.getSingleResult();

        Integer val1 = nval1 == null ? null : nval1.intValue();
        Integer val2 = nval2 == null ? null : nval2.intValue();

        if (val1 == null) return 0;

        if (val2 == null) return val1;

        return val1 - val2;
    }
}
