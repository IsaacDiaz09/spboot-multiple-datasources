CREATE TABLE mock.table_a
(
    id     bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    "name" varchar NULL,
    CONSTRAINT table_a_pk PRIMARY KEY (id)
);

CREATE TABLE demo.table_b
(
    id     bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    "name" varchar NULL,
    CONSTRAINT table_a_pk PRIMARY KEY (id)
);
