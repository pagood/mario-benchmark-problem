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


    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    private String dis;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instance)) return false;

        Instance instance = (Instance) o;

        if (a != null ? !a.equals(instance.a) : instance.a != null) return false;
        if (c != null ? !c.equals(instance.c) : instance.c != null) return false;
        if (d != null ? !d.equals(instance.d) : instance.d != null) return false;
        if (e != null ? !e.equals(instance.e) : instance.e != null) return false;
        if (q != null ? !q.equals(instance.q) : instance.q != null) return false;
        if (s != null ? !s.equals(instance.s) : instance.s != null) return false;
        if (target != null ? !target.equals(instance.target) : instance.target != null) return false;
        if (w != null ? !w.equals(instance.w) : instance.w != null) return false;
        if (x != null ? !x.equals(instance.x) : instance.x != null) return false;
        if (z != null ? !z.equals(instance.z) : instance.z != null) return false;
        if (dis != null ? !dis.equals(instance.dis) : instance.dis != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = target != null ? target.hashCode() : 0;
        result = 31 * result + (q != null ? q.hashCode() : 0);
        result = 31 * result + (w != null ? w.hashCode() : 0);
        result = 31 * result + (e != null ? e.hashCode() : 0);
        result = 31 * result + (a != null ? a.hashCode() : 0);
        result = 31 * result + (d != null ? d.hashCode() : 0);
        result = 31 * result + (z != null ? z.hashCode() : 0);
        result = 31 * result + (x != null ? x.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        result = 31 * result + (s != null ? s.hashCode() : 0);
        result = 31 * result + (dis != null ? dis.hashCode() : 0);
        return result;
    }

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
    public String getAttribute(int i){
        switch(i){
            case 0:
                return q;
            case 1:
                return w;
            case 2:
                return e;
            case 3:
                return a;
            case 4:
                return d;
            case 5:
                return z;
            case 6:
                return x;
            case 7:
                return c;
            case 8:
                return s;
            case 9:
                return dis;
            default:
                return "";
        }
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
            case 9:
                setDis(str);
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
        int[] feature = new int[10];
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
                case 9:
                    feature[i] = Integer.valueOf(getDis());
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
