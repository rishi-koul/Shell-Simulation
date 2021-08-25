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


import java.util.ListIterator;

public class Concatenate extends Commands {


  /**
   * Cat class is a subclass of Command that inherits the runCommand(String[]) method in order to
   * run the users input for "cat [FILE+]"
   */


  private String stdOut = "";


  /**
   * Uses an array list of users input arguments and prints out the contents of the file(s)
   * specified in the array list. If the user specifies multiple files (i.e. "cat FILE1 FILE2") the
   * method prints out the contents of each file one by one.
   * 
   * @param list is an array of strings corresponding to the users input
   * @param loc is the FilSystem from which we access files and Directories.
   * @return String
   */

  @Override
  public String runCommand(ListIterator<String> list, FileSystemI fs) {

    String temp = "";
    list.add("end");
    String var = "Error: The target file ";
    if (list.hasNext()) {
      while (list.hasNext()) {
        File file1 = null;
        temp = list.next();
        if (file1 == null && temp.startsWith("/"))
          file1 = fs.getFileFromPath(temp, fs.getBaseDirectory());
        else
          file1 = fs.getFileFromPath(temp, fs.getCurrentDirectory());
        if (list.hasNext()) {
          if (file1 == null) {

            System.out.print(var + temp + " does not exist!\n");
            break;
          } else {
            stdOut += file1.getcontent() + "\n\n\n";
          }
        } else {
          if (file1 == null) {
            System.out.println(var + temp + " does not exist!");
            break;
          } else {
            stdOut += file1.getcontent();
          }
        }
      }
    } else {
      System.out.println("Invalid # of arguments");
    }
    return stdOut;
  }

  /**
   * Creates a specified manual on how to use the [cat] command.
   *
   * @return String for the manual documentation of [cat].
   */
  @Override
  public String getMan() {
    return "cat FILE1 [FILE2 ...]:\n" + "\tDisplay the contents of FILE1 and other "
        + "files(i.e. FILE2 ...) one by one.";
  }


}
