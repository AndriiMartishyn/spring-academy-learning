package martishyn.app.repository;

import martishyn.app.model.CashCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {

    @Query("select c from CashCard as c where c.owner= :#{authentication.name}")
    Optional<Iterable<CashCard>> findAllOwnersCards();

    default Iterable<CashCard> findAll() {
        throw new UnsupportedOperationException("unsupported, please use findAllOwnersCards instead");
    }
}
