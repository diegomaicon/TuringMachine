package Lib;

import Modelo.Entrada;
import Modelo.State;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by Diego on 12/05/2017.
 */
public class ExecuteMachine {
    private Character[] fita;
    private int fEsqueda = 20;
    private int fDireita = 20;
    int Cab = fEsqueda;

    private Character[] iniciaFita(Entrada entrada, String head){


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
        imprimeFita(fita);

        return fita;
    }


    private Character[] atualizaFitaDireita(String head,char ch){
        char aux =  fita[Cab+1];
        fita[Cab+1] = head.charAt(0);
        fita[Cab] = aux;
        fita[Cab+2] = ch;
        fita[Cab+3] = head.charAt(1);
        Cab++;

        imprimeFita(fita);

        return fita;
    }
    private Character[] atualizaFitaEsquerda(String head,char ch){
        char aux =  fita[Cab-1];
        fita[Cab-1] = head.charAt(0);
        fita[Cab] = aux;

        fita[Cab] = ch;

        aux =  fita[Cab+1];
        fita[Cab+1] = head.charAt(1);
        fita[Cab+2] = aux;
        Cab--;

        imprimeFita(fita);

        return fita;
    }
    private void imprimeFita(Character[] fita) {
        for (int i = 0; i < fita.length; i++) {
            if (fita[i] != null) {
                System.out.print(fita[i]);
            }else
                System.out.print("_");
        }
    }

    public void execute(ArrayList<State> Machine, Entrada entrada, String head) {
        iniciaFita(entrada, head);
        System.out.print("\n");
        atualizaFitaDireita(head,'a');
        System.out.print("\n");
        atualizaFitaDireita(head,'x');
        System.out.print("\n");
        atualizaFitaEsquerda(head,'z');
        System.out.print("\n");
        atualizaFitaEsquerda(head,'z');
        System.out.print("\n");
        atualizaFitaDireita(head,'a');
        System.out.print("\n");
        atualizaFitaEsquerda(head,'z');
        System.out.print("\n");
        atualizaFitaEsquerda(head,'z');
        System.out.print("\n");


    }
}
