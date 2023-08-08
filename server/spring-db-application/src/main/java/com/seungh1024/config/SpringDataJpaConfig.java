package com.seungh1024.config;

import com.seungh1024.domain.jpa.JpaItemRepository2;
import com.seungh1024.domain.jpa.SpringDataJpaItemRepository;
import com.seungh1024.repository.ItemRepository;
import com.seungh1024.service.ItemService;
import com.seungh1024.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {
    private final SpringDataJpaItemRepository repository;

    @Bean
    public ItemService itemService(){
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository(){
        return new JpaItemRepository2(repository);
    }
}
