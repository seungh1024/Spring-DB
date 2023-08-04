package com.seungh1024.config;

import com.seungh1024.repository.ItemRepository;
import com.seungh1024.repository.jdbctemplate.JdbcTemplateItemRepositoryV1;
import com.seungh1024.service.ItemService;
import com.seungh1024.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV1Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService(){
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository(){
        return new JdbcTemplateItemRepositoryV1(dataSource);
    }
}
