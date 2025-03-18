package org.example.domain;

public class Angajat extends Entity<Integer> {
    private AgentieTurism agentieTurism;
    public String username;
    private String password;

    public Angajat(String username, String password, AgentieTurism agentieTurism) {
        this.agentieTurism = agentieTurism;
        this.password = password;
        this.username = username;
    }

    public Angajat(Integer id,String username, String password, AgentieTurism agentieTurism) {
        this.setId(id);
        this.agentieTurism = agentieTurism;
        this.password = password;
        this.username = username;
    }

    public AgentieTurism getAgentieTurism() {
        return agentieTurism;
    }

    public void setAgentieTurism(AgentieTurism agentieTurism) {
        this.agentieTurism = agentieTurism;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
