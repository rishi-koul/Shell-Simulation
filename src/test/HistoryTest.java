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
import driver.FileSystem;
import driver.History;

public class HistoryTest {
  
  History his; // A variable that will store an instance of History class
  ListIterator<String> list; //A iterator to iterate arrlist 
  ArrayList<String> arrlist = new ArrayList<String>(); // A list to store the arguments

  /**
   * Setup testing
   * @return void
   **/
  @Before
  public void setUp() {
    his = new History();
  }

  /**
   * Resets the argument arrlist
   * @return void
   **/
  @After
  public void tearDown() {
   History.cmdhistory.clear();
  }

  /**
   * Test empty History with no arguments
   * @return void
   **/
  @Test
  public void testNoHistoryNoArguments() {
    FileSystem fs =  FileSystem.getInstance();
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", his.runCommand(list,fs));
  }

  /**
   * Test History with no arguments
   * @return void
   **/
  @Test
  public void testHistoryNoArguments() {
    FileSystem fs =  FileSystem.getInstance();
    ListIterator<String> list = arrlist.listIterator();
    History.cmdhistory.add("mkdir");
    History.cmdhistory.add("ls");
    History.cmdhistory.add("cd");
    History.cmdhistory.add("history");
    assertEquals("1. mkdir\n" + 
        "2. ls\n" + 
        "3. cd\n" + 
        "4. history\n", his.runCommand(list,fs));
    // prints appropriate output on the console and returns empty string
  }

  /**
   * Test History with arguments
   * @return void
   **/
  @Test
  public void testHistoryWithArguments() {
    FileSystem fs =  FileSystem.getInstance();
    History.cmdhistory.add("mkdir food");
    History.cmdhistory.add("pwd");
    History.cmdhistory.add("history 2");
    arrlist.add("2");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("2. pwd\n3. history 2\n", his.runCommand(list,fs));
  }


}
