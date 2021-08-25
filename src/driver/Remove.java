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

import java.util.*;

public class Remove extends Commands {
  /**
   * Remove class is a subclass of Command that inherits the runCommand(String[]) method in order to
   * run the users input for rm
   *
   */

  /**
   * Removes given Directory from given FileSystemI provided param with existing and well formed
   * path and correct number of arguments, otherwise does nothing.
   * 
   * @param ListIterator<String> list - arguments passed to rm command
   * @param FileSystemI f - FileSystemI containing current working directory
   * @return String the standard Output Remove Class
   **/

  public String runCommand(ListIterator<String> list, FileSystemI f) {

    ArrayList<String> arguments = new ArrayList<String>();
    while (list.hasNext()) {
      arguments.add(list.next());
    }

    if (arguments.size() == 0) {
      System.out.println("bash: rm: Must take 1 argument");

    } else if (arguments.size() == 1) {
      String input = arguments.get(0);
      PathHandler p = new PathHandler();

      boolean fullPath = input.length() > 0 && input.charAt(0) == '/';

      Directory dir = p.pathToDir(f, input, fullPath);
      File file = p.pathToFile(f, input, fullPath);

      if (dir != null) {
        if (f.containsCurrDir(dir)) {
          System.out.println(
              "rm: cannot remove the current directory or" + " any of its direct ancestors.");
        } else {
          f.removeDirectory(dir);
        }
      } else if (file != null) {
        System.out.println("rm: Target item must be directory (not file)");
      } else {
        System.out.println("rm: No such file or directory");
      }

    } else {
      System.out.println("bash: rm: too many arguments");
    }

    return "";

  }

  public String getMan() {
    return "rm DIR:\n\tRemove the Directory DIR from the file system, and all"
        + " the\n\tchildren of DIR";
  }
}
