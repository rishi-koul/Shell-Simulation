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


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Stack;

public class SaveJShell extends Commands implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * A private variable main of type main
   */
  private root main;

  class root implements Serializable {
    private static final long serialVersionUID = 1L;

    private Directory Base; // A variable referring to the Base directory

    private Directory Current; // A variable referring to the the current directory


    /**
     * An array list containing the entire history of command input
     */
    private ArrayList<String> cmdhistory = new ArrayList<String>();

    /**
     * A Stack containing the main stack
     */
    private Stack<String> mainStack = new Stack<String>();

    /**
     * A function to get the Base Directory
     */
    public Directory getBase() {
      return Base;
    }

    /**
     * A function to set the Base Directory
     * 
     * @param Base Directory
     */
    public void setBase(Directory base) {
      Base = base;
    }

    /**
     * A function to get the array list of the entire command History
     */
    public ArrayList<String> getCmdhistory() {
      return cmdhistory;
    }

    /**
     * A function to set the array list of the entire command History
     * 
     * @param The cmdhistory of FileSystem
     */
    public void setCmdhistory(ArrayList<String> cmdhistory) {
      this.cmdhistory = cmdhistory;
    }

    /**
     * A function to get the Current Directory
     */
    public Directory getCurrent() {
      return Current;
    }

    /**
     * A function to set the Current Directory
     * 
     * @param Current Directory
     */
    public void setCurrent(Directory current) {
      Current = current;
    }

    /**
     * A function to get the main stack
     */
    public Stack<String> getMainStack() {
      return mainStack;
    }

    /**
     * A function to set the main stack
     * 
     * @param The mainStack of current FileSystem
     */
    public void setMainStack(Stack<String> mainStack) {
      this.mainStack = mainStack;
    }


  }

  /**
   * Uses an array list of users input arguments and Saves the session of the JShell and of the
   * entire mock FileSystem including any state in the [FILE] provided by the user in the @param
   * "list"
   * 
   * @param list is an array of strings corresponding to the users input
   * @param fs the FileSystem
   * @return String
   */
  public String runCommand(ListIterator<String> list, FileSystemI fs) {
    main = new root();
    main.setBase(fs.getBaseDirectory());
    main.setCmdhistory(History.cmdhistory);
    main.setCurrent(fs.getCurrentDirectory());
    main.setMainStack(fs.getMainStack());

    try {
      list.add("end");
      String FileName = "";
      if (!list.hasNext()) {
        System.out.println("Erorr: Invald # of arguments");
        return "";
      }
      FileName = list.next();
      if (list.hasNext()) {
        System.out.println("Erorr: Invald # of arguments");
        return "";
      }
      if (checkForInvalidFileName(FileName).get(0).equals("false")) {
        System.out.println("Erorr: Invalid FileName");
        return "";
      }
      FileOutputStream fos = new FileOutputStream(FileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(main);
      oos.close();
    } catch (FileNotFoundException e) {
      System.out.println("Erorr: file not found");
    } catch (IOException e) {
      System.out.println("Erorr: file not found");
    }
    return "";
  }

  /**
   * File name can't contains several special characters. This functions checks whether the provided
   * File name contains any of these characters
   * 
   * @param name The name of the File
   * @return returns an array list where whether the dir contains any of these characters as "true"
   *         or "false" is the 1st index and the the invalid character as the 2nd index
   */
  private ArrayList<String> checkForInvalidFileName(String name) {
    ArrayList<String> validityCharPair = new ArrayList<String>();
    validityCharPair.add("true");
    validityCharPair.add(" ");
    ArrayList<String> invalidChars = new ArrayList<String>();
    invalidChars.addAll(Arrays.asList(" ", "!", "@", "#", "$", "%"));
    invalidChars.addAll(Arrays.asList("^", "&", "*", "(", ")", "{", "}", "~"));
    invalidChars.addAll(Arrays.asList("|", "<", ">", "?"));
    for (String s : invalidChars) {
      if (name.contains(s)) {
        validityCharPair.set(0, "false");
        validityCharPair.set(1, s);
        break;
      }
    }
    return validityCharPair;
  }

  /**
   * Creates a specified manual on how to use the [saveJShell] command.
   *
   * @return String for the manual documentation for [saveJShell].
   */
  public String getMan() {
    return "saveJshell FILENAME:\n" + "\tSave the session of the JShell.\n"
        + "\tThe entire mock flesystem including any state\n"
        + "\tof any of the commands is written to FileName\n";
  }
}
