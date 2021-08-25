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
import java.util.Stack;
import org.junit.Test;

import org.junit.Before;
import driver.Directory;
import driver.MockFileSystem;
import driver.FileSystemI;
import driver.PopDirectory;

public class PopDirectoryTest {

  /**
   * Stores the root of the file system
   */
  
  FileSystemI root;
  
  /**
   * Instance of PopDirectory class
   */
  
  PopDirectory popd;
  
  /**
   * Array that will contain the user input
   */
  
  ArrayList<String> arr = new ArrayList<String>();
  
  /**
   * This func initialize root and sets the location of PopDirection instance to the root
   */
  @Before
  public void setUp() {  
    root = MockFileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    popd = new PopDirectory();
    popd.location = root;
    popd.stack = root.getMainStack();
  }
  
  /**
   * this func tests if pushd can get the address of a sub directory
   */
  @Test
  public void testGetAddressOfDir() {
    
    Directory dir = new Directory();
    dir.setName("dir");
    root.addDirectory(dir);
    
    Directory result = popd.getAddressOfDir("dir");
    assertEquals(dir, result);
    
  }
   
  /**
   * This func test the actual runCommand i.e checks if the dir is removed from the stack 
   */
  @Test
  public void testRunCommand() {
    
    Stack<String> stack = new Stack<String>();
    
    Directory dir1 = new Directory();
    dir1.setName("dir1");
    root.addDirectory(dir1);
    root.setCurrentDirectory(dir1);
    
    Directory dir2 = new Directory();
    dir2.setName("dir2");
    root.addDirectory(dir2);
    root.setCurrentDirectory(dir2);
 
    stack.add("/dir1/dir2");
    stack.add("/dir1");
    stack.add("/");
    root.setMainStack(stack);
    
    
    popd.runCommand(arr.listIterator(), root);
    stack.pop();
    assertEquals(stack, root.getMainStack());
//    assertEquals("dir1", root.getCurrentDirectory().getName());
  }
  
  /**
   * This func test the actual runCommand i.e checks after the dir is removed we go in th correct dir
   */
  
  @Test
  public void testIfInRightDirectory() {
    testRunCommand();
    assertEquals("dir1", root.getCurrentDirectory().getName());
  }
}
