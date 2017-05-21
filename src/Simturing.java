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
    private static Entrada entrada;
    private static ArrayList<State> machine;

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Simturing() {
    }

    public static void main(String[] args) throws IOException {
        String haed = "()";
        //Recebe Argumentos impressão.
        if(args[0].equals("-r")){
            ExecuteMachine.FLAGprint = false;
        } else if(args[0].equals("-v")){
            ExecuteMachine.FLAGprint = true;
        }
        //Recebe argumantos do cabeçote.

        //entrada = Entrada.entrada();
        if (args.length > 3) {
            if (args[1].equals("-head")) {
                haed = args[2];
                machine = ArqManipulation.carregaMaquina(args[3]);
            }
        } else{
            machine = ArqManipulation.carregaMaquina(args[1]);
        }


        ExecuteMachine exM = new ExecuteMachine();
        exM.execute(machine, new Entrada(500,50,"cccaccc"),haed);


        System.out.println("\n Fim");
    }
}
