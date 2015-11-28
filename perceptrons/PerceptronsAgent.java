package competition.cig.yuxiao.perceptrons;

import ch.idsia.ai.agents.Agent;
import ch.idsia.mario.environments.Environment;
import competition.cig.yuxiao.level.Level;
import competition.cig.yuxiao.level.LevelScene;

/**
 * Created by xiaoyu on 11/26/15.
 */
public class PerceptronsAgent implements Agent{
    private String name = "perceptronsAgent";
    private Perceptron perceptron;
    private boolean[] action;

    private LevelScene world;
    @Override
    public void reset() {


        world = new LevelScene();
        world.init();
        world.level = new Level(1500,15);
        perceptron = new Perceptron();
        action = new boolean[5];

    }

    @Override
    public boolean[] getAction(Environment observation) {
        world.mario.setKeys(action);
        world.tick();

        world.setLevelScene(observation.getLevelSceneObservationZ(0));
        world.setEnemies(observation.getEnemiesFloatPos());

        int[] feature = new int[6];
        byte[][] levelScene = observation.getCompleteObservation(/*1, 0*/);
//        System.out.println(levelScene[11][12]);
        int count = 0;
        int assign = 0;
        for(int i = 10;i <= 12;i ++){
            for(int j = 10;j <= 12;j ++){
                if(i == 11 && j == 11) continue;
                if(count == 2 || count == 4 || count == 6){
                    feature[assign] = levelScene[i][j] == 0 ? 0 : 1;
//                    System.out.print(feature[count]);
                    count ++;
                    assign ++;
                }
                else if(count == 7){
                    feature[assign] = DangerOfGap(levelScene) ? 0 : 1;
                    assign ++;
                }
                else{
                    count ++;
                }

            }
        }

        feature[assign] = (world.mario.mayJump() || world.mario.jumpTime > 0 )? 1 : 0;
        assign ++;
        feature[assign] = (levelScene[11][13] == -10 || levelScene[11][13] == -11 ||levelScene[11][13] == 20||levelScene[11][13] == 16||levelScene[11][13] == 21 || levelScene[11][13] == 0 )? 0 : 1;
        action = perceptron.getActions(feature);
        System.out.println(DangerOfGap(levelScene));

//        System.out.println(world.mario.isOnGround());
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
    private boolean DangerOfGap(byte[][] levelScene)
    {
//        for (int x = 9; x < 13; ++x)
//        {
//            boolean f = true;
//            for(int y = 12; y < 22; ++y)
//            {
//                if  (levelScene[y][x] != 0)
//                    f = false;
//            }
//            if (f && levelScene[12][11] != 0)
//                return true;
//        }
//
        for(int i = 12;i < 22;i ++){
            if(levelScene[i][12] == -10 || levelScene[i][12] == -11 ||levelScene[i][12] == 20||levelScene[i][12] == 16||levelScene[i][12] == 21) return false;
            else if(levelScene[i][12] == 9 || levelScene[i][12] == 10){
                return true;
            }
        }
//        for(int i = 12;i < 22;i ++){
//            if(levelScene[i][13] == -10 || levelScene[i][13] == -11 ||levelScene[i][13] == 20||levelScene[i][13] == 16||levelScene[i][13] == 21) return false;
//            else if(levelScene[i][12] == 9 || levelScene[i][12] == 10){
//                return true;
//            }
//        }
        return true;
    }
}
