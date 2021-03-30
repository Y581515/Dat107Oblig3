-- Skript for aa opprette databasen og legge inn litt data
    -- Skjema = Oving_JPA
        -- Tabell(er) = Ansatt 

-- MERK!!! DROP SCHEMA ... CASCADE sletter alt !!!
DROP SCHEMA IF EXISTS Oving_JPA CASCADE;

CREATE SCHEMA Oving_JPA;
SET search_path TO Oving_JPA;
    
CREATE TABLE Avdeling (
  avdelingid SERIAL,
  Navn       VARCHAR(30),
  Sjef_Id    INTEGER NOT NULL, 
  CONSTRAINT Avdeling_PK PRIMARY KEY (avdelingid)
);

CREATE TABLE Ansatt
(
	ansattid SERIAL,
    brukernavn Char(4) unique NOT NULL ,
    fornavn VARCHAR(30) NOT NULL,
	etternavn VARCHAR(30) NOT NULL,
	ansettelsesdato Date,
	stilling VARCHAR(30) NOT NULL,
	maanadslon DECIMAL(10,2),
	Avd_Id INTEGER ,
    CONSTRAINT brukernavn_pk PRIMARY KEY (ansattid),
    CONSTRAINT Avdeling_FK FOREIGN KEY (Avd_Id) 
  	REFERENCES Avdeling(avdelingid) -- med referanse til en Avdeling
);

INSERT INTO Avdeling(Navn, Sjef_Id) VALUES 
	('avd1', 1);

INSERT INTO
  Ansatt(brukernavn,fornavn,etternavn,ansettelsesdato,Stilling,maanadslon,Avd_Id)
VALUES
    ('Khal','Khalid','Hassan','2024-06-01','Dataingenior',750000,1),
    ('Yosa','Yosafe','Fesaha','2024-07-01','Dataingenior',750001,1),
	('Lars','Lars','Peters','2016-06-01','Larer',900000,1),
	('Håko','Håkon','Martin','2024-06-01','Dataingenior',750000,1),
	('Tor','Torje','Torden','2024-06-01','Dataingenior',800000,1),
	('Sti','Stian','Hardråde','2024-06-01','Dataingenior',800000,1),
	('Har','Harald','Halsen','2015-06-01','larer',900000,1),
	('Sven','Sven','Olai','2015-06-01','Larer',900000,1),
	('Pat','Patric','Patricio','2018-06-01','Larer',850000,1),
	('emi','emillie','Hassan','2023-06-01','Dataingenior',760000,1);
	
ALTER TABLE Avdeling ADD CONSTRAINT Sjef_FK FOREIGN KEY (Sjef_Id) 
  		REFERENCES Ansatt(ansattid); 
		
	
INSERT INTO Avdeling (Navn, Sjef_Id) VALUES
  ('avd2', 3); -- Prover med Lars som sjef (ansatt nr 3)
UPDATE Ansatt SET Avd_Id = 2 WHERE ansattid = 3; -- Endrer Lars sin avdeling til 2

INSERT INTO Avdeling (Navn, Sjef_Id) VALUES
  ('avd3', 7); -- Prover med Lars som sjef (ansatt nr 3)
UPDATE Ansatt SET Avd_Id = 3 WHERE ansattid = 7; -- Endrer Harald sin avdeling til 3
	

	
CREATE TABLE Prosjekt
(
  projektid        SERIAL,
  Navn      VARCHAR(30),
  Beskrivelse VARCHAR(30),
  CONSTRAINT Prosjekt_PK PRIMARY KEY (projektid)
);

-- Koblingstabellen har i dette tilfellet egne data (timer)
-- Blir da en egen selvstendig entitet.
-- NÃ¥ med surrugatnÃ¸kkel for Ã¥ forenkle kodingen.
CREATE TABLE Prosjektdeltagelse
(
  Prosjektdeltagelse_Id SERIAL,
  Ansatt_Id INTEGER,
  Prosjekt_Id INTEGER,
  Timer     INTEGER,
  Role      VARCHAR(30),
  CONSTRAINT Prosjektdeltagelse_PK PRIMARY KEY (Prosjektdeltagelse_Id),
  CONSTRAINT AnsattProsjekt_Unik UNIQUE  (Ansatt_Id, Prosjekt_Id),
  CONSTRAINT Ansatt_FK FOREIGN KEY (Ansatt_Id) REFERENCES Ansatt(ansattid),
  CONSTRAINT Prosjekt_FK FOREIGN KEY (Prosjekt_Id) REFERENCES Prosjekt(projektid)  
);

INSERT INTO
  Prosjekt(Navn,Beskrivelse)
VALUES
  ('projekt1','gjør projekt1'),
  ('projekt2','gjør projekt2'),
  ('projekt3','gjør projekt3');

INSERT INTO
  Prosjektdeltagelse(Ansatt_Id, Prosjekt_Id, Timer,Role)
VALUES
  (1, 1, 50,'Leder'),
  (2, 1, 100,'LederAsistant'),
  (2, 2, 150,'Leder'),
  (3, 1, 200,'LederAsistant'),
  (3, 2, 250,'LederAsistant'),
  (4, 3, 300,'Leder');

	