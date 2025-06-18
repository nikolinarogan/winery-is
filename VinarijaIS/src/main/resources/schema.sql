-- PostgreSQL compatible schema for Vinarija

-- Create sequences for auto-incrementing IDs
CREATE SEQUENCE IF NOT EXISTS vinograd_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS sorta_grozdja_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS vino_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS zaposleni_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS berba_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS degustacija_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS kupac_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS narudzba_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS podrum_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS bure_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS boca_ser_br_seq START WITH 1 INCREMENT BY 1;

-- Main tables
CREATE TABLE Vinograd (
                          IdV INTEGER PRIMARY KEY DEFAULT nextval('vinograd_id_seq'),
                          ImeV VARCHAR(150) NOT NULL,
                          PoV FLOAT,
                          DatOsn DATE,
                          VKap FLOAT,
                          Vinograd_IdV INTEGER REFERENCES Vinograd(IdV)
);

CREATE TABLE Sorta_Grozdja (
                               IdSrt INTEGER PRIMARY KEY DEFAULT nextval('sorta_grozdja_id_seq'),
                               Boja VARCHAR(50),
                               ProcSec FLOAT,
                               NazSrt VARCHAR(100) NOT NULL
);

CREATE TABLE Vino (
                      IdVina INTEGER PRIMARY KEY DEFAULT nextval('vino_id_seq'),
                      God DATE,
                      ProcAlk FLOAT,
                      NazVina VARCHAR(100) NOT NULL
);

CREATE TABLE Zaposleni (
                           IdZap INTEGER PRIMARY KEY DEFAULT nextval('zaposleni_id_seq'),
                           DatZap DATE NOT NULL,
                           Zar FLOAT NOT NULL,
                           ImeZap VARCHAR(100),
                           kat VARCHAR(100) NOT NULL CHECK (kat IN ('poljoprivrednik', 'somelijer'))
);

CREATE TABLE Berba (
                       IdBer INTEGER NOT NULL,
                       DatBer DATE NOT NULL,
                       Vinograd_IdV INTEGER NOT NULL REFERENCES Vinograd(IdV),
                       PRIMARY KEY (IdBer, Vinograd_IdV)
);

CREATE TABLE Degustacija (
                             IdDeg INTEGER PRIMARY KEY DEFAULT nextval('degustacija_id_seq'),
                             DatDeg DATE NOT NULL,
                             KapDeg INTEGER NOT NULL
);

CREATE TABLE Kupac (
                       IdKup INTEGER PRIMARY KEY DEFAULT nextval('kupac_id_seq'),
                       Email VARCHAR(150),
                       BrTel VARCHAR(30)
);

CREATE TABLE Narudzba (
                          IdNar INTEGER PRIMARY KEY DEFAULT nextval('narudzba_id_seq'),
                          DatNar DATE NOT NULL,
                          PltMtd VARCHAR(30) NOT NULL,
                          Kupac_IdKup INTEGER NOT NULL REFERENCES Kupac(IdKup)
);

CREATE TABLE Podrum (
                        IdPod INTEGER PRIMARY KEY DEFAULT nextval('podrum_id_seq'),
                        PodTmp FLOAT,
                        PodVl FLOAT,
                        KapPod FLOAT
);

CREATE TABLE Bure (
                      IdBur INTEGER PRIMARY KEY DEFAULT nextval('bure_id_seq'),
                      KapBur BYTEA, -- PostgreSQL equivalent of BLOB
                      GodPr DATE,
                      Vino_IdVina INTEGER REFERENCES Vino(IdVina),
                      Podrum_IdPod INTEGER REFERENCES Podrum(IdPod)
);

CREATE TABLE Boca (
                      SerBr INTEGER PRIMARY KEY DEFAULT nextval('boca_ser_br_seq'),
                      KapBoc FLOAT NOT NULL,
                      Vino_IdVina INTEGER REFERENCES Vino(IdVina),
                      Narudzba_IdNar INTEGER REFERENCES Narudzba(IdNar)
);

-- Subtype tables for employees
CREATE TABLE poljoprivrednik (
                                 IdZap INTEGER PRIMARY KEY REFERENCES Zaposleni(IdZap)
);

CREATE TABLE somelijer (
                           IdZap INTEGER PRIMARY KEY REFERENCES Zaposleni(IdZap)
);

-- Junction tables
CREATE TABLE se_uzgaja (
                           Vinograd_IdV INTEGER NOT NULL REFERENCES Vinograd(IdV),
                           Sorta_Grozdja_IdSrt INTEGER NOT NULL REFERENCES Sorta_Grozdja(IdSrt),
                           PRIMARY KEY (Vinograd_IdV, Sorta_Grozdja_IdSrt)
);

CREATE TABLE ucestvuje (
                           Vino_IdVina INTEGER NOT NULL REFERENCES Vino(IdVina),
                           Sorta_Grozdja_IdSrt INTEGER NOT NULL REFERENCES Sorta_Grozdja(IdSrt),
                           PRIMARY KEY (Vino_IdVina, Sorta_Grozdja_IdSrt)
);

CREATE TABLE Radi (
                      poljoprivrednik_IdZap INTEGER NOT NULL REFERENCES poljoprivrednik(IdZap),
                      Vinograd_IdV INTEGER NOT NULL REFERENCES Vinograd(IdV),
                      PRIMARY KEY (poljoprivrednik_IdZap, Vinograd_IdV)
);

CREATE TABLE se_angazuje (
                             Berba_IdBer INTEGER NOT NULL,
                             Berba_Vinograd_IdV INTEGER NOT NULL,
                             Radi_poljoprivrednik_IdZap INTEGER NOT NULL,
                             Radi_Vinograd_IdV INTEGER NOT NULL,
                             PRIMARY KEY (Berba_IdBer, Berba_Vinograd_IdV, Radi_poljoprivrednik_IdZap, Radi_Vinograd_IdV),
                             FOREIGN KEY (Berba_IdBer, Berba_Vinograd_IdV) REFERENCES Berba(IdBer, Vinograd_IdV),
                             FOREIGN KEY (Radi_poljoprivrednik_IdZap, Radi_Vinograd_IdV) REFERENCES Radi(poljoprivrednik_IdZap, Vinograd_IdV)
);

CREATE TABLE organizuje (
                            somelijer_IdZap INTEGER NOT NULL REFERENCES somelijer(IdZap),
                            Degustacija_IdDeg INTEGER NOT NULL REFERENCES Degustacija(IdDeg),
                            PRIMARY KEY (somelijer_IdZap, Degustacija_IdDeg)
);

CREATE TABLE se_prezentuje (
                               Vino_IdVina INTEGER NOT NULL REFERENCES Vino(IdVina),
                               Degustacija_IdDeg INTEGER NOT NULL REFERENCES Degustacija(IdDeg),
                               PRIMARY KEY (Vino_IdVina, Degustacija_IdDeg)
);

--  sequences to start after existing data (run after inserting sample data)
--  new inserts get unique IDs
SELECT setval('vinograd_id_seq', COALESCE((SELECT MAX(IdV) FROM Vinograd), 0) + 1);
SELECT setval('sorta_grozdja_id_seq', COALESCE((SELECT MAX(IdSrt) FROM Sorta_Grozdja), 0) + 1);
SELECT setval('vino_id_seq', COALESCE((SELECT MAX(IdVina) FROM Vino), 0) + 1);
SELECT setval('zaposleni_id_seq', COALESCE((SELECT MAX(IdZap) FROM Zaposleni), 0) + 1);
SELECT setval('degustacija_id_seq', COALESCE((SELECT MAX(IdDeg) FROM Degustacija), 0) + 1);
SELECT setval('kupac_id_seq', COALESCE((SELECT MAX(IdKup) FROM Kupac), 0) + 1);
SELECT setval('narudzba_id_seq', COALESCE((SELECT MAX(IdNar) FROM Narudzba), 0) + 1);
SELECT setval('podrum_id_seq', COALESCE((SELECT MAX(IdPod) FROM Podrum), 0) + 1);
SELECT setval('bure_id_seq', COALESCE((SELECT MAX(IdBur) FROM Bure), 0) + 1);
SELECT setval('boca_ser_br_seq', COALESCE((SELECT MAX(SerBr) FROM Boca), 0) + 1);
