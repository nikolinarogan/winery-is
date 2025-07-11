CREATE SEQUENCE IF NOT EXISTS kupac_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS narudzba_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS boca_serbr_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS vino_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS sorta_grozdja_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS vinograd_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS zaposleni_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS berba_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS degustacija_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS podrum_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS bure_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE Vinograd (
                          IdV INTEGER NOT NULL DEFAULT nextval('vinograd_id_seq'),
                          ImeV VARCHAR(150) NOT NULL,
                          PoV DOUBLE PRECISION NOT NULL,
                          DatOsn DATE NOT NULL,
                          VKap DOUBLE PRECISION NOT NULL,
                          Vinograd_IdV INTEGER,
                          PRIMARY KEY (IdV),
                          FOREIGN KEY (Vinograd_IdV) REFERENCES Vinograd(IdV)
);

CREATE TABLE Berba (
                       IdBer INTEGER NOT NULL DEFAULT nextval('berba_id_seq'),
                       DatBer DATE NOT NULL,
                       Vinograd_IdV INTEGER NOT NULL,
                       PRIMARY KEY (IdBer, Vinograd_IdV),
                       FOREIGN KEY (Vinograd_IdV) REFERENCES Vinograd(IdV)
);

CREATE TABLE Vino (
                      IdVina INTEGER NOT NULL DEFAULT nextval('vino_id_seq'),
                      God DATE NOT NULL,
                      ProcAlk DOUBLE PRECISION NOT NULL,
                      NazVina VARCHAR(100) NOT NULL,
                      PRIMARY KEY (IdVina)
);
CREATE TABLE Kupac (
                       IdKup INTEGER NOT NULL DEFAULT nextval('kupac_id_seq'),
                       Email VARCHAR(150) NOT NULL,
                       BrTel VARCHAR(30) NOT NULL,
                       PRIMARY KEY (IdKup)
);
CREATE TABLE Narudzba (
                          IdNar INTEGER NOT NULL DEFAULT nextval('narudzba_id_seq'),
                          DatNar DATE NOT NULL,
                          PltMtd VARCHAR(30) NOT NULL CHECK (PltMtd IN ('karticno placanje', 'gotovinsko placanje')),
                          Kupac_IdKup INTEGER NOT NULL,
                          PRIMARY KEY (IdNar),
                          FOREIGN KEY (Kupac_IdKup) REFERENCES Kupac(IdKup)
);



CREATE TABLE Boca (
                      SerBr INTEGER NOT NULL DEFAULT nextval('boca_serbr_seq'),
                      KapBoc DOUBLE PRECISION NOT NULL CHECK (KapBoc IN (0.5, 0.75, 1.0, 1.5)),
                      Vino_IdVina INTEGER,
                      Narudzba_IdNar INTEGER,
                      PRIMARY KEY (SerBr),
                      FOREIGN KEY (Vino_IdVina) REFERENCES Vino(IdVina),
                      FOREIGN KEY (Narudzba_IdNar) REFERENCES Narudzba(IdNar)
);

CREATE TABLE Podrum (
                        IdPod INTEGER NOT NULL DEFAULT nextval('podrum_id_seq'),
                        PodTmp DOUBLE PRECISION NOT NULL,
                        PodVl DOUBLE PRECISION NOT NULL,
                        KapPod DOUBLE PRECISION NOT NULL,
                        PRIMARY KEY (IdPod)
);

CREATE TABLE Bure (
                      IdBur INTEGER NOT NULL DEFAULT nextval('bure_id_seq'),
                      KapBur INTEGER NOT NULL CHECK (KapBur IN (10, 20, 50, 100)),
                      GodPr DATE NOT NULL,
                      Vino_IdVina INTEGER,
                      Podrum_IdPod INTEGER,
                      PRIMARY KEY (IdBur),
                      FOREIGN KEY (Vino_IdVina) REFERENCES Vino(IdVina),
                      FOREIGN KEY (Podrum_IdPod) REFERENCES Podrum(IdPod)
);

CREATE TABLE Degustacija (
                             IdDeg INTEGER NOT NULL DEFAULT nextval('degustacija_id_seq'),
                             DatDeg DATE NOT NULL,
                             KapDeg INTEGER NOT NULL,
                             PRIMARY KEY (IdDeg)
);

CREATE TABLE Zaposleni (
                           IdZap INTEGER NOT NULL DEFAULT nextval('zaposleni_id_seq'),
                           DatZap DATE NOT NULL,
                           Zar DOUBLE PRECISION NOT NULL,
                           ImeZap VARCHAR(100) NOT NULL,
                           kat VARCHAR(100) NOT NULL CHECK (kat IN ('poljoprivrednik', 'somelijer')),
                           PRIMARY KEY (IdZap)
);

CREATE TABLE poljoprivrednik (
                                 IdZap INTEGER NOT NULL,
                                 PRIMARY KEY (IdZap),
                                 FOREIGN KEY (IdZap) REFERENCES Zaposleni(IdZap)
);

CREATE TABLE somelijer (
                           IdZap INTEGER NOT NULL,
                           PRIMARY KEY (IdZap),
                           FOREIGN KEY (IdZap) REFERENCES Zaposleni(IdZap)
);

CREATE TABLE Radi (
                      poljoprivrednik_IdZap INTEGER NOT NULL,
                      Vinograd_IdV INTEGER NOT NULL,
                      PRIMARY KEY (poljoprivrednik_IdZap, Vinograd_IdV),
                      FOREIGN KEY (poljoprivrednik_IdZap) REFERENCES poljoprivrednik(IdZap),
                      FOREIGN KEY (Vinograd_IdV) REFERENCES Vinograd(IdV)
);

CREATE TABLE se_angazuje (
                             Berba_IdBer INTEGER NOT NULL,
                             Vinograd_IdV INTEGER NOT NULL,
                             Radi_poljoprivrednik_IdZap INTEGER NOT NULL,
                             PRIMARY KEY (Berba_IdBer, Vinograd_IdV, Radi_poljoprivrednik_IdZap),
                             FOREIGN KEY (Berba_IdBer, Vinograd_IdV) REFERENCES Berba(IdBer, Vinograd_IdV),
                             FOREIGN KEY (Radi_poljoprivrednik_IdZap, Vinograd_IdV) REFERENCES Radi(poljoprivrednik_IdZap, Vinograd_IdV)
);

CREATE TABLE organizuje (
                            somelijer_IdZap INTEGER NOT NULL,
                            Degustacija_IdDeg INTEGER NOT NULL,
                            PRIMARY KEY (somelijer_IdZap, Degustacija_IdDeg),
                            FOREIGN KEY (somelijer_IdZap) REFERENCES somelijer(IdZap),
                            FOREIGN KEY (Degustacija_IdDeg) REFERENCES Degustacija(IdDeg)
);

CREATE TABLE Sorta_Grozdja (
                               IdSrt INTEGER NOT NULL DEFAULT nextval('sorta_grozdja_id_seq'),
                               Boja VARCHAR(50) NOT NULL CHECK (Boja IN ('bela', 'roze', 'crvena')),
                               ProcSec DOUBLE PRECISION NOT NULL,
                               NazSrt VARCHAR(100) NOT NULL,
                               PRIMARY KEY (IdSrt)
);

CREATE TABLE ucestvuje (
                           Vino_IdVina INTEGER NOT NULL,
                           Sorta_Grozdja_IdSrt INTEGER NOT NULL,
                           PRIMARY KEY (Vino_IdVina, Sorta_Grozdja_IdSrt),
                           FOREIGN KEY (Vino_IdVina) REFERENCES Vino(IdVina),
                           FOREIGN KEY (Sorta_Grozdja_IdSrt) REFERENCES Sorta_Grozdja(IdSrt)
);

CREATE TABLE se_prezentuje (
                               Vino_IdVina INTEGER NOT NULL,
                               Degustacija_IdDeg INTEGER NOT NULL,
                               PRIMARY KEY (Vino_IdVina, Degustacija_IdDeg),
                               FOREIGN KEY (Vino_IdVina) REFERENCES Vino(IdVina),
                               FOREIGN KEY (Degustacija_IdDeg) REFERENCES Degustacija(IdDeg)
);

CREATE TABLE se_uzgaja (
                           Vinograd_IdV INTEGER NOT NULL,
                           Sorta_Grozdja_IdSrt INTEGER NOT NULL,
                           PRIMARY KEY (Vinograd_IdV, Sorta_Grozdja_IdSrt),
                           FOREIGN KEY (Vinograd_IdV) REFERENCES Vinograd(IdV),
                           FOREIGN KEY (Sorta_Grozdja_IdSrt) REFERENCES Sorta_Grozdja(IdSrt)
);
