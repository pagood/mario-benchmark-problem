package competition.cig.yuxiao.QL;

import ch.idsia.ai.agents.Agent;
import ch.idsia.mario.environments.Environment;
import competition.cig.yuxiao.level.Level;
import competition.cig.yuxiao.level.LevelScene;

/**
 * Created by xiaoyu on 11/29/15.
 */
public class QlearningAgent implements Agent{
    private String name = "qlearningagent";
    private QLearning qLearning;
    private boolean[] action;

    private LevelScene world;
    @Override
    public void reset() {
        qLearning = new QLearning();
        action = new boolean[5];
        world = new LevelScene();
        world.init();
        world.level = new Level(1500,15);
    }

    @Override
    public boolean[] getAction(Environment observation) {
        qLearning.advance(action);
        qLearning.updateState(observation.getLevelSceneObservationZ(0),observation.getEnemiesFloatPos());
        action = qLearning.getActiont();
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
