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

public class Search extends Commands {

  /**
   * A variable that becomes true if an error occurs and stops the program
   */
  boolean error = false;
  /**
   * Any output besides error messages are returned as stdOut
   */
  private String stdOut = "";
  /**
   * String that saves the type of the object we want to search [f|d]
   */
  private String type = "";
  /**
   * String that saves the file/dir name we want
   */
  private String expression = "";
  /**
   * Int that tells us if the file/dir has been found for each path entered
   */
  private int found = 0;



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
   * Checks if the argument for type the user has entered is strictly f or d
   * 
   * @param the String to check
   * @return boolean true is string has expected syntax
   */
  public boolean checkType(String name) {
    boolean correct = false;
    if (name.equals("f") || name.equals("d")) {
      correct = true;
    }
    return correct;
  }

  /**
   * Checks if the expression that the user has entered is surrounded by quotations and extracts the
   * file/dir name from it
   * 
   * @param the String to check
   * @return boolean true is string has expected syntax
   */
  public boolean checkExpression(String name) {
    boolean correct = false;
    if (name.startsWith("\"") && name.endsWith("\"")) {
      expression = name.substring(1, name.length() - 1);
      correct = true;
    }
    return correct;
  }

  /**
   * Decodes the user input into categories, ie the paths as an array of strings ,the type and
   * expression as strings
   * 
   * @param the arguments as a String list Iterator
   * @return the paths as an array list of strings
   */
  public ArrayList<String> decodeInput(ListIterator<String> list) {
    boolean reached = false;
    ArrayList<String> paths = new ArrayList<>();
    while (list.hasNext() && (reached == false)) {
      String input = list.next();
      if (input.equals("-type")) {
        reached = true;
      } else {
        paths.add(input);
      }
    }
    if (list.hasNext()) {
      type = list.next();
      if (list.hasNext() && checkType(type)) {
        String name = list.next();
        if (name.equals("-name")) {
          expression = list.next();
          if (checkExpression(expression) == false) {
            System.out.println("Input Error: Did not add quotations!");
            error = true;
          }
        }
      } else {
        error = true;
        if (checkType(type))
          System.out.println("Input Error: need more arguements");
        else
          System.out.println("Input Error: did not mention f or d");
      }
    } else {
      error = true;
      if (reached == true)
        System.out.println("Input Error: need more arguements");
      else
        System.out.println("Input Error: '-type' not written in arguement");
    }
    return paths;
  }

  /**
   * Uses an array list of users input arguments and we search for the required object
   * 
   * @param list is an array of strings corresponding to the users input
   * @param location of where we are within the stack currently
   * @return String of print statements
   */
  public String runCommand(ListIterator<String> list, FileSystemI location) {
    Directory original = location.getCurrentDirectory();
    ArrayList<String> paths = decodeInput(list);
    if (paths.isEmpty()) {
      System.out.println("Error: No paths given");
    }
    Iterator<String> pathIterator = paths.iterator();
    while (pathIterator.hasNext() && (error == false)) {
      String path = pathIterator.next();
      boolean result = true;
      if (checkForFullPath(path)) {
        PathHandler newLocation = new PathHandler();
        result = newLocation.parsePath(location, path, true);
      } else {
        PathHandler newLocation = new PathHandler();
        result = newLocation.parsePath(location, path, false);
      }
      if (result == false) {
        System.out.println("Error: dir " + path + " does not exist");
        error = true;
        break;
      } else {
        found = 0;
        recursiveSearch(type, expression, location, 1);
        if (found == 0) {
          System.out.println(expression + " does not exist within " + path);
        }
      }
      location.setCurrentDirectory(original);
    }
    location.setCurrentDirectory(original);
    return stdOut;
  }

  /**
   * Recursively iterates through the path to find the object
   * 
   * @param objectType is the objectType f|d stored in a String
   * @param name is the name of the object stored in a String
   * @param currentLocation is the location of the path
   * @param depth is an int that checks the depth
   * @return void
   */
  private void recursiveSearch(String objectType, String name, FileSystemI currentLocation,
      int depth) {
    // base case
    if (depth > 1) {
      if (objectType.equals("d")) {
        if (name.equals(currentLocation.getCurrentDirectory().getName())) {
          found++;
          Directory temp = currentLocation.getCurrentDirectory();
          String path = currentLocation.getDirectoryPathAsString();
          path += currentLocation.getCurrentDirectory().getName();
          stdOut += path + '\n';
          currentLocation.setCurrentDirectory(temp);
        }
      }
    }
    if (objectType.equals("f")) {
      ArrayList<File> allSubFiles = currentLocation.getAllSubFiles();
      Directory temp = currentLocation.getCurrentDirectory();
      for (File f : allSubFiles) {
        if (f.getName().equals(name)) {
          found++;
          String path = currentLocation.getDirectoryPathAsString();
          if (currentLocation.getCurrentDirectory().getName().equals("/"))
            path += currentLocation.getCurrentDirectory().getName();
          else
            path += currentLocation.getCurrentDirectory().getName() + "/";
          path += f.getName();

          stdOut += path + '\n';
          currentLocation.setCurrentDirectory(temp);
        }
      }
    }

    // recursive case
    ArrayList<Directory> allSubDir = currentLocation.getAllSubDirectories();
    for (Directory dir : allSubDir) {
      currentLocation.setCurrentDirectory(dir);
      recursiveSearch(objectType, name, currentLocation, depth + 1);
    }
  }

  /**
   * 
   * Creates a specified manual on how to use the [Search] command.
   *
   * @return String for the manual documentation for [Search].
   */
  @Override
  public String getMan() {
    return "search [PATH ...] -type [f|d] -name expression:\n"
        + "\tSearches for the file or directory within the paths specified\n"
        + "\tPrints the location of the file/directory if it exists";
  }

}
