package martishyn.app.model;


import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "cash_card")
@Builder
public class CashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String owner;

    public CashCard() {
    }

    public CashCard(Long id, Double amount, String owner) {
        this.id = null;
        this.amount = amount;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
