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

public interface FileSystemI {

  /**
   * This is the class which maintains the entire arrangement of files and directories. It has a
   * tree structure
   */

  /**
   * A variable to track the main stack
   */
  Stack<String> mainStack = new Stack<String>();

  /**
   * Function that adds a directory to the main stack
   * 
   * @param dirName Name of the directory
   */

  public void addToMainStack(String dirName);

  /**
   * Function that pops a directory from the main stack
   */

  public void popFromMainStack();

  /**
   * Function to get back the main stack
   */

  public Stack<String> getMainStack();

  // Desc
  public static String fileName = "";

  /**
   * Function to set back the main stack
   */

  public void setMainStack(Stack<String> mainStack);

  /**
   * Function to set the base directory
   * 
   * @param baseDir The directory the the user bases
   */
  public void setBaseDirectory(Directory baseDir);

  /**
   * Function to set the current directory
   * 
   * @param dir The directory the the user bases
   */

  public void setCurrentDirectory(Directory dir);

  /**
   * Function to get the current directory
   */

  public Directory getCurrentDirectory();

  /**
   * Checks whether the provided dirName is a subDir of the current location
   * 
   * @param name The name of directory to be checked
   * @return Whether it is a sub directory or not
   */


  public boolean checkIfSubDirectory(String name);


  /**
   * Checks whether the provided fileName is a file under the current location
   * 
   * @param name The name of file to be checked
   * @return Whether it is a sub file or not
   */

  public boolean checkIfSubFile(String name);

  /**
   * A function to add directories at the current location
   * 
   * @param dir The directory to add
   */

  public void addDirectory(Directory dir);

  /**
   * A function to add files at the current location
   * 
   * @param dir The file to add
   */

  public void addFile(File file);

  /**
   * A function to get back all the sub directories
   */

  public ArrayList<Directory> getAllSubDirectories();

  /**
   * A function to get back all the sub files
   */

  public ArrayList<File> getAllSubFiles();

  /**
   * A function to get the base directory
   * 
   * @return the base directory
   */
  public Directory getBaseDirectory();

  /**
   * A function to get the parent dir of the current directory
   * 
   * @return the parent directory
   */
  public Directory getParentDir();

  /**
   * Get path of the current location as a string
   * 
   * @return the path of the current location
   */
  public String getDirectoryPathAsString();

  /**
   * Get path of the current location as an array list
   * 
   * @return the path of the current location as an array list
   */

  public ArrayList<Directory> getDirectoryPath();

  /**
   * A function to remove a directory and its children from the tree of Directories, where the root
   * of the tree is baseDir
   * 
   * @param dir The directory to remove
   * @param base The root directory to search from
   */
  public void removeDirectory(Directory dir);


  /**
   * A function to remove a directory and its children from the tree of Directories, where the root
   * of the tree is base
   * 
   * @param dir The directory to remove
   * @param base The root directory to search from
   */
  public void removeDirectory(Directory dir, Directory base);

  /**
   * Checks if a directory or any of its direct descendants are the current Directory
   * 
   * @param dir The directory to remove
   * @param base The root directory to search from
   */
  public boolean containsCurrDir(Directory dir);

  /**
   * Gets file by the path provided by the user
   * 
   * @param path Path containing file name provided by the user
   * @param Base Directory from which we want to start looking from.
   * @return Returns File if there is file in the path. Otherwise, return null.
   **/
  public File getFileFromPath(String path, Directory Base);

  /**
   * Gets Directory by the path provided by the user
   * 
   * @param path Path containing file name provided by the user
   * @param Base Directory from which we want to start looking from.
   * @return Returns Directory if there is Directory in the path. Otherwise, return null.
   **/
  public Directory getDirFromPath(String path, Directory Base);

}
