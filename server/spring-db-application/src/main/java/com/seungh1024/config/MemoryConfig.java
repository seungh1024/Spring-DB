package com.seungh1024.config;

import com.seungh1024.repository.ItemRepository;
import com.seungh1024.repository.memory.MemoryItemRepository;
import com.seungh1024.service.ItemService;
import com.seungh1024.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {
    @Bean
    public ItemService itemService(){
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository(){
        return new MemoryItemRepository();
    }
}
