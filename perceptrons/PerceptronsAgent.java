package competition.cig.yuxiao.perceptrons;

import ch.idsia.ai.agents.Agent;
import ch.idsia.mario.environments.Environment;

/**
 * Created by xiaoyu on 11/26/15.
 */
public class PerceptronsAgent implements Agent{
    private String name = "perceptronsAgent";
    private Perceptron perceptron;
    private boolean[] action;
    @Override
    public void reset() {
        perceptron = new Perceptron();
        action = new boolean[5];

    }

    @Override
    public boolean[] getAction(Environment observation) {
        int[] feature = new int[9];
        byte[][] levelScene = observation.getCompleteObservation(/*1, 0*/);
        int count = 0;
        for(int i = 10;i <= 12;i ++){
            for(int j = 10;j <= 12;j ++){
                if(i == 11 && j == 11) continue;
                feature[count] = levelScene[i][j] == 0 ? 0 : 1;
                count ++;
            }
        }
        feature[count] = 1;
        return perceptron.getActions(feature);
    }

    @Override
    public AGENT_TYPE getType() {
        return AGENT_TYPE.AI;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
