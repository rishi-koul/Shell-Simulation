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
import driver.Remove;
import driver.Directory;
import driver.FileSystemI;
import driver.MockFileSystem;

public class RemoveTest {
  
  FileSystemI root;
  Remove rm;
  ArrayList<String> args = new ArrayList<String>();
  
  @Before
  public void setUp() {  
    root = MockFileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    rm = new Remove();
  }
  
  @Test
  public void testRunCommand() {
    Directory d1 = new Directory();
    d1.setName("folder1");
    root.addDirectory(d1);
    root.setCurrentDirectory(d1);
    
    Boolean result = true;
    
    //remove subdir
    Directory d2 = new Directory();
    d2.setName("folder2");
    d1.addDirectory(d2);
    d2.setParentDir(d1);
    args.add("folder2");
    rm.runCommand(args.listIterator(), root);
    if(root.getCurrentDirectory().getAllSubDirectories().size() != 0) {
      result = false;
    }
    
    //remove subdir of subdir
    d1.addDirectory(d2);
    d2.setParentDir(d1);
    Directory d3 = new Directory();
    d3.setName("folder3");
    d2.addDirectory(d3);
    d3.setParentDir(d2);
    args.clear();
    args.add("folder2/folder3");
    rm.runCommand(args.listIterator(), root);
    if(d2.getAllSubDirectories().size() != 0) {
      result = false;
    }
    
    //attempted removal of direct ancestor of current directory
    d2.addDirectory(d3);
    d3.setParentDir(d2);
    root.setCurrentDirectory(d3);
    args.clear();
    args.add("/folder1/folder2");
    rm.runCommand(args.listIterator(), root);
    if(d1.getAllSubDirectories().size() != 1) {
      result = false;
    }
    
    //removal of indirect ancestor of current directory
    Directory d4 = new Directory();
    d4.setName("folder4");
    d1.addDirectory(d4);
    d4.setParentDir(d1);
    args.clear();
    args.add("/folder1/folder4");
    rm.runCommand(args.listIterator(), root);
    if(d1.getAllSubDirectories().size() != 1) {
      result = false;
    }
   
    assertEquals(true, result);
  }

}
