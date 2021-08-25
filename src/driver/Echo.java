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

public class Echo extends Commands {
  /**
   * Echo class is a subclass of Command that inherits the runCommand(String[]) method in order to
   * run the users input for "echo "String" > file.txt" or "echo "String" >> file.txt."
   *
   */

  String content = "";
  boolean temp;

  /**
   * Edit the specified file content with a string provided by a user. If the specified file is
   * empty, the method creates the file with the name specified and sets content as the string. When
   * a symbol for the echo command is >, the file's content gets overwritten by the string.
   * Otherwise, when the symbol is >>, the file's content gets appended by the string.
   * 
   * @param list is an array of strings corresponding to the users input
   * @param loc is the FilSystem from which we access files and Directories.
   * @return String
   */
  @Override
  public String runCommand(ListIterator<String> list, FileSystemI loc) {


    ArrayList<String> arr = echoParse();

    Redirection redir = new Redirection();

    temp = redir.checkRedirection(arr.listIterator());
    if (temp) {
      list = redir.rmvRedir(arr.listIterator());
      return echoCmd(list);
    } else
      return echoCmd(arr.listIterator());



  }

  /**
   * Helper function for Echo class that return the string provided by the user in the @param list.
   * 
   * @param list is an array of strings corresponding to the users input
   * @return String
   */
  private String echoCmd(ListIterator<String> list) {

    String cmd = "";

    if (!list.hasNext()) {
      System.out.println("Echo needs one argument");
      return "";
    }
    // Note: The list that we are given automatically removes echo

    // Traverse over the string and append it with " "
    // we append " " so that we can undo they split(" ") we did in the JShell

    while (list.hasNext()) {

      cmd += list.next() + " ";
    }

    cmd = cmd.substring(0, cmd.length() - 1);

    // System.out.println(cmd);
    if (cmd.startsWith("\"") && cmd.endsWith("\"")) {
      if (cmd.length() > 1) {
        if (!temp)
          return cmd.substring(1, cmd.length() - 1);
        else
          return content;
      }

      else
        System.out.println("Error: Wrong format");
    } else {
      System.out.println("Error: Wrong format");
    }
    return "";
  }

  /**
   * Parses the user input by getting the original lines at history and returns the arguments in the
   * form of a array list
   * 
   * @return ArrayList<String>
   */
  private ArrayList<String> echoParse() {

    String userInput = History.cmdhistory.get(History.cmdhistory.size() - 1);
    userInput = userInput.trim();
    userInput = userInput.replace("echo ", "");
    while (userInput.startsWith(" ")) {
      userInput = userInput.replaceFirst(" ", "");
    }
    userInput = Redirection.dealWithNoSpaces(userInput);
    ArrayList<String> list = new ArrayList<String>();
    String array[] = userInput.split(" ");
    for (int i = 0; i < array.length; i++) {
      list.add(array[i]);
    }
    if (userInput.contains("\"")) {
      int start = userInput.indexOf("\"");
      int end = userInput.lastIndexOf("\"");
      if (start != end)
        content = userInput.substring(start + 1, end);
    }


    return list;

  }

  /**
   * Creates a specified manual on how to use the [echo] command.
   *
   * @return String for the manual documentation for [echo].
   */
  @Override
  public String getMan() {
    return "echo STRING [> OUTFILE] or [>> OUTFILE]:\n" + "\tWrite STRING into OUTFILE.\n"
        + "\tNote:\n" + "\t1.If OUTFILE is not provided, just print STRING on the shell"
        + ".\n\t2.If OUTFILE do not exist, this command will create it.\n"
        + "\t3.\">\" means overwrites while \">>\" means appends.";

  }


}
