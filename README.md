#  Kino-system – Oppsett med PostgreSQL

Dette prosjektet simulerer et kinosystem basert på eksamensoppgaven i Objektorientert Programmering. Følg stegene under for å sette opp databasen og kjøre programmet.

---

##  1. Installer PostgreSQL

Last ned og installer PostgreSQL:  
 https://www.postgresql.org/download/

---

##  2. Opprett database og bruker

Koble til PostgreSQL med pgAdmin eller terminal, og kjør:

```sql
CREATE DATABASE kino;

DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'Case') THEN
        CREATE ROLE "Case" WITH LOGIN PASSWORD 'Esac';
    END IF;
END$$;

GRANT ALL PRIVILEGES ON DATABASE kino TO "Case";

CREATE SCHEMA IF NOT EXISTS kino AUTHORIZATION "Case";
SET search_path TO kino;
```

---

##  3. Importer databasen

Importer `Kino_postgres_v5_final.sql` i pgAdmin eller via kommandolinje for å sette opp tabeller, testdata og brukere.

---

##  4. Gi riktige rettigheter og oppdater PIN

Etter import, kjør:

```sql
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA kino TO "Case";

ALTER DEFAULT PRIVILEGES IN SCHEMA kino
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO "Case";

ALTER TABLE kino.tbllogin ALTER COLUMN l_pinkode TYPE VARCHAR(100);

-- Sett hash-verdi (erstat med faktisk verdi fra Java-hashing)
UPDATE kino.tbllogin SET l_pinkode = '<hash_for_1234>' WHERE l_brukernavn = 'tomas';
UPDATE kino.tbllogin SET l_pinkode = '<hash_for_4321>' WHERE l_brukernavn = 'samot';
```

---

##  5. Kompiler og kjør Java

Alle nødvendige `.jar`-filer (PostgreSQL, bcrypt, bytes) følger med i prosjektmappen.

###  Windows

```bash
javac -cp "postgresql-42.7.3.jar;bcrypt-0.10.2.jar;bytes-1.0.0.jar;." Main.java dao\*.java model\*.java util\*.java
java -cp "postgresql-42.7.3.jar;bcrypt-0.10.2.jar;bytes-1.0.0.jar;." Main
```

###  macOS/Linux

```bash
javac -cp "postgresql-42.7.3.jar:bcrypt-0.10.2.jar:bytes-1.0.0.jar:." Main.java dao/*.java model/*.java util/*.java
java -cp "postgresql-42.7.3.jar:bcrypt-0.10.2.jar:bytes-1.0.0.jar:." Main
```

---

##  Opprette brukere og teste funksjonalitet

1. **Start programmet via `Main.java` først** for å:
   - Opprette nye brukere (planlegger eller betjent)
   - Endre PIN-kode
   - Teste innlogging

2. **Etter at du har opprettet brukere**, kjør `HovedMeny.java` for å:
   - Logge inn som kunde eller ansatt
   - Bruke betjent- og planleggerverktøyene
   - Reservere billetter eller administrere visninger

---

##  Ferdig!

Du kan nå logge inn og bruke kino-appen med både kunde- og ansatt-funksjoner 
