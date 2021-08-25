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
import driver.List;
import driver.Tree;
import driver.MockFileSystem;

public class TreeTest {
  /**
   * Stores the root of the file system
   */
  FileSystemI root;
  /**
   * Instance of List class
   */
  Tree tree;
  /**
   * List to input arguments into runCommand
   */
  ArrayList<String> args = new ArrayList<String>();
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
    
    tree = new Tree();
  }
  /**
   * 
   * This func tests the recursive function and if output is correctly formatted
   */
  @Test
  public void testRunCommand() {
    Directory d1 = new Directory();
    d1.setName("d1");
    root.addDirectory(d1);
    
    Directory d2 = new Directory();
    d2.setName("d2");
    root.addDirectory(d2);
    
    assertEquals("/\n\td1\n\td2\n",tree.runCommand(args.listIterator(),root));

  }
  /**
   * 
   * This func tests if the indents aligns with depth as expected
   */
  @Test
  public void testSpacingFormat() {

    String result = tree.spacingFormat(2);

    assertEquals("\t\t", result);
  }
  

}
