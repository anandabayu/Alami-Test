package com.anandabayu.alami.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    //
    // Fields
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Field
    @Column(name = "name")
    private String name;

    @Field
    @Column(name = "born_date")
    private Date borndate;

    @Field
    @Column(name = "address")
    private String address;

    //
    //  Constructors
    //
    public User(String name, Date borndate, String address) {
        this.name = name;
        this.borndate = borndate;
        this.address = address;
    }

    public User() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBorndate() {
        return borndate;
    }

    public void setBorndate(Date borndate) {
        this.borndate = borndate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    //
    // toString
    //
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", borndate=" + borndate +
                ", address='" + address + '\'' +
                '}';
    }
}
