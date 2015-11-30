package competition.cig.yuxiao.QL;

import competition.cig.yuxiao.level.Level;
import competition.cig.yuxiao.level.LevelScene;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by xiaoyu on 11/29/15.
 */
public class QLearning {
    private final int STEP = 511;
    private double[][] Q;
    private int[][] R;
    private final double rate = 0.8;
    private LevelScene currentState;
    public QLearning(){
        this.Q = new double[STEP][STEP];
        this.R = new int[STEP][STEP];
        //each state only have two sub state so dont need to set 0 and -1
        for(int i = 1;i < 3;i ++){
            for(int j = (STEP - 1) / 2;j < (STEP - 3)/2 + 1;j ++){
                R[j][2 * j + i] = 100;
            }
        }
        currentState = new LevelScene();
        currentState.init();
        currentState.level = new Level(1500,15);

    }
    public void train(){
        //to optimize matrix Q and R
        //starting from 0
        //randomly chose one action ,0 or 1 left or right
//        for(int i = 1;i  < 3;i ++){
//            Random random = new Random();
//            LevelScene copy = getStateCopy(currentState);
//            boolean[] action = {false, true, false, false, false};
//            boolean move = true;
//            int state = 0;// starting from state 0e
//            while (move && state != 7 && state != 8 && state != 9 && state != 10 && state != 11 && state != 12 && state != 13 && state != 14) {
//                int a;
//                if(copy.mario.mayJump() || copy.mario.jumpTime > 0){
//                    a = 2 * state + i;
//
//                }
//                else{
//                    if(i == 2){
//                        Q[state][2 * state + 2] = -100;
//                        continue;
//
//                    }
//                    else{
//                        a = 2 * state + i;
//                    }
//                }
//                action[3] = (i == 1) ? false : true;
//                int stateN = a;
//                int damage = copy.mario.damage;
//                LevelScene parent = getStateCopy(copy);
//                copy.mario.setKeys(action);
//                copy.tick();
//                if (copy.mario.damage - damage > 0 || stopMove(copy,parent)) {
//                    R[state][a] = -100;
//                    move = false;
//                }
//                Q[state][a] = (stateN == 7 || stateN == 8 || stateN == 9 || stateN == 10 || stateN == 11 || stateN == 12 || stateN == 13 || stateN == 14) ? (double) R[state][a] + rate * Q[stateN][stateN] : (double) R[state][a] + rate * (Math.max(Q[stateN][2 * stateN + 1], Q[stateN][2 * stateN + 2]));
//                state = stateN;
//            }
//        }
        System.out.println();
        for(int i = 0;i < 200;i ++) {
            Random random = new Random();
            LevelScene copy = getStateCopy(currentState);
            boolean[] action = {false, true, false, false, false};
            boolean move = true;
            int state = 0;// starting from state 0e
            while (move && andHelper(state)) {
                int rand;
                if(copy.mario.mayJump() || copy.mario.jumpTime > 0){
                    rand = random.nextInt(2);
                }
                else{
                    rand = 0;
                    Q[state][2 * state + 2] = -100;

                }
                action[3] = (rand == 1) ? true : false;
                int a = (rand == 1) ? 2 * state + 2 : 2 * state + 1;
                int stateN = a;
                int damage = copy.mario.damage;
                LevelScene parent = getStateCopy(copy);
                copy.mario.setKeys(action);
                copy.tick();
                if (copy.mario.damage - damage > 0 || stopMove(copy,parent)) {
                    Q[state][a] = -100;
                    move = false;
                }
                else {
                    Q[state][a] = (orHeapler(stateN)) ? (double) R[state][a] + rate * Q[stateN][stateN] : (double) R[state][a] + rate * (Math.max(Q[stateN][2 * stateN + 1], Q[stateN][2 * stateN + 2]));
                    state = stateN;
                }
            }
        }
        System.out.println(Q[0][1] + "and " + Q[0][2]);
//        for(int i = 0;i < Q.length;i ++){
//            for(int j = 0; j< Q[0].length;j ++){
//                System.out.print(Q[i][j]);
//            }
//        }


    }
    public boolean andHelper(int state){
        for(int i =(STEP - 1) / 2;i < STEP;i ++){
            if(state == i) return false;
        }
        return true;
    }
    public boolean orHeapler(int state){
        for(int i =(STEP - 1) / 2;i < STEP;i ++){
            if(state == i) return true;
        }
        return false;
    }
    public boolean stopMove(LevelScene copy, LevelScene parent){
        boolean[] t = {false,true,false,false,false};
        boolean[] t2 = {false,true,false,true,false};
        if(Arrays.equals(t, copy.mario.keys)){
            if(copy.mario.xa < 0.7f){
                if(!copy.mario.isOnGround() && copy.mario.jumpTime == 0){
                    if(Arrays.equals(parent.mario.keys,t2)){
                        return true;
                    }
                    else{
                        return false;
                    }

                }
                else{
                    return true;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }

    }
    public boolean[] getActiont(){
        boolean[] action = {false,true,false,false,false};
        train();
        int state = 0;
        action[3] = Double.compare(Q[state][2 * state + 1],Q[state][2 * state + 2]) >= 0 ? false : true;

        return action;

    }
    public void advance(boolean[] action){
        currentState.mario.setKeys(action);
        currentState.tick();


    }
    public void updateState(byte[][] levelPart, float[] enemies){
        currentState.setLevelScene(levelPart);
        currentState.setEnemies(enemies);
    }
    public LevelScene getStateCopy(LevelScene levelScene){
        LevelScene copy = null;
        try {
            copy = (LevelScene)levelScene.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }
}
