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
import java.util.Iterator;
import java.util.ListIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import driver.Directory;
import driver.FileSystemI;
import driver.List;
import driver.Man;
import driver.Search;
import driver.MockFileSystem;

public class SearchTest {
  
  /**
   * Stores the root of the file system
   */
  FileSystemI root;
  /**
   * Instance of Search class
   */
  Search search;
  /**
   * List to input arguments into runCommand
   */
  ArrayList<String> args = new ArrayList<String>();
  /**
   * 
   * This func initializes root and sets the location of Commands instance to 
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
    
    search = new Search();
  }
  /**
   * 
   * This func tests basic functionality of search and if it returns an empty
   * string if error is encountered
   */  
  @Test
  public void testRunCommand() {
    Directory d1 = new Directory();
    d1.setName("d1");
    root.addDirectory(d1);
    
    Directory d2 = new Directory();
    d2.setName("d2");
    root.addDirectory(d2);
    
    assertEquals("",search.runCommand(args.listIterator(),root));

  }
  /**
   * This checks if full path given is recognized as full and vice versa
   */
  @Test
  public void testCheckForFullPath() {

    boolean result = search.checkForFullPath("not/full/path");

    assertEquals(false, result);
  }
  /**
   * This func checks if string entered is only f or d  and if it is 
   * case sensitive
   */
  @Test
  public void testCheckType() {

    boolean result = search.checkType("not/full/path");
    assertEquals(false, result);
    result = search.checkType("f");
    assertEquals(true, result);
    result = search.checkType("D");
    assertEquals(false, result);
  }
  /**
   * This func checks if quotations are recognized correctly 
   */
  @Test
  public void testCheckExpression() {

    boolean result = search.checkExpression("not/full/path");
    assertEquals(false, result);
    result = search.checkExpression("\"h\"");
    assertEquals(true, result);

  }
  /**
   * This func checks if only paths are extracted from arguments correctly
   */
  @Test
  public void testDecodeInput() {
    
    args.add("/");
    args.add("d1");
    args.add("-type");
    args.add("d");
    args.add("-name");
    args.add("\"h\"");
    ListIterator<String> list = args.listIterator();
    ArrayList<String> result = search.decodeInput(list);
    ArrayList<String> answer = new ArrayList<String>();
    answer.add("/");
    answer.add("d1");
    assertEquals(answer.toString(), result.toString());
  }
  
}
