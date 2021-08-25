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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.ListIterator;

public class Commands {


  /**
   * protected HashMap that connects class names with actual classes in the driver; protected so
   * subclasses my access it
   */
  /**
   * private string referring to the currentCommand we work on
   */
  protected HashMap<String, String> commands = new HashMap<String, String>();
  private String currentCommand;

  private String stdOut;

  /**
   * Constructor to do the basic initialization of the Hashmap
   */
  public Commands() {

    commands.put("mkdir", "driver.MakeDirectory");
    commands.put("cd", "driver.ChangeDirectory");
    commands.put("ls", "driver.List");
    commands.put("pwd", "driver.PrintWorkingDirectory");
    commands.put("pushd", "driver.PushDirectory");
    commands.put("popd", "driver.PopDirectory");
    commands.put("history", "driver.History");
    commands.put("cat", "driver.Concatenate");
    commands.put("echo", "driver.Echo");
    commands.put("man", "driver.Man");
    commands.put("exit", "driver.Exit");
    commands.put("rm", "driver.Remove");
    commands.put("mv", "driver.Move");
    commands.put("cp", "driver.Copy");
    commands.put("curl", "driver.Curl");
    commands.put("saveJShell", "driver.SaveJShell");
    commands.put("loadJShell", "driver.LoadJShell");
    commands.put("search", "driver.Search");
    commands.put("tree", "driver.Tree");

  }


  /**
   * A function to get the currentCommand
   * 
   * @return the currentCommand
   */
  public String getCommand() {
    return currentCommand;
  }

  /**
   * A function to set the currentCommand to input
   * 
   * @param string input
   * @return void
   */
  public void setCommand(String input) {
    this.currentCommand = input;
  }

  /**
   * A function to check if input exists as a command in our hashmap
   * 
   * @param string input
   * @return boolean true if command exists
   */
  public boolean checkCommand(String input) {
    boolean isKeyPresent = commands.containsKey(input);
    return isKeyPresent;
  }

  /**
   * A function that opens and runs the class for the appropriate command
   * 
   * @param string input is the name of the command
   * @param list of user arguments
   * @param location of where we are currently on the stack
   * @return void
   */
  public void openClass(String input, ListIterator<String> list, FileSystemI location) {

    try {

      String className = commands.get(input);

      try {

        Commands inputCommand =
            (Commands) (Class.forName(className).getDeclaredConstructor().newInstance());

        Redirection redir = new Redirection();
        boolean isRedirection = redir.checkRedirection(list);
        list = redir.getOriginalList();
        if (isRedirection) {
          list = redir.rmvRedir(list);
          stdOut = inputCommand.runCommand(list, location);
        } else
          stdOut = inputCommand.runCommand(list, location);
        if (!isRedirection || stdOut.equals("")) {
          printResult(input);
        }

        else
          redir.redirect((String) stdOut, location);

      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    } finally {
    }
  }

  /**
   * A function that every subclass will overwrite this as per their functionality, their main
   * functionality will reside here
   * 
   * @param list of user arguments
   * @param location of where we are currently on the stack
   * @return void
   */

  public String runCommand(ListIterator<String> list, FileSystemI location) {
    return "";
  }

  private void printResult(String cmd) {
    if (!stdOut.equals("")) {
      if (cmd.equals("echo") || cmd.equals("cat"))
        System.out.println(stdOut);
      else
        System.out.print(stdOut);
    }

  }

  /**
   * Creates a specified manual on how to use the command that overwrites it. will be overwritten in
   * all subclasses
   *
   * @return String for the manual documentation for the command.
   */
  public String getMan() {
    return "";
  }

}
