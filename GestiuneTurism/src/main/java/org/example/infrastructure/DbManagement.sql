CREATE DATABASE "GestiuneTurism";

\connect "GestiuneTurism";

CREATE TABLE "AgentiiTurism" (
    "agentieId" INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "numeAgentie" VARCHAR(50)
);

CREATE TABLE "Angajati" (
    "angajatId" INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "agentieId" INT,
    "username" VARCHAR(50),
    "password" VARCHAR(50),
    CONSTRAINT fk_Agentie_Angajati FOREIGN KEY ("agentieId") REFERENCES "AgentiiTurism"("agentieId")
);


CREATE TABLE "Rezervari" (
    "rezervareId" INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,    "numeClient" VARCHAR(50),
    "agentieId" INT,
    "telefon" VARCHAR(15),
    "nrLocuriRezervate" INT,
    CONSTRAINT "fk_Agentie_Rezervari" FOREIGN KEY ("agentieId") REFERENCES "AgentiiTurism"("agentieId")
);

CREATE TABLE "Excursii" (
    "excursieId" INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "obiectiv" VARCHAR(50),
    "firmaTransport" VARCHAR(50),
    "nrLocuri" INT,
    "pret" INT,
    "dataOraPlecare" TIMESTAMP
);
ALTER TABLE "Rezervari" ADD COLUMN "excursieId" INT;
ALTER TABLE "Rezervari" ADD CONSTRAINT "fk_Rezervari_Excursii" FOREIGN KEY ("excursieId") REFERENCES "Excursii"("excursieId");

ALTER TABLE "Excursii" DROP COLUMN "pret";
ALTER TABLE "Excursii" ADD COLUMN "pret" INT;


DROP  TABLE "Angajati";
DROP  TABLE "Excursii";
DROP  TABLE "Rezervari";
DROP  TABLE "AgentiiTurism";
