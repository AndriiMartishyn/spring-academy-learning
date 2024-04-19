package com.martishyn.familycashcard.controller;

import com.martishyn.familycashcard.model.CashCard;
import com.martishyn.familycashcard.repository.CashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    private CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCashCardById(@PathVariable Long id) {
        Optional<CashCard> foundCashCard = cashCardRepository.findById(id);
        if (foundCashCard.isPresent()) {
            return ResponseEntity.ok(foundCashCard.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<CashCard>> findAll(Pageable pageable){
        Page<CashCard> page = cashCardRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                ));
        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping
    public ResponseEntity<?> createCashCard(@RequestBody CashCard cashCard, UriComponentsBuilder uriComponentsBuilder) {
        CashCard savedEntity = cashCardRepository.save(cashCard);
        URI locationOfNewCard = uriComponentsBuilder
                .path("/cashcards/{id}")
                .buildAndExpand(savedEntity.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCard).build();

    }
}
