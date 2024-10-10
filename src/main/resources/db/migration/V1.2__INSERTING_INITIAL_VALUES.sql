INSERT INTO shape (id)
VALUES
    (DEFAULT),
    (DEFAULT),
    (DEFAULT),
    (DEFAULT),
    (DEFAULT),
    (DEFAULT),
    (DEFAULT),
    (DEFAULT),
    (DEFAULT);

INSERT INTO layer (id, content, shape_id)
VALUES
    (DEFAULT, '1', 1),
    (DEFAULT, '22', 2),
    (DEFAULT, '333', 3),
    (DEFAULT, '4444', 4),
    (DEFAULT, '55555', 5),
    (DEFAULT, '666', 6),
    (DEFAULT, '666', 6),
    (DEFAULT, '777', 7),
    (DEFAULT, '7777', 7),
    (DEFAULT, '8888', 8),
    (DEFAULT, '8888', 8),
    (DEFAULT, '999', 9),
    (DEFAULT, '999', 9),
    (DEFAULT, '999', 9);

INSERT INTO parcel (id, name, filling_symbol, shape_id)
VALUES
    (DEFAULT, 'Куб', '1', 1),
    (DEFAULT, 'Кирпич', '2', 2),
    (DEFAULT, 'Что-то длинное', '3', 3),
    (DEFAULT, 'Змея', '4', 4),
    (DEFAULT, 'Шланг', '5', 5),
    (DEFAULT, 'Комод', '6', 6),
    (DEFAULT, 'Огрызок', '7', 7),
    (DEFAULT, 'Кровать', '8', 8),
    (DEFAULT, 'Мегакуб жесть', '9', 9);