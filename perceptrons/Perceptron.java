package competition.cig.yuxiao.perceptrons;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import competition.cig.yuxiao.InsTest;
import competition.cig.yuxiao.Instance;
import competition.cig.yuxiao.util.JsonHelper;
import org.json.JSONException;

/**
 * Created by xiaoyu on 11/26/15.
 */
public class Perceptron {
    private double[] weight;
    private double bias;
    private double alpha;
    private double theta;

    public Perceptron(){
        weight = new double[6];
        bias = 0;
        alpha = 1;
        theta = 0.2;
        List<InsTest> trainingSet = new ArrayList<InsTest>();
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("/Users/xiaoyu/Desktop/trainingset2 2.txt"));
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while((ch = dis.read()) != -1){
                if(ch == '\n'){
                    String jsonString = sb.toString();
                    sb.delete(0,sb.length());
                    InsTest temp = new InsTest();
                    JsonHelper.toJavaBean(temp, jsonString);
                    trainingSet.add(temp);
                }
                else{
                    sb.append((char)ch);
                }
            }
            dis.close();
//            JsonHelper.toJavaBean(sketch, jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(trainingSet.size());
        train(trainingSet);


//        bias = -1;
//        weight = new double[]{0, 0, 0, 0, 2, 0, 0, 0, 0};

        System.out.println(bias);
        for(int i = 0;i < weight.length;i ++){
            System.out.println(weight[i]);
        }
    }

    public void train(List<InsTest> trainingSet){
        int count = 0;
        int j = 0;
        int test = 0;
        while(count != trainingSet.size()){
            j = j % trainingSet.size();
            InsTest temp = trainingSet.get(j);
            int[] feature = temp.generateFeature();
            int yi = 0;
            for(int i = 0;i < weight.length;i ++){
                yi += feature[i] * weight[i];
            }
            yi += bias;
            int yo = 0;
            if(yi <= theta && -theta <= yi){
                yo = 0;
            }
            else if(yi > theta){
                yo = 1;
            }
            else{
                yo = -1;
            }
            if(yo != Integer.valueOf(temp.getTarget())){
                count = 0;
                update(temp);
            }
            else{
                count ++;
            }
            j ++;
            test ++;
            System.out.println(test);


        }
        System.out.println(test);
    }
    public void update(InsTest temp){
        int[] feature = temp.generateFeature();
        for(int i = 0;i < weight.length;i ++){
            weight[i] = weight[i] + alpha * Integer.valueOf(temp.getTarget()) * feature[i];
        }
        bias += alpha * Integer.valueOf(temp.getTarget());
    }
    public boolean[] getActions(int[] feature){
        int yi = 0;
        for(int i = 0;i < weight.length;i ++){
            yi += feature[i] * weight[i];
        }
        yi += bias;
        int yo = 0;
        if(yi <= theta && -theta <= yi){
            yo = 0;
        }
        else if(yi > theta){
            yo = 1;
        }
        else{
            yo = -1;
        }

        boolean[] action = {false,true,false,false,false};
        action[3] = yo == 1 ? true : false;

        return action;
    }
    public  String[] helper(int[] nums){
        String[] str = new String[nums.length];
        for(int i = 0;i < nums.length;i ++){
            str[i] = String.valueOf(nums[i]);
        }
        return str;
    }
}