package Challenge;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestResult {

  @Test
  public void testResultGetScore() {
    Result r = new Result("Genre 1", 30);
    assertEquals(30, r.getScore());
  }

  @Test
  public void testResultGetGenre() {
    Result r = new Result("Genre 1", 30);
    assertEquals("Genre 1", r.getGenre());
  }

  @Test
  public void testResultSetScore() {
    Result r = new Result("Genre 1", 30);
    r.setScore(0);
    assertEquals(0, r.getScore());
  }

  @Test
  public void testResultEquals() {
    Result r = new Result("Genre 1", 30);
    Result r2 = new Result("Genre 2", 10);
    Result r3isr = r;
    Result r4 = new Result("Genre 1", 30);


    assertEquals(true, r.equals(r));
    assertEquals(false, r.equals(r2));
    assertEquals(true, r.equals(r3isr));
    assertEquals(true, r.equals(r4));

    // random test for another object case
    assertEquals(false, r.equals(new Book("Test", "Test")));
  }

  @Test
  public void testResultHashCode() {
    Result r = new Result("Genre 1", 30);
    Result r2 = new Result("Genre 2", 10);
    Result r3isr = r;
    Result r4 = new Result("Genre 1", 30);

    assertEquals(true, r.hashCode() == r4.hashCode());
    assertEquals(true, r.hashCode() == r3isr.hashCode());
    assertEquals(false, r.hashCode() == r2.hashCode());
  }

  @Test
  public void testResultCompareTo() {
    Result r = new Result("Genre 1", 30);
    Result r2 = new Result("Genre 2", 30);
    Result r3 = new Result("Genre 3", 29);
    Result r4 = new Result("Genre 4", 31);

    assertEquals(0, r.compareTo(r));
    assertEquals(0, r.compareTo(r2));
    assertEquals(1, r.compareTo(r3));
    assertEquals(-1, r.compareTo(r4));
  }
}
