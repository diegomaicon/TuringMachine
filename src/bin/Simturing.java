package bin;

import Lib.ArqManipulation;
import Lib.ExecuteMachine;
import Modelo.Entrada;
import Modelo.State;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Diego Maicon Silva - 11913 on 12/05/2017.
 */
public class Simturing {
    public static Entrada entrada = null;
    private static ArrayList<State> machine;
    public static int totalThreads;
    public Simturing() {
    }

    private static void Start(String[] args){
        String haed = "()";
        //Recebe Argumentos impressão.
        if(args[0].equals("-r")){
            ExecuteMachine.FLAGprint = false; //Imprime somete resultado
        } else if(args[0].equals("-v")){
            ExecuteMachine.FLAGprint = true; //Imprime passo a passo
        }
        //Recebe argumantos do cabeçote.

        //bin.Simturing.entrada = Entrada.entrada();
        Simturing.totalThreads = 20;//Simturing.entrada.getLimTreads();

        if (args.length > 3) {
            if (args[1].equals("-head")) {
                haed = args[2];
                try {
                    machine = ArqManipulation.carregaMaquina(args[3]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else{
            try {
                machine = ArqManipulation.carregaMaquina(args[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ExecuteMachine exM = new ExecuteMachine();
        Simturing.entrada = new Entrada(100,20,"cccaccc"); //comentar
        exM.execute(machine,Simturing.entrada ,haed);

    }

    public static void main(String[] args) throws IOException {
        Simturing.Start(args);

    }
}
