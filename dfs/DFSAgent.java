package competition.cig.yuxiao.dfs;

import ch.idsia.ai.agents.Agent;
import ch.idsia.mario.environments.Environment;
import competition.cig.yuxiao.Instance;
import competition.cig.yuxiao.util.JsonHelper;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    DataOutputStream dos;

    @Override
    public void reset() {

        dfs = new DFS();
        action = new boolean[5];
        try {
            dos = new DataOutputStream(new FileOutputStream("/Users/xiaoyu/Desktop/trainingset.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean[] getAction(Environment observation) {

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

        lastX = observation.getMarioFloatPos()[0];
        lastY = observation.getMarioFloatPos()[1];
        action = dfs.search();
        //////////////////////////
        //save training set data//
        //////////////////////////
        byte[][] levelScene = observation.getCompleteObservation(/*1, 0*/);
        int c = 0;
        Instance instance = new Instance();
        for(int i = 10;i <= 12;i ++ ){
            for(int j = 10;j <= 12;j ++){
                if(i == 11 && j == 11) continue;
//                instance.getFeature()[c] = levelScene[i][j] == 0 ? "0" : "1";
                instance.assignFeature(c,levelScene[i][j] == 0 ? "0" : "1");
                c ++;
            }
        }
        instance.setTarget(action[3] ? "1" : "-1");
        JSONObject jsonObject = JsonHelper.toJSON(instance);
        try {
            System.out.println(++counter);

//            DataOutputStream dos = new DataOutputStream(new FileOutputStream("/Users/xiaoyu/Desktop/trainingset.txt"));
//            dos.writeUTF(jsonObject.toString());
            dos.write(jsonObject.toString().getBytes());
            dos.writeUTF("\n");
            dos.flush();
//            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return action;
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
