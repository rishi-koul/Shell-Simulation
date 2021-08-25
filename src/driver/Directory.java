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
import java.io.Serializable;

public class Directory implements Serializable {

  /**
   * This class is responsible for performing the function of a "directory"
   */

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * A variable that refers to the name of the directory
   */
  private String name;

  /**
   * An array list containing all the sub directories
   */

  private ArrayList<Directory> allSubDirectories;

  /**
   * An array list containing all the sub files
   */
  private ArrayList<File> allSubFiles;

  /**
   * A variable refering to the parent of the current directory
   */
  private Directory parent;


  /**
   * Constructor to do the basic initialization
   */
  public Directory() {
    this.name = "";
    allSubDirectories = new ArrayList<Directory>();
    allSubFiles = new ArrayList<File>();
  }

  /**
   * A function to set the parent of the current directory
   * 
   * @param parent The parent dir
   */

  public void setParentDir(Directory parent) {
    this.parent = parent;
  }

  /**
   * A function to get the parent of the current directory
   * 
   * @return the parent directory
   */

  public Directory getParentDir() {
    return parent;
  }

  /**
   * A function to set the name of the directory
   * 
   * @param name Name of the directory
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * A function to add a directory under the current directory
   * 
   * @param dir The dir to be added
   */

  public void addDirectory(Directory dir) {
    allSubDirectories.add(dir);
  }

  /**
   * A function to add a file in the directory
   * 
   * @param file The file to be added
   */
  public void addFile(File file) {
    allSubFiles.add(file);
  }

  /**
   * A function to get the name of the directory
   * 
   * @return the name of the directory
   */

  public String getName() {
    return name;
  }

  /**
   * A function to get all the sub directories
   * 
   * @return a list of all the sub directories
   */

  public ArrayList<Directory> getAllSubDirectories() {

    return allSubDirectories;
  }

  /**
   * A function to get all the sub files
   * 
   * @return a list of all the sub files
   */

  public ArrayList<File> getAllSubFiles() {

    return allSubFiles;
  }

  /**
   * A function to remove a given Directory from allSubDirectories
   * 
   * @param the Directory to remove
   */

  public void removeSubDirectory(Directory dir) {

    this.allSubDirectories.remove(dir);
  }

  /**
   * A function to remove a given File from allSubDirectories
   * 
   * @param the File to remove
   */

  public void removeSubFile(File file) {

    this.allSubFiles.remove(file);
  }

  /**
   * A function to check whether this Directory has a given Directory as a descendant. A directory
   * is its own descendant.
   * 
   * @param the Directory to check for
   */

  public boolean checkDescendant(Directory dir) {
    if (this == dir) {
      return true;
    }
    for (Directory subDir : this.getAllSubDirectories()) {
      if (subDir.checkDescendant(dir)) {
        return true;
      }
    }
    return false;
  }

  /**
   * A function to check whether this Directory has an immediate subdirectory with a given name.
   * Returns such a directory if so, otherwise returns null
   * 
   * @param the directory name to check for
   * @return the found Directory, otherwise null
   */

  public Directory checkSubdirectory(String dirName) {
    for (Directory subDir : this.allSubDirectories) {
      if (subDir.getName().equals(dirName)) {
        return subDir;
      }
    }
    return null;
  }

  /**
   * A function to check whether this Directory has a subfile with a given name. Returns such a file
   * if so, otherwise returns null
   * 
   * @param the file name to check for
   * @return the found File, otherwise null
   */

  public File checkSubFile(String fileName) {
    for (File subFile : this.allSubFiles) {
      if (subFile.getName().equals(fileName)) {
        return subFile;
      }
    }
    return null;
  }


}
