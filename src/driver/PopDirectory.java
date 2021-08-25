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

import java.util.Stack;
import java.util.ListIterator;

public class PopDirectory extends Commands {

  /**
   * Popd class is a subclass of Command that inherits the runCommand(String[]) method in order to
   * run the users input for popd
   *
   */
  
  /**
   * The standard output of the PrintWorkingDirectory Class
   */
  private String stdOut = "";
  
  /**
   * The stack that later will refer to the mainStack
   */

  public Stack<String> stack = new Stack<String>();

  /**
   * A variable the will later be initialized to the current location in the FileSystemI
   */

  public FileSystemI location;

//  /**
//   * every time popd cmd is executed it prints the new stack. This function exactly carries out that
//   * function i.e print the stack
//   */
//
//  private void printResult() {
//
//    if (stack.size() == 0) {
//      System.out.println("popd: directory stack empty");
//    } else {
//      for (int i = stack.size() - 1; i >= 0; i--) {
//        stdOut += "~" + stack.get(i) + "  ";
//      }
//      stdOut += "\n";
//    }
//  }

  /**
   * As users only passes a string we need to convert that string to its respective directory. This
   * function performs this task
   * 
   * @param dirName The name of the directory whose address needs to be found
   * @return Address of the directory has the same name as the argument
   */

  public Directory getAddressOfDir(String dirName) {

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
   * popd not only removes from the stack but also changes directory to the top most one in the
   * stack. This function is performed by moveDirectory()
   * 
   */

  private void moveDirectory() {
    String dirPath = stack.get(stack.size() - 1).trim();
    String dirPathAsArray[] = dirPath.split("/");

    location.setCurrentDirectory(location.getBaseDirectory());

    for (int i = 1; i < dirPathAsArray.length; i++) {
      location.setCurrentDirectory(getAddressOfDir(dirPathAsArray[i]));
    }

  }

  /**
   * The main function that run the popd command. It also check the whether the no.of arguments
   * passed are valid, if not gives appropriate error.
   * 
   * @return String The standard output of the PrintWorkingDirectory Class
   */
  public String runCommand(ListIterator<String> list, FileSystemI location) {

    if (list.hasNext()) {
      System.out.println("popd: popd takes no arguments");
      return stdOut;
    }
    stack = location.getMainStack();
    this.location = location;
    if (!stack.isEmpty()) {
      location.popFromMainStack();
    }
//    printResult();
    if(stack.isEmpty())
      System.out.println("Stack is empty");
    if (!stack.isEmpty()) {
      moveDirectory();
    }
    return stdOut;
  }

  /**
   * This function can be used to get the description of the popd command
   */

  public String getMan() {
    return "popd\n Remove the top entry from the directory stack, and cd into\n"
        + " it. The removal must be consistent as per the LIFO behavior of  a\n"
        + " stack.  The popd command removes the top most directory from the\n"
        + " directory stack and makes it the current working directory.\n"
        + "  If there is no directory onto the stack, then give appropriate\n"
        + " error message.  ";
  }
}
