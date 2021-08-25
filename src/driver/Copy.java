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

public class Copy extends Commands {
  /**
   * Copy class is a subclass of Command that inherits the runCommand(String[]) method in order to
   * run the users input for cp
   *
   */

  /**
   * Copies item OLDPATH to NEWPATH. Behavior depends on the existence and class of OLDPATH and
   * NEWPATH
   * 
   * @param ListIterator<String> list - arguments passed to cp command
   * @param FileSystemI f - FileSystem containing current working directory
   **/
  public String runCommand(ListIterator<String> list, FileSystemI f) {

    ArrayList<String> arguments = new ArrayList<String>();
    while (list.hasNext()) {
      arguments.add(list.next());
    }

    if (arguments.size() <= 0) {
      System.out.println("cp: missing file operand");

    } else if (arguments.size() == 1) {
      String shorter = "cp: missing destination file operand after '";
      System.out.println(shorter + arguments.get(0) + "'");

    } else if (arguments.size() == 2) {
      String input1 = arguments.get(0);
      String input2 = arguments.get(1);
      PathHandler p = new PathHandler();

      boolean fullPath = input1.length() > 0 && input1.charAt(0) == '/';
      Directory dirToCopy = p.pathToDir(f, input1, fullPath);
      File fileToCopy = p.pathToFile(f, input1, fullPath);

      fullPath = input2.length() > 0 && input2.charAt(0) == '/';
      Directory dirCopyTo = p.pathToDir(f, input2, fullPath);
      File fileCopyTo = p.pathToFile(f, input2, fullPath);

      copyItem(dirToCopy, fileToCopy, input1, dirCopyTo, fileCopyTo, input2, f);

    } else {
      System.out.println("cp: too many arguments");
    }

    return "";

  }

  /**
   * Copy an item, which may be a Directory or a File or neither (in which case nothing occurs) to
   * another item which may be a Directory or a File or a new Directory or new File. Behaviour
   * differs by case.
   * 
   * @param Directory dir1 - the Directory to be copied; null implies path1 does not designate an
   *        existing directory
   * @param File file1 - the File to be copied; null implies path1 does not designate an existing
   *        file
   * @param String path1 - the inputed operand to be copied
   * @param Directory dir2 - the Directory to be copied to; null implies path1 does not designate an
   *        existing directory
   * @param File file2 - the File to be copied to; null implies path1 does not designate an existing
   *        file
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully copied
   **/
  public boolean copyItem(Directory dir1, File file1, String path1, Directory dir2, File file2,
      String path2, FileSystemI f) {

    Directory currDir = f.getCurrentDirectory();
    if (dir1 != null) {
      if (dir2 != null) {
        // CASE 8. cp existing_dir_path1 existing_dir_path2
        return copyItem(dir1, path1, dir2, path2, f);
      } else if (file2 != null) {
        // cannot copy Directory into File
        System.out.println(file2 == null);
        System.out.println(file2.getName() + file2.getParentDir().getName());
        String shorter = "cp: cannot overwrite non-directory '" + path2;
        System.out.println(shorter + "' with directory '" + path1 + "'");
        return false;
      } else { // (path2.charAt(path2.length()-1) == '/')
        // CASE: cp existing_dir_path1 new_dir_path
        // CASE 11. cp existing_dir_path new_file_path
        // tested in bash, seem to be indistinct
        return copyItem(dir1, path1, path2, f);
      }
    } else if (file1 != null) {
      if (dir2 != null) {
        // CASE 7. cp existing_file_path existing_dir_path
        return copyItem(file1, path1, dir2, path2, f);
      } else if (file2 != null) {
        // CASE 9. cp existing_file_path1 existing_file_path2
        file2.setContent(file1.getcontent());
        return true;
      } else if (path2.charAt(path2.length() - 1) == '/') {
        // CASE: cp existing_file_path1 new_dir_path
        String shorter = "cp: cannot copy '" + path1 + "' to '" + path2;
        System.out.println(shorter + "': No such file or directory");
        return false;
      } else {
        // CASE 10. cp existing_file_path new_file_path
        return copyItem(file1, path1, path2, f);
      }
    } else {
      String shorter = "cp: cannot stat '" + path1;
      System.out.println(shorter + "': No such file or " + "directory");
      return false;
    }
  }


  /**
   * Completes the general copyItem() for CASE 8
   * 
   * @param Directory dir1 - the Directory to be copied; null implies path1 does not designate an
   *        existing directory
   * @param String path1 - the inputed operand to be copied
   * @param Directory dir2 - the Directory to be copied to; null implies path1 does not designate an
   *        existing directory
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully copied
   **/
  public boolean copyItem(Directory dir1, String path1, Directory dir2, String path2,
      FileSystemI f) {

    // CASE 8. cp existing_dir_path1 existing_dir_path2
    // copies dir at 'existing_dir_path1' inside dir 'existing_dir_path2'
    Directory currDir = f.getCurrentDirectory();
    if (dir1.checkDescendant(dir2)) {
      // cannot copy parent to child
      String[] itemNames = path1.split("/");
      String lastItem = itemNames[itemNames.length - 1];
      if (!(path2.charAt(path2.length() - 1) == '/')) {
        lastItem = "/" + lastItem;
      }
      System.out.println("cp: cannot copy '" + path1 + "' to a subdirectory" + " of itself, '"
          + path2 + lastItem + "'");
      return false;
    }
    // else if(dir1.checkDescendant(currDir) && dir1 != currDir) {
    // //cannot copy the direct ancestor of current working directory
    // //BUT CAN copy the current working directory (tested on bash)
    // System.out.println("cp: cannot copy '" + path1 + "' to '" + path2
    // + "': Device or resource busy");
    // return false;
    // }
    else if (dir2.checkSubdirectory(dir1.getName()) != null) {
      // cannot copy into a directory containing a directory with a duplicate
      // name, except if the duplicate directory is empty
      Directory dupDir = dir2.checkSubdirectory(dir1.getName());
      if (dupDir.getAllSubDirectories() == null || dupDir.getAllSubDirectories().size() == 0) {
        // copy can occur. Duplicate directory is overridden
        dupDir.getParentDir().removeSubDirectory(dupDir);
        Directory copy = copy(dir1);
        dir2.addDirectory(copy);
        copy.setParentDir(dir2);
        return true;
      }
      System.out.println("cp: cannot copy '" + path1 + "' to '" + path2 + "/" + dupDir.getName()
          + "': Directory not empty");
      return false;
    } else {
      // copy can occur
      Directory copy = copy(dir1);
      dir2.addDirectory(copy);
      copy.setParentDir(dir2);
      return true;
    }
  }


  /**
   * Completes the general copyItem() for CASE: cp existing_dir_path1 new_dir_path
   * 
   * @param Directory dir1 - the Directory to be copied; null implies path1 does not designate an
   *        existing directory
   * @param String path1 - the inputed operand to be copied
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully copied
   **/
  public boolean copyItem(Directory dir1, String path1, String path2, FileSystemI f) {
    // CASE: cp existing_dir_path1 new_dir_path
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
          .println("cp: cannot copy '" + path1 + "' to '" + path2 + "': No such file or directory");
      return false;
    } else {
      String tempName = dir1.getName();
      dir1.setName(itemNames[itemNames.length - 1]);
      // we are now essentially in CASE 2
      if (copyItem(dir1, path1, parent, path2, f)) {
        return true;
      }
      dir1.setName(tempName);
      return false;
    }
  }

  /**
   * Completes the general copyItem() for CASE 7
   * 
   * @param File file1 - the File to be copied; null implies path1 does not designate an existing
   *        file
   * @param String path1 - the inputed operand to be copied
   * @param Directory dir2 - the Directory to be copied to; null implies path1 does not designate an
   *        existing directory
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully copied
   **/
  public boolean copyItem(File file1, String path1, Directory dir2, String Path2, FileSystemI f) {
    // CASE 7. cp existing_file_path existing_dir_path
    File dupFile = dir2.checkSubFile(file1.getName());
    // file with duplicate name is overridden
    if (dupFile != null) {
      dupFile.getParentDir().removeSubFile(dupFile);
    }
    File copy = copy(file1);
    dir2.addFile(copy);
    copy.setParentDir(dir2);
    return true;
  }

  /**
   * Completes the general copyItem() for CASE 10
   * 
   * @param File file1 - the File to be copied; null implies path1 does not designate an existing
   *        file
   * @param String path1 - the inputed operand to be copied
   * @param String path2 - the inputed operand to be the destination
   * @param FileSystemI f - FileSystem containing current working directory
   * @return - whether the function successfully copied
   **/
  public boolean copyItem(File file1, String path1, String path2, FileSystemI f) {
    // CASE 10. cp existing_file_path new_file_path
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
          .println("cp: cannot copy '" + path1 + "' to '" + path2 + "': No such file or directory");
      return false;
    } else {
      file1.setName(itemNames[itemNames.length - 1]);
      // We are now essentially in CASE 7
      return copyItem(file1, path1, parent, path2, f);
    }
  }

  /**
   * returns a copy of Directory dir, save for the parent attribute
   * 
   * @param Directory dir - the directory to be copied
   * @return - the copied directory
   **/

  public Directory copy(Directory dir) {
    Directory copy = new Directory();
    copy.setName(dir.getName());
    Directory temp = new Directory();
    for (Directory d : dir.getAllSubDirectories()) {
      temp = new Directory();
      temp = copy(d);
      temp.setParentDir(dir);
      copy.addDirectory(temp);
    }
    File tempf = new File();
    for (File f : dir.getAllSubFiles()) {
      tempf = new File();
      tempf = copy(f);
      tempf.setParentDir(dir);
      copy.addFile(tempf);
    }
    return copy;
  }

  /**
   * returns a copy of File file, save for the parent attribute
   * 
   * @param Directory dir - the directory to be copied
   * @return - the copied directory
   **/
  public File copy(File file) {
    File copy = new File();
    copy.setName(file.getName());
    copy.setContent(file.getcontent());
    return copy;
  }

  public String getMan() {
    return "cp OLDPATH NEWPATH:\n\tCopy item OLDPATH to item NEWPATH, "
        + "behaviour depends on the\n\texistence of NEWPATH, and whether "
        + "OLDPATH is a Directory\n\tor File, and whether NEWPATH is a "
        + "Directory or file. Copies\n\trecursively where possible.";
  }
}
