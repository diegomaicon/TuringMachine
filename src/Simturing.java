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

    public static void main(String[] args) throws IOException {

        if(args[0].equals("-r")){
            ExecuteMachine.FLAGprint = false;
        } else if(args[0].equals("-v")){
            ExecuteMachine.FLAGprint = true;
        }

        //entrada = Entrada.entrada();
        machine = ArqManipulation.carregaMaquina("palindromo.mt");
        ExecuteMachine exM= new ExecuteMachine();
        exM.execute(machine, new Entrada(500,50,"ccaccc"),"[]");


        System.out.println("\n Fim");
    }
}
