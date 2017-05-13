package Lib;

import Modelo.Entrada;
import Modelo.State;

import java.util.ArrayList;

/**
 * Created by Diego on 12/05/2017.
 */
public class ExecuteMachine {
    private Character[] fita;
    private int fEsqueda = 20;
    private int fDireita = 20;
    private int cabecote;
    private boolean FLAG = true;
    private String printFim ="";

    public static boolean FLAGprint;

    private Character[] iniciaFita(Entrada entrada, String head,int id){

        cabecote = fEsqueda;
        fita = new Character[fEsqueda+entrada.getWord().length()+fDireita];


        fita[fEsqueda] = head.charAt(0);
        fita[fEsqueda+1] = entrada.getWord().charAt(0);
        fita[fEsqueda+2] = head.charAt(1);


        for (int i = 1; i < entrada.getWord().length(); i++) {
            fita[fEsqueda+2+i] = entrada.getWord().charAt(i);
        }

        for (int i = 0; i < fita.length; i++) {
            if(fita[i] == null ){
                fita[i] = '_';
            }
        }
        cabecote++;
        if(FLAG)imprimeFita(fita,id,"........");

        return fita;
    }


    private Character[] atualizaFitaDireita(char ch,int id){
        char aux;

        aux = ch;
        fita[cabecote] = fita[cabecote -1];
        fita[cabecote -1] = aux;

        aux = fita[cabecote +1];
        fita[cabecote +1] = fita[cabecote +2];
        fita[cabecote +2] = aux;

        cabecote++;

        if(FLAG)imprimeFita(fita,id,"........");

        return fita;
    }
    private Character[] atualizaFitaEsquerda(char ch,int id){
        char aux = ch;
        fita[cabecote] =   fita[cabecote +1];
        fita[cabecote +1] = aux;


        aux =  fita[cabecote -2];
        fita[cabecote -2] =   fita[cabecote -1];
        fita[cabecote -1] = aux;
        cabecote--;

        if(FLAG) imprimeFita(fita,id,"........");
        return fita;
    }
    private Character[] atualizaFitaParada(char ch,int id){

        fita[cabecote] = ch;

        if(FLAG)imprimeFita(fita,id,"........");
        return fita;
    }
    private void imprimeFita(Character[] fita,int id,String nDert) {

        String print = nDert + String.format("%04d", id) +": ";
        for (int i = 0; i < fita.length; i++) {
            if (fita[i] != null)
                print += fita[i];
            }
            if (FLAGprint)
                System.out.println(print);

    }

    public void execute(ArrayList<State> machine, Entrada entrada, String head) {
        int eAtual = 1;
        iniciaFita(entrada, head,eAtual);
        boolean achou = false;

        for (int i = 0; i < entrada.getLimConfig(); i++) {
            for (State e: machine) {

                if(e.isAceita() && eAtual == e.getId()){
                    if(!FLAGprint){
                        FLAGprint = true;
                        imprimeFita(fita,0,"........");
                    }
                    if(FLAG) System.out.println("........ aceita ");
                    i = entrada.getLimConfig();
                }

                if (eAtual == e.getId()){
                    achou = true;
                    if(e.getNaFita() == fita[cabecote]){
                        eAtual = e.getFrom();
                        if (e.getDirecao() == 'e'){
                            atualizaFitaEsquerda(e.getEscreve(),e.getId());
                            break;
                        }else if (e.getDirecao() == 'd') {
                            atualizaFitaDireita(e.getEscreve(),e.getId());
                            break;
                        }else if (e.getDirecao() == 'i') {
                            atualizaFitaParada(e.getEscreve(),e.getId());
                            break;
                        }

                    }

                }else achou = false;
            }

            if (!achou){
                if(!FLAGprint){
                    FLAGprint = true;
                    imprimeFita(fita,0,"........");
                }
                if(FLAG) System.out.println("........ rejeita ");
                    i = entrada.getLimConfig();
                break;
            }
        }

    }
}
