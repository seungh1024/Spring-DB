package com.seungh1024;

import com.seungh1024.config.JdbcTemplateV1Config;
import com.seungh1024.config.JdbcTemplateV2Config;
import com.seungh1024.config.JdbcTemplateV3Config;
import com.seungh1024.config.MemoryConfig;
import com.seungh1024.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV2Config.class)
@Import(JdbcTemplateV3Config.class)
@SpringBootApplication(scanBasePackages ={"com.seungh1024.controller","com.seungh1024.env"})
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class,args);
    }

    @Bean
    @Profile("local")
    public TestDataInit testDataInit(ItemRepository itemRepository){
        return new TestDataInit(itemRepository);
    }
}
