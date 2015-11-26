package competition.cig.yuxiao;

/**
 * Created by xiaoyu on 11/26/15.
 */
public class Instance {
    private int[] feature;
    private int target;

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int[] getFeature() {
        return feature;
    }

    public void setFeature(int[] feature) {
        this.feature = feature;
    }

    public Instance(){
        this.feature = new int[9];
        this.target = 0;
    }
}
