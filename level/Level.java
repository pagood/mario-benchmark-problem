package competition.cig.yuxiao.level;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class Level implements Cloneable
{
    public static final String[] BIT_DESCRIPTIONS = {//
    "BLOCK UPPER", //
            "BLOCK ALL", //
            "BLOCK LOWER", //
            "SPECIAL", //
            "BUMPABLE", //
            "BREAKABLE", //
            "PICKUPABLE", //
            "ANIMATED",//
    };

    public static byte[] TILE_BEHAVIORS = new byte[256];

    public static final int BIT_BLOCK_UPPER = 1 << 0;
    public static final int BIT_BLOCK_ALL = 1 << 1;
    public static final int BIT_BLOCK_LOWER = 1 << 2;
    public static final int BIT_SPECIAL = 1 << 3;
    public static final int BIT_BUMPABLE = 1 << 4;
    public static final int BIT_BREAKABLE = 1 << 5;
    public static final int BIT_PICKUPABLE = 1 << 6;
    public static final int BIT_ANIMATED = 1 << 7;

    //private static final int FILE_HEADER = 0x271c4178;
    public int width;
    public int height;
    public int maxDiscoveredWidth = 0;
    
    public byte[][] map;
    
    public boolean[] isGap;
    public int[] gapHeight;
    
    public List<int[]> modifiedMapTiles = new ArrayList<int[]>(0);
    //public byte[][] data;
    //public byte[][] observation;

    public competition.cig.yuxiao.level.SpriteTemplate[][] spriteTemplates;

    public int xExit;
    public int yExit;

    @Override
	public Object clone() throws CloneNotSupportedException
    {
    	//System.out.println("Cloning level, wh: "+width + " "+height);
    	competition.cig.yuxiao.level.Level l = (competition.cig.yuxiao.level.Level) super.clone();
    	List<int[]> clone = new ArrayList<int[]>(modifiedMapTiles.size());
    	for(int[] item: modifiedMapTiles)
    		clone.add((int[]) item.clone());
    	l.modifiedMapTiles = clone;
    	return l;
    }


    public Level(int width, int height)
    {
        this.width = width;
        this.height = height;

        xExit = 100000;
        yExit = 10;
        map = new byte[width][height];
        isGap = new boolean[width];
        gapHeight = new int[width];
        for (int i = 0; i < width; i++)
        {
        	map[i][14] = 4;
        	isGap[i] = false;
        	gapHeight[i] = 15;
        }
        //data = new byte[width][height];
        spriteTemplates = new competition.cig.yuxiao.level.SpriteTemplate[width][height];
        //observation = new byte[width][height];
    }

    public static void loadBehaviors()
    {
        //dis.readFully(Level.TILE_BEHAVIORS);

    	// This is the data from tiles.dat. Could load it from the file, but to avoid i/o problems, data is here.
        competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[0] = 0;
        competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[1] = 20;
        competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[2] = 28;
        competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[3] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[4] = -126;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[5] = -126;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[6] = -126;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[7] = -126;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[8] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[9] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[10] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[11] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[12] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[13] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[14] = -118;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[15] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[16] = -94;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[17] = -110;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[18] = -102;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[19] = -94;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[20] = -110;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[21] = -110;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[22] = -102;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[23] = -110;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[24] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[25] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[26] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[27] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[28] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[29] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[30] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[31] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[32] = -64;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[33] = -64;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[34] = -64;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[35] = -64;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[36] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[37] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[38] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[39] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[40] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[41] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[42] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[43] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[44] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[45] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[46] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[47] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[48] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[49] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[50] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[51] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[52] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[53] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[54] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[55] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[56] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[57] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[58] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[59] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[60] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[61] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[62] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[63] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[64] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[65] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[66] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[67] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[68] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[69] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[70] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[71] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[72] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[73] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[74] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[75] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[76] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[77] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[78] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[79] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[80] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[81] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[82] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[83] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[84] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[85] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[86] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[87] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[88] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[89] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[90] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[91] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[92] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[93] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[94] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[95] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[96] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[97] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[98] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[99] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[100] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[101] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[102] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[103] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[104] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[105] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[106] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[107] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[108] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[109] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[110] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[111] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[112] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[113] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[114] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[115] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[116] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[117] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[118] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[119] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[120] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[121] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[122] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[123] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[124] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[125] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[126] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[127] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[128] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[129] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[130] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[131] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[132] = 1;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[133] = 1;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[134] = 1;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[135] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[136] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[137] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[138] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[139] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[140] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[141] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[142] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[143] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[144] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[145] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[146] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[147] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[148] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[149] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[150] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[151] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[152] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[153] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[154] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[155] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[156] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[157] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[158] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[159] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[160] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[161] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[162] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[163] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[164] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[165] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[166] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[167] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[168] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[169] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[170] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[171] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[172] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[173] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[174] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[175] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[176] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[177] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[178] = 2;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[179] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[180] = 1;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[181] = 1;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[182] = 1;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[183] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[184] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[185] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[186] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[187] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[188] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[189] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[190] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[191] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[192] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[193] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[194] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[195] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[196] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[197] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[198] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[199] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[200] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[201] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[202] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[203] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[204] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[205] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[206] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[207] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[208] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[209] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[210] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[211] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[212] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[213] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[214] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[215] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[216] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[217] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[218] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[219] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[220] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[221] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[222] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[223] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[224] = 1;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[225] = 1;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[226] = 1;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[227] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[228] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[229] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[230] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[231] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[232] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[233] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[234] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[235] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[236] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[237] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[238] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[239] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[240] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[241] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[242] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[243] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[244] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[245] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[246] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[247] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[248] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[249] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[250] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[251] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[252] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[253] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[254] = 0;
    	competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[255] = 0;
    }

    public static void saveBehaviors(DataOutputStream dos) throws IOException
    {
        dos.write(competition.cig.yuxiao.level.Level.TILE_BEHAVIORS);
    }


    public void tick()
    {
    }

    public byte getBlock(int x, int y)
    {
        if (x < 0) x = 0;
        if (y < 0) return 0;
        if (x >= width) x = width - 1;
        if (y >= height) y = height - 1;
        if ((competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[map[x][y] & 0xff] & competition.cig.yuxiao.level.Level.BIT_BREAKABLE) > 0)
        {
        	for(int[] a : modifiedMapTiles)
        	{
        		if (a[0] == x && a[1] == y)
        			return (byte) 0;
        	}
        }
        return map[x][y];
    }

    public void setBlock(int x, int y, byte b)
    {
        if (x < 0) return;
        if (y < 0) return;
        if (x >= width) return;
        if (y >= height) return;
        if ((competition.cig.yuxiao.level.Level.TILE_BEHAVIORS[map[x][y] & 0xff] & competition.cig.yuxiao.level.Level.BIT_BREAKABLE) > 0 && b == 0)
        {
        	int[] modified = new int[2];
        	modified[0] = x;
        	modified[1] = y;
        	modifiedMapTiles.add(modified);
        }
        else
        	map[x][y] = b;
    }

    public void setBlockData(int x, int y, byte b)
    {
        if (x < 0) return;
        if (y < 0) return;
        if (x >= width) return;
        if (y >= height) return;
        //data[x][y] = b;
    }

    public boolean isBlocking(int x, int y, float xa, float ya)
    {
        byte block = getBlock(x, y);
//        System.out.println("Block: "+block);
        boolean blocking = ((TILE_BEHAVIORS[block & 0xff]) & BIT_BLOCK_ALL) > 0;
        blocking |= (ya > 0) && ((TILE_BEHAVIORS[block & 0xff]) & BIT_BLOCK_UPPER) > 0;
        blocking |= (ya < 0) && ((TILE_BEHAVIORS[block & 0xff]) & BIT_BLOCK_LOWER) > 0;

        return blocking;
    }

    public competition.cig.yuxiao.level.SpriteTemplate getSpriteTemplate(int x, int y)
    {
        if (x < 0) return null;
        if (y < 0) return null;
        if (x >= width) return null;
        if (y >= height) return null;
        return spriteTemplates[x][y];
    }

    public void setSpriteTemplate(int x, int y, SpriteTemplate spriteTemplate)
    {
        if (x < 0) return;
        if (y < 0) return;
        if (x >= width) return;
        if (y >= height) return;
        spriteTemplates[x][y] = spriteTemplate;
    }

    public int getWidthCells() {         return width;    }

    public double getWidthPhys() {         return width * 16;    }
}