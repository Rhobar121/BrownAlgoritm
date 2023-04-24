package brown;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Brown {
    private final ArrayList<ArrayList<Integer>> matrix;
    private ArrayList<Integer> sumOfPlayerA;
    private ArrayList<Integer> sumOfPlayerB;
    private final HashMap<Integer,Integer> strategyOfA;
    private final HashMap<Integer,Integer> strategyOfB;
    private int iterationNumber;

    public Brown(String path) throws IOException {
        matrix = new ArrayList<>();
        readFile(path);
        sumOfPlayerB = new ArrayList<>(matrix.size());
        strategyOfA = new HashMap<>();
        strategyOfB = new HashMap<>();
        for (int i=0; i< matrix.size();i++){
            sumOfPlayerB.add(0);
        }
    }

    public void print(){
        for(ArrayList<Integer> i : matrix){
            for(int j : i){
                System.out.print(j+" ");
            }
            System.out.print("\n");
        }
    }

    public HashMap<Integer, Integer> numberOfStrategy(String player){
        if (player.equals("A")){
            return strategyOfA;
        } else {
            return strategyOfB;
        }
    }

    /*
        [lower,upper]
     */
    public float[] gameValue(String player){
        ArrayList<Integer> sumOfPlayer;
        if(player.equals("A")){
            sumOfPlayer = sumOfPlayerA;
        } else {
            sumOfPlayer = sumOfPlayerB;
        }
        float lower = (float) Collections.min(sumOfPlayer)/iterationNumber;
        float upper = (float) Collections.max(sumOfPlayer)/iterationNumber;
        return new float[] {lower,upper};
    }

    public HashMap<Integer, Float> frequencyOfStrategy(String player){
        HashMap<Integer, Float> frequency = new HashMap<>();
        if (player.equals("A")) {
            for (int i : strategyOfA.keySet()) {
                frequency.put(i, (float) strategyOfA.get(i) / iterationNumber);
            }
        } else {
            for (int i : strategyOfB.keySet()) {
                frequency.put(i, (float) strategyOfB.get(i) / iterationNumber);
            }
        }
        return frequency;
    }

    public void start(int iteration, int start){
        iterationNumber = iteration;
        ArrayList<Integer> strategy = matrix.get(start-1);
        sumOfPlayerA = strategy;
        int strategyNumber;
        for (int i=1;i<=iteration;i++){
            // PLAYER B
            strategyNumber = strategy.indexOf(Collections.min(strategy));
            if(!strategyOfB.containsKey(strategyNumber+1))
                strategyOfB.put(strategyNumber+1,0);
            strategyOfB.put(strategyNumber+1, strategyOfB.get(strategyNumber+1)+1);
            strategy = getColumnMatrix(strategyNumber);
            sumOfPlayerB = addStrategies(sumOfPlayerB,strategy);
            // PLAYER A
            strategyNumber = strategy.indexOf(Collections.max(strategy));
            if(!strategyOfA.containsKey(strategyNumber+1))
                strategyOfA.put(strategyNumber+1,0);
            strategyOfA.put(strategyNumber+1, strategyOfA.get(strategyNumber+1)+1);
            strategy = matrix.get(strategyNumber);
            sumOfPlayerA = addStrategies(sumOfPlayerA,strategy);
        }
    }

    private ArrayList<Integer> addStrategies(ArrayList<Integer> allStrategies, ArrayList<Integer> playerStrategy){
        ArrayList<Integer> temp = new ArrayList<>(allStrategies.size());
        for (int i=0;i< allStrategies.size();i++){
            temp.add(allStrategies.get(i)+playerStrategy.get(i));
        }
        return temp;
    }

    private ArrayList<Integer> getColumnMatrix(int index){
        ArrayList<Integer> temp = new ArrayList<>();
        for (ArrayList<Integer> i : matrix){
            temp.add(i.get(index));
        }

        return temp;
    }

    private void readFile(String path) throws IOException {
        ArrayList<Integer> column = new ArrayList<>();
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while((line = bufferedReader.readLine()) != null ){
            String[] variables = line.split(" ");
            for(String i :variables){
                column.add(Integer.parseInt(i));
            }
            matrix.add(column);
            column = new ArrayList<>();
        }
    }
}

