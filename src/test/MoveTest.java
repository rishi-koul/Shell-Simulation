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
import driver.File;
import driver.FileSystemI;
import driver.MockFileSystem;
import driver.Move;

public class MoveTest {
  
  FileSystemI root;
  Move mv;
  ArrayList<String> args = new ArrayList<String>();
  
  @Before
  public void setUp() {  
    root = MockFileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    mv = new Move();
  }
  
  @Test
  public void testRunCommand() {
    Directory d1 = new Directory();
    d1.setName("folder1");
    root.addDirectory(d1);
    root.setCurrentDirectory(d1);
    
    Boolean result = true;
    
    //move subdir to subdir
    Directory d2 = new Directory();
    d2.setName("folder2");
    d1.addDirectory(d2);
    d2.setParentDir(d1);
    Directory d3 = new Directory();
    d3.setName("folder3");
    d1.addDirectory(d3);
    d3.setParentDir(d1);
    args.add("folder3");
    args.add("folder2");
    mv.runCommand(args.listIterator(), root);
    if(!(d2.getAllSubDirectories().contains(d3) 
        && d1.getAllSubDirectories().size() ==1)) {
      result = false;
    }
    
    //move subdir to subdir of subdir
    Directory d4 = new Directory();
    d4.setName("folder4");
    d1.addDirectory(d4);
    d4.setParentDir(d1);
    args.clear();
    args.add("folder4");
    args.add("folder2/folder3");
    mv.runCommand(args.listIterator(), root);
    if(!(d3.getAllSubDirectories().contains(d4) 
        && d1.getAllSubDirectories().size() == 1)) {
      result = false;
    }
    
    //recursive move
    args.clear();
    args.add("folder2/folder3");
    args.add("/folder1");
    mv.runCommand(args.listIterator(), root);
    if(!(d3.getAllSubDirectories().contains(d4) 
        && d2.getAllSubDirectories().size() == 0
        && d1.getAllSubDirectories().contains(d3))){
      result = false;
    }
    
    //attempted move of direct ancestor of current directory
    root.setCurrentDirectory(d4);
    args.clear();
    args.add("/folder1/folder3");
    args.add("/folder1/folder2");
    mv.runCommand(args.listIterator(), root);
    if(d2.getAllSubDirectories().contains(d3)
        || !(d1.getAllSubDirectories().contains(d3))){
      result = false;
    }
    
    //attempted move of dir to subdir
    args.clear();
    args.add("/folder1/folder3");
    args.add("/folder1/folder3/folder4");
    mv.runCommand(args.listIterator(), root);
    if(d4.getAllSubDirectories().contains(d3)) {
      result = false;
    }
    
    //move dir to newpath
    args.clear();
    args.add("/folder1/folder2");
    args.add("/folder1/folder3/folder4/new_folder");
    mv.runCommand(args.listIterator(), root);
    if(d1.getAllSubDirectories().contains(d2)
        || !(d4.getAllSubDirectories().contains(d2))
        || !(d2.getName().equals("new_folder"))) {
      result = false;
    }
    
    //move file to dir
    File f1 = new File();
    f1.setName("file1");
    d1.addFile(f1);
    f1.setParentDir(d1);
    args.clear();
    args.add("/folder1/file1");
    args.add("/folder1/folder3");
    mv.runCommand(args.listIterator(), root);
    if(d1.getAllSubFiles().contains(f1)
        || !(d3.getAllSubFiles().contains(f1))) {
      result = false;
    }
    
    assertEquals(true, result);
    
  }

}
