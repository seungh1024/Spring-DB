package com.seungh1024.config;

import com.seungh1024.domain.jpa.JpaItemRepository2;
import com.seungh1024.domain.jpa.JpaItemRepository3;
import com.seungh1024.domain.jpa.SpringDataJpaItemRepository;
import com.seungh1024.repository.ItemRepository;
import com.seungh1024.repository.v2.ItemQueryRepositoryV2;
import com.seungh1024.repository.v2.ItemRepositoryV2;
import com.seungh1024.service.ItemService;
import com.seungh1024.service.ItemServiceV1;
import com.seungh1024.service.ItemServiceV2;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class V2Config {
    private final SpringDataJpaItemRepository repository;
    private final EntityManager em;
    private final ItemRepositoryV2 itemRepositoryV2; //SpringDataJPA가 자동으로 빈 생성하기 떄문에 주입 받아 사용

    @Bean
    public ItemServiceV2 itemService(){
        return new ItemServiceV2(itemRepositoryV2,itemQueryRepositoryV2());
    }

    @Bean
    public ItemQueryRepositoryV2 itemQueryRepositoryV2(){
        return new ItemQueryRepositoryV2(em);
    }

    //테스트 데이터 초기화용
    @Bean
    public ItemRepository itemRepository(){
        return new JpaItemRepository3(em);
    }

}
