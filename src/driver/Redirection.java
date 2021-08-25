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

public class Redirection {

  /**
   * The symbol that the use passes '>' or '>>'
   */

  private String redirectSymbol;

  /**
   * The name of the file that user passes
   */

  private String outFileName;

  /**
   * The list passed by the command class
   */
  ListIterator<String> originalList;


  /**
   * A func to get back the original list
   * 
   * @return ListIterator<String> The original list
   */

  public ListIterator<String> getOriginalList() {
    return originalList;
  }

  /**
   * The func deals with case when > or >> is not surrounded by space
   * 
   * @param input The input given by user
   * @return String the string with > or >> surrounded by spaces
   */

  public static String dealWithNoSpaces(String input) {

    String dealtWithSpaces = input;
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      String shorter = input.substring(0, i);
      if (c == '>') {
        if (i + 1 < input.length() && input.charAt(i + 1) == '>') {
          if (i + 2 < input.length())
            dealtWithSpaces = shorter + " >> " + input.substring(i + 2);
        } else if (i - 1 > 0 && input.charAt(i - 1) != '>')
          if (i + 1 < input.length())
            dealtWithSpaces = shorter + " > " + input.substring(i + 1);
      }
    }
    return dealtWithSpaces;
  }

  /**
   * This func removes the redirection part from the list
   * 
   * @param list list provided by the command class
   * @return ListIterator<String> list without the redirection part
   */
  public ListIterator<String> rmvRedir(ListIterator<String> list) {

    ArrayList<String> arrayList = new ArrayList<String>();
    while (list.hasNext()) {
      arrayList.add(list.next());
    }

    ArrayList<String> arrayList2 = new ArrayList<String>();
    for (int i = 0; i < arrayList.size(); i++) {
      if (arrayList.get(i).equals("")) {
      } else
        arrayList2.add(arrayList.get(i));
    }

    redirectSymbol = arrayList2.get(arrayList2.size() - 2);
    outFileName = arrayList2.get(arrayList2.size() - 1);
    arrayList2.remove(arrayList2.size() - 2);
    arrayList2.remove(arrayList2.size() - 1);

    return arrayList2.listIterator();

  }

  /**
   * Check if the list contains redirection
   * 
   * @param list list provided by the command class
   * @return boolean whether or not list has redirection
   */
  public boolean checkRedirection(ListIterator<String> list) {
    boolean isRedirection = false;

    ArrayList<String> arrayList = new ArrayList<String>();
    list.forEachRemaining(arrayList::add);

    ArrayList<String> arrayList2 = new ArrayList<String>();
    for (int i = 0; i < arrayList.size(); i++) {
      if (arrayList.get(i).equals("") || arrayList.get(i).equals(" ")) {
      } else
        arrayList2.add(arrayList.get(i));
    }
    // System.out.println("List: ");
    // for(int i = 0; i < arrayList2.size(); i++) {
    // System.out.println(arrayList2.get(i));
    // }
    // System.out.println("End.");
    if (arrayList2.size() > 1) {
      if (arrayList2.get(arrayList2.size() - 2).equals(">>")
          || arrayList2.get(arrayList2.size() - 2).equals(">")
              && !arrayList2.get(arrayList2.size() - 1).contains("\"")) {
        isRedirection = true;
      }
    }

    originalList = arrayList2.listIterator();
    return isRedirection;
  }

  /**
   * the func actually performs redirection
   * 
   * @param content the content of file
   * @param location the current location in the file system
   */
  public void redirect(String content, FileSystemI location) {
    ArrayList<String> listForRedir = new ArrayList<>();
    listForRedir.add(content);
    listForRedir.add(redirectSymbol);
    ArrayList<String> invalidFileName = checkForInvalidFileName(outFileName);
    listForRedir.add(outFileName);
    // MakeDirectory mkdir = new MakeDirectory();
    // mkdir.checkCommand(outFileName);
    if (invalidFileName.get(0).equals("true")) {
      if (!content.equals("")) {
        performRedir(listForRedir.listIterator(), location);
      }
    } else {
      String error = "Error: File names can't contain ";
      System.out.println(error + invalidFileName.get(1));
    }
  }


  /**
   * This func creates the file and add the contents
   * 
   * @param list the list containing symbol name and content of file
   * @param loc the current location in the file system
   */
  public void performRedir(ListIterator<String> list, FileSystemI loc) {
    ArrayList<String> arg = new ArrayList<String>();
    list.forEachRemaining(arg::add);
    if (!arg.get(1).equals(">>") && !arg.get(1).equals(">")) {
      System.out.println("Syntax error expected '>' or '>>'");
    }
    char var[] = arg.get(2).toCharArray();
    File found = findFile(var[0], arg.get(2), loc);
    if (found == null) {
      File new_File = new File();
      Directory add = findDir(var[0], arg.get(2), loc);
      if (add == null) {
        System.out.println("Error: Invalid File path");
      } else {
        new_File.setName(FileSystem.fileName);
        new_File.setContent(arg.get(0));
        add.addFile(new_File);
      }
    } else if (arg.get(1).equals(">")) {
      found.setContent(arg.get(0));
    } else {
      String temp = arg.get(0);
      found.setContent(found.getcontent().concat(temp));
    }
  }

  /**
   * Find Directory by the path provided by the user
   * 
   * @param var indicates form which we want to start looking from.
   * @param path Path containing file name provided by the user
   * @param loc is the FilSystem from which we access files and Directories.
   * @return Returns File if there is File in the path. Otherwise, return null.
   **/
  private File findFile(char var, String path, FileSystemI loc) {
    File found = null;
    if (found == null && var == '/')
      found = loc.getFileFromPath(path, loc.getBaseDirectory());
    if (found == null)
      found = loc.getFileFromPath(path, loc.getCurrentDirectory());
    return found;
  }

  /**
   * Find Directory by the path provided by the user
   * 
   * @param var indicates form which we want to start looking from.
   * @param path Path containing file name provided by the user
   * @param loc is the FilSystem from which we access files and Directories.
   * @return Returns Directory if there is Directory in the path. Otherwise, return null.
   **/
  private Directory findDir(char var, String path, FileSystemI loc) {
    Directory add = null;

    if (add == null && var == '/') {
      add = loc.getDirFromPath(path, loc.getBaseDirectory());
    }
    if (add == null) {
      add = loc.getDirFromPath(path, loc.getCurrentDirectory());
    }
    return add;
  }


  /**
   * Checks if the file name is valid or not
   * 
   * @param name the name of the file
   * @return ArrayList<String> the pair of true/false and the invalid character
   */
  private ArrayList<String> checkForInvalidFileName(String name) {
    ArrayList<String> validityCharPair = new ArrayList<String>();
    validityCharPair.add("true");
    validityCharPair.add(" ");
    ArrayList<String> invalidChars = new ArrayList<String>();
    invalidChars.addAll(Arrays.asList(".", " ", "!", "@", "#", "$", "%"));
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
}
