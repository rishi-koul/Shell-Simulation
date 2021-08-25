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

public class History extends Commands {
  /**
   * History class is a subclass of Command that inherits the runCommand(String[]) method in order
   * to run the users input for "history [number]"
   */

  private String stdOut = "";
  // List of string storing history of command input
  public static ArrayList<String> cmdhistory = new ArrayList<String>();

  /**
   * Checks if the users give a valid argument for history
   * 
   * @param Str argument provided by user
   * @return Returns true if the argument is valid
   */
  public static boolean validateString(String str) {
    str = str.toLowerCase();
    char[] charArray = str.toCharArray();
    for (int i = 0; i < charArray.length; i++) {
      char ch = charArray[i];
      if ((ch >= 'a' && ch <= 'z')) {
        return false;
      }
    }
    String check_symbol = "[!@#$%&*()_+=|<>?{}\\[\\]~-]";
    char[] symbol = check_symbol.toCharArray();
    for (int i = 0; i < charArray.length; i++) {
      char ch = charArray[i];
      for (int j = 0; j < symbol.length; j++) {
        if (ch == symbol[j]) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Uses an array list of users input arguments and prints out all inputs the user has made in
   * numbered order from oldest to most recent.
   * 
   * @param list is an array of strings corresponding to the users input
   * @param Loc not required
   * @return String
   */
  @Override
  public String runCommand(ListIterator<String> list, FileSystemI location) {

    list.add(new String("empty"));
    if (!list.hasNext()) {
      ListIterator<String> temp = cmdhistory.listIterator();
      for (int i = 0; i < cmdhistory.size(); i++) {
        stdOut += i + 1 + ". " + temp.next() + "\n";
      }
    }
    if (list.hasNext()) {
      String validate = list.next();

      if (!History.validateString(validate)) {
        System.out.println("Error: Invalid arguments");
        return stdOut;
      }
      int number = Integer.parseInt(validate);
      if (list.hasNext()) {
        System.out.println("Error: Invalid # of arguments");
        return stdOut;
      } else {
        ListIterator<String> temp = cmdhistory.listIterator();
        if (number < 0) {
          System.out.println("Error: Invalid argument number");
          return stdOut;

        }
        if (cmdhistory.size() < number)
          number = cmdhistory.size();

        for (int i = 0; i < cmdhistory.size() - number; i++)
          temp.next();
        for (int i = cmdhistory.size() - number; i < cmdhistory.size(); i++) {
          stdOut += i + 1 + ". " + temp.next() + "\n";
        }
      }
    }

    return stdOut;

  }

  /**
   * Creates a specified manual on how to use the [history] command.
   *
   * @return String for the manual documentation for [history].
   */
  @Override
  public String getMan() {
    return "history [number]:\n" + "\tPrint out recent commands.\n\tNote: If number is "
        + "given, the output will be truncate by this number.";
  }
}
