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
import java.util.ListIterator;

/**
 * Takes in one user input argument and prints out the descriptions for that class
 * 
 * @param list is an array of strings corresponding to the users input
 * @param Loc not required
 * @return void
 */

public class Man extends Commands {

  private String stdOut = "";

  public String runCommand(ListIterator<String> list, FileSystemI location) {
    boolean error = false;
    if (!list.hasNext()) {
      System.out.println("man : Man command needs one argument");
      return "";
    }
    String wantDes = list.next();
    list.hasNext();
    if (list.hasNext()) {
      System.out.println("man: Man command only takes one argument");
      error = true;
    }
    if (error == false) {
      boolean exists = super.checkCommand(wantDes);
      if (exists) {
        try {
          String className = commands.get(wantDes);
          try {
            Commands inputCommand =
                (Commands) (Class.forName(className).getDeclaredConstructor().newInstance());

            stdOut += inputCommand.getMan() + "\n";

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
      } else {
        System.out.println("Error: The command you want a description of does " + "not exist");
        error = true;
      }
    }
    return stdOut;
  }

  /**
   * 
   * Creates a specified manual on how to use the [Man] command.
   *
   * @return String for the manual documentation for [Man].
   */
  @Override
  public String getMan() {
    String description = "man CMD :\n\tPrint description for CMD(s)";
    return description;
  }
}
