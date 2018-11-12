package Challenge;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGenre {

  @Test
  public void testGenreGetName(){
    Genre g = new Genre("Sci-fi");

    assertEquals("Sci-fi", g.getName());
  }

  @Test
  public void testGenreGetKeywords(){
    Genre g = new Genre("Sci-fi");

    assertEquals(0, g.getKeywords().size());
  }

  @Test
  public void testGenreSettingKeywordsThroughGetKeywords(){
    Genre g = new Genre("Sci-fi");
    g.getKeywords().put("Test", 10);

    assertEquals(1, g.getKeywords().size());
    assertEquals(10, g.getKeywords().get("Test"), 0.01);
  }

  @Test
  public void testGenreEquals(){
    Genre g = new Genre("Sci-fi");
    Genre g_copy = new Genre("Sci-fi");
    Genre g2 = new Genre("Action");
    Genre g3isg = g;

    assertEquals(true, g.equals(g));
    assertEquals(true, g.equals(g3isg));
    assertEquals(false, g.equals(g_copy));
    assertEquals(false, g.equals(g2));

    // lets say we modified our Genre, will values equals be what they should
    g.getKeywords().put("Test", 0);
    assertEquals(false, g.equals(g_copy));
    assertEquals(true, g.equals(g3isg));
  }

  @Test
  public void testGenreHashCode(){
    Genre g = new Genre("Sci-fi");
    Genre g_copy = new Genre("Sci-fi");
    Genre g2 = new Genre("Action");
    Genre g3isg = g;

    assertEquals(true, g.hashCode() == g3isg.hashCode());
    assertEquals(false, g.hashCode() == g2.hashCode());
    assertEquals(true, g.hashCode() == g_copy.hashCode());
  }
}