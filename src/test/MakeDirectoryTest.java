
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
import org.junit.Before;
import org.junit.Test;
import driver.Directory;
import driver.FileSystem;
import driver.FileSystemI;
import driver.MakeDirectory;
import driver.MockFileSystem;
import driver.PushDirectory;

public class MakeDirectoryTest {
  
  /**
   * The root of the file system
   */
  FileSystemI root;
  
  /**
   * Instance of the Make Directory class
   */
  
  MakeDirectory mkdir;
  
  /**
   * Array that will contain the user input
   */
  
  ArrayList<String> arr = new ArrayList<String>();
  
  /**
   * The func sets up the root to the base and sets the location in MakeDirectory instance to root
   */
  @Before
  public void setUp() {  
    root = MockFileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    mkdir = new MakeDirectory();
    mkdir.location = root;
  }
  
  /**
   * This func tests if mkdir creates duplicate dir
   */
  @Test
  public void testCheckForDuplicates() {
    
    Directory dir = new Directory();
    dir.setName("dir1");
    root.addDirectory(dir);
    
    boolean result = mkdir.checkForDuplicates("dir1");
    
    assertEquals(true, result);
  }
  
  /**
   * This func tests if mkdir checks the dir name is valid or not
   */
  @Test
  public void testCheckForInvalidCmd() {
    
    ArrayList<String> arr = mkdir.checkForInvalidCmd("invalid$dirName");
    
    assertEquals("false", arr.get(0));
    assertEquals("$", arr.get(1));
  }
  
  /**
   * this func test if mkdir actually creates a dir or not at the CURRENT LOCATION
   */
  @Test
  public void testCreateDirectory() {
    
    mkdir.createDir("dir2");
    Directory dir = new Directory();
    dir.setName("dir2");
    
    for(Directory direc : root.getAllSubDirectories()) {
      if(direc.getName().equals("dir2")) {
        assertTrue(true);
        return;
      }
    }
    fail();
  }
  
  /**
   * This func check if mkdir diff bw a full and relative path
   */
  @Test
  public void testCheckForFullPath() {
    
    boolean result = mkdir.checkForFullPath("not/full/path");
    
    assertEquals(false, result);
  }
  
  /**
  * This func check if mkdir diff bw a full and relative path
  */
  @Test
  public void testCheckForRelativePath() {
    
    boolean result = mkdir.checkForRelativePath("is/relative/path");
    
    assertEquals(true, result);
  }
  
  /**
   * This func test if the relative or full path are passed properly
   */
  @Test 
  public void testParsePath() {
    
    mkdir.createDir("dir1");
    
    String path = "/dir1/dir2";
    
    mkdir.isFullPath = true;
    mkdir.parsePath(path);
    
    assertEquals("dir1", root.getCurrentDirectory().getName());
  }
  
  /**
   * this func test if dir is created at any location provided
   */
  @Test 
  public void testCreateFirstDir() {
    
    mkdir.isFullPath = true;
    Directory directory = new Directory();
    directory.setName("folder1_1");
    root.addDirectory(directory);
    
    mkdir.allDirectories.add("/folder1_1/folder2_1");
    mkdir.dirName = mkdir.allDirectories.get(0);
    
    mkdir.createFirstDir(root.getBaseDirectory(), 0);
    
    for(Directory dir : root.getAllSubDirectories()) {
      if(dir.getName().equals("folder1_1"))
      {
        root.setCurrentDirectory(dir);
        System.out.println("This worked");
      }
        
    }
    
    for(Directory dir : root.getAllSubDirectories()) {
      if(dir.getName().equals("folder2_1"))
      {
        assertTrue(true);
        return;
      }   
    }
    fail();
  }
  
  /**
   * this func tests the final runCommand incorporating everything
   */
  @Test 
  public void testRunCommand() {
    
    Directory directory = new Directory();
    directory.setName("folder1_1");
    root.addDirectory(directory);
    root.setCurrentDirectory(directory);
    
    arr.add("folder2_1");
    
    arr.add("/folder1_1/folder2_1/folder3_1");
    
    arr.add("folder1_$");
    
    
    mkdir.runCommand(arr.listIterator(), root);
    boolean result = false;
    for(Directory dir : root.getAllSubDirectories()) {
      if(dir.getName().equals("folder2_1")) {
        root.setCurrentDirectory(dir);
        for(Directory d : root.getAllSubDirectories()) {
          if(d.getName().equals("folder3_1")) {
            root.setCurrentDirectory(d);
            result = true;
          }
                   
        }
      }
    }
    
    root.setCurrentDirectory(directory);
    for(Directory dir : root.getAllSubDirectories()) {
      if(dir.getName().equals("folder1_$"))
        result = false;
    }
    
    assertEquals(true, result);
  }
  
}
