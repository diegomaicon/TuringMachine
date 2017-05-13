package Modelo;

import java.util.Scanner;

/**
 * Created by Diego on 12/05/2017.
 */
public class Entrada {
    private int limConfig;
    private int limTreads;
    private String word;

    public Entrada(int limConfig, int limTreads, String word) {
        this.limConfig = limConfig;
        this.limTreads = limTreads;
        this.word = word;
    }

    public Entrada() {
    }

    public int getLimConfig() {
        return limConfig;
    }

    public void setLimConfig(int limConfig) {
        this.limConfig = limConfig;
    }

    public int getLimTreads() {
        return limTreads;
    }

    public void setLimTreads(int limTreads) {
        this.limTreads = limTreads;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "limConfig=" + limConfig +
                ", limTreads=" + limTreads +
                ", word='" + word + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entrada)) return false;

        Entrada entrada = (Entrada) o;

        if (limConfig != entrada.limConfig) return false;
        if (limTreads != entrada.limTreads) return false;
        return word != null ? word.equals(entrada.word) : entrada.word == null;
    }

    @Override
    public int hashCode() {
        int result = limConfig;
        result = 31 * result + limTreads;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }


    public static Entrada entrada (){
        Scanner ler = new Scanner(System.in);
        Entrada entrada = new Entrada();
        System.out.println("\nSimulador de Máquina de Turing com Oráculo ver 1.0\n"+
                "Desenvolvido como trabalho prático para adisciplina de Teoria da Computação\n"+
                "Diego Maicon Silva, IFMG, 2017. \n");

        System.out.println("Forneça o limite de computações.\n"+
                "Valores grandes podem demandar muito tempo\n"+
                "(max: 1000) −−> ");
        entrada.setLimConfig(ler.nextInt());
        System.out.println("Forneça o limite de threads simultâneas.\n"+
                "Valores grandes podem demandar muito tempo.\n"+
                "(max: 100) −−> ");
        entrada.setLimTreads(ler.nextInt());
        ler = new Scanner(System.in);
        System.out.println("Forneça a palavra inicial:" );
        entrada.setWord(ler.nextLine());

        return entrada;
    }
}
