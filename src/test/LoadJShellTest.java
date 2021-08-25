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

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import driver.File;
import driver.History;
import driver.LoadJShell;
import driver.MockFileSystem;


public class LoadJShellTest {
  MockFileSystem root; //  A variable that stores the root of the MockFileSystem
  LoadJShell LJ; // A variable that will store an instance of LoadJShell class
  History hs; //A variable that will store an instance of History class
  String path; // a variable to store path
  ListIterator<String> list; //A iterator to iterate arrlist 
  ArrayList<String> arrlist = new ArrayList<String>(); // A list to store the arguments

  /**
   * Setup testing
   * @return void
   **/
  @Before
  public void setUp() throws Exception {
    LJ = new LoadJShell();
    root = MockFileSystem.getInstance();
    hs = new History();
    path =  FileLoader.run("src/test/LoadJShellTest.txt");
    History.cmdhistory.add("loadJShell " + path);
  }

  /**
   * Empty the argument list and history for testing other stuff
   * @return void
   **/
  @After
  public void tearDown() throws Exception {
    arrlist.clear();
    History.cmdhistory.clear();
   
  }

  /**
   * 
   * Test LJ basic functionality
   * @return void
   **/
  @Test
  public void testRunCommand() {
    arrlist.add(path);
    list = arrlist.listIterator();
    assertEquals("", LJ.runCommand(list,root));
    assertEquals("main",root.getCurrentDirectory().getName());
    assertEquals("/",root.getBaseDirectory().getName());
    
    ArrayList<File> file = root.getBaseDirectory().getAllSubFiles();
    assertEquals("test1",file.get(0).getName());
    assertEquals("hello",file.get(0).getcontent());
   
    Stack<String> test = new Stack<String>();
    assertEquals(test,root.getMainStack());
  }
  
  
  /**
   * Test LJ with no arguments
   * @return void
   **/
  @Test
  public void testNoArg() {
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", LJ.runCommand(list,root));
  }
  
  /**
   * Test LJ with invalidFilename
   * @return void
   **/
  @Test
  public void testInvalidFileName() {
    arrlist.add("$@*!!(FileName");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", LJ.runCommand(list,root));
  }
  
  /**
   * 
   * Test LJ with extra arg
   * @return void
   **/
  @Test
  public void testExcessArg() {
    arrlist.add("FileName");
    arrlist.add("FileName2");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", LJ.runCommand(list,root));
  }
  
  /**
   * 
   * Test LJ after the first cmd is typed
   * @return void
   **/
  @Test
  public void testloadJShellLate() {
    History.cmdhistory.add("loadJShell "+path);
    arrlist.add("FileName");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", LJ.runCommand(list,root));
  }


}
