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

public class PrintWorkingDirectory extends Commands {

  /**
   * PrintWorkingDirectory class is a subclass of Command that inherits the runCommand(String[])
   * method in order to run the users input for pwd
   *
   */

  /**
   * The standard output of the PrintWorkingDirectory Class
   */
  private String stdOut = "";

  /**
   * This is the main function that runs the pwd command. It checks whether the no.of arguments are
   * valid, if not prints the appropriate error message. If no errors prints the full path of the
   * current directory
   * 
   * @return String The standard output of the PrintWorkingDirectory Class
   */
  public String runCommand(ListIterator<String> list, FileSystemI location) {

    if (list.hasNext()) {
      System.out.println("pwd: pwd takes no argument");
      return stdOut;
    }
    String currentDirName = location.getCurrentDirectory().getName();

    String prevDirList = "";
    for (Directory dir : location.getDirectoryPath()) {
      if (!dir.getName().equals(location.getBaseDirectory().getName())) {
        prevDirList = prevDirList + "/" + dir.getName();
      } else {

      }
    }
    if (!currentDirName.equals(location.getBaseDirectory().getName())) {
      // System.out.println(prevDirList + "/" + currentDirName);
      stdOut += prevDirList + "/" + currentDirName;
    } else {
      // System.out.println(prevDirList + currentDirName);
      stdOut += prevDirList + currentDirName;
    }
    return stdOut + "\n";
  }

  /**
   * A function that can be used to get the description of pwd command
   */
  public String getMan() {
    String desc = "Print the current working directory";
    return "pwd\n\t" + desc + " (including the whole path).";
  }
}
