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
import driver.FileSystem;
import driver.FileSystemI;
import driver.PushDirectory;

public class PushDirectoryTest {

  /**
   * Stores the root of the file system
   */
  
  FileSystemI root;
  
  /**
   * instance of the PushDirectory Class
   */
  
  PushDirectory pushd;
  
  /**
   * Array that will contain the user input
   */
  
  ArrayList<String> arr = new ArrayList<String>();
  
  /**
   * the func sets up the root and sets the location of instance of pushd to root
   */
  @Before
  public void setUp() {  
    root = FileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    pushd = new PushDirectory();
    pushd.location = root;
    pushd.stack = root.getMainStack();
  }
  
  /**
   * this func tests if pushd can recognize if a dir is subDiectory of the curr dir
   */
  @Test
  public void testCheckIfSubDirectory() {
    
    Directory dir = new Directory();
    dir.setName("dir");
    root.addDirectory(dir);
    boolean result = pushd.checkIfSubDirectory("dir");
    assertEquals(true, result);
    
  }
  
  /**
   * this func tests if pushd can get the address of a subDirectory
   */
  @Test
  public void testGetAddressOfDir() {
    
    Directory dir = new Directory();
    dir.setName("dir");
    root.addDirectory(dir);
    
    Directory result = pushd.getAddressOfDir("dir");
    assertEquals(dir, result);
    
  }
   
  /**
   * this func tests if pushd actually adds to the main stack
   */
  @Test
  public void testAddToStack() {
    
    Stack<String> stack = new Stack<String>();
    String path = "dir1";
    pushd.isFullPath = false;
    pushd.addToStack(path);
    path = "dir2";
    pushd.isFullPath = false;
    pushd.addToStack(path);
    
    stack.push("/dir1/dir2");
    stack.push("/");
    stack.push("/dir1");
    stack.push("/dir1/dir2");
    stack.push("/dir1");
    stack.push("/dir2");
    
    
    assertEquals(stack, root.getMainStack());
  }
  
  /**
   * tests is pushd can diff bw full paths 
   */
  @Test
  public void testCheckForFullPath() {
    String notFullPath = "a/b/b";
    
    assertEquals(false, pushd.checkForFullPath(notFullPath));
  }
  
  /**
   * tests if pushd can properly pass a path and move to the correct dir
   */
  @Test
  public void testParsePath() {
    
    Directory dir = new Directory();
    dir.setName("dir");
    root.addDirectory(dir);
    
    String path = "/dir/check";
   pushd.parsePath(path, false);
   
    assertEquals(dir, root.getCurrentDirectory());
  }
  
  /**
   * tests if pushd . works properly
   */
  @Test
  public void testDealWithDot() {
    
    pushd.isFullPath = true;
    Stack<String> stack = new Stack<String>();
    
    Directory dir1 = new Directory();
    dir1.setName("dir1");
    root.addDirectory(dir1);
    root.setCurrentDirectory(dir1);
    
    Directory dir2 = new Directory();
    dir2.setName("dir2");
    root.addDirectory(dir2);
    root.setCurrentDirectory(dir2);
    pushd.original = root.getCurrentDirectory();
    
    pushd.dealWithDot();
    
    root.setCurrentDirectory(root.getBaseDirectory());
    
    pushd.original = root.getCurrentDirectory();
    pushd.dealWithDot();
    
    
    stack.push("/dir1/dir2");
    stack.push("/");
    stack.push("/dir1");
    stack.push("/dir1/dir2");
    stack.push("/dir1");
    stack.push("/dir1/dir2");
    stack.push("/");
    stack.push("/");
    assertEquals(stack, root.getMainStack());
  }

  /**
   * tests if pushd .. works properly
   */
  @Test
  public void testDealWithDoubleDot() {
    
    pushd.isFullPath = true;
    Stack<String> stack = new Stack<String>();
    
    Directory dir1 = new Directory();
    dir1.setName("dir1");
    root.addDirectory(dir1);
    root.setCurrentDirectory(dir1);
    
    Directory dir2 = new Directory();
    dir2.setName("dir2");
    root.addDirectory(dir2);
    root.setCurrentDirectory(dir2);
    pushd.original = root.getCurrentDirectory();
    
    pushd.dealWithDoubleDot();
    
    root.setCurrentDirectory(root.getBaseDirectory());
    
    pushd.original = root.getCurrentDirectory();
    pushd.dealWithDoubleDot();
    
    stack.push("/dir1/dir2");
    stack.push("/dir1/");
    assertEquals(stack, root.getMainStack());
  }
  
  /**
   * tests if pushd parses the path and pushes the dir into the stack
   * also check if its gives error msges for errors.
   */
  @Test
  public void testPushDir() {
    
    pushd.isFullPath = false;
    Stack<String> stack = new Stack<String>();
    
    Directory dir1 = new Directory();
    dir1.setName("dir1");
    root.addDirectory(dir1);
    root.setCurrentDirectory(dir1);
    
    Directory dir2 = new Directory();
    dir2.setName("dir2");
    root.addDirectory(dir2);
    
    root.setCurrentDirectory(root.getBaseDirectory());
    pushd.original = root.getCurrentDirectory();
    
    
    pushd.pushDir("dir1/dir2", "dir1/dir2");
    
    root.setCurrentDirectory(dir1);
    pushd.original = root.getCurrentDirectory();
    
    pushd.pushDir("dir2", "dir2");
    
    stack.push("/dir1/dir2");
    stack.push("/");
    stack.push("/dir1");
    stack.push("/dir1/dir2");
    assertEquals(stack, root.getMainStack());
  }
}
