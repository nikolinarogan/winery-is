-- Sequences for single-column primary keys
CREATE SEQUENCE berba_IdBer_seq START 1;
CREATE SEQUENCE boca_SerBr_seq START 1;
CREATE SEQUENCE bure_IdBur_seq START 1;
CREATE SEQUENCE degustacija_IdDeg_seq START 1;
CREATE SEQUENCE kupac_IdKup_seq START 1;
CREATE SEQUENCE narudzba_IdNar_seq START 1;
CREATE SEQUENCE podrum_IdPod_seq START 1;
CREATE SEQUENCE poljoprivrednik_IdZap_seq START 1;
CREATE SEQUENCE somelijer_IdZap_seq START 1;
CREATE SEQUENCE sorta_grozdja_IdSrt_seq START 1;
CREATE SEQUENCE vino_IdVina_seq START 1;
CREATE SEQUENCE vinograd_IdV_seq START 1;
CREATE SEQUENCE zaposleni_IdZap_seq START 1;

-- Table: Zaposleni (no FK dependencies)
CREATE TABLE Zaposleni (
  IdZap INTEGER NOT NULL DEFAULT nextval('zaposleni_IdZap_seq'),
  DatZap DATE NOT NULL,
  Zar REAL NOT NULL,
  ImeZap VARCHAR(30) NOT NULL,
  kat VARCHAR(100) NOT NULL,
  PRIMARY KEY (IdZap),
  CHECK (kat IN ('poljoprivrednik', 'somelijer'))
);

-- Table: Vinograd (self-reference Vinograd_IdV)
CREATE TABLE Vinograd (
  IdV INTEGER NOT NULL DEFAULT nextval('vinograd_IdV_seq'),
  ImeV VARCHAR(150) NOT NULL,
  PoV REAL NOT NULL,
  DatOsn DATE NOT NULL,
  VKap REAL NOT NULL,
  Vinograd_IdV INTEGER,
  PRIMARY KEY (IdV),
  FOREIGN KEY (Vinograd_IdV) REFERENCES Vinograd(IdV)
);

-- Table: Kupac
CREATE TABLE Kupac (
  IdKup INTEGER NOT NULL DEFAULT nextval('kupac_IdKup_seq'),
  Email VARCHAR(150) NOT NULL,
  BrTel VARCHAR(30),
  PRIMARY KEY (IdKup)
);

-- Table: Vino
CREATE TABLE Vino (
  IdVina INTEGER NOT NULL DEFAULT nextval('vino_IdVina_seq'),
  God DATE NOT NULL,
  ProcAlk REAL NOT NULL,
  NazVina VARCHAR(100) NOT NULL,
  PRIMARY KEY (IdVina)
);

-- Table: Sorta_Grozdja
CREATE TABLE Sorta_Grozdja (
  IdSrt INTEGER NOT NULL DEFAULT nextval('sorta_grozdja_IdSrt_seq'),
  Boja VARCHAR(50) NOT NULL,
  ProcSec REAL,
  NazSrt VARCHAR(100) NOT NULL,
  PRIMARY KEY (IdSrt),
  CHECK (Boja IN ('crvena', 'roze', 'bela'))
);

-- Table: Podrum
CREATE TABLE Podrum (
  IdPod INTEGER NOT NULL DEFAULT nextval('podrum_IdPod_seq'),
  PodTmp REAL NOT NULL,
  PodVl REAL NOT NULL,
  KapPod REAL NOT NULL,
  PRIMARY KEY (IdPod)
);

-- Table: Narudzba (depends on Kupac)
CREATE TABLE Narudzba (
  IdNar INTEGER NOT NULL DEFAULT nextval('narudzba_IdNar_seq'),
  DatNar DATE NOT NULL,
  PltMtd VARCHAR(30) NOT NULL,
  Kupac_IdKup INTEGER NOT NULL,
  PRIMARY KEY (IdNar),
  CHECK (PltMtd IN ('Karticno placanje', 'Gotovinsko placanje')),
  FOREIGN KEY (Kupac_IdKup) REFERENCES Kupac(IdKup)
);

-- Table: Zaposleni Subtypes: poljoprivrednik and somelijer (depends on Zaposleni)

CREATE TABLE poljoprivrednik (
  IdZap INTEGER NOT NULL DEFAULT nextval('poljoprivrednik_IdZap_seq'),
  OblastRada VARCHAR(70),
  PRIMARY KEY (IdZap),
  FOREIGN KEY (IdZap) REFERENCES Zaposleni(IdZap)
);

CREATE TABLE somelijer (
  IdZap INTEGER NOT NULL DEFAULT nextval('somelijer_IdZap_seq'),
  Sertifikat VARCHAR(70),
  PRIMARY KEY (IdZap),
  FOREIGN KEY (IdZap) REFERENCES Zaposleni(IdZap)
);

-- Table: Radi (depends on poljoprivrednik and Vinograd)
CREATE TABLE Radi (
  poljoprivrednik_IdZap INTEGER NOT NULL,
  Vinograd_IdV INTEGER NOT NULL,
  PRIMARY KEY (poljoprivrednik_IdZap, Vinograd_IdV),
  FOREIGN KEY (poljoprivrednik_IdZap) REFERENCES poljoprivrednik(IdZap),
  FOREIGN KEY (Vinograd_IdV) REFERENCES Vinograd(IdV)
);

-- Table: Berba (depends on Vinograd)
CREATE TABLE Berba (
  IdBer INTEGER NOT NULL DEFAULT nextval('berba_IdBer_seq'),
  DatBer DATE NOT NULL,
  Vinograd_IdV INTEGER NOT NULL,
  PRIMARY KEY (IdBer, Vinograd_IdV),
  FOREIGN KEY (Vinograd_IdV) REFERENCES Vinograd(IdV)
);

-- Table: Bure (depends on Podrum, Vino)
CREATE TABLE Bure (
  IdBur INTEGER NOT NULL DEFAULT nextval('bure_IdBur_seq'),
  KapBur INTEGER NOT NULL,
  GodPr DATE,
  Vino_IdVina INTEGER,
  Podrum_IdPod INTEGER,
  PRIMARY KEY (IdBur),
  CHECK (KapBur IN (10, 20, 50, 100)),
  FOREIGN KEY (Podrum_IdPod) REFERENCES Podrum(IdPod),
  FOREIGN KEY (Vino_IdVina) REFERENCES Vino(IdVina)
);

-- Table: Boca (depends on Vino, Narudzba)
CREATE TABLE Boca (
  SerBr INTEGER NOT NULL DEFAULT nextval('boca_SerBr_seq'),
  KapBoc REAL NOT NULL,
  Vino_IdVina INTEGER,
  Narudzba_IdNar INTEGER,
  PRIMARY KEY (SerBr),
  CHECK (KapBoc IN (0.5, 0.75, 1.0, 1.75)),
  FOREIGN KEY (Narudzba_IdNar) REFERENCES Narudzba(IdNar),
  FOREIGN KEY (Vino_IdVina) REFERENCES Vino(IdVina)
);

-- Table: Degustacija
CREATE TABLE Degustacija (
  IdDeg INTEGER NOT NULL DEFAULT nextval('degustacija_IdDeg_seq'),
  DatDeg DATE NOT NULL,
  KapDeg INTEGER NOT NULL,
  PRIMARY KEY (IdDeg)
);

-- Table: organizuje (depends on somelijer and Degustacija)
CREATE TABLE organizuje (
  somelijer_IdZap INTEGER NOT NULL,
  Degustacija_IdDeg INTEGER NOT NULL,
  PRIMARY KEY (somelijer_IdZap, Degustacija_IdDeg),
  FOREIGN KEY (Degustacija_IdDeg) REFERENCES Degustacija(IdDeg),
  FOREIGN KEY (somelijer_IdZap) REFERENCES somelijer(IdZap)
);

-- Table: se_uzgaja (depends on Vinograd and Sorta_Grozdja)
CREATE TABLE se_uzgaja (
  Vinograd_IdV INTEGER NOT NULL,
  Sorta_Grozdja_IdSrt INTEGER NOT NULL,
  PRIMARY KEY (Vinograd_IdV, Sorta_Grozdja_IdSrt),
  FOREIGN KEY (Sorta_Grozdja_IdSrt) REFERENCES Sorta_Grozdja(IdSrt),
  FOREIGN KEY (Vinograd_IdV) REFERENCES Vinograd(IdV)
);

-- Table: ucestvuje (depends on Vino and Sorta_Grozdja)
CREATE TABLE ucestvuje (
  Vino_IdVina INTEGER NOT NULL,
  Sorta_Grozdja_IdSrt INTEGER NOT NULL,
  PRIMARY KEY (Vino_IdVina, Sorta_Grozdja_IdSrt),
  FOREIGN KEY (Sorta_Grozdja_IdSrt) REFERENCES Sorta_Grozdja(IdSrt),
  FOREIGN KEY (Vino_IdVina) REFERENCES Vino(IdVina)
);

-- Table: se_prezentuje (depends on Vino and Degustacija)
CREATE TABLE se_prezentuje (
  Vino_IdVina INTEGER NOT NULL,
  Degustacija_IdDeg INTEGER NOT NULL,
  PRIMARY KEY (Vino_IdVina, Degustacija_IdDeg),
  FOREIGN KEY (Degustacija_IdDeg) REFERENCES Degustacija(IdDeg),
  FOREIGN KEY (Vino_IdVina) REFERENCES Vino(IdVina)
);

-- Table: se_angazuje (depends on Berba and Radi)
CREATE TABLE se_angazuje (
  Berba_IdBer INTEGER NOT NULL,
  Radi_poljoprivrednik_IdZap INTEGER NOT NULL,
  Radi_Vinograd_IdV INTEGER NOT NULL,
  PRIMARY KEY (Berba_IdBer, Radi_poljoprivrednik_IdZap, Radi_Vinograd_IdV),
  FOREIGN KEY (Berba_IdBer, Radi_Vinograd_IdV) REFERENCES Berba(IdBer, Vinograd_IdV),
  FOREIGN KEY (Radi_poljoprivrednik_IdZap, Radi_Vinograd_IdV) REFERENCES Radi(poljoprivrednik_IdZap, Vinograd_IdV)
);

-- ======================
-- TRIGGERS for subtype discriminator enforcement

-- Trigger function to enforce kat='somelijer' in somelijer table
CREATE OR REPLACE FUNCTION check_somelijer_kat() RETURNS trigger AS $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM Zaposleni WHERE IdZap = NEW.IdZap AND kat = 'somelijer') THEN
    RAISE EXCEPTION 'FK somelijer_Zaposleni_FK violates kat discriminator column must be ''somelijer''';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_check_somelijer_kat
BEFORE INSERT OR UPDATE ON somelijer
FOR EACH ROW EXECUTE FUNCTION check_somelijer_kat();

-- Trigger function to enforce kat='poljoprivrednik' in poljoprivrednik table
CREATE OR REPLACE FUNCTION check_poljoprivrednik_kat() RETURNS trigger AS $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM Zaposleni WHERE IdZap = NEW.IdZap AND kat = 'poljoprivrednik') THEN
    RAISE EXCEPTION 'FK poljoprivrednik_Zaposleni_FK violates kat discriminator column must be ''poljoprivrednik''';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_check_poljoprivrednik_kat
BEFORE INSERT OR UPDATE ON poljoprivrednik
FOR EACH ROW EXECUTE FUNCTION check_poljoprivrednik_kat();
