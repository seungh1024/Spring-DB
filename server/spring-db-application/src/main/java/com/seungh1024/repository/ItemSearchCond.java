package com.seungh1024.repository;

import lombok.Getter;

@Getter
public class ItemSearchCond {

    private String itemName;
    private Integer maxPrice;

    public ItemSearchCond(){

    }

    public ItemSearchCond(String itemName, Integer maxPrice){
        this.itemName = itemName;
        this.maxPrice = maxPrice;
    }
}
