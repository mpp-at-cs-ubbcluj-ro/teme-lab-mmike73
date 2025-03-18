package org.example;

import org.example.domain.AgentieTurism;
import org.example.domain.Angajat;
import org.example.domain.Excursie;
import org.example.domain.Rezervare;
import org.example.infrastructure.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        DbConnection dbConnection = new DbConnection(
                "jdbc:postgresql://localhost:5432/GestiuneTurism",
                "mihai",
                "mpp"
        );

        AngajatRepository angajatRepository = new AngajatRepository(dbConnection);
        AgentieRepository agentieRepository = new AgentieRepository(dbConnection);
        ExcursieRepository excursieRepository = new ExcursieRepository(dbConnection);
        RezervareRepository rezervareRepository = new RezervareRepository(dbConnection);

//        AgentieTurism agentieTransilvania = new AgentieTurism("Transilvania");
//        AgentieTurism agentieReMax = new AgentieTurism("ReMax");
//        agentieTurism = agentieRepository.save(agentieTransilvania);
//        agentieReMax = agentieRepository.save(agentieReMax);

        Optional<AgentieTurism> agentieTransilvania = agentieRepository.findById(1);
        Optional<AgentieTurism> agentieReMax = agentieRepository.findById(2);

//        Angajat angajatMihai = new Angajat("Mihai", "bugudum", agentieTransilvania.get());
//        Angajat angajatRoli = new Angajat("Roli", "bugudum", agentieReMax.get());
//        angajatMihai = angajatRepository.save(angajatMihai).get();
//        angajatRoli = angajatRepository.save(angajatRoli).get();
        Optional<Angajat> angajatMihai = angajatRepository.findById(3);
        Optional<Angajat> angajatRoli = angajatRepository.findById(4);

//        Excursie excursieGermania = new Excursie("Germania", 50, 1000, "Tabita", LocalDateTime.parse("2025-07-21 10:00:00.000", formatter));
//        Excursie excursieItalia = new Excursie("Italia", 60, 700, "Wizz", LocalDateTime.parse("2025-09-14 21:00:00.000", formatter));
//        excursieGermania = excursieRepository.save(excursieGermania).get();
//        excursieItalia = excursieRepository.save(excursieItalia).get();

        Excursie excursieGermania = excursieRepository.findById(3).get();
        Excursie excursieItalia = excursieRepository.findById(4).get();

//        Rezervare rezervareDorelGermania = new Rezervare("Dorel", 4, "0746123456", excursieGermania, agentieTransilvania.get());
//        Rezervare rezervareGiovanniGermania = new Rezervare("Giovanni", 20, "0746654321", excursieGermania, agentieReMax.get());
//        Rezervare rezervareMarcoItalia = new Rezervare("Marco", 2, "0746321654", excursieItalia, agentieTransilvania.get());
//        Rezervare rezervareClauItalia = new Rezervare("Clau", 5, "0746654123", excursieItalia, agentieReMax.get());
//        rezervareClauItalia = rezervareRepository.save(rezervareClauItalia).get();
//        rezervareMarcoItalia = rezervareRepository.save(rezervareMarcoItalia).get();
//        rezervareDorelGermania = rezervareRepository.save(rezervareDorelGermania).get();
//        rezervareGiovanniGermania = rezervareRepository.save(rezervareGiovanniGermania).get();


        System.out.println("Correct");
    }
}