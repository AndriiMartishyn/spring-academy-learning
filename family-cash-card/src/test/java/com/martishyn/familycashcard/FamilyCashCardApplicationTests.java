package com.martishyn.familycashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}
