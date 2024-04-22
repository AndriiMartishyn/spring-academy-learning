package martishyn.app.controller;

import lombok.RequiredArgsConstructor;
import martishyn.app.annotation.CurrentOwner;
import martishyn.app.model.CashCard;
import martishyn.app.repository.CashCardRepository;
import martishyn.app.request.CashCardRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cashcards")
@RequiredArgsConstructor
public class CashCardController {
    private final CashCardRepository cashCardRepository;

    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        return cashCardRepository.findById(requestedId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CashCard> createCashCard(@RequestBody CashCardRequest cashCardRequest,
                                                   @CurrentOwner String owner) {
        CashCard newCashCard = CashCard.builder()
                .amount(cashCardRequest.amount())
                .owner(owner)
                .build();
        CashCard savedCashCard = cashCardRepository.save(newCashCard);
        URI locationOfNewCard = UriComponentsBuilder
                .fromPath("/cashcards/{id}")
                .buildAndExpand(savedCashCard.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCard).build();
    }

    @GetMapping
    public ResponseEntity<Iterable<CashCard>> findAll(@CurrentOwner String owner) {
        return cashCardRepository.findAllOwnersCards(owner)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

