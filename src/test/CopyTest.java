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
import driver.Copy;

public class CopyTest {
  
  FileSystemI root;
  Copy cp;
  ArrayList<String> args = new ArrayList<String>();
  
  @Before
  public void setUp() {  
    root = MockFileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    
    cp = new Copy();
  }
  
  @Test
  public void testRunCommand() {
    Directory d1 = new Directory();
    d1.setName("folder1");
    root.addDirectory(d1);
    root.setCurrentDirectory(d1);
    
    Boolean result = true;
    
    //Copy subdir to subdir
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
    cp.runCommand(args.listIterator(), root);
    if(!(d2.getAllSubDirectories().get(0).getName().equals("folder3")
        && !(d2.getAllSubDirectories().contains(d3))
        && d1.getAllSubDirectories().contains(d3))) {
      result = false;
    }
    
    //recursive copy
    args.clear();
    args.add("folder2");
    args.add("folder3");
    cp.runCommand(args.listIterator(), root);
    if(!(d3.getAllSubDirectories().get(0).getName().equals("folder2"))
        || !(d3.getAllSubDirectories().get(0).getAllSubDirectories().get(0).
            getName().equals("folder3"))) {
      result = false;
    }
    
    //attempted copy of dir into subdir
    for(Directory dir : d1.getAllSubDirectories()) {
      d1.removeSubDirectory(dir);
    }
    Directory dA = new Directory();
    dA.setName("folderA");
    d1.addDirectory(dA);
    dA.setParentDir(d1);
    Directory dB = new Directory();
    dB.setName("folderB");
    dA.addDirectory(dB);
    dB.setParentDir(dA);
    args.clear();
    args.add("folderA");
    args.add("folderA/folderB");
    cp.runCommand(args.listIterator(), root);
    if(dB.getAllSubDirectories().size() != 0) {
      result = false;
    }
    
    //copy file to dir
    File f1 = new File();
    f1.setName("file1");
    dA.addFile(f1);
    f1.setParentDir(dA);
    args.clear();
    args.add("folderA/file1");
    args.add("folderA/folderB");
    cp.runCommand(args.listIterator(), root);
    if(!(dA.getAllSubFiles().contains(f1))
        || !(dB.getAllSubFiles().get(0).getName().equals("file1"))
        || dB.getAllSubFiles().contains(f1)) {
      result = false;
    }
    
    assertEquals(true, result);
    
  }

  @Test
  public void testCopyDirectory() {
    Directory d1 = new Directory();
    d1.setName("folder1");
    Directory d2 = new Directory();
    d2.setName("folder2");
    File f1 = new File();
    f1.setName("file");
    f1.setContent("sample");
    d1.addDirectory(d2);
    d2.setParentDir(d1);
    d1.addFile(f1);
    f1.setParentDir(d1);
    
    Directory copy = cp.copy(d1);
    boolean result = !(copy.equals(d1)) && copy.getName().equals("folder1")
        && !(copy.getAllSubDirectories().get(0).equals(d2))
        && copy.getAllSubDirectories().get(0).getName().equals("folder2")
        && !(copy.getAllSubFiles().get(0).equals(f1))
        && copy.getAllSubFiles().get(0).getName().equals("file")
        && copy.getAllSubFiles().get(0).getcontent().equals("sample");
    
    assertEquals(true, result);
  }

  @Test
  public void testCopyFile() {
    File f1 = new File();
    f1.setName("file");
    f1.setContent("sample");
    
    File copy = cp.copy(f1);
    boolean result = !(copy.equals(f1)) && copy.getName().equals("file")
        && copy.getcontent().equals("sample");
    
    assertEquals(true, result);
  }

}
