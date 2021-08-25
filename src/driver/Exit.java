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

import java.util.ListIterator;

public class Exit extends Commands {

  /**
   * Exit JShell
   * 
   * @param list unused
   * @param Loc unused
   * @return String
   */
  @Override
  public String runCommand(ListIterator<String> list, FileSystemI location) {
    JShell.exit = false;
    return "";
  }

  /**
   * Creates a specified manual on how to use the [exit] command.
   *
   * @return String for the manual documentation for [exit].
   */
  @Override
  public String getMan() {
    return "exit: (no parameters)\n\tQuits the program";
  }

}
