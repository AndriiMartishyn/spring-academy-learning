package martishyn.app.repository;

import martishyn.app.model.CashCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {

    @Query("select c from CashCard as c where c.owner=:owner")
    Optional<Iterable<CashCard>> findAllOwnersCards(String owner);

    default Optional<Iterable<CashCard>> findAllCashCards(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String owner = authentication.getName();
        return findAllOwnersCards(owner);
    }
}
