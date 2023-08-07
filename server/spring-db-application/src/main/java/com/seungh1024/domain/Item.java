package com.seungh1024.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="item_name", length = 10)
    private String itemName;
    @Column(name = "price")
    private Integer price;
    @Column(name = "quantity")
    private Integer quantity;

    public Item(){}

    public Item(String itemName, Integer price, Integer quantity){
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId()) && Objects.equals(getItemName(), item.getItemName()) && Objects.equals(getPrice(), item.getPrice()) && Objects.equals(getQuantity(), item.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItemName(), getPrice(), getQuantity());
    }
}
