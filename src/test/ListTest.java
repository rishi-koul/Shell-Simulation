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
import driver.List;
import driver.MockFileSystem;
import driver.Directory;
import driver.File;
import driver.FileSystemI;

public class ListTest {
  /**
   * Stores the root of the file system
   */
  FileSystemI root;
  /**
   * Instance of List class
   */
  List ls;
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

    ls = new List();
  }
  /**
   * This func tests the basic functionality of the runCommand i.e 
   * is correct contents are printed
   */
  @Test
  public void testRunCommand() {
    Directory d1 = new Directory();
    d1.setName("d1");
    root.addDirectory(d1);
    
    Directory d2 = new Directory();
    d2.setName("d2");
    root.addDirectory(d2);
    
    
    
    assertEquals("d1\nd2\n",ls.runCommand(args.listIterator(),root));

  }
  /**
   * This checks if full path given is recognized as full and vice versa
   */
  @Test
  public void testCheckForFullPath() {

    boolean result = ls.checkForFullPath("not/full/path");
    assertEquals(false, result);
  }
  /**
   * This func checks if the last element of the stack is extracted for all 
   * types of path
   */
  @Test
  public void testTraverseFullPath() {

    assertEquals("path", ls.traverseFullPath("not/full/path"));
    assertEquals("path", ls.traverseFullPath("/not/full/path"));
  }
  /**
   * This func tests if the contents of a dir are 
   * correct if no arguments are given
   */
  @Test
  public void testPrintCurrentDir() {

    Directory d1 = new Directory();
    d1.setName("d1");
    root.addDirectory(d1);
    
    Directory d2 = new Directory();
    d2.setName("d2");
    root.addDirectory(d2);
    
    File f1 = new File();
    f1.setName("f1");
    root.addFile(f1);

    assertEquals("d1\nd2\nf1\n",ls.runCommand(args.listIterator(),root));
  }
  /**
   * This func tests if error is detected if path does not exist
   */
  @Test
  public void testPathChecker() {

    boolean result = ls.pathChecker(root,"not/full/path");
    assertEquals(false, result);
  }
  /**
   * This func tests if recursion is occurring as expected
   */
  @Test
  public void testRecursiveTraverse() {


    Directory d1 = new Directory();
    d1.setName("d1");
    root.addDirectory(d1);
    
    args.add("-R");
    
    assertEquals("/ : d1    ||   \nd1 : \n", ls.runCommand(args.listIterator(),root));
  }
  /**
   * This func tests if error is detected in Recursion if path does not exist
   */
  @Test
  public void testRecursivePath() {
    
    args.add("-R");
    args.add("hello");
    assertEquals("",ls.runCommand(args.listIterator(),root));
    
  }
}
