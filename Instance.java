package competition.cig.yuxiao;

/**
 * Created by xiaoyu on 11/26/15.
 */
public class Instance {
    private String target;
    private String q; // 0
    private String w; // 1
    private String e; // 2
    private String a; // 3
    private String d; // 4
    private String z; // 5
    private String x; // 6
    private String c; // 7

    private String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public void assignFeature(int i,String str){
        switch (i){
            case 0:
                setQ(str);
                break;
            case 1:
                setW(str);
                break;
            case 2:
                setE(str);
                break;
            case 3:
                setA(str);
                break;
            case 4:
                setD(str);
                break;
            case 5:
                setZ(str);
                break;
            case 6:
                setX(str);
                break;
            case 7:
                setC(str);
                break;
            case 8:
                setS(str);
                break;
            default:
                break;
        }
    }
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int[] generateFeature() {
        int[] feature = new int[9];
        for(int i = 0;i < feature.length;i ++){
            switch (i){
                case 0:
                    feature[i] = Integer.valueOf(getQ());
                    break;
                case 1:
                    feature[i] = Integer.valueOf(getW());
                    break;
                case 2:
                    feature[i] = Integer.valueOf(getE());
                    break;
                case 3:
                    feature[i] = Integer.valueOf(getA());
                    break;
                case 4:
                    feature[i] = Integer.valueOf(getD());
                    break;
                case 5:
                    feature[i] = Integer.valueOf(getZ());
                    break;
                case 6:
                    feature[i] = Integer.valueOf(getX());
                    break;
                case 7:
                    feature[i] = Integer.valueOf(getC());
                    break;
                case 8:
                    feature[i] = Integer.valueOf(getS());
                    break;
                default:
                    break;
            }
        }
        return feature;
    }

//    public Instance(){
////        this.feature = new String[8];
//        this.target = "0";
//    }
}
