package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class AgentieTurism extends Entity<Integer>{
    private String numeAgentie;

    public AgentieTurism(String numeAgentie) {
        this.numeAgentie = numeAgentie;
    }

    public AgentieTurism(Integer id, String numeAgentie) {
        this.setId(id);
        this.numeAgentie = numeAgentie;
    }


    public String getNumeAgentie() {
        return numeAgentie;
    }

    public void setNumeAgentie(String numeAgentie) {
        this.numeAgentie = numeAgentie;
    }
}
