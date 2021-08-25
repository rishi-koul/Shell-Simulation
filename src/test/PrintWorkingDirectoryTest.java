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
import org.junit.Test;

import org.junit.Before;
import driver.Directory;
import driver.FileSystemI;
import driver.MockFileSystem;
import driver.PrintWorkingDirectory;

public class PrintWorkingDirectoryTest {

  /**
   * stores the root of the file system
   */
  FileSystemI root;
  
  /**
   * Instance of the PrintWorkingDirectory class
   */
  
  PrintWorkingDirectory pwd;
  
  /**
   * Array that will contain the user input
   */
  
  ArrayList<String> arr = new ArrayList<String>();
  
  /**
   * This func sets up the root 
   */
  @Before
  public void setUp() {  
    root = MockFileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    pwd = new PrintWorkingDirectory();
   
  }
    
  
  /**
   * this func test pwd at the base
   */
  @Test
  public void testPrintWorkingDirectoryAtBase() {
    
    String currentWorkingDir = pwd.runCommand(arr.listIterator(), root);
    
    assertEquals("/\n", currentWorkingDir);
    
  }
  
  /**
   * this func test pwd if we are present in a subdir of base
   */
  @Test
  public void testPrintWorkingDirectoryAtDepth1() {
    
    Directory dir = new Directory();
    dir.setName("dir1");
    root.addDirectory(dir);
    root.setCurrentDirectory(dir);
    
    String currentWorkingDir = pwd.runCommand(arr.listIterator(), root);
    
    assertEquals("/dir1\n", currentWorkingDir);
    
  }
  
  
  /**
   * this func test pwd if we are present in a subdir of subdir of base
   */
  
  @Test
  public void testPrintWorkingDirectoryAtDepth2() {
    
    Directory dir1 = new Directory();
    dir1.setName("dir1");
    root.addDirectory(dir1);
    root.setCurrentDirectory(dir1);
    
    Directory dir2 = new Directory();
    dir2.setName("dir2");
    root.addDirectory(dir2);
    root.setCurrentDirectory(dir2);
    
    String currentWorkingDir = pwd.runCommand(arr.listIterator(), root);
    
    assertEquals("/dir1/dir2\n", currentWorkingDir);
    
  }

}
