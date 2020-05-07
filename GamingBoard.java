import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class GamingBoard extends HBox
{
    /*用于储存正方形的引用*/
    private Rectangle[][] grid=new Rectangle[Constants.BLOCK_NUM_ONE_ROW][Constants.TOTAL_HEIGHT];
    private Block[][] blockGrid=new Block[Constants.BLOCK_NUM_ONE_ROW][Constants.TOTAL_HEIGHT];
    private Block block;

    private Pane pane=new Pane();
    private VBox vBox=new VBox();
    private ScoreBoard scoreBoard=new ScoreBoard();
    private ScoreBoard scoreBoardDecoratorMusic=new ScoreBoardDecoratorMusic(scoreBoard);

    private NextBlockBoard nextBlockBoard=new NextBlockBoard();

    private boolean isGameOver;//记录游戏是否结束
    private double score;

    private int blockSerialNum=(int)(Math.random()*7)+1; //方块的序列号，表示产生哪一个方块

    private Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"游戏结束！一些游戏功能没空写了QAQ，以后完善", ButtonType.YES);

    public GamingBoard()
    {
        pane.setPrefWidth(Constants.GAMINGBOARD_WIDTH);
        pane.setPrefHeight(Constants.TOTAL_HEIGHT);
        pane.setStyle("-fx-border-color:black");
        vBox.getChildren().addAll(scoreBoardDecoratorMusic,nextBlockBoard);
        vBox.setPrefSize(Constants.INFOBOARD_WIDTH,Constants.INFOBOARD_HEIGHT);
        vBox.setStyle("-fx-border-color:black");
        getChildren().addAll(pane,vBox);

        initGrid();
        block=new Block();
        pane.getChildren().add(block);
        nextBlockBoard.showNextBlock("shape"+blockSerialNum+".png");//初始化nextBlockBoard内容

        RunAnimation runAnimation=new RunAnimation();

        isGameOver=false;
        ExecutorService executor=Executors.newFixedThreadPool(1);
        executor.execute(runAnimation);
        executor.shutdown();

    }

    /**Block作为内部类，方便访问GamingBoard信息*/
    public class Block extends Parent
    {
        private Rectangle[] rectangles=createShape(); //方块
        private int[] nx=new int[4]; //下一次移动的位置坐标
        private int[] ny=new int[4];
        private int[] ntx=new int[4];//下一次变形的位置坐标
        private int[] nty=new int[4];
        private int x,y;//位移
        private String[] shape1;//储存四个形态
        private String[] shape2;
        private String[] shape3;
        private String[] shape4;

        private int status=1;//记录处于四种状态中的第几种


        public Block()
        {
            this((int)(Math.random()*7)+1); //随机产生1-7
        }

        /**根据参数创建不同的方块*/
        public Block(int type)
        {
            initShape(type);
            computeNextTransformPosition(shape1);
            changePosition(status);
            setTransformEvent();
            setColor(Constants.colors[type-1]);
            getChildren().addAll(rectangles[0],rectangles[1],rectangles[2],rectangles[3]);
        }

        /**创建方块*/
        private Rectangle[] createShape()
        {
            Rectangle[] rectangles=new Rectangle[4];

            for(int i=0;i<rectangles.length;i++)
            {
                rectangles[i]=new Rectangle();
                rectangles[i].setWidth(Constants.LENGTH);
                rectangles[i].setHeight(Constants.LENGTH);
                rectangles[i].setStroke(Color.BLACK);
                rectangles[i].setStrokeWidth(1);
                rectangles[i].setFill(Color.TRANSPARENT);
            }

            return rectangles;
        }

        /**初始化方块*/
        public void initShape(int type)
        {
            switch (type)
            {
                case 1:
                    shape1=Constants.shape1_1;
                    shape2=Constants.shape1_2;
                    shape3=Constants.shape1_3;
                    shape4=Constants.shape1_4;
                    break;
                case 2:
                    shape1=Constants.shape2_1;
                    shape2=Constants.shape2_2;
                    shape3=Constants.shape2_3;
                    shape4=Constants.shape2_4;
                    break;
                case 3:
                    shape1=Constants.shape3_1;
                    shape2=Constants.shape3_2;
                    shape3=Constants.shape3_3;
                    shape4=Constants.shape3_4;
                    break;
                case 4:
                    shape1=Constants.shape4_1;
                    shape2=Constants.shape4_2;
                    shape3=Constants.shape4_3;
                    shape4=Constants.shape4_4;
                    break;
                case 5:
                    shape1=Constants.shape5_1;
                    shape2=Constants.shape5_2;
                    shape3=Constants.shape5_3;
                    shape4=Constants.shape5_4;
                    break;
                case 6:
                    shape1=Constants.shape6_1;
                    shape2=Constants.shape6_2;
                    shape3=Constants.shape6_3;
                    shape4=Constants.shape6_4;
                    break;
                case 7:
                    shape1=Constants.shape7_1;
                    shape2=Constants.shape7_2;
                    shape3=Constants.shape7_3;
                    shape4=Constants.shape7_4;
                    break;
            }
        }

        /**改变方块的位置*/
        public void changePosition(int status)
        {
            switch (status)
            {
                case 1:
                    changeToShape(2);
                    break;
                case 2:
                    changeToShape(3);
                    break;
                case 3:
                    changeToShape(4);
                    break;
                case 4:
                    changeToShape(1);
                    break;
            }
        }

        /**改变形状*/
        public void changeToShape(int status)
        {
            for(int i=0;i<rectangles.length;i++)
            {
                rectangles[i].setX(ntx[i]);
                rectangles[i].setY(nty[i]);
            }
            this.status=status;
        }

        /**计算下次变形的位置*/
        public boolean computeNextTransformPosition(String[] nextShape)
        {
            /*暂时储存ntx，nty信息，如果下次变形有效再赋值给ntx，nty，如果这次变形无效，不赋值*/
            int[] tx=new int[4];
            int[] ty=new int[4];

            int count=0;
            for(int i=0;i<nextShape.length;i++)
            {
                for(int j=0;j<nextShape[0].length();j++)
                {
                    if(nextShape[i].charAt(j)=='1')
                    {
                        tx[count]=Constants.START_X+j*30+x;//加上x，才是实时的位置
                        ty[count]=Constants.START_Y+i*30+y;//加上y才是实时的位置
                        count++;
                    }
                }
            }
            /*如果越界，此次计算无效*/
            if(isOutOfBoundary(tx,ty))
            {
                for(int i=0;i<4;i++)
                {
                    ntx[i]=(int)rectangles[i].getX();//这个很重要！！！！啊啊
                }

                for(int i=0;i<4;i++)
                    System.out.println(i+": ("+x+","+y+")");
                return true;
            }
            /*此次计算有效的情况下*/
            for(int i=0;i<nextShape.length;i++)
            {
                ntx[i]=tx[i];
                nty[i]=ty[i];
                /*这个更新必须的*/
                nx[i]=tx[i];
                ny[i]=ty[i];
            }
            return false;
        }

        /**计算该正方形是否越界*/
        public boolean isOutOfBoundary(int[] x,int[] y)
        {
            int[] tx=new int[4];
            int[] ty=new int[4];

            for(int i=0;i<4;i++)
            {
                tx[i]=x[i]/Constants.LENGTH;
                ty[i]=y[i]/Constants.LENGTH;

                if(tx[i]<=Constants.LEFT_BOUNDARY-1||tx[i]>=Constants.RIGHT_BOUNDARY||
                        ty[i]>=Constants.BOTTOM_BOUNDARY)
                {
                    return true;
                }
                else if(ty[i]>=0&&grid[tx[i]][ty[i]]!=null)//ty[i]>=0是因为一开始有的方块的y位置坐标是负的，这样会抛出数组越界异常
                    return true;
            }
            return false;
        }

        /**计算四个正方形现有位置*/
        public int[][] getRectanglesPosition()
        {
            int[][] info=new int[4][];
            for(int i=0;i<info.length;i++)
            {
                info[i]=new int[2];
                info[i][0]=(int)(rectangles[i].getX()/Constants.LENGTH);
                info[i][1]=(int)(rectangles[i].getY()/Constants.LENGTH);
            }
            return info;
        }

        /**方块下降*/
        public int fall()
        {
            if(isOutOfBoundary(nx,ny))
            {
                System.out.println("越界了！！");

                for(int i=0;i<ny.length;i++)
                {
                    if(ny[i]==0)
                        return -1; //游戏结束了
                }

                return 1;
            }

            /*每次下降后计算下一次下降的位置*/
            for(int i=0;i<rectangles.length;i++)
            {
                rectangles[i].setY(ny[i]); //每次下降一个单位
                /*刷新下一次降落的坐标*/
                ny[i]=(int)(rectangles[i].getY()+Constants.LENGTH);

                nty[i]=nty[i]+Constants.LENGTH;//这个个个个 个很重要！！！！！！！！！！！
            }
            y=y+Constants.LENGTH;
            return 0;
        }

        /**方块向左还是向右*/
        public boolean moveLeftOrRight(boolean isLeft)
        {
            int[] tx=new int[4];
            int[] ty=new int[4];
            if(isLeft)
            {
                for(int i=0;i<4;i++)
                {
                    tx[i]=nx[i]-30;
                    ty[i]=ny[i];
                }
            }
            else
            {
                for(int i=0;i<4;i++)
                {
                    tx[i]=nx[i]+30;
                    ty[i]=ny[i];
                }
            }
            if(isOutOfBoundary(tx,ty))//越界返回true，此次移动作废
                return true;
            else
            {
                if(isLeft==true)
                {
                    System.out.println("左移成功！！！");
                    for(int i=0;i<4;i++)
                    {
                        rectangles[i].setX((int)(rectangles[i].getX()-Constants.LENGTH));
                        nx[i]=tx[i];
                    }
                    x=x-30;
                }
                else
                {
                    for(int i=0;i<4;i++)
                    {
                        rectangles[i].setX((int)(rectangles[i].getX()+Constants.LENGTH));
                        nx[i]=tx[i];
                    }
                    x=x+30;
                }
            }
            return false;
        }

        /**方块直接降落到底*/
        public void fallDirectly()
        {
            int[] x=new int[nx.length];
            int[] y=new int[nx.length];

            for(int i=0;i<rectangles.length;i++)
            {
                x[i]=(int)rectangles[i].getX();
                y[i]=(int)rectangles[i].getY();
            }

            int count=0;

            while(!isOutOfBoundary(x,y))
            {
                for(int i=0;i<x.length;i++)
                {
                    y[i]=y[i]+Constants.LENGTH;
                }
                count++;
            }

            System.out.println(count);
            if(count>=2)//防止原地踏步
            {
                for (int i = 0; i < rectangles.length; i++)
                {
                    x[i] = (int) rectangles[i].getX();
                    y[i] = (int) rectangles[i].getY() + (count - 1) * Constants.LENGTH;
                    nty[i] = nty[i] + (count-1)*Constants.LENGTH;
                }
            }
            if(count==1)
            {
                return;
            }

            this.y=this.y+(count-1)*Constants.LENGTH;
            ny=y;
            for(int i=0;i<rectangles.length;i++)
            {
                rectangles[i].setY(y[i]);
            }
        }

        /**设置变形事件*/
        public void setTransformEvent()
        {
            //ToDo:在左/右边界快速左/右移动后变形，会把方块卡出去。需要查清bug来源。
            setOnKeyPressed(e->
            {

                if(e.getCode()== KeyCode.UP)
                {
                    boolean cannotChange=false;
                    switch (status)
                    {
                        case 1:cannotChange=computeNextTransformPosition(shape1);break;
                        case 2:cannotChange=computeNextTransformPosition(shape2);break;
                        case 3:cannotChange=computeNextTransformPosition(shape3);break;
                        case 4:cannotChange=computeNextTransformPosition(shape4);break;
                    }
                    if(!cannotChange)
                        changePosition(status);
                }
                else if(e.getCode()==KeyCode.LEFT)
                {
                    moveLeftOrRight(true);
                }
                else if(e.getCode()==KeyCode.RIGHT)
                {
                    moveLeftOrRight(false);
                }
                else if(e.getCode()==KeyCode.DOWN)
                {
                    fallDirectly();
                }

            });
        }

        /**设置颜色*/
        public void setColor(Color color)
        {
            for(int i=0;i<rectangles.length;i++)
            {
                rectangles[i].setFill(color);
            }
        }

        /**矩形的访问器*/
        public Rectangle[] getRectangles()
        {
            return rectangles;
        }

        /**删除一个正方形*/
        public void delete(Rectangle rectangle)
        {
            for(int i=0;i<4;i++)
            {
                if(rectangles[i]==rectangle)
                {
                    getChildren().remove(rectangle);
                }
            }
        }

        /**设置正方形的位置,x,y为偏移量*/
        public void setRectanglesPosition(Rectangle rectangle,int x,int y)
        {
            for(int i=0;i<4;i++)
            {
                if(rectangles[i]==rectangle)
                {
                    rectangles[i].setX((int)(rectangles[i].getX()+x));
                    rectangles[i].setY((int)(rectangles[i].getY()+y));
                }
            }
        }
    }

    /**初始化用于储存引用及判断的格子*/
    public void initGrid()
    {
        for(int i=0;i<grid.length;i++)
        {
            grid[i]=new Rectangle[Constants.BLOCK_NUM_ONE_COLUMN];
            blockGrid[i]=new Block[Constants.BLOCK_NUM_ONE_COLUMN];
        }
    }

    /**方块下降的线程*/
    class RunAnimation implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                while(!isGameOver)
                {
                    Platform.runLater(()->
                    {
                        if(block.fall()==1)
                        {
                            try
                            {
                                Thread.sleep(200);//这个用来让方块碰撞后还有100ms的修改时间，玩起来更平滑
                            }
                            catch(Exception ex)
                            {

                            }
                            int[][] position=block.getRectanglesPosition();
                            Rectangle[] rectangles=block.getRectangles();
                            /*把矩形的引用加入到grid中区*/
                            for(int i=0;i<rectangles.length;i++)
                            {
                                grid[position[i][0]][position[i][1]]=rectangles[i];
                                blockGrid[position[i][0]][position[i][1]]=block;
                            }

                            //判断是否可消或者游戏结束
                            boolean isEliminatable=true;
                            for(int i=grid[0].length-1;i>=0;i--)
                            {
//                                System.out.println("检测第"+(grid[0].length-i)+"行");
                                for(int j=0;j<grid.length;j++)
                                {
                                    if(grid[j][i]==null)
                                    {
                                        isEliminatable = false;
                                        break;
                                    }
                                }
                                if(isEliminatable)
                                {
                                    score=score+10;
//                                    System.out.println("第"+(grid[0].length-i)+"行可以消除了！");
                                    for(int k=0;k<grid.length;k++)
                                    {
                                        blockGrid[k][i].delete(grid[k][i]);
                                        grid[k][i]=null;//消去这个正方形
                                        blockGrid[k][i]=null;
                                    }
                                    /*上面所有正方形向下移动*/
                                    for(int k=i-1;k>=0;k--)
                                    {
                                        for(int p=0;p<grid.length;p++)
                                        {
                                            if(grid[p][k]!=null)
                                            {
                                                /*开始下降*/
                                                blockGrid[p][k].setRectanglesPosition(grid[p][k],0,Constants.LENGTH);
                                                grid[p][k+1]=grid[p][k];
                                                grid[p][k]=null;
                                                blockGrid[p][k+1]=blockGrid[p][k];
                                                blockGrid[p][k]=null;
                                            }
                                        }
                                    }
                                    i++;
                                }
                                isEliminatable=true;//值更新
                            }
                            scoreBoardDecoratorMusic.updateScore((int)score);//更新分数
                            Block newBlock=new Block(blockSerialNum);
                            block=newBlock;
                            pane.getChildren().add(newBlock);
                            blockSerialNum=(int)(Math.random()*7)+1;//更新方块序列号，为下一轮做准备
                            nextBlockBoard.deleteImage();//清除原来的内容
                            nextBlockBoard.showNextBlock("shape"+blockSerialNum+".png");//更新nextBlockBoard图片
                            return;
                        }
                        else if(block.fall()==-1)
                        {
                            isGameOver=true;
                            return;
                        }
                    });
                    Thread.sleep(Constants.LENGENDARY);
                }
            }
            catch (InterruptedException ex)
            {

            }
            finally
            {
                Platform.runLater(()->{
                    alert.showAndWait();
                });
            }

        }
    }

    /**方块的访问器*/
    public Block getBlock()
    {
        return block;
    }

    /**游戏状态访问器*/
    public boolean isGameOver()
    {
        return isGameOver;
    }
}