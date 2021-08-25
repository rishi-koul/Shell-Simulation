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

import java.io.Serializable;


public class File implements Serializable {

  /**
   * This class is responsible for performing the function of a "file"
   */

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Variable that refers to the name of the file
   */
  private String name;

  /**
   * variable that refers to the content of the file
   */

  private String content;

  /**
   * A variable that will marks as the end of last node of a branch in the tree
   */

  private File lastNode;

  /**
   * A variable that refers to the parent directory of the file
   */
  private Directory parentDir;

  /**
   * Constructor for basic initialization
   */
  public File() {
    name = " ";
    content = " ";
    lastNode = null;
  }

  /**
   * A function to set the name of the file
   * 
   * @param name The name of the file
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * A function to set the parentDir
   * 
   * @param dir The parent directory
   */
  public void setParentDir(Directory dir) {
    this.parentDir = dir;
  }

  /**
   * A function to get back the parent directory of the file
   * 
   * @return the parent directory
   */
  public Directory getParentDir() {
    return parentDir;
  }


  /**
   * Function to set the content of the file
   * 
   * @param content The content that the user wants to set
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Function to get the name of the file
   * 
   * @return Name of the file
   */
  public String getName() {
    return name;
  }

  /**
   * Function to get the content of the file
   * 
   * @return content of the file
   */

  public String getcontent() {
    return content;
  }

  /**
   * Function to print the content of the file when the file is printed
   * 
   * @return content of the file
   */

  public String toString() {
    return content;
  }

}
