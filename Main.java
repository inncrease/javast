
class Score {
    int points = 0; 
}

final class GameResult {
    
    private final Score safeScore; 

    GameResult(Score inputScore) {
        this.safeScore = new Score(); 
        this.safeScore.points = inputScore.points;
    }

    Score getScore() {
        Score copyShow = new Score(); 
        copyShow.points = this.safeScore.points; 
        return copyShow;
    }
}

public class Main {
    public static void main(String[] args) {
        
        Score score = new Score();
        score.points = 100;
        System.out.println("My Score: " + score.points);

        GameResult result = new GameResult(score);

        score.points = 101; 
        System.out.println("Trying to change Score: " + score.points);

        Score scoreFromSafe = result.getScore();
        System.out.println("Approve of immutable Score: " + scoreFromSafe.points);
    }
}
