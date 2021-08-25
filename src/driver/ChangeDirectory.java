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

public class ChangeDirectory extends Commands {
  /**
   * ChangeDirectory class is a subclass of Command that inherits the runCommand(String[]) method in
   * order to run the users input for cd
   *
   */

  private String stdOut = "";
  /**
   * The shorthands that refer to current directory and previous directory when taken as part of the
   * argument for cd
   */
  public static final String THIS_DIR = ".";
  public static final String PREV_DIR = "..";

  /**
   * Changes current working directory provided param with existing and well formed path and correct
   * number of arguments, otherwise does nothing.
   * 
   * @param ListIterator<String> list - arguments passed to cd command
   * @param FileSystemI f - FileSystemI containing current working directory
   * 
   * @return String The standard output of the PrintWorkingDirectory Class
   **/
  public String runCommand(ListIterator<String> list, FileSystemI f) {

    ArrayList<String> arguments = new ArrayList<String>();
    Directory original = f.getCurrentDirectory();
    while (list.hasNext()) {
      arguments.add(list.next());
    }

    if (arguments.size() == 0) {
      System.out.println("bash: cd: Must take 1 argument");

    } else if (arguments.size() == 1) {
      String input = arguments.get(0);
      if (input.contains("//"))
        System.out.println("bash: cd: Not a directory");
      else
        changeForInput(input, f, original);

    } else {
      System.out.println("bash: cd: too many arguments");
    }

    return stdOut;
  }

  /**
   * Changes current working directory provided param with existing and well formed path, otherwise
   * does nothing.
   * 
   * @param String input - one argument as needed by cd
   * @param FileSystemI f - FileSystemI containing current working directory
   **/
  public void changeForInput(String input, FileSystemI f, Directory original) {
    ArrayList<String> dirNameList = new ArrayList<String>();
    boolean base = false;
    if (containsOnly(input, "/")) {
      input = "/ ";
      dirNameList.add(f.getBaseDirectory().getName());
      base = true;
    }
    String[] pathRefs = input.split("/");
    int i = 0;
    Directory currDir;
    if (pathRefs[i].equals("")) {
      i++;
      currDir = f.getBaseDirectory();
    } else {
      currDir = f.getCurrentDirectory();
    }
    while (i < pathRefs.length) {
      if (pathRefs[i].equals(THIS_DIR) || pathRefs[i].equals("")) {
        // no effect
      } else if (pathRefs[i].equals(PREV_DIR) && currDir != null) {
        currDir = currDir.getParentDir();
      } else {
        dirNameList.add(pathRefs[i]);
      }
      i++;
    }
    while (currDir != f.getBaseDirectory() && currDir != null) {
      dirNameList.add(0, currDir.getName());
      currDir = currDir.getParentDir();
    }
    PathHandler p = new PathHandler();
    boolean found = p.parsePath(f, dirNameList, true) || base;
    if (!found) { // !found indicates no such path or path may end in File
      System.out.println("bash: cd: Not a directory");
      f.setCurrentDirectory(original);
    }
  }

  /**
   * Checks if s contains only toFind
   * 
   * @param String s - String to be checked
   * @param String toFind - char to be found
   **/
  public boolean containsOnly(String s, String toFind) {
    boolean success = true;
    for (int i = 0; i < s.length(); i++) {
      if (!(("" + s.charAt(i)).equals(toFind))) {
        success = false;
      }
    }
    return success;
  }

  public String getMan() {
    return "cd DIR:\n\tChange current working directory to path DIR, "
        + "which is a String:\n\t\t1. [.] current directory\n\t\t2. [..] "
        + "parent of current directory\n\t\t3. [/name1/name2/..] full "
        + "path\n\t\t4. [name1/name2/..] relative path";
  }

}
