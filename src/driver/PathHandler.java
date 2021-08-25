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

public class PathHandler {

  /**
   * This a common class to handle paths
   */

  /**
   * A variable the will later be initialized to the current location in the FileSystemI
   */

  private FileSystemI location;

  /**
   * A variable that will later be initialized to the path that the user passes
   */
  private String path;


  /**
   * Parses the path given by user.
   * 
   * @param path Path containing file name provided by the user
   * @return main is an array of strings corresponding to the users input
   **/
  public static ArrayList<String> parseAnyPath(String path) {

    ArrayList<String> list = JShell.myparse(path, "/");
    ListIterator<String> arguments = list.listIterator();
    ArrayList<String> main = new ArrayList<String>();
    arguments.add("end");
    while (arguments.hasNext()) {
      main.add(arguments.next());
    }
    return main;
  }


  /**
   * If the user passes the path as string we need to convert that string into array and remove any
   * unwanted characters
   * 
   * @return the path as an array list
   */

  public ArrayList<String> getPathAsArray() {
    String tempPathAsArray[] = path.split("/");
    ArrayList<String> pathAsArray = new ArrayList<>();

    for (String i : tempPathAsArray) {
      if (i.equals("") || i.equals(" ")) {

      } else {
        pathAsArray.add(i);
      }
    }
    return pathAsArray;
  }

  /**
   * As users only passes a string we need to convert that string to its respective directory. This
   * function performs this task
   * 
   * @param dirName The name of the directory whose address needs to be found
   * @return Address of the directory has the same name as the argument
   */
  private Directory getAddressOfDir(String dirName) {

    Directory temp = new Directory();
    for (Directory dir : location.getAllSubDirectories()) {
      if (dir.getName().equals(dirName)) {
        temp = dir;
        break;
      }

    }
    return temp;
  }

  /**
   * As users only passes a string we need to convert that string to its respective file. This
   * function performs this task
   * 
   * @param fileName The name of the file whose address needs to be found
   * @return Address of the file that has the same name as the argument
   */
  private File getAddressOfFile(String fileName) {

    File temp = new File();
    for (File file : location.getAllSubFiles()) {
      if (file.getName().equals(fileName)) {
        temp = file;
        break;
      }

    }
    return temp;
  }

  /**
   * Checks whether the provided dirName is a subDir of the current location
   * 
   * @param dirName The name of directory to be checked
   * @return Whether it is a sub directory or not
   */

  private boolean checkIfSubDirectory(String dirName) {

    boolean isSubDir = false;
    for (Directory dir : location.getAllSubDirectories()) {
      if (dir.getName().equals(dirName)) {
        isSubDir = true;
      }
    }
    return isSubDir;
  }

  /**
   * Checks whether the provided fileName is a subFile of the current location
   * 
   * @param fileName The name of directory to be checked
   * @return Whether it is a subFile or not
   */

  private boolean checkIfSubFile(String fileName) {

    boolean isFile = false;
    for (File file : location.getAllSubFiles()) {
      if (file.getName().equals(fileName)) {
        isFile = true;
      }
    }
    return isFile;
  }


  /**
   * This function parses the path passed by the user and changes the current location to the
   * location to the last directory in the path Also checks whether the path is valid or not
   * 
   * @param loc The current location
   * @param path The path that the user passes
   * @param isFullPath Whether its a full path or not
   * @return whether the path is correct or not
   */

  public boolean parsePath(FileSystemI loc, String path, boolean isFullPath) {

    boolean isCorrectPath = true;
    this.location = loc;
    this.path = path;
    ArrayList<String> pathAsArray = getPathAsArray();


    if (isFullPath)
      location.setCurrentDirectory(location.getBaseDirectory());
    else
      location.setCurrentDirectory(location.getCurrentDirectory());

    for (String dirName : pathAsArray) {
      if (checkIfSubDirectory(dirName)) {
        location.setCurrentDirectory(getAddressOfDir(dirName));
      } else {
        isCorrectPath = false;
        break;
      }
    }
    return isCorrectPath;
  }

  /**
   * Same as the last function just the path is not a string but an array
   * 
   * @param loc The current location
   * @param path The path as an array
   * @param i Whether its a full path or not
   * @return whether the path is correct or not
   */

  public boolean parsePath(FileSystemI loc, ArrayList<String> path, boolean i) {

    boolean isCorrectPath = true;
    this.location = loc;
    ArrayList<String> pathAsArray = path;

    if (i)
      location.setCurrentDirectory(location.getBaseDirectory());
    else
      location.setCurrentDirectory(location.getCurrentDirectory());
    for (String dirName : pathAsArray) {
      if (checkIfSubDirectory(dirName)) {
        location.setCurrentDirectory(getAddressOfDir(dirName));
      } else {
        isCorrectPath = false;
        break;
      }
    }
    return isCorrectPath;
  }

  /**
   * This function parses the path passed by the user and returns the Directory referred to by the
   * path if it exists, otherwise returns null
   * 
   * @param loc The current location
   * @param path The path that the user passes
   * @param isFullPath Whether its a full path or not
   * @return the wanted Directory or null
   */

  public Directory pathToDir(FileSystemI loc, String path, boolean isFullPath) {

    this.location = loc;
    this.path = path;
    ArrayList<String> pathAsArray = getPathAsArray();
    Directory temp = location.getCurrentDirectory();


    if (isFullPath)
      location.setCurrentDirectory(location.getBaseDirectory());
    else
      location.setCurrentDirectory(location.getCurrentDirectory());

    for (String dirName : pathAsArray) {
      if (checkIfSubDirectory(dirName)) {
        location.setCurrentDirectory(getAddressOfDir(dirName));
      } else {
        location.setCurrentDirectory(temp);
        return null;
      }
    }
    Directory found = location.getCurrentDirectory();
    location.setCurrentDirectory(temp);
    return found;
  }

  /**
   * This function parses the path passed by the user and returns the File referred to by the path
   * if it exists, otherwise returns null
   * 
   * @param loc The current location
   * @param path The path that the user passes
   * @param isFullPath Whether its a full path or not
   * @return the wanted File or null
   */

  public File pathToFile(FileSystemI loc, String path, boolean isFullPath) {

    this.location = loc;
    this.path = path;
    ArrayList<String> pathAsArray = getPathAsArray();
    Directory temp = location.getCurrentDirectory();
    File found = new File();


    if (isFullPath)
      location.setCurrentDirectory(location.getBaseDirectory());
    else
      location.setCurrentDirectory(location.getCurrentDirectory());

    for (int i = 0; i < pathAsArray.size() - 1; i++) {
      String dirName = pathAsArray.get(i);
      if (checkIfSubDirectory(dirName)) {
        location.setCurrentDirectory(getAddressOfDir(dirName));
      } else {
        location.setCurrentDirectory(temp);
        return null;
      }
    }
    try {
      String fileName = pathAsArray.get(pathAsArray.size() - 1);
      if (checkIfSubFile(fileName)) {
        found = getAddressOfFile(fileName);
        location.setCurrentDirectory(temp);
        return found;
      }
    } catch (Exception e) {

    }

    location.setCurrentDirectory(temp);
    return null;
  }

}
