import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class ScoreBoard extends VBox
{
    private Text scoreText=new Text("Score:0");

    public ScoreBoard()
    {
        setPrefSize(Constants.TOTAL_WIDTH/2,Constants.TOTAL_HEIGHT/2);
        getChildren().add(scoreText);
        setAlignment(Pos.CENTER);
        setStyle("-fx-border-color:black");
    }

    public void updateScore(int score)
    {
        String scoreString="Score:"+String.valueOf(score);
        System.out.println(scoreString);
        scoreText.setText(scoreString);
    }

}
