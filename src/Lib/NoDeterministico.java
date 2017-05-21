package Lib;

import Modelo.Entrada;
import Modelo.State;

import java.util.ArrayList;

/**
 * Created by Diego on 21/05/2017.
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
        System.out.println("Thread: " + this.eAtual);
        imprimeFita(f,eAtual,"........");
        boolean achou = false;
        ArrayList<State> lstate = new ArrayList<State>();
        int contT=0;

        for (State e:machine) {

            if (e.isAceita() && eAtual == e.getId()) {
                if (!FLAGprint) {
                    FLAGprint = true;
                    imprimeFita(f, 0, "........");
                }
                if (FLAG) {
                    System.out.println("........ aceita ");
                    return;
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

        if (!achou){
            if (!FLAGprint) {
                FLAGprint = true;
                imprimeFita(f, 0, "........");
            }
            if (FLAG) {
                System.out.println("........ rejeita ");
                return;
            }
        } else  if (contT == 1 && achou) {
            eAtual = lstate.get(0).getFrom();
            if (lstate.get(0).getDirecao() == 'e') {
                f = atualizaFitaEsquerda(f, lstate.get(0).getEscreve(), lstate.get(0).getId());
            } else if (lstate.get(0).getDirecao() == 'd') {
                f = atualizaFitaDireita(f,lstate.get(0).getEscreve(), lstate.get(0).getId());
            } else if (lstate.get(0).getDirecao() == 'i') {
                f = atualizaFitaParada(f, lstate.get(0).getEscreve(), lstate.get(0).getId());
            }
        }
  }

    /**
     *  Anda com cabeçote para Direita
     * @param f
     * @param ch
     * @param id
     * @return
     */
    private Character[] atualizaFitaDireita(Character[] f,char ch,int id){
        char aux;
        aux = ch;
        f[cabecote] = f[cabecote -1];
        f[cabecote -1] = aux;

        aux = f[cabecote +1];
        f[cabecote +1] = f[cabecote +2];
        f[cabecote +2] = aux;

        cabecote++;

        if(FLAG)imprimeFita(f,id,"........");

        return f;
    }

    /**
     *  Anda com cabeçote para esquerda.
     * @param f
     * @param ch
     * @param id
     * @return
     */
    private Character[] atualizaFitaEsquerda(Character[] f,char ch,int id){
        char aux = ch;
        f[cabecote] =   f[cabecote +1];
        f[cabecote +1] = aux;


        aux =  f[cabecote -2];
        f[cabecote -2] =   f[cabecote -1];
        f[cabecote -1] = aux;
        cabecote--;

        if(FLAG) imprimeFita(f,id,"........");
        return f;
    }

    /**
     *  Permacece com cabeçote perado.
     * @param f
     * @param ch
     * @param id
     * @return
     */
    private Character[] atualizaFitaParada(Character[] f,char ch,int id){

        f[cabecote] = ch;

        if(FLAG)imprimeFita(f,id,"........");
        return f;
    }

    /**
     *  Imprime conteudo da fita
     *
     * @param fita
     * @param id
     * @param nDert
     */
    private void imprimeFita(Character[] fita,int id,String nDert) {

        String print = nDert + String.format("%04d", id) +": ";
        for (int i = 0; i < fita.length; i++) {
            if (fita[i] != null)
                print += fita[i];
        }
        if (FLAGprint)
            System.out.println(print);

    }

}
