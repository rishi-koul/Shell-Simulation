// **********************************************************

// Assignment2:

// Student1:
// UTORID user_name: pandapri
// UT Student #: 1005970914
// Author: Pritish Panda
//
// Student2:
// UTORID user_name: koulrish
// UT Student #: 1005697220
// Author: Rishi Koul
//
// Student3:
// UTORID user_name: xiaoyi10
// UT Student #: 1006291175
// Author: Yihai Xiao
//
// Student4:
// UTORID user_name: hameed10
// UT Student #: 1004942477
// Author: Sarah Hameed
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import driver.Concatenate;
import driver.MockFileSystem;

public class ConcatenateTest {

  MockFileSystem root; //  A variable that stores the root of the MockFileSystem
  Concatenate cat;// A variable that will store an instance of Concatenate class
  ListIterator<String> list; //A iterator to iterate arrlist 
  ArrayList<String> arrlist = new ArrayList<String>(); // A list to store the arguments

  /**
   * Setup testing
   * @return void
   **/
  @Before
  public void setUp() {
    cat = new Concatenate();
    root = MockFileSystem.getInstance();
  }
  /**
   * Resets the argument arrlist
   * @return void
   **/
  @After
  public void tearDown() {
    arrlist.clear();
  }
  
  /**
   * Test cat with no arguments
   * @return void
   **/
  @Test
  public void testNoArg() throws Exception {
    ListIterator<String> list = arrlist.listIterator();

    assertEquals("", cat.runCommand(list, root));
  }

  /**
   * Test cat with one arguments
   * @return void
   **/
  @Test
  public void testOneArg() throws Exception {
    arrlist.add("/some/valid/path");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("hello", cat.runCommand(list, root));
  }

  /**
   * Test cat with two arguments
   * @return void
   **/
  @Test
  public void testTwoArg() throws Exception {
    arrlist.add("/some/valid/path");
    arrlist.add("/some/valid1/path");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("hello\n\n\nbye", cat.runCommand(list, root));
  }

  /**
   * Test cat with three arguments
   * @return void
   **/
  @Test
  public void testThreeArg() throws Exception {
    arrlist.add("/some/valid/path");
    arrlist.add("/some/valid1/path");
    arrlist.add("/some/valid2/path");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("hello\n\n\nbye\n\n\nhi", cat.runCommand(list, root));
  }

  /**
   * Test cat with an argument that contains dir name
   * @return void
   **/
  @Test
  public void testBadArg1() throws Exception {
    arrlist.add("dirName");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", cat.runCommand(list, root));
  }

  /**
   * Test cat with a non existing file
   * @return void
   **/
  @Test
  public void testBadArg2() throws Exception {
    arrlist.add("noFile");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", cat.runCommand(list, root));
  }

}


