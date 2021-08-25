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

public class List extends Commands {
  /**
   * A variable that becomes true if an error occurs and stops the program
   */
  boolean error = false;

  /**
   * Any output besides error messages are returned as stdOut
   */

  String stdOut = "";


  /**
   * Checks if the user has entered an absolute path or not and is also used to check if current
   * directory is the root
   * 
   * @param the absolute path as a String
   * @return boolean true is path is absolute
   */
  public boolean checkForFullPath(String name) {
    boolean isFullPath = false;
    if (name.startsWith("/")) {
      isFullPath = true;
    }
    return isFullPath;
  }

  /**
   * If the user passes the path as string we need to extract the last instance of the path
   * 
   * @param the absolute path as a String
   * @return the last instance of the path as a String
   */
  public String traverseFullPath(String name) {
    String tempPathAsArray[] = name.split("/");
    ArrayList<String> pathAsArray = new ArrayList<>();

    for (String i : tempPathAsArray) {
      if (i.equals("") || i.equals(" ")) {

      } else {
        pathAsArray.add(i);
      }
    }
    String finalPath = null;
    int count = 0;
    Iterator<String> strIterator = pathAsArray.iterator();
    if (strIterator.hasNext()) {
      while (strIterator.hasNext()) {
        count++;
        finalPath = strIterator.next();
      }
    } else {
      finalPath = "/";
    }
    return finalPath;
  }

  /**
   * Uses an array list of users input arguments (classes) and for each argument we print its
   * contents
   * 
   * @param list is an array of strings corresponding to the users input
   * @param location of where we are within the stack currently
   * @return String
   */
  public String runCommand(ListIterator<String> list, FileSystemI location) {

    if (list.hasNext()) {
      String path = list.next();
      if (path.equals("-R")) {
        recursivePath(location, list);
      } else {
        path = list.previous();
        while (list.hasNext() && (error == false)) {
          path = list.next();
          pathChecker(location, path);
        }
      }

    } else {
      printCurrentDir(location.getCurrentDirectory());
    }
    return stdOut;
  }

  /**
   * Prints a directory's contents
   * 
   * @param Directory that needs to be printed
   * @return void
   */
  public void printCurrentDir(Directory curr) {

    ArrayList<Directory> allSubDirectories = curr.getAllSubDirectories();
    Iterator<Directory> dirIterator = allSubDirectories.iterator();
    while (dirIterator.hasNext()) {
      Directory dir = dirIterator.next();
      stdOut = stdOut + dir.getName() + "\n";
    }

    ArrayList<File> allSubFiles = curr.getAllSubFiles();
    Iterator<File> fileIterator = allSubFiles.iterator();
    while (fileIterator.hasNext()) {
      File file = fileIterator.next();
      stdOut = stdOut + file.getName() + "\n";
    }

  }

  /**
   * Evaluates the path on whether it's absolute, relative, or a file path and sends it to the
   * correct methods with the correct parameters
   * 
   * @param path is the absolute path in a string
   * @param location of where we are within the stack currently
   * @return void
   */
  public boolean pathChecker(FileSystemI location, String path) {
    boolean result = true;
    Directory original = location.getCurrentDirectory();
    if (checkForFullPath(path)) {
      PathHandler newLocation = new PathHandler();
      result = newLocation.parsePath(location, path, true);
      if (result == false) {
        path = traverseFullPath(path);
        relativePath(location, path);
      }

    } else {
      PathHandler newLocation = new PathHandler();
      result = newLocation.parsePath(location, path, false);
      if (result == false) {
        path = traverseFullPath(path);
        relativePath(location, path);
      }
    }
    if (result == true) {
      path = traverseFullPath(path);
      if (path.equals("/")) {
        location.setCurrentDirectory(location.getBaseDirectory());
        printCurrentDir(location.getCurrentDirectory());
      } else {
        location.setCurrentDirectory(location.getParentDir());
        relativePath(location, path);
      }
    }
    location.setCurrentDirectory(original);
    return result;
  }

  /**
   * Prints the contents of the dir/ file we want
   * 
   * @param path is the dir/file whose contents we want
   * @param location of where we are within the stack currently
   * @return void
   */
  public void relativePath(FileSystemI location, String path) {
    if (location.checkIfSubFile(path)) {
      stdOut = stdOut + path + "\n";
    }

    else if (location.checkIfSubDirectory(path)) {
      stdOut = stdOut + path + ":\n";
      PathHandler newLocation = new PathHandler();
      Directory original = location.getCurrentDirectory();
      newLocation.parsePath(location, path, false);
      printCurrentDir(location.getCurrentDirectory());
      location.setCurrentDirectory(original);
      stdOut = stdOut + " \n";

    } else {
      System.out.println(path + " not present within the current directory");
      error = true;
    }

  }

  /**
   * Prints the contents of the filesystem starting from the path given
   * 
   * @param location of where we are within the stack currently
   * @return void
   */
  public void recursiveTraverse(FileSystemI currentLocation) {

    stdOut = stdOut + currentLocation.getCurrentDirectory().getName() + " : ";

    int comma = 0;
    ArrayList<File> allSubFiles = currentLocation.getAllSubFiles();
    Iterator<File> fileIterator = allSubFiles.iterator();
    while (fileIterator.hasNext()) {
      File file = fileIterator.next();
      stdOut = stdOut + file.getName() + "   ||   ";

    }

    ArrayList<Directory> allSubDirectories = currentLocation.getAllSubDirectories();
    Iterator<Directory> dirIterator = allSubDirectories.iterator();
    while (dirIterator.hasNext()) {
      Directory dir = dirIterator.next();
      String dirName = dir.getName();
      stdOut = stdOut + dirName + "    ||   ";

    }
    stdOut = stdOut + "\n";

    ArrayList<Directory> allSubDirectories1 = currentLocation.getAllSubDirectories();
    Iterator<Directory> dirIterator1 = allSubDirectories1.iterator();
    while (dirIterator1.hasNext()) {
      PathHandler newLocation = new PathHandler();
      Directory dir = dirIterator1.next();
      String dirName = dir.getName();
      Directory original = currentLocation.getCurrentDirectory();
      newLocation.parsePath(currentLocation, dirName, false);
      recursiveTraverse(currentLocation);
      currentLocation.setCurrentDirectory(original);
    }

  }

  /**
   * Prints the contents of the filesystem starting from the path given
   * 
   * @param location of where we are within the stack currently
   * @param list of arguments that user wants us to print recursively
   * @return void
   */
  public void recursivePath(FileSystemI location, ListIterator<String> list) {
    Directory original = location.getCurrentDirectory();
    if (list.hasNext()) {
      while (list.hasNext() && (error == false)) {
        boolean result = true;
        location.setCurrentDirectory(original);
        PathHandler newLocation = new PathHandler();
        String path = list.next();
        // System.out.println("path is : " + path);

        if (checkForFullPath(path)) {
          result = newLocation.parsePath(location, path, true);
          if (result == true) {
            recursiveTraverse(location);
          } else {
            path = traverseFullPath(path);
            relativePath(location, path);
          }
        } else {
          result = newLocation.parsePath(location, path, false);
          if (result == true) {
            recursiveTraverse(location);
          } else {
            path = traverseFullPath(path);
            relativePath(location, path);
          }
        }
      }
    } else {
      recursiveTraverse(location);
    }
    location.setCurrentDirectory(original);
  }

  /**
   * 
   * Creates a specified manual on how to use the [List] command.
   *
   * @return String for the manual documentation for [List].
   */
  @Override
  public String getMan() {
    return "ls [PATH ...]:\n" + "\tPrints contents of PATH if PATH refers" + " to a directory\n"
        + "\tPrints the name of the file if PATH refers to a file\n\t"
        + "Gives an error if PATH does not exist";
  }

}
