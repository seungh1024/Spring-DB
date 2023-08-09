package com.seungh1024.repository.v2;

import com.seungh1024.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryV2 extends JpaRepository<Item,Long> {
}
