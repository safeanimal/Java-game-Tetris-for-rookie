public class ScoreBoardDecorator extends ScoreBoard
{
    private ScoreBoard scoreBoard;
    public ScoreBoardDecorator(ScoreBoard scoreBoard)
    {
        this.scoreBoard=scoreBoard;
    }

    public void updateScore(int score)
    {
        scoreBoard.updateScore(score);
    }

}
