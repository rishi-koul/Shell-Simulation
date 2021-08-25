package driver;

import java.util.ArrayList;
import java.util.Stack;

public class MockFileSystem implements FileSystemI {

  private static MockFileSystem instance = new MockFileSystem();

  private MockFileSystem() {}

  public static MockFileSystem getInstance() {
    return instance;
  }

  private Directory currentDir = new Directory();

  private Directory baseDir = new Directory();

  private Stack<String> mainStack = new Stack<String>();

  public void addToMainStack(String dirName) {
    mainStack.push(dirName);
  }

  public void popFromMainStack() {
    mainStack.pop();
  }


  public Stack<String> getMainStack() {
    return mainStack;
  }


  public void setMainStack(Stack<String> mainStack) {
    this.mainStack = mainStack;
  }

  public void setBaseDirectory(Directory baseDir) {
    this.baseDir = baseDir;
    this.currentDir = baseDir;
  }

  public void setCurrentDirectory(Directory dir) {
    this.currentDir = dir;
  }

  public Directory getCurrentDirectory() {
    return currentDir;
  }

  public boolean checkIfSubDirectory(String name) {

    if (name.equals("testDirectory")) {
      return true;
    }
    return false;
  }

  public boolean checkIfSubFile(String name) {

    if (name.equals("testFile")) {
      return true;
    }
    return false;
  }

  public void addDirectory(Directory dir) {
    dir.setParentDir(currentDir);
    currentDir.addDirectory(dir);
  }


  public void addFile(File file) {
    file.setParentDir(currentDir);
    currentDir.addFile(file);
  }

  public ArrayList<Directory> getAllSubDirectories() {

    return currentDir.getAllSubDirectories();
  }

  public ArrayList<File> getAllSubFiles() {

    return currentDir.getAllSubFiles();
  }

  public Directory getBaseDirectory() {
    return baseDir;
  }

  public Directory getParentDir() {
    return currentDir.getParentDir();
  }

  public String getDirectoryPathAsString() {

    return "/this/is/test/directory/path";
  }

  public ArrayList<Directory> getDirectoryPath() {

    ArrayList<Directory> reverseDir = new ArrayList<Directory>();
    ArrayList<Directory> dirPath = new ArrayList<Directory>();
    Directory temp = currentDir;

    while (temp.getParentDir() != null) {
      reverseDir.add(temp.getParentDir());
      temp = temp.getParentDir();
    }


    for (int i = reverseDir.size() - 1; i >= 0; i--) {
      dirPath.add(reverseDir.get(i));
    }
    return dirPath;
  }

  public void removeDirectory(Directory dir) {
    if (dir != this.currentDir) {
      if (dir == this.baseDir) {
        setBaseDirectory(null);
      } else {
        removeDirectory(dir, this.baseDir);
      }
    }
  }

  public void removeDirectory(Directory dir, Directory base) {
    boolean found = false;
    for (Directory subDir : base.getAllSubDirectories()) {
      if (subDir == dir && !containsCurrDir(subDir)) {
        found = true;
      } else {
        removeDirectory(dir, subDir);
      }
    }
    if (found) {
      base.removeSubDirectory(dir);
    }
  }

  /**
   * Gets file by the path provided by the user
   * 
   * @param path Path containing file name provided by the user
   * @param Base Directory from which we want to start looking from.
   * @return Returns File if there is file in the path. Otherwise, return null.
   **/
  public File getFileFromPath(String path, Directory Base) {
    File valid = new File();
    valid.setContent("hello");
    File valid1 = new File();
    valid1.setContent("bye");
    File valid2 = new File();
    valid2.setContent("hi");
    if (path.equals("/some/valid/path"))
      return valid;
    else if (path.equals("/some/valid1/path"))
      return valid1;
    else if (path.equals("/some/valid2/path"))
      return valid2;
    else
      return null;
  }

  /**
   * Gets Directory by the path provided by the user
   * 
   * @param path Path containing file name provided by the user
   * @param Base Directory from which we want to start looking from.
   * @return Returns Directory if there is Directory in the path. Otherwise, return null.
   **/
  public Directory getDirFromPath(String path, Directory Base) {
    return Base;
  }

  /**
   * Checks if a directory or any of its direct descendants are the current Directory
   * 
   * @param dir The directory to remove
   * @param base The root directory to search from
   */


  public boolean containsCurrDir(Directory dir) {
    if (dir == this.getCurrentDirectory()) {
      return true;
    }
    for (Directory subDir : dir.getAllSubDirectories()) {
      if (containsCurrDir(subDir)) {
        return true;
      }
    }
    return false;
  }
}
