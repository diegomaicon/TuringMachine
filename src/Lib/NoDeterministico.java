package Lib;

import Modelo.Entrada;
import Modelo.State;
import bin.Simturing;

import java.util.ArrayList;

/**
 * Created by Diego Maicon Silva - 11913 on 21/05/2017.
 */
public class NoDeterministico extends ExecuteMachine implements Runnable {
    private Character[] f;
    private int eAtual;
    private int cabecote;
    private int fEsqueda = 20;
    private int fDireita = 20;
    private boolean FLAG = true;
    private ArrayList<State> machine;
    private boolean FLAGprintND = FLAGprint;

    /**
     * Metodo construtor, recebe parametros para continuar a Thread a partir de onde a ultima parou
     * @param f
     * @param eAtual
     * @param cabecote
     * @param fEsqueda
     * @param fDireita
     * @param FLAG
     * @param machine
     */
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

    /**
     * Roda a cada Thread a procura de estado de aceitação, consegue gerar novas Thread's
     * @autor Diego
     */
    @Override
    public void run() {
        if (FLAGprintND) System.out.println("Thread: " + (Simturing.totalThreads - Simturing.entrada.getLimTreads()));
        if (FLAGprintND) imprimeFita(f, eAtual, "........");

        do {
            boolean achou = false;
            ArrayList<State> lstate = new ArrayList<State>();
            int contT = 0;
            //Perforre lista de estados, a procura da direção ir
            for (State e : machine) {
                if (e.isAceita() && eAtual == e.getId()) {
                    if (!FLAGprintND) {
                        FLAGprintND = true;
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
                 /* ’*’ pode ser usado como coringa em <novo estado> e <novo símbolo> para significar ausência de mudança.*/
                if (lstate.get(0).getFrom() != -42)
                    eAtual = lstate.get(0).getFrom();
                else eAtual = lstate.get(0).getId();
            }

            if (!achou) {
                if (!FLAGprintND) {
                    FLAGprintND = true;
                    imprimeFita(f, 0, "........");
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
                    f = atualizaFitaEsquerda(f, lstate.get(0).getEscreve(), lstate.get(0).getId());
                } else if (lstate.get(0).getDirecao() == 'd') {
                    f = atualizaFitaDireita(f, lstate.get(0).getEscreve(), lstate.get(0).getId());
                } else if (lstate.get(0).getDirecao() == 'i') {
                    f = atualizaFitaParada(f, lstate.get(0).getEscreve(), lstate.get(0).getId());
                }

                //Se a lista de é maior que 2, ouve não determinismo e tem que disparar as Thread's
            } else if (lstate.size() >= 2) {
                if (FLAG) imprimeFita(f, lstate.get(0).getId(), "...N....");
                Character[] auxf;
                int cab = cabecote;
                for (State e : lstate) {
                    FLAG = false;
                    auxf = f.clone();
                     /* Verifica fireção onde cabeçote vai */
                    if (e.getDirecao() == 'e') {
                        auxf = atualizaFitaEsquerda(auxf, e.getEscreve(), e.getId());
                    } else if (e.getDirecao() == 'd') {
                        auxf = atualizaFitaDireita(auxf, e.getEscreve(), e.getId());
                    } else if (e.getDirecao() == 'i') {
                        auxf = atualizaFitaParada(auxf, e.getEscreve(), e.getId());
                    }
                    //Cria novas Thread's
                    Runnable noD = new NoDeterministico(auxf, e.getFrom(), cabecote, fEsqueda, fDireita, true, machine);
                    Thread t = new Thread(noD, e.getId() + "");
                    t.start();
                    cabecote = cab;
                    Simturing.entrada.setLimTreads(Simturing.entrada.getLimTreads() - 1); //Diminui limite de Thread's
                    try {
                        //Aguarda fim da Thread
                        t.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }
                return;
            } else {
                if (!FLAGprintND) {
                    FLAGprintND = true;
                    imprimeFita(f, 0, "........");
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

            Simturing.entrada.setLimConfig(Simturing.entrada.getLimConfig() - 1);
            if (Simturing.entrada.getLimTreads() == 0) Simturing.entrada.setLimConfig(0);
        } while (Simturing.entrada.getLimConfig() > 0);

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
        if (FLAGprintND)
            System.out.println(print);

    }

}
