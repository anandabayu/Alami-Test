package com.anandabayu.alami.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Field
    @Column(name = "transaction_date")
    private Date transactionDate;

    @Field
    @Column(name = "user_id")
    private int userId;

    @Field
    @Column(name = "nominal")
    private int nominal;

    @Field
    @Column(name = "type")
    private int type;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    //
    //  Constructors
    //
    public Transaction(Date transactionDate, int userId, int nominal, int type, User user) {
        this.transactionDate = transactionDate;
        this.userId = userId;
        this.nominal = nominal;
        this.type = type;
        this.user = user;
    }

    public Transaction() {
    }

    //
    // Getter and Setter
    //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //
    // toString
    //
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", userId=" + userId +
                ", nominal=" + nominal +
                ", type=" + type +
                ", user=" + user +
                '}';
    }
}
