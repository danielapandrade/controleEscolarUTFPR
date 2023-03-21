package br.edu.utfpr;

public class Evento {

    private String evento;
    private String escola;
    private String tipoEvento;
    private String data;
    private boolean comida;
    private boolean bebida;

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isComida() {
        return comida;
    }

    public void setComida(boolean comida) {
        this.comida = comida;
    }

    public boolean isBebida() {
        return bebida;
    }

    public void setBebida(boolean bebida) {
        this.bebida = bebida;
    }

    @Override
    public String toString() {

        String evento = "";

        String comida = "";

        String bebida = "";

        if(isComida()){
             comida = "Levar comida";
        }
        if(isBebida()){
             bebida = "Levar bebida";
        }

        return "Evento: " + getEvento() + "\n"
                + "Escola: " + getEscola() + "\n" +
                "Tipo do evento Evento: " + getTipoEvento() + "\n" +
                "Data: " + getData() + "\n"  + comida +  "\n"  + bebida + "\n";


    }
}
