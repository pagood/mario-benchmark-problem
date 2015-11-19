package competition.cig.yuxiao.ES;

import competition.cig.yuxiao.level.Level;
import competition.cig.yuxiao.level.LevelScene;

import java.util.Random;
import java.util.*;

/**
 * Created by xiaoyu on 11/19/15.
 */
public class ES {
    private Queue<Individual> max;
    private Queue<Individual> min;
    private List<Individual> population;
    private LevelScene currentState;
    private final int POPULATION_NUMBER = 10;
    private final int GENERATION = 30;
    public ES(){
        init();

    }
    public void init(){
        currentState = new LevelScene();
        currentState.init();
        currentState.level = new Level(1500,15);
    }
    public void advance(boolean[] action){
        currentState.mario.setKeys(action);
        currentState.tick();


    }
    public void updateState(byte[][] levelPart, float[] enemies){
        currentState.setLevelScene(levelPart);
        currentState.setEnemies(enemies);
    }
    public boolean[] search(){
        boolean[] action = new boolean[4];
        Random random = new Random();
        max = new PriorityQueue<Individual>(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                return -(Float.compare(o1.fitness,o2.fitness));
            }
        });
        min = new PriorityQueue<Individual>(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                return Float.compare(o1.fitness,o2.fitness);
            }
        });
//        population = new ArrayList<Individual>();
        //generate population
        for(int i = 0;i < POPULATION_NUMBER;i ++){
            LevelScene copy = getStateCopy();
            Individual individual = new Individual(copy);
            max.add(individual);// pop best
            min.add(individual);// pop worst
//            population.add(individual);
        }
        //
        for(int i = 0;i < GENERATION;i ++){
            for(int j = 0;j < 5;j ++){
                Individual temp = max.poll();
                //mutate
                int mutation = random.nextInt(30);
                Individual offspring = new Individual(temp,mutation);
                max.add(temp);


                //add offspring to population replace worst one
                Individual badOne = min.poll();
                max.remove(badOne);
//                population.remove(badOne);
                min.add(offspring);
                max.add(offspring);


            }
        }
        action = max.peek().actions[0].getAction();
        return action;
    }
    public LevelScene getStateCopy(){
        LevelScene copy = null;
        try {
            copy = (LevelScene)currentState.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }

}
class Individual{
    LevelScene worldState;
    LevelScene oriState;
    float fitness;
    Action[] actions;
    float start;
    int damge;
    final int STEP_NUMBER = 30;
    public Individual(LevelScene worldState){
        this.worldState = worldState;
        this.oriState = worldState;
        this.start = worldState.mario.x;
        this.damge = worldState.mario.damage;
        actions = new Action[STEP_NUMBER];
        Random random = new Random();
        for(int i = 0;i < STEP_NUMBER;i ++){
            actions[i] = new Action(random.nextInt(4));
        }
        evaluate();
    }
    public Individual(Individual parent,int mutation){
        this.worldState = parent.oriState;
        this.oriState = parent.oriState;
        this.start = oriState.mario.x;
        this.damge = oriState.mario.damage;
        for(int i = 0;i < STEP_NUMBER;i ++){
            actions[i] = new Action(parent.actions[0].getNumber());
        }
        actions[mutation].mutate();
        evaluate();
    }
    public void advance(){
        for(int i = 0;i < STEP_NUMBER;i ++){
            if(worldState.mario.status == worldState.mario.STATUS_DEAD) break;
            worldState.mario.setKeys(actions[i].getAction());
            worldState.tick();
        }
    }
    public void evaluate(){
        advance();
        if(worldState.mario.status == worldState.mario.STATUS_DEAD){
            fitness = -1;
        }
        else{
            fitness = (worldState.mario.x - start) - 10 * (worldState.mario.damage - this.damge);
        }

    }
}
