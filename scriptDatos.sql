-- Creacion de SCRIPT PARA LOS DATOS NECESARIOS PARA LA BD --
-- Insercion de PROVEEDORES (3)
INSERT INTO `db_sistema_stock`.`proveedor`
(`cuil`,`direccion`,`nombre_empresa`) VALUES
('20-12345678-9', 'Calle Falsa 123, Buenos Aires', 'Relojeando S.A.'),
('27-87654321-0', 'Avenida Siempre Viva 742, Springfield', 'Inspeccionando Billeteras SRL'),
('30-11223344-5', 'Boulevard de los Sueños Rotos 456, Mendoza', 'TecnoGalpon SA');

-- Insercion de PRODUCTOS (10)
INSERT INTO `db_sistema_stock`.`producto`
(`costo`,`descripcion`,`habilitado`,`nombre`,`precio_de_venta`)
VALUES
(500, 'Gafas reforzadas estilo oblicuo ideales para el protegerte del sol y llevar tu estilo a otro nivel', true, 'Gafas de Sol', 1200),
(300, 'Billetera de cuero negro con cierre magnetico, bolsillos internos y tarjetero', true, 'Billetera', 800),
(650, 'Moño de seda negra de primera calidad, tamaño regulable ', true, 'Moño de vestir', 1500),
(1200, 'Auriculares Ear-In color negro, con estuche inteligente para cargar su bateria', true, 'Auriculares Bluetooth', 2500),
(750, 'Corbata de algodon con patron cuadrille sutil roja y blanca talla M', true, 'Corbata de vestir', 1650),
(400, 'Tarjetero de cuero marron, 8 compartimentos, cierre con boton y cuerda', true, 'Tarjetero de bolsillo', 900),
(250, 'Pluma de tinta negra con tapa integrada, color madera en el exterior y punta dorada', true, 'Pluma Estilografica', 750),
(900, 'Reloj Analogico de muñeca, 4 en 1,  color plateado correa de metal, estilo clasico', true, 'Reloj Analogico', 2000),
(120, 'Birome de escritorio de tinta azul con tapa', true, 'Birome Basica', 300),
(550, 'Cuaderno reciclable tipo libreta anillado hojas blancas', true, 'Cuaderno de Bamboo', 1100);

-- Insercion ded Stock para cada Producto

INSERT INTO `db_sistema_stock`.`stock`
(`cantidad_actual`,`punto_minimo_de_stock`,`reabastecer`,`id_producto`)
VALUES
(15, 5, false, 1), 		-- GAFAS
(20, 8, false, 2), 		-- BILLETERA
(12, 3, false, 3), 		-- MOÑO
(8, 4, false, 4), 		-- AURIS 
(25, 10, false, 5),		-- CORBATA
(15, 5, false, 6),		-- TARJETERO
(30, 10, false, 7),		-- PLUMA ESTILOGRAFICA
(18, 7, false, 8),		-- RELOJ 
(50, 15, false, 9),		-- BIROME 
(30, 10, false, 10);	-- CUADERNO 

-- Insercion de User cliente y admin

INSERT INTO `db_sistema_stock`.`user`
(`created_at`,`enabled`,`password`,`updated_at`,`username`)
VALUES
('2024-06-14 10:00:00', true, '$2a$10$c.Iw1lmGH0nh5zuTlx8TtO2fqZeads6OkMZNr4eLdje//GHa/4.N.', '2024-06-14 10:00:00', 'admin'), 
('2024-06-14 11:00:00', true, '$2a$10$LglnUirU0zAn97ilLptthOVDl0klCjrC34BAhstejgqM0V5/jnjqe', '2024-06-14 11:00:00', 'cliente');

-- Insercion de user_role

INSERT INTO `db_sistema_stock`.`user_role`
(`created_at`,`role`,`updated_at`,`user_id`)
VALUES
('2024-06-14 10:30:00', 'ROLE_ADMIN', '2024-06-14 10:30:00', 1),
('2024-06-14 11:30:00', 'ROLE_CLIENT', '2024-06-14 11:30:00', 2);

