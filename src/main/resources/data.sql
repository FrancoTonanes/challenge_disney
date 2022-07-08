INSERT INTO genero (genero_id, nombre, imagen) VALUES (1, 'terror', 'c25955a8bfca4f3fd4b099a8dbc38d04.png');
INSERT INTO genero (genero_id, nombre, imagen) VALUES (2, 'Sci-fi', 'c25955a8bfca4f3fd4b099a8dbc38d04.png');

INSERT INTO pelicula (pelicula_id, imagen, titulo, fecha_creacion, genero_id) VALUES (1, 'c25955a8bfca4f3fd4b099a8dbc38d04.png', 'El resplandor', '2018-05-10', 1);

INSERT INTO pelicula (pelicula_id, imagen, titulo, fecha_creacion, genero_id) VALUES (2, 'c25955a8bfca4f3fd4b099a8dbc38d04.png', 'El origen', '2019-06-10', 2);

INSERT INTO personaje (personaje_id, imagen, nombre, edad, peso, historia) VALUES (1, 'c25955a8bfca4f3fd4b099a8dbc38d04.png', 'Franco', 28, 71, 'Historia del personaje');
INSERT INTO personaje (personaje_id, imagen, nombre, edad, peso, historia) VALUES (2, 'c25955a8bfca4f3fd4b099a8dbc38d04.png', 'Roberto', 22, 90, 'Historia del personaje');
INSERT INTO personaje (personaje_id, imagen, nombre, edad, peso, historia) VALUES (3, 'c25955a8bfca4f3fd4b099a8dbc38d04.png', 'Mario', 48, 100, 'Historia del personaje');

INSERT INTO pelicula_personaje(pelicula_id, personaje_id) VALUES (1, 1);

INSERT INTO pelicula_personaje(pelicula_id, personaje_id) VALUES (1, 3);
INSERT INTO pelicula_personaje(pelicula_id, personaje_id) VALUES (2, 2);
