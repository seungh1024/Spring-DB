package com.seungh1024.config;

import com.seungh1024.repository.ItemRepository;
import com.seungh1024.repository.jdbctemplate.JdbcTemplateItemRepositoryV3;
import com.seungh1024.repository.mybatis.ItemMapper;
import com.seungh1024.repository.mybatis.MybatisItemRepository;
import com.seungh1024.service.ItemService;
import com.seungh1024.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final ItemMapper itemMapper;

    @Bean
    public ItemService itemService(){
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository(){
        return new MybatisItemRepository(itemMapper);
    }
}
