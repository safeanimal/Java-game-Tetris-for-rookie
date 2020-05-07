import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class NextBlockBoard extends StackPane
{
    private ImageView nextBlockImage;
    public NextBlockBoard()
    {
        setPrefSize(Constants.TOTAL_WIDTH/2,Constants.TOTAL_HEIGHT/2);
        setStyle("-fx-border-color:black");
    }
    /**显示下一轮出现的方块的图片*/
    public void showNextBlock(String nextBlock)
    {
        nextBlockImage=new ImageView(nextBlock);
        getChildren().add(nextBlockImage);
    }

    /**删除图片*/
    public void deleteImage()
    {
        getChildren().clear();
    }
}
