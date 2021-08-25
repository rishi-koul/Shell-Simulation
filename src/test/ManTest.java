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
import driver.FileSystemI;
import driver.Man;
import driver.List;
import driver.MockFileSystem;

public class ManTest {
  /**
   * Stores the root of the file system
   */
  FileSystemI root;
  /**
   * Instance of Man class
   */
  Man man;
  /**
   * List to input arguments into runCommand
   */
  ArrayList<String> args = new ArrayList<String>();
  /**
   * Instance of List class to test man
   */
  List ls;
  /**
   * 
   * This func initialize root and sets the location of Commands instance to 
   * the root
   */
  @Before
  public void setUp() {  
    root = MockFileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    man = new Man();
  }
  /**
   * 
   * This func tests the RunCommand for multiple arguments and checks if it 
   * enters other classes correctly
   */
  @Test
  public void testRunCommand() {
    
    args.add("");
    args.add("");
    assertEquals("",man.runCommand(args.listIterator(),root)); 
    
    
    args.clear();
    args.add("ls");
    ls = new List();
    String result= ls.getMan();
    assertEquals(result+"\n",man.runCommand(args.listIterator(),root));
    
  }
  
}
