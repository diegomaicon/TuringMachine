package Lib;

import Modelo.Entrada;
import Modelo.State;
import bin.Simturing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Diego Maicon Silva - 11913 on 21/05/2017.
 */
public class ExecuteMachine {

    private Character[] fita;           //Fita da máquina de Turing
    private int fEsqueda = 20;
    private int fDireita = 20;
    private int cabecote;               //indica posição cabeçote
    private boolean FLAG = true;

    public static boolean FLAGprint;   // dependendo do parametro, imprime passo a passo, ou não.

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

    /**
     *  Executa máquina, achar estado de aceitação, ou terminar a computação ou limite de threads
     *
     * @param machine
     * @param entrada
     * @param head
     */
    public void execute(ArrayList<State> machine, Entrada entrada, String head) {
        int eAtual = 1;
        ArrayList<State> lstate = new ArrayList<State>();
        fita = iniciaFita(fita, entrada, head, eAtual);
        boolean achou;
        int contT;


        do {
            achou = false;
            contT = 0;
            //Perforre lista de estados, a procura da direção ir
            for (State e : machine) {
                if (e.isAceita() && eAtual == e.getId()) {
                    if (!FLAGprint) {
                        FLAGprint = true;
                        imprimeFita(fita, 0, "........");
                    }
                    if (FLAG) {
                        System.out.println("........ aceita ");
                        System.exit(0);
                    }
                }

                if (eAtual == e.getId()) {
                    achou = true;
                    if (e.getNaFita() == fita[cabecote]) {
                        contT++;
                        lstate.add(e);
                    }
                }
            }
            if (lstate.size() == 1) {
                /* ’*’ pode ser usado como coringa em <novo estado> e <novo símbolo> para significar ausência de mudança.*/
                if (lstate.get(0).getFrom() != -42)
                 eAtual = lstate.get(0).getFrom();
                else eAtual = lstate.get(0).getId();
            }

            if (!achou) {
                if (!FLAGprint) {
                    FLAGprint = true;
                    imprimeFita(fita, 0, "........");
                }
                if (FLAG) {
                    System.out.println("........ rejeita ");
                    return;
                }
                // if contT ==1, não tem no-Determinismo
            } else if (contT == 1 && achou) {
                /* ’*’ pode ser usado como coringa em <novo estado> e <novo símbolo> para significar ausência de mudança.*/
                if (lstate.get(0).getFrom() != -42)
                    eAtual = lstate.get(0).getFrom();
                else eAtual = lstate.get(0).getId();
                /* Verifica fireção onde cabeçote vai */
                if (lstate.get(0).getDirecao() == 'e') {
                    fita = atualizaFitaEsquerda(fita, lstate.get(0).getEscreve(), lstate.get(0).getId());
                } else if (lstate.get(0).getDirecao() == 'd') {
                    fita = atualizaFitaDireita(fita,lstate.get(0).getEscreve(), lstate.get(0).getId());
                } else if (lstate.get(0).getDirecao() == 'i') {
                    fita = atualizaFitaParada(fita, lstate.get(0).getEscreve(), lstate.get(0).getId());
                }
                //Se a lista de é maior que 2, ouve não determinismo e tem que disparar as Thread's
            } else if (lstate.size() >= 2){
                if(FLAG) imprimeFita(fita,lstate.get(0).getId(),"...N....");
                Character[] f;
                int cab = cabecote;
                //Percorre lista de estados não deterministico
                for (State e:lstate) {
                    FLAG = false;
                    f = fita.clone();
                     /* Verifica fireção onde cabeçote vai */
                    if (e.getDirecao() == 'e') {
                        f = atualizaFitaEsquerda(f,e.getEscreve(), e.getId());
                    } else if (e.getDirecao() == 'd') {
                        f = atualizaFitaDireita(f,e.getEscreve(), e.getId());
                    } else if (e.getDirecao() == 'i') {
                        f = atualizaFitaParada(f, e.getEscreve(), e.getId());
                    }
                    //Cria novas Thread's
                    Runnable noD = new NoDeterministico(f,e.getFrom(),cabecote,fEsqueda,fDireita,true,machine);
                    Thread t  = new Thread(noD,e.getId()+"");
                    t.start();
                    cabecote = cab;
                    //Diminui limite de Thread's
                    Simturing.entrada.setLimTreads(Simturing.entrada.getLimTreads()-1);
                    try {
                        //Aguarda fim da Thread
                        t.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                return;
            } else {
                if (!FLAGprint) {
                    FLAGprint = true;
                    imprimeFita(fita, 0, "........");
                }
                if (FLAG) {
                    System.out.println("........ rejeita ");
                    return;
                }
            }

            //limpa lista de estados
            lstate.clear();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Simturing.entrada.setLimConfig(Simturing.entrada.getLimConfig()-1);
            //controla parada
        } while (Simturing.entrada.getLimConfig() > 0  || Simturing.entrada.getLimConfig() > 0);

        System.out.println("........Computação ou Limite de Threads esgotado :( ");
        System.exit(0);
    }
}
