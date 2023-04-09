package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 7:25 PM
 */
@Entity
@Table(name = "market")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "inst_id", nullable = false , unique = true)
    private String instId;

    @Column(name = "min_amount")
    private Double minAmount;

    @Column(name = "max_amount")
    private Double maxAmount;

    @Column(name = "min_notional")
    private Double minNotional;

    public Market(String instId) {
        this.instId = instId;
    }
}
