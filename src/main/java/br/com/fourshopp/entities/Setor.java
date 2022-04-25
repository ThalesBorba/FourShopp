package br.com.fourshopp.entities;

public enum Setor {

    MERCEARIA(1,"MERCEARIA"),
    BAZAR(2,"BAZAR"),
    ;


    private int cd;
    private String setor;

    Setor(int cd, String setor) {
        this.cd = cd;
        this.setor = setor;
    }

    public int getCd() {
        return cd;
    }

    public String getSetor() {
        return setor;
    }
}
