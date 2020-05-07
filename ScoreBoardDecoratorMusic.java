import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ScoreBoardDecoratorMusic extends ScoreBoardDecorator
{
    String bgMusicUrl=this.getClass().getClassLoader().getResource("bgMusic.wav").toExternalForm();
    String eliminationMusicUrl=this.getClass().getClassLoader().getResource("eliminationMusic.wav").toExternalForm();

    private Media bgMusic;
    private Media eliminationMusic;
    private MediaPlayer bgMusicPlayer,eliminationMusicPlayer;
    public ScoreBoardDecoratorMusic(ScoreBoard scoreBoard)
    {
        super(scoreBoard);
        bgMusic=new Media(bgMusicUrl);
        bgMusicPlayer=new MediaPlayer(bgMusic);
        bgMusicPlayer.setAutoPlay(true);
        bgMusicPlayer.setCycleCount(999999);

        eliminationMusic=new Media(eliminationMusicUrl);
        eliminationMusicPlayer=new MediaPlayer(eliminationMusic);
    }

    @Override
    public void updateScore(int score)
    {
        super.updateScore(score);
        eliminationMusicPlayer.play();
    }
}
