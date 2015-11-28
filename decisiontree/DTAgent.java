package competition.cig.yuxiao.decisiontree;

import ch.idsia.ai.agents.Agent;
import ch.idsia.mario.environments.Environment;
import competition.cig.yuxiao.level.Level;
import competition.cig.yuxiao.level.LevelScene;

/**
 * Created by xiaoyu on 11/28/15.
 */
public class DTAgent implements Agent{
    private String name = "decisionTreeAgent";
    private DecisionTree decisionTree;
    private boolean[] action;
    private LevelScene world;
    @Override
    public void reset() {


        world = new LevelScene();
        world.init();
        world.level = new Level(1500,15);
        decisionTree = new DecisionTree();
        action = new boolean[5];

    }

    @Override
    public boolean[] getAction(Environment observation) {
        world.mario.setKeys(action);
        world.tick();

        world.setLevelScene(observation.getLevelSceneObservationZ(0));
        world.setEnemies(observation.getEnemiesFloatPos());


        int[] feature = new int[9];
        byte[][] levelScene = observation.getCompleteObservation(/*1, 0*/);
//        System.out.println(levelScene[11][12]);
        int count = 0;
        for(int i = 10;i <= 12;i ++){
            for(int j = 10;j <= 12;j ++){
                if(i == 11 && j == 11) continue;
                feature[count] = levelScene[i][j] == 0 ? 0 : 1;
                System.out.print(feature[count]);
                count ++;
            }
        }
        System.out.println();
        feature[count] = (world.mario.mayJump() || world.mario.jumpTime > 0 )? 1 : 0;
        action = decisionTree.search(feature);
        return action;
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
