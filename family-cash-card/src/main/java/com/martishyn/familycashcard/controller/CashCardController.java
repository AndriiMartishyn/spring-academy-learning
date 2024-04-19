package com.martishyn.familycashcard.controller;

import com.martishyn.familycashcard.model.CashCard;
import com.martishyn.familycashcard.repository.CashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (foundCashCard.isPresent()){
            return ResponseEntity.ok(foundCashCard.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
