package Lib;

import Modelo.State;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Diego on 12/05/2017.
 */
public class ArqManipulation {

    public static ArrayList<State> carregaMaquina(String caminho)throws IOException {
        ArrayList<State> m1 = new ArrayList<State>();
        State e = null;
        StringTokenizer st;
        String linha = "",aux="";
        try {
            FileReader arquivo = new FileReader(caminho);
            BufferedReader br = new BufferedReader(arquivo);
            StringBuffer line = new StringBuffer();
            linha = br.readLine();
            while (linha != null) {
                st = new StringTokenizer(linha, " ");

                if (!linha.equals(""))
                if (linha.charAt(0) != ';' ){
                   e = new State();
                   e.setId(Integer.parseInt(st.nextToken().toString()));
                   aux = st.nextToken();
                   if(!aux.equals("pare-aceita")){
                        e.setNaFita(aux.charAt(0));
                        st.nextToken();
                        e.setEscreve(st.nextToken().charAt(0));
                        e.setDirecao(st.nextToken().charAt(0));
                        aux = st.nextToken();
                        if(!aux.equals("*"))
                            e.setFrom(Integer.parseInt(aux));
                        else e.setFrom(-(int) aux.charAt(0)); // quando estado for -42, '*' pode ser usado como coringa.
                        if (st.countTokens() != 0){
                            aux = st.nextToken();
                            if(aux.equals("!")) // '!' pode ser usado no final da linha para criar um breakpoint.
                            e.setBreakpoint(true);
                            }
                   } else e.setAceita(true);

                   m1.add(e);
                }


                linha = br.readLine();

            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }

        return m1;

    }
}
