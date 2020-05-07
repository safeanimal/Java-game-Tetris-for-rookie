import javafx.scene.paint.Color;

public final class Constants
{
    public static final int LENGTH=30;
    public static final int GAMINGBOARD_WIDTH=360;
    public static final int GAMINGBOARD_HEIGHT=480;
    public static final int INFOBOARD_WIDTH=GAMINGBOARD_WIDTH/2;
    public static final int INFOBOARD_HEIGHT=GAMINGBOARD_HEIGHT/2;
    public static final int TOTAL_WIDTH=GAMINGBOARD_WIDTH+INFOBOARD_WIDTH;
    public static final int TOTAL_HEIGHT=GAMINGBOARD_HEIGHT+INFOBOARD_HEIGHT;
    public static final int BLOCK_NUM_ONE_ROW=GAMINGBOARD_WIDTH/LENGTH;
    public static final int BLOCK_NUM_ONE_COLUMN=TOTAL_HEIGHT/LENGTH;

    /*起始坐标*/
    public static final int START_X=GAMINGBOARD_WIDTH/2-LENGTH;
    public static final int START_Y=-3*LENGTH;

    /*游戏难度*/
    public static final int ROOKIE=1500; //菜鸟
    public static final int NORMAL=1000; //正常
    public static final int MASTER=600;  //大师
    public static final int LENGENDARY=400; //不朽之传奇

    /*边界*/
    public static final int LEFT_BOUNDARY=0;
    public static final int RIGHT_BOUNDARY=GAMINGBOARD_WIDTH/LENGTH;
    public static final int BOTTOM_BOUNDARY=TOTAL_HEIGHT/LENGTH;

    /*方块颜色*/
    public static final Color[] colors={Color.RED,Color.BLUE,Color.YELLOW,Color.PURPLE,Color.GRAY,Color.GREEN,Color.BROWN};

    /************************方块***************************************/
    public static final String[] shape1_1={"0000","0110","0110","0000"};
    public static final String[] shape1_2={"0000","0110","0110","0000"};
    public static final String[] shape1_3={"0000","0110","0110","0000"};
    public static final String[] shape1_4={"0000","0110","0110","0000"};
    /******************************************************************/

    /*****************************条形*********************************/
    public static final String[] shape2_1={"0000","1111","0000","0000"};
    public static final String[] shape2_2={"0100","0100","0100","0100"};
    public static final String[] shape2_3={"0000","1111","0000","0000"};
    public static final String[] shape2_4={"0100","0100","0100","0100"};
    /******************************************************************/

    /***************************凸型***********************************/
    public static final String[] shape3_1={"0000","0010","0111","0000"};
    public static final String[] shape3_2={"0010","0011","0010","0000"};
    public static final String[] shape3_3={"0000","0111","0010","0000"};
    public static final String[] shape3_4={"0010","0110","0010","0000"};
    /******************************************************************/

    /***************************Z型***********************************/
    public static final String[] shape4_1={"0000","0110","0011","0000"};
    public static final String[] shape4_2={"0001","0011","0010","0000"};
    public static final String[] shape4_3={"0000","0110","0011","0000"};
    public static final String[] shape4_4={"0001","0011","0010","0000"};
    /******************************************************************/

    /***************************反Z型***********************************/
    public static final String[] shape5_1={"0000","0011","0110","0000"};
    public static final String[] shape5_2={"0010","0011","0001","0000"};
    public static final String[] shape5_3={"0000","0011","0110","0000"};
    public static final String[] shape5_4={"0010","0011","0001","0000"};
    /******************************************************************/

    /***************************L型***********************************/
    public static final String[] shape6_1={"0100","0100","0110","0000"};
    public static final String[] shape6_2={"0000","1110","1000","0000"};
    public static final String[] shape6_3={"1100","0100","0100","0000"};
    public static final String[] shape6_4={"0000","0010","1110","0000"};
    /******************************************************************/

    /***************************反L型***********************************/
    public static final String[] shape7_1={"0010","0010","0110","0000"};
    public static final String[] shape7_2={"0000","0100","0111","0000"};
    public static final String[] shape7_3={"0011","0010","0010","0000"};
    public static final String[] shape7_4={"0000","0111","0001","0000"};
    /******************************************************************/


}
