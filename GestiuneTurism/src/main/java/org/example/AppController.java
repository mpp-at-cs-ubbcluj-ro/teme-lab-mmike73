package org.example;

import org.example.infrastructure.AgentieRepository;
import org.example.infrastructure.AngajatRepository;
import org.example.infrastructure.ExcursieRepository;
import org.example.infrastructure.RezervareRepository;

public class AppController {
    public AgentieRepository agentieRepository;
    public ExcursieRepository excursieRepository;
    public AngajatRepository angajatRepository;
    public RezervareRepository rezervareRepository;

    public AppController(AgentieRepository agentieRepository, AngajatRepository angajatRepository, ExcursieRepository excursieRepository, RezervareRepository rezervareRepository) {
        this.agentieRepository = agentieRepository;
        this.angajatRepository = angajatRepository;
        this.excursieRepository = excursieRepository;
        this.rezervareRepository = rezervareRepository;
    }
}
