DROP TABLE IF EXISTS cash_card;
CREATE TABLE IF NOT EXISTS cash_card
(
    ID     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    AMOUNT NUMBER NOT NULL DEFAULT 0
);