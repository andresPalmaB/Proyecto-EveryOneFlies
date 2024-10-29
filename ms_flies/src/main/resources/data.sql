-- datos para la tabla aerolinea
insert into AEROLINEA (nombre, siglas) values ('everyoneflies', 'eof');

-- datos para la tabla location
insert into LOCATION (pais, ciudad, fecha_inicio_alta, fecha_fin_alta, fecha_inicio_alta_dos, fecha_fin_alta_dos, fecha_inicio_media, fecha_fin_media, fecha_inicio_media_dos, fecha_fin_media_dos, precio_base)
values
('colombia', 'barranquilla', '2024-12-01', '2024-01-31', '2024-02-01', '2024-03-31', '2024-04-01', '2024-04-30', '2024-07-01', '2024-08-31', 100000),
('colombia', 'medellin', '2024-12-01', '2024-01-31', '2024-02-01', '2024-03-31', '2024-04-01', '2024-04-30', '2024-07-01', '2024-08-31', 100000),
('venezuela', 'maracaibo', '2024-12-01', '2024-01-31', '2024-02-01', '2024-03-31', '2024-04-01', '2024-04-30', '2024-07-01', '2024-08-31', 100000),
('colombia', 'bogotá', '2024-12-01', '2024-01-31', '2024-02-01', '2024-03-31', '2024-04-01', '2024-04-30', '2024-07-01', '2024-08-31', 100000);

-- datos para la tabla aeropuerto
insert into AEROPUERTO (codigo_iata, nombre_aeropuerto, id_location)
values
('baq', 'aeropuerto internacional ernesto cortissoz', 1),
('mde', 'aeropuerto internacional josé maría córdova', 2),
('bog', 'aeropuerto internacional el dorado', 4),
('brr', 'aeropuerto internacional de barranquilla', 1);

-- Datos para la tabla vuelo
INSERT INTO VUELO (codigo, id_aerolinea, id_aeropuerto_origen, id_aeropuerto_destino, fecha_salida, hora_salida, fecha_llegada, hora_llegada, ECONOMICOS, ECONOMICOS_PREMIUN, BUSINESS, PRIMERA_CLASE, TOTAL_PUESTOS)
VALUES
('eof1', 1, 1, 3, '2024-12-15', '10:00:00', '2024-12-15', '12:00:00', 6, 3, 2, 1, 12),
('eof2', 1, 1, 3, '2024-12-15', '11:00:00', '2024-12-15', '12:00:00', 6, 3, 2, 1, 12),
('eof3', 1, 3, 1, '2024-12-16', '10:00:00', '2024-12-16', '12:00:00', 6, 3, 2, 1, 12),
('eof4', 1, 3, 2, '2024-12-16', '10:00:00', '2024-12-16', '12:00:00', 6, 3, 2, 1, 12),
('eof5', 1, 1, 3, '2024-12-17', '12:00:00', '2024-12-17', '12:00:00', 6, 3, 2, 1, 12),
('eof6', 1, 2, 3, '2024-12-17', '10:00:00', '2024-12-17', '12:00:00', 6, 3, 2, 1, 12),
('eof7', 1, 3, 1, '2024-12-18', '10:00:00', '2024-12-18', '12:00:00', 6, 3, 2, 1, 12),
('eof8', 1, 3, 2, '2024-12-18', '10:00:00', '2024-12-18', '12:00:00', 6, 3, 2, 1, 12);