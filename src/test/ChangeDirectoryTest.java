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
import driver.FileSystemI;
import driver.ChangeDirectory;
import driver.MockFileSystem;

public class ChangeDirectoryTest {
  
  FileSystemI root;
  ChangeDirectory cd;
  ArrayList<String> args = new ArrayList<String>();
  
  @Before
  public void setUp() {  
    root = MockFileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    cd = new ChangeDirectory();
  }
  
  @Test
  public void testRunCommand() {
    Directory d1 = new Directory();
    d1.setName("folder1");
    root.addDirectory(d1);
    root.setCurrentDirectory(d1);
    
    Boolean result = true;
    
    //enter subdir 
    Directory d2 = new Directory();
    d2.setName("folder1");
    d1.addDirectory(d2);
    d2.setParentDir(d1);
    args.add("folder1");
    cd.runCommand(args.listIterator(), root);
    if(!(root.getCurrentDirectory().equals(d2))) {
      result = false;
    }
    
    //enter non-existent subdir
    args.clear();
    args.add("non-existant");
    cd.runCommand(args.listIterator(), root);
    if(!(root.getCurrentDirectory().equals(d2))) {
      result = false;
    }
    
    //enter subdir of subdir
    Directory d3 = new Directory();
    d3.setName("folder3");
    Directory d4 = new Directory();
    d4.setName("folder4");
    d2.addDirectory(d3);
    d3.setParentDir(d2);
    d3.addDirectory(d4);
    d4.setParentDir(d3);
    args.clear();
    args.add("folder3/folder4");
    cd.runCommand(args.listIterator(), root);
    System.out.println(root.getCurrentDirectory().getName());
    if(!(root.getCurrentDirectory().equals(d4))) {
      result = false;
    }
    
    //enter prev dir
    args.clear();
    args.add("..");
    cd.runCommand(args.listIterator(), root);
    if(!(root.getCurrentDirectory().equals(d3))) {
      result = false;
    }
    
    //enter current dir
    args.clear();
    args.add(".");
    cd.runCommand(args.listIterator(), root);
    if(!(root.getCurrentDirectory().equals(d3))) {
      result = false;
    }
    
    //enter from full path
    args.clear();
    args.add("/folder1");
    cd.runCommand(args.listIterator(), root);
    if(!(root.getCurrentDirectory().equals(d1))) {
      result = false;
    }
    
    assertEquals(true, result);
    
  }

  @Test
  public void testContainsOnly() {
    assertEquals(cd.containsOnly("111", "1"), true);
    assertEquals(cd.containsOnly("112", "1"), false);
  }

}
