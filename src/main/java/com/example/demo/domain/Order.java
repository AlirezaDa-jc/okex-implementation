package com.example.demo.domain;

import com.example.demo.domain.enums.OrderSide;
import com.example.demo.domain.enums.OrderType;
import lombok.*;

import javax.persistence.*;

/**
 * Created By Alireza Dolatabadi
 * Date: 4/9/2023
 * Time: 6:48 PM
 */
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "okex_id", nullable = false, unique = true)
    private String okexId;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "order_side")
    @Enumerated(EnumType.STRING)
    private OrderSide side;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private OrderType type;

    public Order(String okexId, Double unitPrice, Double amount, OrderSide side, OrderType type) {
        this.okexId = okexId;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.side = side;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", okexId='" + okexId + '\'' +
                ", unitPrice=" + unitPrice +
                ", size=" + amount +
                ", side=" + side +
                ", type=" + type +
                '}';
    }
}
