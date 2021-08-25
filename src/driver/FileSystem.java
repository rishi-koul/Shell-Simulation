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
import java.util.Stack;

public class FileSystem implements FileSystemI {

  // create an object of FileSystem
  private static FileSystem instance = new FileSystem();

  // make the constructor private so that this class cannot be
  // instantiated
  private FileSystem() {}

  // Get the only object available
  public static FileSystem getInstance() {
    return instance;
  }

  /**
   * This is the class which maintains the entire arrangement of files and directories. It has a
   * tree structure
   */

  /**
   * A variable to track the current directory
   */

  private Directory currentDir = new Directory();

  /**
   * A variable to track the base directory
   */

  private Directory baseDir = new Directory();

  /**
   * A variable to track the main stack
   */
  private Stack<String> mainStack = new Stack<String>();


  // Strings storing fileNmae when found in getDirFrom Path
  public static String fileName = "";

  /**
   * Function that adds a directory to the main stack
   * 
   * @param dirName Name of the directory
   */

  public void addToMainStack(String dirName) {
    mainStack.push(dirName);
  }

  /**
   * Function that pops a directory from the main stack
   */

  public void popFromMainStack() {
    mainStack.pop();
  }

  /**
   * Function to get back the main stack
   */

  public Stack<String> getMainStack() {
    return mainStack;
  }

  /**
   * Function to set back the main stack
   */

  public void setMainStack(Stack<String> mainStack) {
    this.mainStack = mainStack;
  }

  /**
   * Function to set the base directory
   * 
   * @param baseDir The directory the the user bases
   */
  public void setBaseDirectory(Directory baseDir) {
    this.baseDir = baseDir;
    this.currentDir = baseDir;
  }

  /**
   * Function to set the current directory
   * 
   * @param dir The directory the the user bases
   */

  public void setCurrentDirectory(Directory dir) {
    this.currentDir = dir;
  }

  /**
   * Function to get the current directory
   */

  public Directory getCurrentDirectory() {
    return currentDir;
  }

  /**
   * Checks whether the provided dirName is a subDir of the current location
   * 
   * @param name The name of directory to be checked
   * @return Whether it is a sub directory or not
   */


  public boolean checkIfSubDirectory(String name) {
    ArrayList<Directory> subDirectories = currentDir.getAllSubDirectories();

    boolean isSubDirectory = false;
    for (Directory dir : subDirectories) {
      if (dir.getName().equals(name)) {
        isSubDirectory = true;;
      }
    }
    return isSubDirectory;
  }


  /**
   * Checks whether the provided fileName is a file under the current location
   * 
   * @param name The name of file to be checked
   * @return Whether it is a sub file or not
   */

  public boolean checkIfSubFile(String name) {
    ArrayList<File> subFiles = currentDir.getAllSubFiles();

    boolean isSubFile = false;
    for (File f : subFiles) {
      if (f.getName().equals(name)) {
        isSubFile = true;;
      }
    }
    return isSubFile;
  }

  /**
   * A function to add directories at the current location
   * 
   * @param dir The directory to add
   */

  public void addDirectory(Directory dir) {
    dir.setParentDir(currentDir);
    currentDir.addDirectory(dir);
  }

  /**
   * A function to add files at the current location
   * 
   * @param dir The file to add
   */

  public void addFile(File file) {
    file.setParentDir(currentDir);
    currentDir.addFile(file);
  }

  /**
   * A function to get back all the sub directories
   */

  public ArrayList<Directory> getAllSubDirectories() {

    return currentDir.getAllSubDirectories();
  }

  /**
   * A function to get back all the sub files
   */

  public ArrayList<File> getAllSubFiles() {

    return currentDir.getAllSubFiles();
  }

  /**
   * A function to get the base directory
   * 
   * @return the base directory
   */
  public Directory getBaseDirectory() {
    return baseDir;
  }

  /**
   * A function to get the parent dir of the current directory
   * 
   * @return the parent directory
   */
  public Directory getParentDir() {
    return currentDir.getParentDir();
  }

  /**
   * Get path of the current location as a string
   * 
   * @return the path of the current location
   */
  public String getDirectoryPathAsString() {

    ArrayList<Directory> reverseDir = new ArrayList<Directory>();
    String dirPath = "";
    Directory temp = currentDir;

    while (temp.getParentDir() != null) {
      reverseDir.add(temp.getParentDir());
      temp = temp.getParentDir();
    }


    for (int i = reverseDir.size() - 1; i >= 0; i--) {
      if (reverseDir.get(i).getName().equals(baseDir.getName())) {
        dirPath = dirPath.concat(reverseDir.get(i).getName());
      } else {
        dirPath = dirPath.concat(reverseDir.get(i).getName() + "/");
      }

    }

    return dirPath;
  }

  /**
   * Get path of the current location as an array list
   * 
   * @return the path of the current location as an array list
   */

  public ArrayList<Directory> getDirectoryPath() {

    ArrayList<Directory> reverseDir = new ArrayList<Directory>();
    ArrayList<Directory> dirPath = new ArrayList<Directory>();
    Directory temp = currentDir;

    while (temp.getParentDir() != null) {
      reverseDir.add(temp.getParentDir());
      temp = temp.getParentDir();
    }


    for (int i = reverseDir.size() - 1; i >= 0; i--) {
      dirPath.add(reverseDir.get(i));
    }
    return dirPath;
  }

  /**
   * A function to remove a directory and its children from the tree of Directories, where the root
   * of the tree is baseDir. Cannot remove the current working directory or any of its direct
   * ancestors
   * 
   * @param dir The directory to remove
   * @param base The root directory to search from
   */
  public void removeDirectory(Directory dir) {
    if (dir != this.currentDir) {
      if (dir == this.baseDir) {
        setBaseDirectory(null);
      } else {
        removeDirectory(dir, this.baseDir);
      }
    }
  }


  /**
   * A function to remove a directory and its children from the tree of Directories, where the root
   * of the tree is base. Cannot remove the current working directory or any of its direct ancestors
   * 
   * @param dir The directory to remove
   * @param base The root directory to search from
   */
  public void removeDirectory(Directory dir, Directory base) {
    boolean found = false;
    for (Directory subDir : base.getAllSubDirectories()) {
      if (subDir == dir && !containsCurrDir(subDir)) {
        found = true;
      } else {
        removeDirectory(dir, subDir);
      }
    }
    if (found) {
      base.removeSubDirectory(dir);
    }
  }


  /**
   * Checks if a directory or any of its direct descendants are the current Directory
   * 
   * @param dir The directory to remove
   * @param base The root directory to search from
   */
  public boolean containsCurrDir(Directory dir) {
    if (dir == this.getCurrentDirectory()) {
      return true;
    }
    for (Directory subDir : dir.getAllSubDirectories()) {
      if (containsCurrDir(subDir)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets file by the path provided by the user
   * 
   * @param path Path containing file name provided by the user
   * @param Base Directory from which we want to start looking from.
   * @return Returns File if there is file in the path. Otherwise, return null.
   **/
  public File getFileFromPath(String path, Directory Base) {

    ArrayList<String> main = PathHandler.parseAnyPath(path);

    File found = null;
    Directory root = getDirFromPath(path, Base);
    if (root == null) {
      return null;
    }
    for (File temp1 : root.getAllSubFiles()) {
      if ((main.get(main.size() - 1)).equals(temp1.getName())) {

        found = temp1;
        break;
      }
    }

    return found;
  }

  /**
   * Gets Directory by the path provided by the user
   * 
   * @param path Path containing file name provided by the user
   * @param Base Directory from which we want to start looking from.
   * @return Returns Directory if there is Directory in the path. Otherwise, return null.
   **/
  public Directory getDirFromPath(String path, Directory Base) {

    ArrayList<String> main = PathHandler.parseAnyPath(path);
    Directory root = null;
    Boolean exist = false;
    root = Base;
    if (main.size() == 0)
      return null;
    for (int i = 0; i < main.size() - 1; i++) {
      exist = false;
      for (Directory temp : root.getAllSubDirectories()) {
        if ((main.get(i)).equals(temp.getName())) {
          root = temp;
          exist = true;
        }
      }
      if (exist == false)
        return null;
    }
    fileName = main.get(main.size() - 1);
    return root;
  }

}

