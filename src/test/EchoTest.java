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
import org.junit.Before;
import org.junit.Test;
import driver.Echo;
import driver.History;
import driver.MockFileSystem;

public class EchoTest {

  Echo echo; // A variable that will store an instance of Echo class
  History his= new History(); // A variable that will store an instance of History class
  ArrayList<String> arrlist = new ArrayList<String>();  //A iterator to iterate arrlist 
  ListIterator<String> list = arrlist.listIterator();  // A list to store the arguments
  MockFileSystem root; //  A variable that stores the root of the MockFileSystem

  /**
   * Setup testing
   * @return void
   **/
  @Before
  public void setUp() {
    echo = new Echo();
    root = MockFileSystem.getInstance();
  }

  /**
   * Test Echo with Valid arguments
   * @return void
   **/
  @Test
  public void testArgs() throws Exception {
    History.cmdhistory.add("echo \"hello\"");
    assertEquals("hello", echo.runCommand(list,root));
    History.cmdhistory.add("echo \"!yello wolrd\"");
    assertEquals("!yello wolrd", echo.runCommand(list,root));
    History.cmdhistory.add("echo   \"hi!\"");
    assertEquals("hi!", echo.runCommand(list,root));
    History.cmdhistory.add("echo \" hello   hi!\"");
    assertEquals(" hello   hi!", echo.runCommand(list,root));
  }
  
  /**
   * Test Echo with two arguments
   * @return void
   **/
  @Test
  public void testExtraArg() throws Exception {
    History.cmdhistory.add("echo \"hello\" \"ey\"");
    assertEquals("hello\" \"ey", echo.runCommand(list,root));
  }
  
  /**
   * Test Echo with no arguments
   * @return void
   **/
  @Test
  public void testNoArg() {
    History.cmdhistory.add("echo");
    assertEquals("", echo.runCommand(list,root));
  }
  
  /**
   * Test Echo with invalid quotation marks in filename
   * @return void
   **/
  @Test
  public void testNoquotation() {
    History.cmdhistory.add("echo \"hello");
    assertEquals("", echo.runCommand(list,root));
  }
  
  /**
   * Test Echo with invalid quotation marks in filename
   * @return void
   **/
  @Test
  public void testNoquotation2() {
    History.cmdhistory.add("echo \"");
    assertEquals("", echo.runCommand(list,root));
  }


}
