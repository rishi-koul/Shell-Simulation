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
package driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;


public class Tree extends Commands {

  /**
   * Any output besides error messages are returned as stdOut
   */

  private String stdOut = "";

  /**
   * Uses an array list of users input arguments (classes) and for each argument we print its
   * contents
   * 
   * @param list is an array of strings corresponding to the users input
   * @param location of where we are within the stack currently
   * @return String of output
   */
  public String runCommand(ListIterator<String> list, FileSystemI location) {
    boolean error = false;
    if (list.hasNext()) {
      System.out.println("Error: tree does not take any input");
      error = true;
    } else {
      if (error == false) {
        Directory original = location.getCurrentDirectory();
        location.setCurrentDirectory(location.getBaseDirectory());
        recursiveTraverse(location, 1);
        location.setCurrentDirectory(original);
      }
    }
    return stdOut;
  }

  /**
   * Configures the spacing based on depth within tree
   * 
   * @param int length is the depth of the tree
   * @return Spacing as a String
   */
  public String spacingFormat(int length) {
    String depth = String.format("%0" + length + "d", 0).replace('0', '\t');
    return depth;
  }

  /**
   * Prints the contents of the filesystem starting from the root
   * 
   * @param location of root
   * @param int depth within tree
   * @return void
   */
  public void recursiveTraverse(FileSystemI currentLocation, int depth) {
    String spaces = spacingFormat(depth);
    if (depth == 1) {
      stdOut = stdOut + currentLocation.getCurrentDirectory().getName() + "\n";
    }

    ArrayList<File> allSubFiles = currentLocation.getAllSubFiles();
    Iterator<File> fileIterator = allSubFiles.iterator();
    while (fileIterator.hasNext()) {
      File file = fileIterator.next();
      stdOut = stdOut + spaces + file.getName() + "\n";
    }

    ArrayList<Directory> allSubDirectories = currentLocation.getAllSubDirectories();
    Iterator<Directory> dirIterator = allSubDirectories.iterator();
    while (dirIterator.hasNext()) {
      Directory dir = dirIterator.next();
      String dirName = dir.getName();
      stdOut = stdOut + spaces + dirName + "\n";
      PathHandler newLocation = new PathHandler();
      Directory original = currentLocation.getCurrentDirectory();

      newLocation.parsePath(currentLocation, dirName, false);

      recursiveTraverse(currentLocation, depth + 1);
      currentLocation.setCurrentDirectory(original);
    }

    depth = depth - 1;
  }

  /**
   * Creates a specified manual on how to use the tree command.
   *
   * @return String for the manual documentation for [tree].
   */
  @Override
  public String getMan() {
    return "tree :\n" + "\tThe tree command takes in no input parameter.\n "
        + "\tIt displays the entire filesystem as a tree starting from the root " + "directory";
  }
}
