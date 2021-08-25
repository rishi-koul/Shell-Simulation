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

public class Move extends Commands {
  /**
   * Move class is a subclass of Command that inherits the runCommand(String[]) method in order to
   * run the users input for mv
   *
   */

  /**
   * Moves item OLDPATH to NEWPATH. Behavior depends on the existence and class of OLDPATH and
   * NEWPATH
   * 
   * @param ListIterator<String> list - arguments passed to mv command
   * @param FileSystemI f - FileSystem containing current working directory
   * 
   * @return String The standard output of the PrintWorkingDirectory Class
   **/
  public String runCommand(ListIterator<String> list, FileSystemI f) {

    ArrayList<String> arguments = new ArrayList<String>();
    while (list.hasNext()) {
      arguments.add(list.next());
    }

    if (arguments.size() <= 0) {
      System.out.println("mv: missing file operand");

    } else if (arguments.size() == 1) {
      System.out.println("mv: missing destination file operand after '" + arguments.get(0) + "'");

    } else if (arguments.size() == 2) {
      String input1 = arguments.get(0);
      String input2 = arguments.get(1);
      PathHandler p = new PathHandler();

      boolean fullPath = input1.length() > 0 && input1.charAt(0) == '/';
      Directory dirToMove = p.pathToDir(f, input1, fullPath);
      File fileToMove = p.pathToFile(f, input1, fullPath);

      fullPath = input2.length() > 0 && input2.charAt(0) == '/';
      Directory dirMoveTo = p.pathToDir(f, input2, fullPath);
      File fileMoveTo = p.pathToFile(f, input2, fullPath);

      moveItem(dirToMove, fileToMove, input1, dirMoveTo, fileMoveTo, input2, f);

    } else {
      System.out.println("mv: too many arguments");
    }

    return "";

  }

  /**
   * Move an item, which may be a Directory or a File or neither (in which case nothing occurs) to
   * another item which may be a Directory or a File or a new Directory or new File. Behaviour
   * differs by case.
   * 
   * @param Directory dir1 - the Directory to be moved; null implies path1 does not designate an
   *        existing directory
   * @param File file1 - the File to be moved; null implies path1 does not designate an existing
   *        file
   * @param String path1 - the inputed operand to be moved
   * @param Directory dir2 - the Directory to be moved to; null implies path1 does not designate an
   *        existing directory
   * @param File file2 - the File to be moved to; null implies path1 does not designate an existing
   *        file
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully moved
   **/
  public boolean moveItem(Directory dir1, File file1, String path1, Directory dir2, File file2,
      String path2, FileSystemI f) {
    Directory currDir = f.getCurrentDirectory();
    if (dir1 != null) {
      if (dir2 != null) {
        // CASE 2. mv existing_dir_path1 existing_dir_path2 =
        return moveItem(dir1, path1, dir2, path2, f);
      } else if (file2 != null) {
        // cannot move Directory into File
        System.out.println(
            "mv: cannot overwrite non-directory '" + path2 + "' with directory '" + path1 + "'");
        return false;
      } else { // (path2.charAt(path2.length()-1) == '/')
        // CASE 3. mv existing_dir_path1 new_dir_path
        // AND mv existing_dir_path1 new_file_path
        // tested in bash, seem to be indistinct
        return moveItem(dir1, path1, path2, f);
      }
    } else if (file1 != null) {
      if (dir2 != null) {
        // CASE 1. mv existing_file_path existing_dir_path
        return moveItem(file1, path1, dir2, path2, f);
      } else if (file2 != null) {
        // CASE 6 . mv existing_file_path1 existing_file_path2
        Directory parent = file2.getParentDir();
        parent.removeSubFile(file2);
        file1.getParentDir().removeSubFile(file1);
        parent.addFile(file1);
        file1.setParentDir(parent);
        return true;
      } else if (path2.charAt(path2.length() - 1) == '/') {
        // CASE 4. mv existing_file_path1 new_dir_path
        System.out.println(
            "mv: cannot move '" + path1 + "' to '" + path2 + "': No such file or directory");
        return false;
      } else {
        // CASE 5. mv existing_file_path1 new_file_path
        return moveItem(file1, path1, path2, f);
      }
    } else {
      System.out.println("mv: cannot stat '" + path1 + "': No such file or " + "directory");
      return false;
    }
  }


  /**
   * Completes the general moveItem() for CASE 2
   * 
   * @param Directory dir1 - the Directory to be moved; null implies path1 does not designate an
   *        existing directory
   * @param String path1 - the inputed operand to be moved
   * @param Directory dir2 - the Directory to be moved to; null implies path1 does not designate an
   *        existing directory
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully moved
   **/
  public boolean moveItem(Directory dir1, String path1, Directory dir2, String path2,
      FileSystemI f) {

    // CASE 2. mv existing_dir_path1 existing_dir_path2 =
    // moves dir at 'existing_dir_path1' inside dir 'existing_dir_path2'
    Directory currDir = f.getCurrentDirectory();
    if (dir1.checkDescendant(dir2)) {
      // cannot move parent to child
      String[] itemNames = path1.split("/");
      String lastItem = itemNames[itemNames.length - 1];
      if (!(path2.charAt(path2.length() - 1) == '/')) {
        lastItem = "/" + lastItem;
      }
      System.out.println("mv: cannot move '" + path1 + "' to a subdirectory" + " of itself, '"
          + path2 + lastItem + "'");
      return false;
    } else if (dir1.checkDescendant(currDir) && dir1 != currDir) {
      // cannot move the direct ancestor of current working directory
      // BUT CAN move the current working directory (tested on bash)
      System.out
          .println("mv: cannot move '" + path1 + "' to '" + path2 + "': Device or resource busy");
      return false;
    } else if (dir2.checkSubdirectory(dir1.getName()) != null) {
      // cannot move into a directory containing a directory with a duplicate
      // name, except if the duplicate directory is empty
      Directory dupDir = dir2.checkSubdirectory(dir1.getName());
      if (dupDir.getAllSubDirectories() == null || dupDir.getAllSubDirectories().size() == 0) {
        // move can occur. Duplicate directory is overridden
        dupDir.getParentDir().removeSubDirectory(dupDir);
        dir1.getParentDir().removeSubDirectory(dir1);
        dir2.addDirectory(dir1);
        dir1.setParentDir(dir2);
        return true;
      }
      System.out.println("mv: cannot move '" + path1 + "' to '" + path2 + "/" + dupDir.getName()
          + "': Directory not empty");
      return false;
    } else {
      // move can occur
      dir1.getParentDir().removeSubDirectory(dir1);
      dir2.addDirectory(dir1);
      dir1.setParentDir(dir2);
      return true;
    }
  }


  /**
   * Completes the general moveItem() for CASE 3
   * 
   * @param Directory dir1 - the Directory to be moved; null implies path1 does not designate an
   *        existing directory
   * @param String path1 - the inputed operand to be moved
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully moved
   **/
  public boolean moveItem(Directory dir1, String path1, String path2, FileSystemI f) {
    // CASE 3. mv existing_dir_path1 new_dir_path
    String[] itemNames = path2.split("/");
    String newPath = "";
    for (int i = 0; i < itemNames.length - 1; i++) {
      newPath = newPath + itemNames[i] + "/";
    }
    PathHandler p = new PathHandler();
    boolean fullPath = path2.length() > 0 && path2.charAt(0) == '/';
    if (fullPath) {
      newPath = "/" + newPath;
    }
    Directory parent = p.pathToDir(f, newPath, fullPath);
    if (parent == null) {
      System.out
          .println("mv: cannot move '" + path1 + "' to '" + path2 + "': No such file or directory");
      return false;
    } else {
      String tempName = dir1.getName();
      dir1.setName(itemNames[itemNames.length - 1]);
      // we are now essentially in CASE 2
      if (moveItem(dir1, path1, parent, path2, f)) {
        return true;
      }
      dir1.setName(tempName);
      return false;
    }
  }

  /**
   * Completes the general moveItem() for CASE 1
   * 
   * @param File file1 - the File to be moved; null implies path1 does not designate an existing
   *        file
   * @param String path1 - the inputed operand to be moved
   * @param Directory dir2 - the Directory to be moved to; null implies path1 does not designate an
   *        existing directory
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully moved
   **/
  public boolean moveItem(File file1, String path1, Directory dir2, String Path2, FileSystemI f) {
    // CASE 1. mv existing_file_path existing_dir_path
    File dupFile = dir2.checkSubFile(file1.getName());
    // file with duplicate name is overridden
    if (dupFile != null) {
      dupFile.getParentDir().removeSubFile(dupFile);
    }
    file1.getParentDir().removeSubFile(file1);
    dir2.addFile(file1);
    file1.setParentDir(dir2);
    return true;
  }

  /**
   * Completes the general moveItem() for CASE 5
   * 
   * @param File file1 - the File to be moved; null implies path1 does not designate an existing
   *        file
   * @param String path1 - the inputed operand to be moved
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully moved
   **/
  public boolean moveItem(File file1, String path1, String path2, FileSystemI f) {
    // CASE 5. mv existing_file_path1 new_file_path
    String[] itemNames = path2.split("/");
    String newPath = "";
    for (int i = 0; i < itemNames.length - 1; i++) {
      newPath = newPath + itemNames[i] + "/";
    }
    PathHandler p = new PathHandler();
    boolean fullPath = path2.length() > 0 && path2.charAt(0) == '/';
    if (fullPath) {
      newPath = "/" + newPath;
    }
    Directory parent = p.pathToDir(f, newPath, fullPath);
    if (parent == null) {
      System.out
          .println("mv: cannot move '" + path1 + "' to '" + path2 + "': No such file or directory");
      return false;
    } else {
      file1.setName(itemNames[itemNames.length - 1]);
      // We are now essentially in CASE 1
      return moveItem(file1, path1, parent, path2, f);
    }
  }

  public String getMan() {
    return "mv OLDPATH NEWPATH:\n\tMove item OLDPATH to item NEWPATH, "
        + "behaviour depends on the\n\texistence of NEWPATH, and whether "
        + "OLDPATH is a Directory\n\tor File, and whether NEWPATH is a " + "Directory or file.";
  }

}
