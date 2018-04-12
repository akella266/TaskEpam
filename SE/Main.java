import FirstTask.Calculator;
import SecondTask.Solver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        int choice = Integer.parseInt(args[0]);
        switch (choice){
            case 1:{
                if (args[1].length()<3)
                    System.out.println("Invalid argument");
                else{
                    Calculator calc = new Calculator();
                    System.out.println(args[1] + " = " + calc.calculate(args[1]));
                }
            }
            break;
            case 2:{
                try{
                    int mainValue = Integer.parseInt(args[1]);
                    List<Integer> adds = new ArrayList<>();
                    for(int i = 2; i < args.length; i++){
                        Integer add = Integer.parseInt(args[i]);
                        adds.add(add);
                    }
                    Solver solver = new Solver(mainValue, (Integer[]) adds.toArray());
                    solver.searchAllAdds();
                }
                catch(ClassCastException e){
                    System.out.println("Invalid argument");
                }
            }
            break;
            default:{
                System.out.println("Invalid argument");
            }
        }
    }
}
