CREATE TABLE IF NOT EXISTS tb_envio (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    autor VARCHAR(255) NOT NULL,
    url VARCHAR(500),
    status INTEGER NOT NULL,
    criado_em TIMESTAMP DEFAULT current_timestamp
);