package com.seungh1024.domain.jpa;

import com.seungh1024.domain.Item;
import com.seungh1024.repository.ItemRepository;
import com.seungh1024.repository.ItemSearchCond;
import com.seungh1024.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaItemRepository2 implements ItemRepository {

    private final SpringDataJpaItemRepository repository;
    @Override
    public Item save(Item item) {
        return repository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = repository.findById(itemId).orElseThrow();
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond)  {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        if(StringUtils.hasText(itemName) && maxPrice != null){
//            return repository.findByItemNameLikeAndPriceLessThanEqual("%"+itemName+"%",maxPrice);
            return repository.findItems("%"+itemName+"%",maxPrice);
        }else if(StringUtils.hasText(itemName)){
            return repository.findByItemNameLike("%"+itemName+"%");
        }else if(maxPrice != null){
            return repository.findByPriceLessThanEqual(maxPrice);
        }else{
            return repository.findAll();
        }
    }
}
