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
import driver.Directory;
import driver.File;
import driver.History;
import driver.MockFileSystem;
import driver.SaveJShell;



public class SaveJShellTest {

  
  MockFileSystem root; //  A variable that stores the root of the MockFileSystem
  Directory dir;// A variable that will store an instance of Dir class
  SaveJShell SJ; // A variable that will store an instance of SaveJShell class
  ListIterator<String> list; //A iterator to iterate arrlist 
  ArrayList<String> arrlist = new ArrayList<String>(); // A list to store the arguments
  
  
  /**
   * Setup testing
   * @return void
   **/
  @Before
  public void setUp() throws Exception {
    SJ = new SaveJShell();
    root = MockFileSystem.getInstance();
    dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
  }

  /**
   * Empty the argument list  for testing other stuff
   * @return void
   **/
  @After
  public void tearDown() throws Exception {
    arrlist.clear();
  }

  /**
   * 
   * Test SJ basic functionality
   * @return void
   **/
  @Test
  public void testRunCommand() {
 
    Directory check =  new Directory();
    dir.addDirectory(check);
    check.setName("main");
    History.cmdhistory.add("mkdir main");
    
    File test1 = new File();
    test1.setName("test1");
    test1.setContent("hello");
    root.addFile(test1);
    History.cmdhistory.add("echo \"hello\" > test1");
    
    root.setCurrentDirectory(check);
    History.cmdhistory.add("cd main");
    
    String path =  FileLoader.run("src/test/LoadJShellTest.txt");
    arrlist.add(path);
    ListIterator<String> list = arrlist.listIterator();
     
    History.cmdhistory.add("saveJShell " +path);
    
    assertEquals("", SJ.runCommand(list,root));
    
    History.cmdhistory.clear();
    LoadJShellTest check1 = new LoadJShellTest();
    
    try {
      check1.setUp();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    check1.testRunCommand();
  }
  
  /**
   * Test SJ with no arguments
   * @return void
   **/
  @Test
  public void testNoArg() {
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", SJ.runCommand(list,root));
  }
  
  /**
   * Test SJ with invalidFilename
   * @return void
   **/
  @Test
  public void testInvalidFileName() {
    arrlist.add("$@*!!(FileName");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", SJ.runCommand(list,root));
  }
  
  /**
   * 
   * Test SJ with extra arg
   * @return void
   **/
  @Test
  public void testExcessArg() {
    arrlist.add("FileName");
    arrlist.add("FileName2");
    ListIterator<String> list = arrlist.listIterator();
    assertEquals("", SJ.runCommand(list,root));
  }

}
