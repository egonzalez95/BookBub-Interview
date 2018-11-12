package Challenge;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBook {

  @Test
  public void testBookGetTitle(){
    Book b = new Book("Test Title", "Test Description.");

    assertEquals("Test Title", b.getTitle());
  }

  @Test
  public void testBookGetDescription(){
    Book b = new Book("Test Title", "Test Description.");

    assertEquals("Test Description.", b.getDescription());
  }

  @Test
  public void testEquals(){
    Book b = new Book("Test Title", "Test Description.");
    Book b2 = new Book("Test Title", "Test Description.");
    Book b3isb = b;
    Book b4 = new Book("Test Title 2", "Test Description 2.");

    assertEquals(true, b.equals(b));
    assertEquals(true, b.equals(b2));
    assertEquals(true, b.equals(b3isb));
    assertEquals(false, b.equals(b4));
  }

  @Test
  public void testHashCode(){
    Book b = new Book("Test Title", "Test Description.");
    Book b2 = new Book("Test Title", "Test Description.");
    Book b3isb = b;
    Book b4 = new Book("Test Title 2", "Test Description 2.");

    assertEquals(true, b.hashCode() == b3isb.hashCode());
    assertEquals(true, b.hashCode() == b2.hashCode());
    assertEquals(false, b.hashCode() == b4.hashCode());
  }
}
