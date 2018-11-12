package Challenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;


/**
 * @author Emilio Gonzalez.
 *
 * Coded for the BookBub coding challenege.
 *
 * GenreClassifier is a took that determines a genre of a book.
 * This is determined by keywords in the book title and description.
 */

public class GenreClassifier{

  private static List<Genre> genres = new ArrayList();
  public static List<Book> books = new ArrayList();
  private static int totalPages;

  /**
   * Parse JSON file to program.
   *
   * @param filepath file path to JSON.
   */
  @SuppressWarnings("unchecked")
  private static void parseBooksJson(String filepath) throws InterruptedException {
    if (filepath == null) {
      throw new IllegalArgumentException("Filepath string can't be null.");
    }
    if (filepath.equals("")) {
      throw new IllegalArgumentException("Filepath can't be an empty string.");
    }

      boolean isEmpty = false;
      int pageCount = 1;
      System.out.println(pageCount);
      while (!isEmpty) {
          BookRunnable br = new BookRunnable(filepath + "?page=" + (pageCount));
          System.out.println(filepath + "?page=" + (pageCount));
          br.start();
          if (br.getFlag()) {
            isEmpty = true;
          }
          if (!br.getFlag()) {
            pageCount++;
          }
        System.out.println(pageCount);
      }
      pageCount--;
      totalPages = pageCount;
      // sort alphabetically.
      Collections.sort(books);
  }

  /**
   * Parse CSV file to program.
   *
   * @param filepath file path to CSV.
   */
  private static void parseGenreCSV(String filepath) {
    if (filepath == null) {
      throw new IllegalArgumentException("Filepath string can't be null.");
    }
    if (filepath.equals("")) {
      throw new IllegalArgumentException("Filepath can't be an empty string.");
    }

    try {
      BufferedReader buffreader = new BufferedReader(new FileReader(filepath));
      // empty set to store genres from split
      Set<String> set = new HashSet<>();
      String row;
      int count = 0;

      // if the count is 0, we are at the header. We can skip this.
      while ((row = buffreader.readLine()) != null) {
        if (count == 0) {
          count++;
          continue;
        }

        // string array for our CSV row, split by a comma.
        String[] stringarr = row.split(",");

        // note: we need to trim the values for clean input.
        // Case for existing Genres
        if (set.add(stringarr[0])) {
          Genre genre = new Genre(stringarr[0]);
          genre.getKeywords().put(stringarr[1].trim(), Integer.valueOf(stringarr[2].trim()));
          genres.add(genre);
        }

        // if Genre does not exist, create one.
        else {
          Genre genre = genres.get(findGenre(stringarr[0]));
          genre.getKeywords().put(stringarr[1].trim(), Integer.valueOf(stringarr[2].trim()));
        }

      }
      // close the reader
      buffreader.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Finds an existing genre in a genre list.
   *
   * @param s string of given genre name
   * @return index value for genre.
   */
  private static int findGenre(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Genre string can't be null.");
    }
    if (s.equals("")) {
      throw new IllegalArgumentException("Genre can't be an empty string.");
    }

    for (int i = 0; i < genres.size(); i++) {
      Genre g = genres.get(i);
      if (s.equals(g.getName())) {
        return i;
      }
    }

    // if there is not match, throw an exception.
    throw new IllegalArgumentException("Genre not found.");
  }

  /**
   * @param lbook  is our list of Book generated from JSON file.
   * @param lgenre is our list of Genre generated from CSV input.
   */
  private static void process(List<Book> lbook, List<Genre> lgenre) {
    if (lbook == null) {
      throw new IllegalArgumentException("Books can't be null.");
    }
    if (lgenre == null) {
      throw new IllegalArgumentException("Genres can't be null.");
    }

    for (Book b : lbook) {
      for (Genre g : lgenre) {
        b.process(g);
      }
    }
  }

  public static void topGenre() {
    Map<String, Integer> gmap = new HashMap<String, Integer>();
    String topGenreSoFar = null;
    for (Book b : books) {
      List<Result> temprlist = b.getResults();
      for(Result r : temprlist) {
        String name = r.getGenre();
        if (gmap.containsKey(name)) {
          gmap.replace(name, gmap.get(name) + r.getScore());
        }
        else {
          gmap.put(name, r.getScore());
        }
      }
    }

    // find highest.
    Set<String> ks = gmap.keySet();
    for (String s : ks) {
      if (topGenreSoFar == null) {
        topGenreSoFar = s;
      }
      else {
        if (gmap.get(s) > gmap.get(topGenreSoFar)) {
          topGenreSoFar = s;
        }
      }
    }

    System.out.println("Top Genre: " + topGenreSoFar + " with score: " + gmap.get(topGenreSoFar));

  }

  /**
   * this is the main method for this project.
   */
  public static void main(String[] args) throws InterruptedException {
    // first argument JSON and second is CSV.
    //parseBooksJson(args[0]);
    //parseGenreCSV(args[1]);
    //parseBooksJson("/Users/emilio/IntelliJ workspace/BookBub/sample_book_json");
    parseBooksJson("https://pairing-library.herokuapp.com/api/v1/books");
    parseGenreCSV("/Users/emilio/IntelliJ workspace/BookBub/sample_genre_keyword_value.csv");
    // process all the descriptions of books with genres list.
    process(books, genres);
    // output.
    for (Book b : books) {
      System.out.println(b.getTitle());
      List<Result> temp = b.returnTop3();
      for (int i = 0; i < temp.size(); i++) {
        if (i == temp.size() - 1) {
          System.out.println(temp.get(i).getGenre() + ", " + temp.get(i).getScore() + "\n");
        } else {
          System.out.println(temp.get(i).getGenre() + ", " + temp.get(i).getScore());
        }
      }
    }
    //System.out.println("Total pages: " + totalPages);
    topGenre();
    System.out.println("Total pages: " + totalPages);
  }
}