package Modelo;

/**
 * Created by Diego on 12/05/2017.
 */
public class State {
    private int id;
    private char naFita;
    private char escreve;
    private char direcao;
    private int from;

    public State(int id, char naFita, char escreve, char direcao, int from) {
        this.id = id;
        this.naFita = naFita;
        this.escreve = escreve;
        this.direcao = direcao;
        this.from = from;
    }

    public State() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getNaFita() {
        return naFita;
    }

    public void setNaFita(char naFita) {
        this.naFita = naFita;
    }

    public char getEscreve() {
        return escreve;
    }

    public void setEscreve(char escreve) {
        this.escreve = escreve;
    }

    public char getDirecao() {
        return direcao;
    }

    public void setDirecao(char direcao) {
        this.direcao = direcao;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}