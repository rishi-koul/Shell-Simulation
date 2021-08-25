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

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

public class PushDirectory extends Commands {

  /**
   * Pushd class is a subclass of Command that inherits the runCommand(String[]) method in order to
   * run the users input for pushd dirName
   *
   */

  /**
   * This string stores the standard output of PushDirectory class
   */
  private String stdOut = "";
  /**
   * The stack that later will refer to the mainStack
   */

  public Stack<String> stack = new Stack<String>();

  /**
   * A variable the will later be initialized to the current location in the fileSystem
   */
  
  public FileSystemI location;

  /**
   * A boolean to determine is the given path is relativePath or fullPath
   */
  public boolean isFullPath;

  /**
   * A variable to keep track of the current directory
   */
  public Directory original;

  /**
   * Checks whether the provided dirName is a subDir of the current location
   * 
   * @param dirName The name of directory to be checked
   * @return Whether it is a sub directory or not
   */

  public boolean checkIfSubDirectory(String dirName) {
    boolean isSubDir = false;
    for (Directory dir : location.getAllSubDirectories()) {
      if (dir.getName().equals(dirName)) {
        isSubDir = true;
      }
    }
    return isSubDir;
  }

  /**
   * As users only passes a string we need to convert that string to its respective directory. This
   * function performs this task
   * 
   * @param dirName The name of the directory whose address needs to be found
   * @return Address of the directory has the same name as the argument
   */

  public Directory getAddressOfDir(String dirName) {

    Directory temp = new Directory();
    for (Directory dir : location.getAllSubDirectories()) {
      if (dir.getName().equals(dirName)) {
        temp = dir;
        break;
      }
    }
    return temp;
  }

  /**
   * Pushd commands works by adding to a stack. This function adds the provided directory name to
   * the stack
   * 
   * @param dirName The dirName that the user pases
   */

  public void addToStack(String dirName) {
    if (!isFullPath) {
      String prevDir = location.getDirectoryPathAsString();
      prevDir = prevDir + location.getCurrentDirectory().getName();
      if (prevDir.matches("/")) {
        dirName = prevDir + dirName;
      } else {
        dirName = prevDir + "/" + dirName;
      }
    }
    location.addToMainStack(dirName);
  }


//  /**
//   * every time pushd cmd is executed it print the new stack. This func exactly carries out that
//   * function i.e print the stack
//   */
//
//  private void printResult() {
//
//    for (int i = stack.size() - 1; i >= 0; i--) {
//
//      stdOut += "~" + stack.get(i) + "  ";
//    }
//    stdOut += "\n";
//
//  }

  /**
   * Checks whether the path given is user is full path or not
   * 
   * @param name The path that the user passes
   * @return Whether the path is full or not
   */

  public boolean checkForFullPath(String name) {
    boolean isFullPath = false;
    if (name.startsWith("/")) {
      isFullPath = true;
    }
    return isFullPath;
  }


  /**
   * It parses the path passed by the user. Also checks whether the path is valid or not
   * 
   * @param name The path that the user passes
   * @param isFullPath Whether its a full path or not
   * @return An array containing whether the path is correct or not as string "true" or "false" at
   *         1st index and the current name as the 2nd index
   */

  public ArrayList<String> parsePath(String name, boolean isFullPath) {
    ArrayList<String> correctPathAndNamePair = new ArrayList<String>();
    String tempPathAsArray[] = name.split("/");
    ArrayList<String> pathAsArray = new ArrayList<>();

    for (String i : tempPathAsArray) {
      if (i.equals("") || i.equals(" ")) {

      } else {
        pathAsArray.add(i);
      }
    }
    PathHandler pathHandler = new PathHandler();

    name = pathAsArray.get(pathAsArray.size() - 1);
    pathAsArray.remove(pathAsArray.size() - 1);
    String isCorrect;
    if (pathHandler.parsePath(location, pathAsArray, isFullPath))
      isCorrect = "true";
    else
      isCorrect = "false";

    correctPathAndNamePair.add(isCorrect);
    correctPathAndNamePair.add(name);
    return correctPathAndNamePair;
  }

  /**
   * pushd not only adds to the stack but also changes directory to the one user passes. This func
   * is performed by moveDirectory()
   * 
   */
  public void moveDirectory() {
    String dirPath = stack.get(stack.size() - 1).trim();
    String tempPathAsArray[] = dirPath.split("/");
    ArrayList<String> pathAsArray = new ArrayList<>();

    for (String i : tempPathAsArray) {
      if (i.equals("") || i.equals(" ")) {

      } else {
        pathAsArray.add(i);
      }
    }

    location.setCurrentDirectory(location.getBaseDirectory());

    for (int i = 0; i < pathAsArray.size(); i++) {
      location.setCurrentDirectory(getAddressOfDir(pathAsArray.get(i)));
    }

  }

  /**
   * This function deals the case if user passes the command pushd ..
   */
  public void dealWithDoubleDot() {
    if(location.getParentDir() == null){
      System.out.println("pushd: pushd .. can't be performed at base");
      return;
    }
    Directory original = location.getCurrentDirectory();
    String path = location.getDirectoryPathAsString();
    
    if(location.getParentDir().getName().equals("/")) {
      location.setCurrentDirectory(original);
      String small = location.getDirectoryPathAsString();
      small = small + location.getCurrentDirectory().getName();
      if (stack.isEmpty())
        location.addToMainStack(small);
      else
        stack.set(stack.size() - 1, small);
      addToStack("/");
      moveDirectory();
//      printResult();
    }
    else {
      pushDir(path, path);
    }
    
  }
  
  
  /**
   * This function deals the case if user passes the command pushd .
   */
  public void dealWithDot() {
    Directory original = location.getCurrentDirectory();
    String path = location.getDirectoryPathAsString();
    if(location.getCurrentDirectory().getName().equals("/")) {
      location.setCurrentDirectory(original);
      String small = location.getDirectoryPathAsString();
      small = small + location.getCurrentDirectory().getName();
      if (stack.isEmpty())
        location.addToMainStack(small);
      else
        stack.set(stack.size() - 1, small);
      addToStack("/");
      moveDirectory();
//      printResult();
    }
    else {
      location.setCurrentDirectory(original);
      String curr = location.getCurrentDirectory().getName();
//      System.out.println(path + curr);
      pushDir(path + curr, path + curr);
    }
    location.setCurrentDirectory(original);
  }

  /**
   * This function combines all the functioning of pushd, by adding to stack printing the result and
   * then moving to the correct directory
   * 
   * @param dirPath The path passed by the user
   * @param dirName Name of last directory in the path
   */

  public void pushDir(String dirPath, String dirName) {
    ArrayList<String> correctPathAndNamePair = new ArrayList<String>();
    if (isFullPath)
      correctPathAndNamePair = parsePath(dirName, true);
    else
      correctPathAndNamePair = parsePath(dirName, false);
    if (correctPathAndNamePair.get(0) == "true") {
      dirName = correctPathAndNamePair.get(1);
      if (checkIfSubDirectory(dirName)) {
        location.setCurrentDirectory(original);
        String small = location.getDirectoryPathAsString();
        small = small + location.getCurrentDirectory().getName();
        if (stack.isEmpty())
          location.addToMainStack(small);
        else
          stack.set(stack.size() - 1, small);
        addToStack(dirPath);
//        printResult();
        moveDirectory();
      } else {
        location.setCurrentDirectory(original);
        System.out.println("pushd: " + dirName + ": No such file or directory");
      }
    } else {
      System.out.println("pushd: Incorrect Path");
      location.setCurrentDirectory(original);
    }
  }

  /**
   * 
   * This function runs the pushd commands. It also checks whether the right no.of arguments are
   * passed for pushd. If not give appropriate error messages. Also intializes the stack variable to
   * the mainStack and sets the location variable to the currentLocation. Also checks if the path is
   * a fullPath or not
   * 
   * @return String The standard output of the PushDirectory class
   */
  public String runCommand(ListIterator<String> list, FileSystemI location) {

    this.location = location;
    original = location.getCurrentDirectory();
    stack = location.getMainStack();
    if (!list.hasNext()) {
      System.out.println("pushd: pushd takes 1 argument");
      return stdOut;
    }
    String dirName = list.next();
    if (list.hasNext()) {
      System.out.println("pushd: pushd takes only 1 argument");
      return stdOut;
    }
    if (dirName.equals("/") || dirName.contains("//")) {
      System.out.println("pushd: "+ dirName + ": No such file or directory");
      return stdOut;
    }
    String dirPath = dirName;
    this.isFullPath = checkForFullPath(dirName);
    if(dirPath.equals("."))
    {
      this.isFullPath = true;
      dealWithDot(); 
    }
    else if(dirPath.equals(".."))
    {
      this.isFullPath = true;
      dealWithDoubleDot(); 
    }
    else
      pushDir(dirPath, dirName);
    return stdOut;
  }

  /**
   * Function that can be used to get the description of the pushd command
   */
  public String getMan() {
    return "pushd DIR\n Saves the current working directory by pushing onto \n"
        + "directory stack and then changes the new current working directory\n"
        + " to DIR. The push must be consistent as per the LIFO behavior of\n"
        + " a stack.   The pushd command saves the old current working \n"
        + "directory in directory stack so that it can be returned to at any \n"
        + "time (via the popd command).  The size of the directory stack is \n"
        + "dynamic and dependent on the pushd and the popd commands.  \n";
  }
}
