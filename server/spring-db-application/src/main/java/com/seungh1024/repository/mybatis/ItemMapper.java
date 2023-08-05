package com.seungh1024.repository.mybatis;

import com.seungh1024.domain.Item;
import com.seungh1024.repository.ItemSearchCond;
import com.seungh1024.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(@Param("id") Long id, @Param("updateParam")ItemUpdateDto updateParam);

    List<Item> findAll(ItemSearchCond itemSearch);

    Optional<Item> findById(Long id);
}
