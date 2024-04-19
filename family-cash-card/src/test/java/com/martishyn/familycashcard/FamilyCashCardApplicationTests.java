package com.martishyn.familycashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.martishyn.familycashcard.model.CashCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FamilyCashCardApplicationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnACashCardWhenDataIsSaved() {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/cashcards/99", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(responseEntity.getBody());
        Number id = documentContext.read("id");
        assertThat(id).isEqualTo(99);
        Double amount = documentContext.read("amount");
        assertThat(amount).isEqualTo(123.45);
    }

    @Test
    public void shouldNotReturnCashCardWithUnknownId() {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/cashcards/1000", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isBlank();
    }

    @Test
    void shouldCreateANewCashCard(){
        CashCard cashCard = new CashCard(null, 250.00);
        ResponseEntity<Void> response = testRestTemplate.postForEntity("/cashcards", cashCard, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewCashCard = response.getHeaders().getLocation();
        ResponseEntity<String> newLocationResponse = testRestTemplate.getForEntity(locationOfNewCashCard, String.class);
        assertThat(newLocationResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(newLocationResponse.getBody());
        Number id = documentContext.read("id");
        Double amount = documentContext.read("amount");
        assertThat(id).isNotNull();
        assertThat(amount).isEqualTo(250.00);
    }
}
