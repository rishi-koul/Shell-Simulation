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

public class JShell {
  protected static boolean exit;

  /**
   * Parses the user input and returns the command and its arguments in the form of a list
   */
  public static ArrayList<String> myparse(String userinput, String var) {

    userinput = userinput.trim();
    ArrayList<String> list = new ArrayList<String>();
    String array[] = userinput.split(var);
    for (int i = 0; i < array.length; i++) {
      if (!array[i].equals("")) {
        list.add(array[i]);
      }
    }
    return list;
  }

  /**
   * This function is used to interpret the commands entered by the user by calling the Command
   * Class, also checks if the command is valid and gives back appropriate error if not
   * 
   * @param arg The command passed by the user as an array list
   * @param root The root of the file system
   */
  public static void callCmdClass(ArrayList<String> arg, FileSystemI root) {
    ListIterator<String> list = arg.listIterator();
    String var = list.next();
    Commands currentCommand = new Commands();
    boolean exists = currentCommand.checkCommand(var);
    if (exists) {
      currentCommand.setCommand(var);
      currentCommand.openClass(var, list, root);
    } else {
      System.out.println("Error:Invalid command");
    }
  }

  /**
   * This is the main method. It sets the base directory to '/'. It takes in the user input and
   * parses it It calls exit class to exit the program. It is also responsible for calling the
   * command class for interpreting the command passed by the user
   * 
   * @param args
   */
  public static void main(String[] args) {
    JShell.exit = true;
    Scanner myObj = new Scanner(System.in);
    String command;
    ArrayList<String> arguments;
    FileSystem root = FileSystem.getInstance();
    Directory dir = new Directory();
    dir.setName("/");
    dir.setParentDir(null);
    root.setBaseDirectory(dir);
    root.setCurrentDirectory(root.getBaseDirectory());
    String current_dir;
    while (JShell.exit) {
      current_dir = root.getCurrentDirectory().getName();
      current_dir = current_dir.replace("/", " ");
      System.out.print("~" + current_dir + " $ ");
      command = myObj.nextLine();
      if (command.equals(""))
        continue;
      History.cmdhistory.add(command);
      command = Redirection.dealWithNoSpaces(command);
      arguments = JShell.myparse(command, " ");
      // System.out.println("Here are the arguments " + command);
      callCmdClass(arguments, root);

      arguments.clear();
    }
    myObj.close();
  }

}
