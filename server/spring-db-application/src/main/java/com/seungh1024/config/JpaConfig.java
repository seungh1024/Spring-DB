package com.seungh1024.config;

import com.seungh1024.domain.jpa.JpaItemRepository;
import com.seungh1024.repository.ItemRepository;
import com.seungh1024.repository.mybatis.MybatisItemRepository;
import com.seungh1024.service.ItemService;
import com.seungh1024.service.ItemServiceV1;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {
    private final EntityManager em;

    public JpaConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public ItemService itemService(){
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository(){
        return new JpaItemRepository(em);
    }
}
