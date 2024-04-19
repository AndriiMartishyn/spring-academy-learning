package com.martishyn.familycashcard.repository;

import com.martishyn.familycashcard.model.CashCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {
}
