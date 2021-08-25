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
import driver.Directory;
import driver.File;
import driver.FileSystem;
import driver.FileSystemI;
import driver.Redirection;

public class RedirectionTest {

  /**
   * Stores the base of the file system
   */
  
  FileSystemI root;
  
  /**
   * instance of the redirection class
   */
  
  Redirection redir;
  
  /**
   * Array that will contain the user input
   */
  
  ArrayList<String> arr = new ArrayList<String>();
  
  /**
   * this func sets up the root
   */
  @Before
  public void setUp() {  
    root = FileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    redir = new Redirection();
  }
  
  /**
   * tests if > or >> are not surrounded then also redirection takes place
   */
  
  @Test
  public void testDealWithNoSpaces() {
    
    String result = Redirection.dealWithNoSpaces("ls>fileName");
    
    assertEquals("ls > fileName", result);
  }
  
  
  /**
   * tests if the class can recogonize if a list contains redirection
   */
  @Test
  public void testCheckRedirection() {  
    arr.add("ls");
    arr.add(">");
    arr.add("fileName");
    Boolean result = redir.checkRedirection(arr.listIterator());
    
    assertEquals(true, result);
  }
  
  /**
   * tests if the class can remove the redirection part from the list if its present there
   */
  
  @Test
  public void testRmvRedirection() {
    ArrayList<String> withoutRedirection = new ArrayList<String>();
    withoutRedirection.add("ls");
    arr.add("ls");
    arr.add(">");
    arr.add("fileName");
    ListIterator<String> resultAsIterator = redir.rmvRedir(arr.listIterator());
    
    ArrayList<String> result = new ArrayList<String>();
    resultAsIterator.forEachRemaining(result :: add);
    assertEquals(withoutRedirection, result);
  }
  
  /**
   * tests if the class creates files and adds data for when path is relative
   */
  @Test
  public void testPerformRedir() {
    Directory dir = new Directory();
    dir.setName("a");
    root.addDirectory(dir);
    ArrayList<String> withoutRedirection = new ArrayList<String>();
    withoutRedirection.add("ls");
    arr.add("Content");
    arr.add(">");
    arr.add("a/name");
    
    
    redir.performRedir(arr.listIterator(), root);
    
    for(Directory d : root.getAllSubDirectories()) {
      if(d.getName().equals("a")) {
        for(File f : d.getAllSubFiles()) {
          if(f.getName().equals("name") && f.getcontent().equals("Content")) {
            assertTrue(true);
            return;
          }
        }
      }
    }
    fail();
  }
  
  /**
   * tests if the class creates files and adds data for when path is full
   */
  
  @Test
  public void testPerformRedirFullPath() {
    Directory dir = new Directory();
    dir.setName("a");
    root.addDirectory(dir);
    root.setCurrentDirectory(dir);
    ArrayList<String> withoutRedirection = new ArrayList<String>();
    withoutRedirection.add("ls");
    arr.add("Content");
    arr.add(">");
    arr.add("/a/name");
    
    
    redir.performRedir(arr.listIterator(), root);
        for(File f : root.getAllSubFiles()) {
          if(f.getName().equals("name") && f.getcontent().equals("Content")) {
            assertTrue(true);
            return;
          }
        }
    fail();
  }
}
