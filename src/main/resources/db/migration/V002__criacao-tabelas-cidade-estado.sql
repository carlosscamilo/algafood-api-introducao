CREATE TABLE estado (
	id bigserial PRIMARY KEY,
	nome varchar(80) NOT NULL
);

CREATE TABLE cidade (
	id bigserial PRIMARY KEY,
	nome_cidade varchar(80) NOT NULL,
	estado_id INT NOT NULL,
    CONSTRAINT fk_cidade_estado FOREIGN KEY (estado_id) REFERENCES estado(id)
);