-- Sample data for PostgreSQL Vinarija schema

-- Insert grape varieties
INSERT INTO Sorta_Grozdja (IdSrt, Boja, ProcSec, NazSrt) VALUES
                                                             (1, 'crvena', 13.5, 'Merlot'),
                                                             (2, 'crvena', 14.2, 'Cabernet Sauvignon'),
                                                             (3, 'bela', 12.8, 'Chardonnay'),
                                                             (4, 'crvena', 13.0, 'Pinot Noir'),
                                                             (5, 'bela', 12.5, 'Sauvignon Blanc'),
                                                             (6, 'crvena', 14.5, 'Syrah'),
                                                             (7, 'bela', 11.8, 'Riesling'),
                                                             (8, 'crvena', 13.8, 'Malbec');

-- Insert vineyards
INSERT INTO Vinograd (IdV, ImeV, PoV, DatOsn, VKap, Vinograd_IdV) VALUES
                                                                      (1, 'Vinograd Fruška Gora', 25.50, '1995-03-15', 50000.0, NULL),
                                                                      (2, 'Vinograd Sremski Karlovci', 15.20, '2000-06-20', 30000.0, 1),
                                                                      (3, 'Vinograd Vršac', 30.75, '1990-09-10', 60000.0, NULL),
                                                                      (4, 'Vinograd Subotica', 18.30, '2005-01-15', 35000.0, NULL),
                                                                      (5, 'Vinograd Smederevo', 12.80, '2010-05-22', 25000.0, NULL),
                                                                      (6, 'Vinograd Negotinska Krajina', 22.45, '1985-11-08', 45000.0, NULL),
                                                                      (7, 'Vinograd Sektor A', 8.50, '2002-07-12', 15000.0, 1),
                                                                      (8, 'Vinograd Sektor B', 7.00, '2003-03-01', 12000.0, 1);

-- Insert employees
INSERT INTO Zaposleni (IdZap, DatZap, Zar, ImeZap, kat) VALUES
                                                            (1, '2015-03-15', 45000.00, 'Milan Petrović', 'poljoprivrednik'),
                                                            (2, '2018-06-20', 55000.00, 'Ana Jovanović', 'somelijer'),
                                                            (3, '2012-09-10', 48000.00, 'Dragan Nikolić', 'poljoprivrednik'),
                                                            (4, '2019-01-15', 52000.00, 'Jelena Stojanović', 'somelijer'),
                                                            (5, '2016-05-22', 46000.00, 'Stefan Đorđević', 'poljoprivrednik'),
                                                            (6, '2014-11-08', 47000.00, 'Marija Pavlović', 'poljoprivrednik'),
                                                            (7, '2020-03-01', 50000.00, 'Nikola Milić', 'somelijer'),
                                                            (8, '2017-07-12', 45500.00, 'Sofija Ristić', 'poljoprivrednik');

-- Insert employee subtypes
INSERT INTO poljoprivrednik (IdZap) VALUES (1), (3), (5), (6), (8);
INSERT INTO somelijer (IdZap) VALUES (2), (4), (7);

-- Insert wines
INSERT INTO Vino (IdVina, God, ProcAlk, NazVina) VALUES
                                                     (1, '2022-01-01', 13.5, 'Fruškogorsko Merlot 2022'),
                                                     (2, '2021-01-01', 14.2, 'Karlovački Cabernet 2021'),
                                                     (3, '2023-01-01', 12.8, 'Vršački Chardonnay 2023'),
                                                     (4, '2022-01-01', 13.0, 'Subotički Pinot Noir 2022'),
                                                     (5, '2023-01-01', 12.5, 'Smederevski Sauvignon 2023'),
                                                     (6, '2021-01-01', 14.5, 'Negotinski Syrah 2021'),
                                                     (7, '2023-01-01', 11.8, 'Fruškogorsko Riesling 2023'),
                                                     (8, '2022-01-01', 13.8, 'Karlovački Malbec 2022');

-- Insert harvests
INSERT INTO Berba (IdBer, DatBer, Vinograd_IdV) VALUES
                                                    (1, '2022-09-15', 1),
                                                    (2, '2021-09-20', 2),
                                                    (3, '2023-09-10', 3),
                                                    (4, '2022-09-25', 4),
                                                    (5, '2023-09-05', 5),
                                                    (6, '2021-09-30', 6),
                                                    (7, '2023-09-12', 7),
                                                    (8, '2022-09-18', 8),
                                                    (9, '2023-09-15', 1),
                                                    (10, '2022-09-20', 2);

-- Insert vineyard-grape variety relationships
INSERT INTO se_uzgaja (Vinograd_IdV, Sorta_Grozdja_IdSrt) VALUES
                                                              (1, 1), (1, 7), -- Fruška Gora: Merlot, Riesling
                                                              (2, 2), (2, 8), -- Sremski Karlovci: Cabernet, Malbec
                                                              (3, 3), -- Vršac: Chardonnay
                                                              (4, 4), -- Subotica: Pinot Noir
                                                              (5, 5), -- Smederevo: Sauvignon Blanc
                                                              (6, 6), -- Negotinska Krajina: Syrah
                                                              (7, 1), -- Sektor A: Merlot
                                                              (8, 2); -- Sektor B: Cabernet

-- Insert wine-grape variety relationships
INSERT INTO ucestvuje (Vino_IdVina, Sorta_Grozdja_IdSrt) VALUES
                                                             (1, 1), -- Fruškogorsko Merlot - Merlot
                                                             (2, 2), -- Karlovački Cabernet - Cabernet Sauvignon
                                                             (3, 3), -- Vršački Chardonnay - Chardonnay
                                                             (4, 4), -- Subotički Pinot Noir - Pinot Noir
                                                             (5, 5), -- Smederevski Sauvignon - Sauvignon Blanc
                                                             (6, 6), -- Negotinski Syrah - Syrah
                                                             (7, 7), -- Fruškogorsko Riesling - Riesling
                                                             (8, 8); -- Karlovački Malbec - Malbec

-- Insert employee-vineyard assignments (FIXED: Only valid combinations)
INSERT INTO Radi (poljoprivrednik_IdZap, Vinograd_IdV) VALUES
                                                           (1, 1), (1, 7), -- Milan Petrović radi u Fruška Gora i Sektor A
                                                           (3, 3), -- Dragan Nikolić radi u Vršac
                                                           (5, 5), -- Stefan Đorđević radi u Smederevo
                                                           (6, 6), -- Marija Pavlović radi u Negotinska Krajina
                                                           (8, 3), (8, 8); -- Sofija Ristić radi u Vršac i Sektor B

-- Insert harvest participation (FIXED: Only valid combinations that exist in Radi table)
INSERT INTO se_angazuje (Berba_IdBer, Vinograd_IdV, Radi_poljoprivrednik_IdZap) VALUES
                                                                                    (1, 1, 1),
                                                                                    (3, 3, 3),
                                                                                    (3, 3, 8),
                                                                                    (5, 5, 5),
                                                                                    (6, 6, 6),
                                                                                    (7, 7, 1),
                                                                                    (8, 8, 8),
                                                                                    (9, 1, 1);


-- Insert customers
INSERT INTO Kupac (IdKup, Email, BrTel) VALUES
                                            (1, 'petar.markovic@email.com', '061-123-4567'),
                                            (2, 'ana.djordjevic@email.com', '062-234-5678'),
                                            (3, 'milan.stojanovic@email.com', '063-345-6789'),
                                            (4, 'jelena.nikolic@email.com', '064-456-7890'),
                                            (5, 'stefan.pavlovic@email.com', '065-567-8901'),
                                            (6, 'marija.ristic@email.com', '066-678-9012');

-- Insert orders
INSERT INTO Narudzba (IdNar, DatNar, PltMtd, Kupac_IdKup) VALUES
                                                              (1, '2024-01-15', 'karticno placanje', 1),
                                                              (2, '2024-01-20', 'gotovinsko placanje', 2),
                                                              (3, '2024-02-01', 'karticno placanje', 3),
                                                              (4, '2024-02-05', 'gotovinsko placanje', 4),
                                                              (5, '2024-02-10', 'gotovinsko placanje', 5),
                                                              (6, '2024-02-15', 'karticno placanje', 6),
                                                              (7, '2024-02-20', 'gotovinsko placanje', 4),
                                                              (8, '2024-03-01', 'karticno placanje', 2);

-- Insert cellars
INSERT INTO Podrum (IdPod, PodTmp, PodVl, KapPod) VALUES
                                                      (1, 15.5, 75.0, 10000.0),
                                                      (2, 16.0, 70.0, 8000.0),
                                                      (3, 14.8, 80.0, 12000.0),
                                                      (4, 15.2, 72.0, 6000.0);

-- Insert barrels
INSERT INTO Bure (IdBur, KapBur, GodPr, Vino_IdVina, Podrum_IdPod) VALUES
(1, 50, '2023-09-15', 1, 1),
(2, 100, '2022-09-16', 2, 2),
(3, 20, '2021-09-17', 3, 3),
(4, 10, '2020-09-18', 4, 4),
(5, 50, '2019-09-19', 5, 1),
(6, 100, '2018-09-20', 6, 2),
(7, 20, '2017-09-21', 7, 3),
(8, 10, '2016-09-22', 8, 4);


-- Insert bottles
INSERT INTO Boca (SerBr, KapBoc, Vino_IdVina, Narudzba_IdNar) VALUES
(1, 0.5, 1, 1),
(2, 0.75, 1, 1),
(3, 1.0, 3, 1),
(4, 1.5, 2, 2),
(5, 0.5, 5, 2),
(6, 0.75, 4, 3),
(7, 1.0, 4, 3),
(8, 1.5, 6, 3),
(9, 0.5, 8, 3),
(10, 0.75, 7, 4),
(11, 1.0, 7, 4),
(12, 1.5, 1, 5),
(13, 0.5, 1, 5),
(14, 0.75, 1, 5),
(15, 1.0, 2, 5),
(16, 1.5, 3, 6),
(17, 0.5, 3, 6),
(18, 1.0, 5, 6);

-- Insert additional bottles available for purchase (not assigned to any order)
INSERT INTO Boca (SerBr, KapBoc, Vino_IdVina, Narudzba_IdNar) VALUES
(19, 0.75, 1, NULL), -- Available Merlot bottle
(20, 1.0, 2, NULL),  -- Available Cabernet bottle
(21, 0.5, 3, NULL),  -- Available Chardonnay bottle
(22, 1.5, 4, NULL),  -- Available Pinot Noir bottle
(23, 0.75, 5, NULL), -- Available Sauvignon Blanc bottle
(24, 1.0, 6, NULL),  -- Available Syrah bottle
(25, 0.5, 7, NULL),  -- Available Riesling bottle
(26, 1.5, 8, NULL),  -- Available Malbec bottle
(27, 0.75, 1, NULL), -- Another available Merlot bottle
(28, 1.0, 2, NULL),  -- Another available Cabernet bottle
(29, 0.5, 3, NULL),  -- Another available Chardonnay bottle
(30, 1.5, 4, NULL);  -- Another available Pinot Noir bottle

-- Insert wine tastings
INSERT INTO Degustacija (IdDeg, DatDeg, KapDeg) VALUES
                                                    (1, '2024-03-15', 50),
                                                    (2, '2024-04-20', 30),
                                                    (3, '2024-05-10', 40),
                                                    (4, '2024-06-05', 25);

-- Insert wine tasting organization
INSERT INTO organizuje (somelijer_IdZap, Degustacija_IdDeg) VALUES
                                                                (2, 1), -- Ana Jovanović organizuje degustaciju 1
                                                                (4, 2), -- Jelena Stojanović organizuje degustaciju 2
                                                                (7, 3), -- Nikola Milić organizuje degustaciju 3
                                                                (2, 4); -- Ana Jovanović organizuje degustaciju 4

-- Insert wine presentations at tastings
INSERT INTO se_prezentuje (Vino_IdVina, Degustacija_IdDeg) VALUES
                                                               (1, 1), (2, 1), (3, 1), -- Degustacija 1: Merlot, Cabernet, Chardonnay
                                                               (4, 2), (5, 2), -- Degustacija 2: Pinot Noir, Sauvignon
                                                               (6, 3), (7, 3), (8, 3), -- Degustacija 3: Syrah, Riesling, Malbec
                                                               (1, 4), (3, 4); -- Degustacija 4: Merlot, Chardonnay

-- Initialize sequences to start after existing data
SELECT setval('kupac_id_seq', (SELECT COALESCE(MAX(IdKup), 0) + 1 FROM Kupac));
SELECT setval('narudzba_id_seq', (SELECT COALESCE(MAX(IdNar), 0) + 1 FROM Narudzba));
SELECT setval('boca_serbr_seq', (SELECT COALESCE(MAX(SerBr), 0) + 1 FROM Boca));
SELECT setval('vino_id_seq', (SELECT COALESCE(MAX(IdVina), 0) + 1 FROM Vino));
SELECT setval('sorta_grozdja_id_seq', (SELECT COALESCE(MAX(IdSrt), 0) + 1 FROM Sorta_Grozdja));
SELECT setval('vinograd_id_seq', (SELECT COALESCE(MAX(IdV), 0) + 1 FROM Vinograd));
SELECT setval('zaposleni_id_seq', (SELECT COALESCE(MAX(IdZap), 0) + 1 FROM Zaposleni));
SELECT setval('berba_id_seq', (SELECT COALESCE(MAX(IdBer), 0) + 1 FROM Berba));
SELECT setval('degustacija_id_seq', (SELECT COALESCE(MAX(IdDeg), 0) + 1 FROM Degustacija));
SELECT setval('podrum_id_seq', (SELECT COALESCE(MAX(IdPod), 0) + 1 FROM Podrum));
SELECT setval('bure_id_seq', (SELECT COALESCE(MAX(IdBur), 0) + 1 FROM Bure));