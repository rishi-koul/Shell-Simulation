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
import java.util.Arrays;
import java.util.ListIterator;

public class MakeDirectory extends Commands {

  /**
   * MakeDirectory class is a subclass of Command that inherits the runCommand(String[]) method in
   * order to run the users input for mkdir dirName dirName
   *
   */


  /**
   * This string stores the standard output of the
   */
  String stdOut = "";
  /**
   * A variable the will later be initialized to the current location in the fileSystem
   */
  public FileSystemI location;

  /**
   * This is a list of all the directories that the user passes
   */
  public ArrayList<String> allDirectories = new ArrayList<String>();

  /**
   * A variable that will later refer to the first dir name that the user passes
   */

  public String dirName;

  /**
   * A boolean to determine is the given path is fullPath or not
   */

  public boolean isFullPath;

  /**
   * A boolean to determine is the given path is relativePath or not
   */

  public boolean isRelativePath;

  /**
   * Constructor to initialize all variables to null
   */

  public MakeDirectory() {
    this.location = null;
    this.dirName = null;
  }

  /**
   * dir with same names can't be created at the same location. This function ensures no dir with
   * duplicate names exists
   * 
   * @param name The name of the directory
   * @return whether duplicate exists or not
   */

  public boolean checkForDuplicates(String name) {

    boolean z = false;
    for (Directory dir : location.getAllSubDirectories()) {
      if (dir.getName().equals(name)) {
        z = true;
        break;
      }
    }
    return z;
  }

  /**
   * dir name can't contains several special characters. This functions checks whether the provided
   * dir name conatins any of these characters
   * 
   * @param name The name of the directory
   * @return returns an array list where whether the dir contains any of these characters as "true"
   *         or "false" is the 1st index and the the invalid character as the 2nd index
   */
  public ArrayList<String> checkForInvalidCmd(String name) {
    ArrayList<String> validityCharPair = new ArrayList<String>();
    validityCharPair.add("true");
    validityCharPair.add(" ");
    ArrayList<String> invalidChars = new ArrayList<String>();
    invalidChars.addAll(Arrays.asList("/", ".", " ", "!", "@", "#", "$", "%"));
    invalidChars.addAll(Arrays.asList("^", "&", "*", "(", ")", "{", "}", "~"));
    invalidChars.addAll(Arrays.asList("|", "<", ">", "?"));
    for (String s : invalidChars) {
      if (name.contains(s)) {
        if (this.isFullPath || this.isRelativePath) {
          if (s.equals("/")) {
          } else {
            validityCharPair.set(0, "false");
            validityCharPair.set(1, s);
            break;
          }
        } else {
          validityCharPair.set(0, "false");
          validityCharPair.set(1, s);
          break;
        }
      }
    }
    return validityCharPair;
  }

  /**
   * This function creates a directory under the current directory with the same name as the user
   * passes. Also checks whether a dir with same name already exists
   * 
   * @param name The name of the directory
   * @return Whether the dir is created or not.
   */
  public boolean createDir(String name) {

    boolean dirCreated = false;
    String invalid = checkForInvalidCmd(name).get(1);

    if (checkForInvalidCmd(name).get(0) == "false") {
      System.out.print("mkdir: " + name + ": ");
      System.out.println("Directory names can't contain '" + invalid + "'");
    } else {
      if (!checkForDuplicates(name)) {
        if (name.contains("\\"))
          name = name.replace("\\", "");

        Directory temp = new Directory();
        temp.setName(name);
        temp.setParentDir(location.getCurrentDirectory());
        location.addDirectory(temp);
        dirCreated = true;
      } else {
        System.out.println("mkdir: " + name + " : Directory already exists");
      }
    }
    return dirCreated;
  }

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
   * Checks whether the path given is user is relative path or not
   * 
   * @param name The path that the user passes
   * @return Whether the path is relative or not
   */

  public boolean checkForRelativePath(String name) {
    boolean isRelativePath = false;
    String tempDir[] = name.split("/");
    ArrayList<String> pathAsArray = new ArrayList<String>();
    for (String i : tempDir) {
      if (i.equals("") || i.equals(" ")) {

      } else {
        pathAsArray.add(i);
      }
    }

    if (pathAsArray.size() >= 2)
      isRelativePath = true;
    return isRelativePath;
  }

  /**
   * It parses the path passed by the user. Also checks whether the path is valid or not
   * 
   * @param name The path that the user passes
   * @return An array containing whether the path is correct or not as string "true" or "false" at
   *         1st index and the current name as the 2nd index
   */

  public ArrayList<String> parsePath(String name) {

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
   * The mkdir takes in two arguments. This function works on the 1st argument and creating the 1st
   * directory
   * 
   * @param orginal The original location(to ensure mkdir doesn't changes location)
   */

  public void createFirstDir(Directory original, int index) {
    ArrayList<String> correctPathAndNamePair = new ArrayList<String>();
    boolean success = false;
    String firstDir = dirName;

    if (isFullPath || isRelativePath) {
      correctPathAndNamePair = parsePath(firstDir);
      dirName = correctPathAndNamePair.get(1);
      if (correctPathAndNamePair.get(0).equals("true"))
        success = createDir(dirName);
      else {
        System.out.println("mkdir: Path " + firstDir + " is incorrect");
        success = false;
      }
    } else {
      location.setCurrentDirectory(original);
      success = createDir(dirName);
    }
    if (success) {
      if (index + 1 != allDirectories.size()) {
        dirName = allDirectories.get(index + 1);
        this.isFullPath = checkForFullPath(dirName);
        this.isRelativePath = checkForRelativePath(dirName);
        location.setCurrentDirectory(original);
        createFirstDir(original, index + 1);
      }
    }
  }

  /**
   * The main function that runs the mkdir command. It also checks if no.of arguments passed are
   * correct, gives back appropraiet errors if not.
   * 
   * @return String The standard output of mkdir command
   */
  public String runCommand(ListIterator<String> list, FileSystemI location) {

    this.location = location;
    if (!list.hasNext()) {
      System.out.println("mkdir: mkdir needs atleast 1 argument");
      return stdOut;
    }
    while (list.hasNext()) {
      allDirectories.add(list.next());
    }

    dirName = allDirectories.get(0);
    if (dirName.equals("/")) {
      String append = ": Directory names can't contain '/'";
      System.out.println("mkdir: " + dirName + append);
      return stdOut;
    }

    Directory original = location.getCurrentDirectory();
    this.isFullPath = checkForFullPath(dirName);
    this.isRelativePath = checkForRelativePath(dirName);

    createFirstDir(original, 0);
    location.setCurrentDirectory(original);
    return stdOut;
  }

  /**
   * A function that can be used to get the description the mkdir command
   */
  public String getMan() {
    return "mkdir DIR1 DIR2 \n\t This command takes in two arguments only. "
        + "Create directories, \n\teach of which may be relative to the"
        + " current directory or may be\n\t a full path. If creating DIR1 "
        + "results in any kind of error, do \n\t not proceed with creating DIR"
        + " 2. However, if DIR1 was created \n\t successfully, and DIR2 "
        + "creation results in an error, then give \n\t back an error " + "specific to DIR2. ";
  }

}
