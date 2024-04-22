package martishyn.app.repository;

import martishyn.app.model.CashCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {

    @Query("select c from CashCard as c where c.owner=:owner")
    Optional<Iterable<CashCard>> findAllOwnersCards(String owner);
}
