-- Insert into Zaposleni (Employees)
INSERT INTO Zaposleni (IdZap, DatZap, Zar, ImeZap, kat) VALUES
                                                            (1, '2015-03-15', 3000.00, 'Marko Markovic', 'poljoprivrednik'),
                                                            (2, '2017-07-20', 3500.00, 'Ivana Ivanovic', 'somelijer'),
                                                            (3, '2019-11-01', 2800.00, 'Petar Petrovic', 'poljoprivrednik'),
                                                            (4, '2020-01-10', 3700.00, 'Jovana Jovic', 'somelijer'),
                                                            (5, '2021-06-22', 2600.00, 'Milan Milosevic', 'poljoprivrednik'),
                                                            (6, '2018-05-10', 3100.00, 'Jelena Jelic', 'poljoprivrednik'),
                                                            (7, '2022-12-01', 3400.00, 'Nikola Nikolic', 'somelijer'),
                                                            (8, '2021-04-20', 2700.00, 'Sanja Sanic', 'poljoprivrednik'),
                                                            (9, '2023-01-12', 3600.00, 'Ana Anic', 'somelijer'),
                                                            (10, '2020-08-30', 2900.00, 'Milos Milosevic', 'poljoprivrednik');

-- Insert into poljoprivrednik (Farmers)
INSERT INTO poljoprivrednik (IdZap, OblastRada) VALUES
                                                    (1, 'Navodnjavanje'),
                                                    (3, 'Rezidba i zaštita'),
                                                    (5, 'Berba i transport'),
                                                    (6, 'Navodnjavanje'),
                                                    (8, 'Rezidba i zaštita'),
                                                    (10, 'Berba i transport');

-- Insert into somelijer (Wine experts)
INSERT INTO somelijer (IdZap, Sertifikat) VALUES
                                              (2, 'WSET Level 3'),
                                              (4, 'Certified Wine Judge'),
                                              (7, 'Certified Wine Judge'),
                                              (9, 'WSET Level 2');

-- Insert into Sorta_Grozdja (Grape varieties)
INSERT INTO Sorta_Grozdja (IdSrt, Boja, ProcSec, NazSrt) VALUES
                                                             (1, 'crvena', 12.5, 'Cabernet Sauvignon'),
                                                             (2, 'bela', 11.0, 'Chardonnay'),
                                                             (3, 'roze', 10.0, 'Pinot Noir Rosé'),
                                                             (4, 'bela', 12.2, 'Sauvignon Blanc'),
                                                             (5, 'crvena', 13.0, 'Merlot'),
                                                             (6, 'roze', 11.5, 'Zinfandel Rosé'),
                                                             (7, 'bela', 12.3, 'Riesling'),
                                                             (8, 'crvena', 13.8, 'Syrah'),
                                                             (9, 'bela', 10.9, 'Pinot Grigio'),
                                                             (10, 'crvena', 14.1, 'Malbec');

-- Insert into Vinograd (Vineyards)
INSERT INTO Vinograd (IdV, ImeV, PoV, DatOsn, VKap, Vinograd_IdV) VALUES
                                                                      (1, 'Beli Breg', 12.5, '2010-04-15', 5000, NULL),
                                                                      (2, 'Zeleni Vrh', 15.2, '2012-06-20', 6000, 1),
                                                                      (3, 'Crveni Brijeg', 9.5, '2008-09-10', 4000, NULL),
                                                                      (4, 'Zuti Breg', 10.1, '2015-03-01', 5500, 3),
                                                                      (5, 'Sivi Breg', 11.3, '2017-11-05', 5200, NULL),
                                                                      (6, 'Plavi Breg', 10.5, '2016-05-20', 7000, 1),
                                                                      (7, 'Crna Loza', 9.8, '2011-09-15', 5500, 3),
                                                                      (8, 'Suncevo Polje', 13.0, '2019-02-28', 9000, NULL),
                                                                      (9, 'Jesenje Polje', 11.2, '2017-08-10', 6500, 8),
                                                                      (10, 'Ruzicasti Breg', 12.7, '2015-04-22', 6000, 6);

-- Insert into Radi (Farmers working on vineyards)
INSERT INTO Radi (poljoprivrednik_IdZap, Vinograd_IdV) VALUES
                                                           (1, 1),
                                                           (3, 3),
                                                           (5, 5),
                                                           (6, 6),
                                                           (8, 7),
                                                           (10, 8);

-- Insert into Vino (Wine)
INSERT INTO Vino (IdVina, God, ProcAlk, NazVina) VALUES
                                                     (1, '2019-09-01', 13.5, 'Cabernet Deluxe'),
                                                     (2, '2020-05-10', 12.0, 'Chardonnay Classic'),
                                                     (3, '2018-07-15', 11.8, 'Rosé Charm'),
                                                     (4, '2021-04-20', 12.3, 'Sauvignon Supreme'),
                                                     (5, '2017-11-25', 13.0, 'Merlot Magic'),
                                                     (6, '2020-11-01', 11.8, 'Zinfandel Rosé Delight'),
                                                     (7, '2022-07-01', 12.9, 'Riesling Fresh'),
                                                     (8, '2023-02-01', 14.0, 'Syrah Dark'),
                                                     (9, '2021-06-01', 11.0, 'Pinot Grigio Light'),
                                                     (10, '2023-05-01', 14.5, 'Malbec Strong');

-- Insert into Berba (Harvests)
INSERT INTO Berba (IdBer, DatBer, Vinograd_IdV) VALUES
                                                    (1, '2023-09-10', 1),
                                                    (2, '2023-09-15', 3),
                                                    (3, '2023-09-20', 5),
                                                    (4, '2023-09-25', 6),
                                                    (5, '2023-09-30', 7),
                                                    (6, '2023-10-05', 8),
                                                    (7, '2023-10-10', 9),
                                                    (8, '2023-10-15', 10),
                                                    (9, '2023-10-20', 2),
                                                    (10, '2023-10-25', 4);


-- Insert into Kupac (Customers)
INSERT INTO Kupac (IdKup, Email, BrTel) VALUES
                                            (1, 'pera@example.com', '+381641234567'),
                                            (2, 'mika@example.com', '+381651234567'),
                                            (3, 'lana@example.com', '+381661234567'),
                                            (4, 'ana@example.com', '+381671234567'),
                                            (5, 'igor@example.com', '+381681234567'),
                                            (6, 'luka@example.com', '+381651234567'),
                                            (7, 'marta@example.com', '+381661234567'),
                                            (8, 'zoran@example.com', '+381671234567'),
                                            (9, 'ivana2@example.com', '+381681234567'),
                                            (10, 'milena@example.com', '+381691234567');

-- Insert into Narudzba (Orders)
INSERT INTO Narudzba (IdNar, DatNar, PltMtd, Kupac_IdKup) VALUES
                                                              (1, '2023-08-10', 'Karticno placanje', 1),
                                                              (2, '2023-08-15', 'Gotovinsko placanje', 2),
                                                              (3, '2023-08-20', 'Karticno placanje', 3),
                                                              (4, '2023-08-25', 'Gotovinsko placanje', 4),
                                                              (5, '2023-08-30', 'Karticno placanje', 5),
                                                              (6, '2023-09-10', 'Karticno placanje', 6),
                                                              (7, '2023-09-15', 'Gotovinsko placanje', 7),
                                                              (8, '2023-09-20', 'Karticno placanje', 8),
                                                              (9, '2023-09-25', 'Gotovinsko placanje', 9),
                                                              (10, '2023-09-30', 'Karticno placanje', 10);

-- Insert into Boca (Bottles)
INSERT INTO Boca (SerBr, KapBoc, Vino_IdVina, Narudzba_IdNar) VALUES
                                                                  (1001, 0.75, 1, 1),
                                                                  (1002, 0.5, 2, 2),
                                                                  (1003, 1.0, 3, 3),
                                                                  (1004, 1.75, 4, 4),
                                                                  (1005, 0.75, 5, 5),
                                                                  (1006, 1.0, 1, 6),
                                                                  (1007, 0.75, 6, 6),
                                                                  (1008, 0.5, 7, 7),
                                                                  (1009, 1.0, 8, 8),
                                                                  (1010, 1.75, 9, 9),
                                                                  (1011, 0.75, 10, 10),
                                                                  (1012, 1.0, 5, 1);

-- Insert into Podrum (Cellars)
INSERT INTO Podrum (IdPod, PodTmp, PodVl, KapPod) VALUES
                                                      (1, 12.5, 70.0, 500),
                                                      (2, 13.0, 65.0, 600),
                                                      (3, 11.5, 68.0, 450),
                                                      (4, 14.0, 60.0, 700),
                                                      (5, 10.5, 72.0, 550),
                                                      (6, 13.5, 68.5, 450),
                                                      (7, 12.0, 71.0, 370),
                                                      (8, 14.5, 63.0, 520),
                                                      (9, 11.0, 69.0, 480),
                                                      (10, 15.5, 61.0, 1000);

-- Insert into Bure (Barrels)
INSERT INTO Bure (IdBur, KapBur, GodPr, Vino_IdVina, Podrum_IdPod) VALUES
                                                                       (1, 10, '2020-01-01', 1, 1),
                                                                       (2, 20, '2021-05-15', 2, 2),
                                                                       (3, 50, '2019-09-10', 3, 3),
                                                                       (4, 100, '2018-12-20', 4, 4),
                                                                       (5, 20, NULL, 5, 5),
                                                                       (6, 10, '2023-01-01', 6, 6),
                                                                       (7, 20, '2023-01-01', 6, 6),
                                                                       (8, 50, '2022-12-15', 7, 7),
                                                                       (9, 100, NULL, 8, 8),
                                                                       (10, 10, '2021-10-05', 9, 9),
                                                                       (11, 50, '2023-02-20', 10, 10),
                                                                       (12, 20, NULL, 5, 1);

-- Insert into Degustacija (Tasting events)
INSERT INTO Degustacija (IdDeg, DatDeg, KapDeg) VALUES
                                                    (1, '2024-01-10', 30),
                                                    (2, '2024-02-15', 45),
                                                    (3, '2024-03-20', 35),
                                                    (4, '2024-04-25', 50),
                                                    (5, '2024-05-30', 40),
                                                    (6, '2024-01-10', 40),
                                                    (7, '2024-02-15', 55),
                                                    (8, '2024-03-20', 45),
                                                    (9, '2024-04-25', 50),
                                                    (10, '2024-05-30', 60);

-- Insert into organizuje (Somelijer organizes degustacija)
INSERT INTO organizuje (somelijer_IdZap, Degustacija_IdDeg) VALUES
                                                                (2, 1),
                                                                (4, 2),
                                                                (2, 3),
                                                                (4, 4),
                                                                (7, 5),
                                                                (7, 6),
                                                                (9, 7),
                                                                (2, 8),
                                                                (4, 9),
                                                                (7, 10);

-- Insert into se_uzgaja (Vineyard grows grape variety)
INSERT INTO se_uzgaja (Vinograd_IdV, Sorta_Grozdja_IdSrt) VALUES
                                                              (1, 1),
                                                              (2, 2),
                                                              (3, 3),
                                                              (4, 4),
                                                              (5, 5),
                                                              (6, 6),
                                                              (7, 7),
                                                              (8, 8),
                                                              (9, 9),
                                                              (10, 10),
                                                              (3, 5),
                                                              (2, 3),
                                                              (5, 2),
                                                              (1, 4);

-- Insert into ucestvuje (Wine contains grape variety)
INSERT INTO ucestvuje (Vino_IdVina, Sorta_Grozdja_IdSrt) VALUES
                                                             (1, 1),
                                                             (2, 2),
                                                             (3, 3),
                                                             (4, 4),
                                                             (5, 5),
                                                             (6, 6),
                                                             (7, 7),
                                                             (8, 8),
                                                             (9, 9),
                                                             (10, 10),
                                                             (5, 1),
                                                             (4, 3),
                                                             (3, 2);

-- Insert into se_prezentuje (Wine presented at degustacija)
INSERT INTO se_prezentuje (Vino_IdVina, Degustacija_IdDeg) VALUES
                                                               (1, 1),
                                                               (2, 2),
                                                               (3, 3),
                                                               (4, 4),
                                                               (5, 5),
                                                               (6, 6),
                                                               (7, 7),
                                                               (8, 8),
                                                               (9, 9),
                                                               (10, 10),
                                                               (1, 6),
                                                               (2, 7),
                                                               (3, 8);

-- Insert into se_angazuje (Harvest workers assigned)
INSERT INTO se_angazuje (Berba_IdBer, Radi_poljoprivrednik_IdZap, Radi_Vinograd_IdV) VALUES
                                                                                         (1, 1, 1),
                                                                                         (2, 3, 3),
                                                                                         (3, 5, 5),
                                                                                         (4, 6, 6),
                                                                                         (5, 8, 7),
                                                                                         (6, 10, 8);
