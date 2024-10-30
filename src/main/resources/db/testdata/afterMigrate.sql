-- Estados
INSERT INTO estado (nome) 
VALUES ('Pernambuco'),('Paraíba'), ('Ceará'), ('Rio de Janeiro'), ('São Paulo')
ON CONFLICT (id) DO NOTHING;

INSERT INTO estado (nome)
VALUES ('Acre'), ('Amazônia'), ('Pará'), ('Bahia'), ('Alagoas'), ('Santa Catarina')
ON CONFLICT (id) DO NOTHING;

INSERT INTO estado (nome)
VALUES ('Rio Grande do Sul'), ('Rio Grande do Norte'), ('Mato Grosso'), ('Mato Grosso do Sul')
ON CONFLICT (id) DO NOTHING;

INSERT INTO estado (nome)
VALUES ('Brasília'), ('Paraná'), ('Piauí'), ('Sergipe'), ('Maranhão'), ('Góias')
ON CONFLICT (id) DO NOTHING;

INSERT INTO estado (nome)
VALUES ('Espírito Santo'), ('Tocatins'), ('Amapá'), ('Roraima'), ('Rondônia'), ('Minas Gerais')
ON CONFLICT (id) DO NOTHING;

-- Cidades
INSERT INTO cidade (nome_cidade, estado_id)
VALUES 
('Recife', 1), ('João Pessoa', 2), ('Fortaleza', 3), ('Rio de Janeiro', 4), ('São Paulo', 5),
('Rio Branco', 6), ('Manaus', 7), ('Belém', 8), ('Salvador', 9), ('Maceió', 10), 
('Florianópolis', 11), ('Porto Alegre', 12), ('Natal', 13), ('Cuiabá', 14), ('Campo Grande', 15), 
('Brasília', 16), ('Curitiba', 17), ('Teresina', 18), ('Aracaju', 19), ('São Luís', 20), 
('Goiânia', 21), ('Vitória', 22), ('Palmas', 23), ('Macapá', 24), ('Boa Vista', 25), 
('Porto Velho', 26), ('Belo Horizonte', 27), ('Olinda', 1), ('Campina Grande', 2), ('Sobral', 3), 
('Niterói', 4), ('Santos', 5), ('Cruzeiro do Sul', 6), ('Parintins', 7), ('Marabá', 8), 
('Feira de Santana', 9), ('Arapiraca', 10), ('Joinville', 11), ('Caxias do Sul', 12), 
('Mossoró', 13), ('Rondonópolis', 14), ('Dourados', 15), ('Taguatinga', 16), ('Londrina', 17), 
('Parnaíba', 18), ('Lagarto', 19), ('Imperatriz', 20), ('Anápolis', 21), ('Guarapari', 22), 
('Gurupi', 23), ('Santana', 24), ('Rorainópolis', 25), ('Ji-Paraná', 26), ('Uberlândia', 27), 
('Caruaru', 1), ('Patos', 2), ('Juazeiro do Norte', 3), ('Petrópolis', 4), ('Campinas', 5), 
('Sena Madureira', 6), ('Itacoatiara', 7), ('Castanhal', 8), ('Ilhéus', 9), ('Penedo', 10), 
('Blumenau', 11), ('Pelotas', 12), ('Caicó', 13), ('Sinop', 14), ('Três Lagoas', 15), 
('Gama', 16), ('Maringá', 17), ('Picos', 18), ('Itabaiana', 19), ('Caxias', 20), 
('Aparecida de Goiânia', 21), ('Serra', 22), ('Araguaína', 23), ('Laranjal do Jari', 24), 
('Pacaraima', 25), ('Ariquemes', 26), ('Contagem', 27)
ON CONFLICT (id) DO NOTHING;

-- Cozinhas
INSERT INTO cozinha (id, nome) 
VALUES (1, 'Tailandesa'), (2, 'Brasileira'), (3, 'Italiana'), (4, 'Argentina')
ON CONFLICT (id) DO NOTHING;

-- Restaurantes
INSERT INTO restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) 
VALUES 
(1, 'Picanha do Márcio', 5.50, 2, current_timestamp, current_timestamp),
(2, 'Cozinha do Tio Won', 11.27, 1, current_timestamp, current_timestamp),
(3, 'Atlântico', 0.0, 3, current_timestamp, current_timestamp),
(4, 'Java Steakhouse', 12, 4, current_timestamp, current_timestamp),
(5, 'Lanchonete do Tio Sam', 11, 2, current_timestamp, current_timestamp),
(6, 'Bar da Maria', 6, 2, current_timestamp, current_timestamp)
ON CONFLICT (id) DO NOTHING;

-- Forma de pagamento
INSERT INTO forma_pagamento (id, descricao) 
VALUES (1, 'Cartão de Crédito'), (2, 'Cartão de Débito'), (3, 'Dinheiro')
ON CONFLICT (id) DO NOTHING;

-- Restaurante_Forma_Pagamento
INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) 
VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3)
ON CONFLICT (restaurante_id, forma_pagamento_id) DO NOTHING;

-- Produtos
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) 
VALUES 
('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, true, 1),
('Camarão tailandês', '16 camarões grandes ao molho picante', 110, true, 1),
('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, true, 2),
('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, true, 3),
('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, true, 3),
('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, true, 4),
('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, true, 4),
('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, true, 5),
('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, true, 6)
ON CONFLICT (id) DO NOTHING;
