package Challenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a Genre.
 *
 * A Genre is composed of a Genre name and keywords, which has associated point values.
 */
public class Genre {

  private String name;
  private Map<String, Integer> keywords;

  /**
   * Constructor.
   */
  public Genre(String name) {
    this.name = name;
    this.keywords = new HashMap<>();
  }

  /**
   * Gets the name of the Genre.
   *
   * @return String value of the Genre name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the keywords map from this Genre.
   *
   * @return keyword map.
   */
  public Map<String, Integer> getKeywords() {
    return this.keywords;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (o instanceof Genre) {
      Genre temp = (Genre) o;
      return this.name.equals(temp.getName())
              && this.keywords == temp.getKeywords();
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.keywords);
  }
}