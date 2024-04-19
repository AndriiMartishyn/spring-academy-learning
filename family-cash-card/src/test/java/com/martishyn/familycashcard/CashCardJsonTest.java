package com.martishyn.familycashcard;

import com.martishyn.familycashcard.model.CashCard;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class CashCardJsonTest {
    @Autowired
    private JacksonTester<CashCard> json;
    @Autowired
    private JacksonTester<CashCard[]> jsonList;
    private CashCard cashCard = new CashCard(99L, 123.45);
    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCard.setOwner(null);
        cashCards = Arrays.array(new CashCard(99L, 123.45, null),
                new CashCard(100L, 1.5, null),
                new CashCard(101L, 150.00, null));
    }

    @Test
    void cashCardSerializationTest() throws IOException {
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("id")
                .isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("amount")
                .isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id" : 99,
                    "amount": 123.45
                }
                """;
        assertThat(json.parseObject(expected).getId()).isEqualTo(99);
        assertThat(json.parseObject(expected).getAmount()).isEqualTo(123.45);
    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json");
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expectedJson = """
                [
                {"id":99, "amount":123.45},
                {"id":100, "amount":1.5},
                {"id":101, "amount":150}
                ]
                """;
        assertThat(jsonList.parse(expectedJson).getObject().length).isEqualTo(3);
        assertThat(jsonList.parse(expectedJson).getObject()[0].getId()).isEqualTo(cashCards[0].getId());
        assertThat(jsonList.parse(expectedJson).getObject()[1].getId()).isEqualTo(cashCards[1].getId());
        assertThat(jsonList.parse(expectedJson).getObject()[2].getId()).isEqualTo(cashCards[2].getId());
        assertThat(jsonList.parse(expectedJson).getObject()[0].getAmount()).isEqualTo(cashCards[0].getAmount());
        assertThat(jsonList.parse(expectedJson).getObject()[1].getAmount()).isEqualTo(cashCards[1].getAmount());
        assertThat(jsonList.parse(expectedJson).getObject()[2].getAmount()).isEqualTo(cashCards[2].getAmount());
    }
}
