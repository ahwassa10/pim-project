package structural;

public final class Score {
	private int score;
	
	private Score(int initialScore) {
		score = initialScore;
	}
	
	public static Score newScore() {
		return new Score(0);
	}
	
	public static Score newScore(int initialScore) {
		return new Score(initialScore);
	}
	
	public void decrement() {
		score--;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Score)) {return false;}
		
		Score s = (Score) o;
		
		return score == s.score;
	}
	
	public int getScore() {
		return score;
	}
	
	public int hashCode() {
		return Integer.hashCode(score);
	}
	
	public void increment() {
		score++;
	}
	
	public String toString() {
		return Integer.toString(score);
	}
}
