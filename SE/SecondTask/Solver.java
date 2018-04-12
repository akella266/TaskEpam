package SecondTask;

import java.util.*;

public class Solver {

    private List<Integer> sequence;
    private Stack<Integer> adds;
    private int mainNumber;

    public Solver(int mainNumber, Integer... listAdds) {
        this.mainNumber = mainNumber;
        sequence = Arrays.asList(listAdds);
    }

    private void showSubs(){
        for(int i : adds){
            System.out.print(i + " ");
        }
        System.out.println("");
    }

    public void searchAllAdds(){
        adds = new Stack<>();
        searchAllAdds(mainNumber, 0);
    }

    private void searchAllAdds(int x, int i)
    {
        if ( x < 0 ) return;
        else if ( x == 0 ) showSubs();
        else
            for ( ; i < sequence.size(); i++ )
            {
                adds.push(sequence.get(i));
                searchAllAdds(x - sequence.get(i),i);
                adds.pop();
            }
    }
}
