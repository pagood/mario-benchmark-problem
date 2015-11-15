package competition.cig.yuxiao.dfs;

import ch.idsia.ai.agents.Agent;
import ch.idsia.mario.environments.Environment;

/**
 * Created by xiaoyu on 11/2/15.
 */
public class DFSAgent implements Agent{
    private String name = "dfsAgent";
    private DFS dfs;
    private boolean[] action;
    private float lastX = 0;
    private float lastY = 0;

    @Override
    public void reset() {

        dfs = new DFS();
        action = new boolean[5];

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
