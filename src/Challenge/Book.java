package Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * represents a book.
 */
public class Book implements Comparable {

  private String title;
  private String description;

  // genre identification results for the book.
  private List<Result> results;

  /**
   * constructor.
   *
   * @param title       of the book.
   * @param description of the book.
   */
  public Book(String title, String description) {
    this.title = title;
    this.description = description;
    this.results = new ArrayList();
  }

  /**
   * returns the title of a book.
   *
   * @return String title of the book.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * returns the description of a book.
   *
   * @return String description of the book.
   */
  public String getDescription() {
    return this.description;
  }


  /**
   * returns the list of genre results of a book.
   *
   * @return list of genre results of a book.
   */
  public List getResults() {
    return this.results;
  }

  /**
   * processes the book description with given genre.
   *
   * @param g Genre given.
   *
   *          NOTE TO BOOKBUB: Each step has been documented so that you know what is going on.
   */
  public void process(Genre g) {
    // map of genre keywords
    Map<String, Integer> kw = g.getKeywords();
    // list of unique keywords found in the description
    List found = new ArrayList();
    // total occurances of a non-unique keyword
    int occurances = 0;

    // for all string, we:
    for (String s : kw.keySet()) {
      // pull description
      String desc = this.description;
      // set string index
      int prev = 0;

      while (prev != -1) {
        // find the index of our next match, set it to be our prev.
        // why -1? Because indexOf returns -1 when a string does not contain occurance.
        prev = desc.indexOf(s, prev);

        if (prev != -1) {
          // if the unique keyword is not in "found" list when found, add it to our found list.
          // if it is in the list we do nothing because we only add unique occurances to this list.
          if (!(found.contains(s))) {
            found.add(s);
          }
          // increment occurances since a non-unique keyword was found.
          occurances++;
          // our new index is where we left off.
          prev += s.length();
        }
      }
    }

    // based on specs, I did not see a "float" pattern for points so average will be in int.
    // this will be initialized later so for now we can just declare it.
    int average;

    // total points of unique keywords, for later averaging.
    int points = 0;

    // boolean flag if a genre exists or not in our Results.
    boolean gexists = false;

    // for all unique keywords, add the keyword value points up.
    for (int i = 0; i < found.size(); i++) {
      points += kw.get(found.get(i));
    }

    // if found does not have a size, we can't divide by zero - our avg is just 0.
    if (found.size() <= 0) {
      average = 0;
    }

    // if found has one or more elements, lets divide the total points for our average
    else {
      average = points / found.size();
    }

    // for all of our results in this book, if the genre exists, our score will be set to
    // total num keyword matches * avg point value of the unique matching keywords
    for (Result r : this.results) {
      if (r.getGenre().equalsIgnoreCase(g.getName())) {
        gexists = true;
        r.setScore(occurances * average);
      }
    }

    // if the genre does not exist, create it and set score to:
    // total num keyword matches * avg point value of the unique matching keywords
    if (!gexists) {
      this.results.add(new Result(g.getName(), occurances * average));
    }

    // sort the list of results.
    // This requires we make Results comparable and override compareTo
    // we want ascending order.
    Collections.sort(this.results, Collections.reverseOrder());
  }

  /**
   * returns the top 3 Genre results for this Book.
   * side-note: if there are less than 3 genres over score 0, only over zero will return.
   *
   * @return top 3 Results.
   */
  public List returnTop3() {
    // where the results will be added.
    List<Result> temp = new ArrayList();
    for (Result r : this.results.subList(0, 2)) {
      // if score is more than zero, add it from top 3 results.
      // logic here is that results are already sorted by score, so if zero values are in top 3,
      // there were not that many hits for the keyword.
      if (r.getScore() > 0) {
        temp.add(r);
      }
    }
    return temp;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (o instanceof Book) {
      Book temp = (Book) o;
      return this.title.equals(temp.getTitle())
              && this.description.equals(temp.getDescription());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.title, this.description);
  }

  // Override compareTo in Comparable so that we can sort by title.
  @Override
  public int compareTo(Object o) {
    if (this == o) return 0;

    if (o instanceof Book) {
      Book temp = (Book) o;
      return this.title.compareTo(temp.title);
    }

    throw new IllegalArgumentException("Object is not a Result");
  }
}
