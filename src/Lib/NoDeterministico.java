package Lib;

import Modelo.Entrada;
import Modelo.State;
import bin.Simturing;

import java.util.ArrayList;

/**
 * Created by Diego Maicon Silva 11913 on 21/05/2017.
 */
public class NoDeterministico extends ExecuteMachine implements Runnable {
    private Character[] f;
    private int eAtual;
    private int cabecote;
    private int fEsqueda = 20;
    private int fDireita = 20;
    private boolean FLAG = true;
    private ArrayList<State> machine;


    public NoDeterministico(Character[] f, int eAtual, int cabecote, int fEsqueda, int fDireita, boolean FLAG, ArrayList<State> machine) {
        this.f = f;
        this.eAtual = eAtual;
        this.cabecote = cabecote;
        this.fEsqueda = fEsqueda;
        this.fDireita = fDireita;
        this.FLAG = FLAG;
        this.machine = machine;
    }

    public int geteAtual() {
        return eAtual;
    }

    public void seteAtual(int eAtual) {
        this.eAtual = eAtual;
    }

    public Character[] getF() {
        return new Character[0];
    }

    public void setF(Character[] f) {
        this.f = f;
    }

    @Override
    public void run() {
       if(FLAGprint) System.out.println("Thread: " + this.eAtual);
       if(FLAGprint) imprimeFita(f, eAtual, "........");

        do {
            boolean achou = false;
            ArrayList<State> lstate = new ArrayList<State>();
            int contT = 0;

            for (State e : machine) {

                 if (e.isAceita() && eAtual == e.getId()) {
                    if (!FLAGprint) {
                        FLAGprint = true;
                        imprimeFita(f, 0, "........");
                    }
                    if (FLAG) {
                        System.out.println("........ aceita ");
                        System.exit(0);
                    }
                }

                if (eAtual == e.getId()) {
                    achou = true;
                    if (e.getNaFita() == f[cabecote]) {
                        contT++;
                        lstate.add(e);
                    }
                }
            }
            if (lstate.size() == 1) {
                eAtual = lstate.get(0).getFrom();
            }

            if (!achou) {
                if (!FLAGprint) {
                    FLAGprint = true;
                    imprimeFita(f, 0, "........");
                }
                if (FLAG) {
                    System.out.println("........ rejeita ");
                    return;
                }

            } else if (contT == 1 && achou) {
                eAtual = lstate.get(0).getFrom();
                if (lstate.get(0).getDirecao() == 'e') {
                    f = atualizaFitaEsquerda(f, lstate.get(0).getEscreve(), lstate.get(0).getId());
                } else if (lstate.get(0).getDirecao() == 'd') {
                    f = atualizaFitaDireita(f, lstate.get(0).getEscreve(), lstate.get(0).getId());
                } else if (lstate.get(0).getDirecao() == 'i') {
                    f = atualizaFitaParada(f, lstate.get(0).getEscreve(), lstate.get(0).getId());
                }
            } else if (lstate.size() >= 2) {
                if (FLAG) imprimeFita(f, lstate.get(0).getId(), "...N....");
                Character[] auxf;
                int cab = cabecote;
                for (State e : lstate) {
                    FLAG = false;
                    auxf = f.clone();
                    if (e.getDirecao() == 'e') {
                        auxf = atualizaFitaEsquerda(auxf, e.getEscreve(), e.getId());
                    } else if (e.getDirecao() == 'd') {
                        auxf = atualizaFitaDireita(auxf, e.getEscreve(), e.getId());
                    } else if (e.getDirecao() == 'i') {
                        auxf = atualizaFitaParada(auxf, e.getEscreve(), e.getId());
                    }

                    Runnable noD = new NoDeterministico(auxf, e.getFrom(), cabecote , fEsqueda, fDireita, true, machine);
                    Thread t = new Thread(noD, e.getId() + "");
                    t.start(); cabecote = cab;

                    Simturing.entrada.setLimTreads(Simturing.entrada.getLimTreads() - 1);
                    try {
                        t.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }

            } else {
                if (FLAGprint) {
                    FLAGprint = true;
                    imprimeFita(f, 0, "........");
                }
                if (FLAG) {
                    if (FLAGprint) System.out.println("........ rejeita ");
                    return;
                }
            }

            lstate.clear();

            Simturing.entrada.setLimConfig(Simturing.entrada.getLimConfig() - 1);
           if(Simturing.entrada.getLimTreads() == 0) Simturing.entrada.setLimConfig(0);
        }while(Simturing.entrada.getLimConfig() > 0 );

        System.out.println("........Computação ou Limite de Threads esgotado :( ");
        System.exit(0);
    }


    /**
     * Anda com cabeçote para Direita
     *
     * @param f
     * @param ch
     * @param id
     * @return
     */
    private Character[] atualizaFitaDireita(Character[] f, char ch, int id) {
        char aux;
        aux = ch;
        f[cabecote] = f[cabecote - 1];
        f[cabecote - 1] = aux;

        aux = f[cabecote + 1];
        f[cabecote + 1] = f[cabecote + 2];
        f[cabecote + 2] = aux;

        cabecote++;

        if (FLAG) imprimeFita(f, id, "........");

        return f;
    }

    /**
     * Anda com cabeçote para esquerda.
     *
     * @param f
     * @param ch
     * @param id
     * @return
     */
    private Character[] atualizaFitaEsquerda(Character[] f, char ch, int id) {
        char aux = ch;
        f[cabecote] = f[cabecote + 1];
        f[cabecote + 1] = aux;


        aux = f[cabecote - 2];
        f[cabecote - 2] = f[cabecote - 1];
        f[cabecote - 1] = aux;
        cabecote--;

        if (FLAG) imprimeFita(f, id, "........");
        return f;
    }

    /**
     * Permacece com cabeçote perado.
     *
     * @param f
     * @param ch
     * @param id
     * @return
     */
    private Character[] atualizaFitaParada(Character[] f, char ch, int id) {

        f[cabecote] = ch;

        if (FLAG) imprimeFita(f, id, "........");
        return f;
    }

    /**
     * Imprime conteudo da fita
     *
     * @param fita
     * @param id
     * @param nDert
     */
    private void imprimeFita(Character[] fita, int id, String nDert) {

        String print = nDert + String.format("%04d", id) + ": ";
        for (int i = 0; i < fita.length; i++) {
            if (fita[i] != null)
                print += fita[i];
        }
        if (FLAGprint)
            System.out.println(print);

    }

}
