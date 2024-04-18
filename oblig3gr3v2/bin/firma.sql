DROP SCHEMA IF EXISTS oblig3   CASCADE;
CREATE SCHEMA oblig3;
SET search_path TO  oblig3;

-- ansatt tabellen
CREATE TABLE Ansatt
(
    ansatt_id       SERIAL                      ,
    brukernavn      VARCHAR (50) NOT NULL UNIQUE,
    fornavn         VARCHAR (50) NOT NULL       ,
    etternavn       VARCHAR (50) NOT NULL       ,
    ansettelsesDato DATE         NOT NULL       ,
    stilling        VARCHAR (50) NOT NULL       ,
    mlonn           NUMERIC      NOT NULL       ,
    avdelings_id    INTEGER      NOT NULL       ,
    PRIMARY KEY (ansatt_id)
);

INSERT INTO Ansatt(brukernavn, fornavn, etternavn, ansettelsesDato, stilling, mlonn, avdelings_id)
VALUES
('phmc' , 'Phill'     , 'McCracken'  , '2019-01-01', 'Hardware Engineer'    , 70000, 1),
('doke' , 'Don'       , 'Keedick'    , '2016-05-04', 'Data Analyst'         , 75000, 2),
('eido' , 'Eileen'    , 'Dover'      , '2017-04-14', 'Systems Architect'    , 95000, 3),
('bedo' , 'Ben'       , 'Dover'      , '2018-11-07', 'DevOps Specialist'    , 90000, 3),
('wake' , 'Wayne'     , 'Kerr'       , '2019-04-26', 'Technical Writer'     , 65000, 2),
('deza' , 'Dee'       , 'Zaster'     , '2016-10-25', 'UX/UI Designer'       , 70000, 1),
('elko' , 'Ellie'     , 'Kopter'     , '2020-06-01', 'Aviation Expert'      , 80000, 2),
('gaba' , 'Gabe'      , 'Barr'       , '2015-02-12', 'Data Scientist'       , 95000, 3),
('hasa' , 'Harry'     , 'Sachs'      , '2018-01-27', 'Cloud Engineer'       , 82000, 1),
('huja' , 'Hugh'      , 'Jass'       , '2017-07-03', 'Cybersecurity Expert' , 75000, 2);

-- avdelings tabellen
CREATE TABLE Avdeling
(
    avdelings_id    SERIAL      ,
    avdeling_navn   VARCHAR(50) ,
    avdeling_sjef   INTEGER     ,
    PRIMARY KEY (avdelings_id)  ,
    FOREIGN KEY (avdeling_sjef) REFERENCES Ansatt(ansatt_id) ON DELETE CASCADE
);

INSERT INTO Avdeling(avdeling_navn, avdeling_sjef)
VALUES
(    'Research'    , 1),
(    'Marketing'   , 4),
(    'Design'      , 6),
(    'Sales'       , 2),
(    'IT'          , 3);


-- prosjekt tabellen
CREATE TABLE Prosjekt
(
    prosjekt_id             SERIAL               ,
    prosjekt_navn           VARCHAR(50)  NOT NULL,
    prosjekt_beskrivelse    VARCHAR(255) NOT NULL,
    PRIMARY KEY (prosjekt_id)                    
);

INSERT INTO Prosjekt(prosjekt_navn, prosjekt_beskrivelse)
VALUES
    ('Project X'    ,   'Improve our customer support processes'                 ),
    ('Project Y'    ,   'Design a new user interface for our website'            ),
    ('Project Z'    ,   'Conduct a feasibility study for a new product line'     ),
    ('Project W'    ,   'Develop a new e-learning platform for employee training');


-- Deltager tabellen
CREATE TABLE Deltager
(
    deltager_id       SERIAL              ,
    ansatt_id         INTEGER     NOT NULL,  
    prosjekt_id       INTEGER     NOT NULL,
    rolle             VARCHAR(50) NOT NULL,
    arbeidstimer      INTEGER     NOT NULL,
    PRIMARY KEY (deltager_id)             ,
    FOREIGN KEY (ansatt_id)     REFERENCES Ansatt(ansatt_id)     ON DELETE CASCADE,
    FOREIGN KEY (prosjekt_id)   REFERENCES Prosjekt(prosjekt_id) ON DELETE CASCADE
);

INSERT INTO Deltager (ansatt_id, prosjekt_id, rolle, arbeidstimer)
VALUES
(1,  1, 'Project Manager', 40),
(2,  1, 'Developer'      , 30),
(3,  2, 'Project Manager', 35),
(4,  3, 'Designer'       , 20),
(5,  3, 'Developer'      , 15),
(6,  1, 'Analyst'        , 25),
(7,  2, 'Developer'      , 30),
(8,  4, 'Project Manager', 40),
(9,  4, 'Analyst'        , 35),
(10, 4, 'Designer'       , 20);

-- En ansatt kan ikke slettes hvis den ansatte er avdelingssjef
ALTER TABLE Avdeling ADD CONSTRAINT fk_avdeling_sjef
FOREIGN KEY (avdeling_sjef) REFERENCES Ansatt(ansatt_id)
ON DELETE RESTRICT;

-- Kan ikke slette en avdeling hvis det er ansatte som jobber der
ALTER TABLE Ansatt ADD CONSTRAINT fk_ansatt_avdeling
FOREIGN KEY (avdelings_id) REFERENCES Avdeling(avdelings_id)
ON DELETE RESTRICT;