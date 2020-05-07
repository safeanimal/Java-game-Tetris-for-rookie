import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Menu extends StackPane
{
    private ImageView backgroundView=new ImageView("Tetris.jpg");
    private Label label=createLabel();
    private Button startButton=createStartButton();

    private Menu()
    {
        VBox vBox=new VBox();
        vBox.getChildren().addAll(label,startButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(120);
        getChildren().addAll(backgroundView,vBox);
    }

    private static class HolderClass
    {
        private final static Menu instance=new Menu();
    }

    public static Menu getInstance()
    {
        return HolderClass.instance;
    }

    private Label createLabel()
    {
        Label label=new Label("Tetris");
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR,64));
        Reflection reflection=new Reflection();
        reflection.setFraction(0.7);
        label.setEffect(reflection);
        return label;
    }

    private Button createStartButton()
    {
        ImageView imageView=new ImageView("startButton.png");
        Button button=new Button("开始游戏",imageView);
        return button;
    }


    public Button getStartButton()
    {
        return startButton;
    }

}
