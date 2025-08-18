package com.paymentwallet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "walletId")
    private String walletId;

    @Column(name = "balance")
    private double balance;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "Id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "currencyId", referencedColumnName = "Id")
    private Currency currency;

}
