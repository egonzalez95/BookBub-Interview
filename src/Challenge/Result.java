package Challenge;

import java.util.Objects;

/**
 * This class represents a result.
 * A result is the genre and score of a book whose description was analyzed.
 */
public class Result implements Comparable {

  private final String G;
  private int score;

  /**
   * Constructor
   *
   * @param g     given Genre.
   * @param score given Score.
   *              There is no spec for a score being positive or negative, so there are no checks.
   */
  Result(String g, int score) {
    this.G = g;
    this.score = score;
  }

  /**
   * returns the score for this result.
   *
   * @return score for this genre.
   */
  public int getScore() {
    return this.score;
  }

  /**
   * returns the name of the genre in this result.
   *
   * @return the name of the genre in this result.
   */
  public String getGenre() {
    return this.G;
  }

  /**
   * Sets a score for this result.
   *
   * @param in new score for this Result.
   */
  public void setScore(int in) {
    this.score = in;
  }

  // Override compareTo in Comparable so that we can sort by object in Book.
  @Override
  public int compareTo(Object o) {
    final int BEFORE = -1;
    final int EQUAL = 0;
    final int AFTER = 1;

    if (this == o) return EQUAL;

    if (o instanceof Result) {
      if (this.score < ((Result) o).getScore()) return BEFORE;
      else if (this.score > ((Result) o).getScore()) return AFTER;
      else return EQUAL;
    }

    throw new IllegalArgumentException("Object is not a Result");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (o instanceof Result) {
      Result temp = (Result) o;
      return this.G.equals(temp.getGenre())
              && this.score == temp.getScore();
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.G, this.score);
  }
}
