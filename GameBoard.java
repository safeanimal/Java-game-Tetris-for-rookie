import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameBoard extends Application
{
    private GamingBoard gamingBoard;
    private Menu menu=Menu.getInstance();

    public void start(Stage primaryStage)
    {
        Scene menuScene=new Scene(menu,Constants.TOTAL_WIDTH,Constants.TOTAL_HEIGHT);
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("俄罗斯方块");
        primaryStage.setResizable(false);
        primaryStage.show();

        menu.getStartButton().setOnMouseClicked(e->{
            gamingBoard=new GamingBoard();
            gamingBoard.setPadding(new Insets(0,0,0,0));
            Scene gameScene=new Scene(gamingBoard,Constants.TOTAL_WIDTH,Constants.TOTAL_HEIGHT);
            primaryStage.setScene(gameScene);

            ExecutorService executor=Executors.newFixedThreadPool(1);
            executor.execute(new UpdateRequestFocus());
            executor.shutdown();
        });//转换场景事件
    }

    class UpdateRequestFocus implements Runnable
    {
        public void run()
        {
            try
            {
                while(!gamingBoard.isGameOver())
                {
                    Platform.runLater(()-> gamingBoard.getBlock().requestFocus());
                    Thread.sleep(Constants.LENGENDARY);
                }
            }
            catch(InterruptedException ex)
            {

            }
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
