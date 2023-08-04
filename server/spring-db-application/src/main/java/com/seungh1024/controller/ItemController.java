package com.seungh1024.controller;

import com.seungh1024.domain.Item;
import com.seungh1024.repository.ItemSearchCond;
import com.seungh1024.repository.ItemUpdateDto;
import com.seungh1024.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<?> items(@ModelAttribute("itemSearch")ItemSearchCond itemSearch){
        List<Item> items = itemService.findItems(itemSearch);
        return new ResponseEntity<>(items,HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> item(@PathVariable Long itemId){
        Item item = itemService.findById(itemId).get();
        return new ResponseEntity<>(item,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> item(@RequestBody Item item){
        Item savedItem = itemService.save(item);
        return new ResponseEntity<>(savedItem,HttpStatus.OK);
    }

    @PatchMapping("/{itemId}/edit")
    public ResponseEntity<?> edit(@PathVariable Long itemId, @RequestBody ItemUpdateDto updateParam){
        itemService.update(itemId,updateParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
