package com.seungh1024.domain;

import com.seungh1024.repository.ItemRepository;
import com.seungh1024.repository.ItemSearchCond;
import com.seungh1024.repository.ItemUpdateDto;
import com.seungh1024.repository.memory.MemoryItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Transactional
@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

//    @Autowired
//    PlatformTransactionManager transactionManager;
//    TransactionStatus status;
//
//    @BeforeEach
//    void beforeEach(){
//        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//    }

    @AfterEach
    void afterEach(){
        //MemoryItemRepository의 경우 제한적으로 사용. DB사용 안하니까
        if(itemRepository instanceof MemoryItemRepository){
            ((MemoryItemRepository) itemRepository).clearStore();;
        }
//        transactionManager.rollback(status);
    }

    @Test
    void save(){
        //given
        Item item = new Item("itemA",10000,10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId()).get();
        Assertions.assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem(){
        //given
        Item item = new Item("item1",10000,10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        ItemUpdateDto updateParam = new ItemUpdateDto("item2",20000,30);
        itemRepository.update(itemId,updateParam);

        //then
        Item findItem = itemRepository.findById(itemId).get();
        Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        Assertions.assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        Assertions.assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

    @Test
    void findItems(){
        //given
        Item item1 = new Item("itemA-1",10000,10);
        Item item2 = new Item("itemA-2",20000,20);
        Item item3 = new Item("itemB-1",30000,30);


        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        //둘 다 없음 검증
        test(null,null,item1,item2,item3);
        test("",null,item1,item2,item3);

        //itemName 검증
        test("itemA",null,item1,item2);
        test("temA",null,item1,item2);
        test("itemB",null,item3);

        //maxPrice 검증
        test(null,10000,item1);

        //둘 다 있음 검증
        test("itemA",10000,item1);
    }

    void test(String itemName, Integer maxPrice, Item... items){
        List<Item> result = itemRepository.findAll(new ItemSearchCond(itemName,maxPrice));
        Assertions.assertThat(result).containsExactly(items);
    }
}
