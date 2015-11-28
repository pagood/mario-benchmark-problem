package competition.cig.yuxiao.dfs;

import ch.idsia.ai.agents.Agent;
import ch.idsia.ai.agents.ai.ScaredAgent;
import ch.idsia.mario.environments.Environment;
import competition.cig.yuxiao.InsTest;
import competition.cig.yuxiao.Instance;
import competition.cig.yuxiao.level.Level;
import competition.cig.yuxiao.level.LevelScene;
import competition.cig.yuxiao.util.JsonHelper;
import org.json.JSONObject;
import sun.jvm.hotspot.debugger.posix.elf.ELFSectionHeader;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import java.util.HashSet;

/**
 * Created by xiaoyu on 11/2/15.
 */
public class DFSAgent implements Agent{
    private String name = "dfsAgent";
    private DFS dfs;
    private boolean[] action;
    private float lastX = 0;
    private float lastY = 0;
    private int counter = 0;
    private DataOutputStream dos;
    private LevelScene world;
    private HashSet<InsTest> set;


    @Override
    public void reset() {
        set = new HashSet<InsTest>();
        world = new LevelScene();
        world.init();
        world.level = new Level(1500,15);


        dfs = new DFS();
        action = new boolean[5];
        try {
            dos = new DataOutputStream(new FileOutputStream("/Users/xiaoyu/Desktop/trainingset2.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean[] getAction(Environment observation) {
        world.mario.setKeys(action);
        world.tick();

        dfs.advance(action);
        if(dfs.getCurrentState().mario.x != observation.getMarioFloatPos()[0] || dfs.getCurrentState().mario.y != observation.getMarioFloatPos()[1]){
            dfs.getCurrentState().mario.x = observation.getMarioFloatPos()[0];
            dfs.getCurrentState().mario.xa = (observation.getMarioFloatPos()[0] - lastX) * 0.89f;
            if(Math.abs(dfs.getCurrentState().mario.y - observation.getMarioFloatPos()[1]) > 0.1f){
                dfs.getCurrentState().mario.ya = (observation.getMarioFloatPos()[1] - lastY) * 0.85f;
            }
            dfs.getCurrentState().mario.y = observation.getMarioFloatPos()[1];
        }
        dfs.updateState(observation.getLevelSceneObservationZ(0),observation.getEnemiesFloatPos());

        world.setLevelScene(observation.getLevelSceneObservationZ(0));
        world.setEnemies(observation.getEnemiesFloatPos());

        lastX = observation.getMarioFloatPos()[0];
        lastY = observation.getMarioFloatPos()[1];
        action = dfs.search();
        //////////////////////////
        //save training set data//
        //////////////////////////
//        HashSet<Instance> set = new HashSet<Instance>();

        byte[][] levelScene = observation.getCompleteObservation(/*1, 0*/);
//        float mariox = observation.getMarioFloatPos()[0];
//        float enemyx = posHelper(observation,mariox,observation.getMarioFloatPos()[1]);
//        System.out.println(levelScene[11][12]);
        int c = 0;
        int assign = 0;
        InsTest instance = new InsTest();
        for(int i = 10;i <= 12;i ++ ){
            for(int j = 10;j <= 12;j ++){
                if(i == 11 && j == 11) continue;
//                instance.getFeature()[c] = levelScene[i][j] == 0 ? "0" : "1";
                if(c == 2 ||c == 4 ||c == 6 ||c == 7) {
                    instance.assignFeature(assign, levelScene[i][j] == 0 ? "0" : "1");
                    assign ++;
                    c++;
                }
                else{
                    c ++;
                }
            }
        }
        instance.assignFeature(assign,world.mario.mayJump() || world.mario.jumpTime > 0 ? "1" : "0");
        assign ++;
        instance.assignFeature(assign,levelScene[11][13] == 0 ? "0" : "1");
        instance.setTarget(action[3] ? "1" : "-1");

        JSONObject jsonObject = JsonHelper.toJSON(instance);
        if(!set.contains(instance)) {
            set.add(instance);
            try {
//            System.out.println(++counter);

//            DataOutputStream dos = new DataOutputStream(new FileOutputStream("/Users/xiaoyu/Desktop/trainingset1.txt"));
//            dos.writeUTF(jsonObject.toString());
                dos.write(jsonObject.toString().getBytes());
                dos.writeUTF("\n");
                dos.flush();
//            dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(world.mario.mayJump() || world.mario.jumpTime > 0);

        return action;
    }

    private float posHelper(Environment observation,float x,float y) {
        float[] enemies = observation.getEnemiesFloatPos();
        float min = Float.MAX_VALUE;
        if(enemies == null){
            return Float.MAX_VALUE;
        }
        else{
            for(int i = 1;i < enemies.length;i = i + 3){
                if(Float.compare(x,enemies[i]) < 0 && Float.compare(y,enemies[i + 1]) == 0){
                    min = Math.min(min,enemies[i] - x);
                }
            }

        }
        return min;

    }

    @Override
    public AGENT_TYPE getType() {
        return AGENT_TYPE.AI;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
