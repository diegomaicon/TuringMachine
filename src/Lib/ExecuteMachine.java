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

    /**
     * Inicia Fita com palavra informada na entrada
     *
     * @param f
     * @param entrada
     * @param head
     * @param id
     * @return
     */
    private Character[] iniciaFita(Character[] f,Entrada entrada, String head,int id){

        cabecote = fEsqueda;
        //Seta tamanho da fita 20+palavra+20 espaços em branco.
        f = new Character[fEsqueda+entrada.getWord().length()+fDireita];


        f[fEsqueda] = head.charAt(0);
        f[fEsqueda+1] = entrada.getWord().charAt(0);
        f[fEsqueda+2] = head.charAt(1);


        for (int i = 1; i < entrada.getWord().length(); i++) {
            f[fEsqueda+2+i] = entrada.getWord().charAt(i);
        }
        //Onde não tem caracter é preenchido com '_' ou branco
        for (int i = 0; i < f.length; i++) {
            if(f[i] == null ){
                f[i] = '_';
            }
        }
        cabecote++;
        if(FLAG)imprimeFita(f,id,"........");
        return f;
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

    public void execute(ArrayList<State> machine, Entrada entrada, String head) {
        int eAtual = 1;
        fita = iniciaFita(fita,entrada, head,eAtual);
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
                            fita = atualizaFitaEsquerda(fita,e.getEscreve(),e.getId());
                            break;
                        }else if (e.getDirecao() == 'd') {
                            fita = atualizaFitaDireita(fita,e.getEscreve(),e.getId());
                            break;
                        }else if (e.getDirecao() == 'i') {
                            fita = atualizaFitaParada(fita,e.getEscreve(),e.getId());
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

    private void noDEterministico(){


    }

}
