package com.martishyn.familycashcard.repository;

import com.martishyn.familycashcard.model.CashCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CashCardRepository extends CrudRepository<CashCard, Long>,
        PagingAndSortingRepository<CashCard, Long> {
}
