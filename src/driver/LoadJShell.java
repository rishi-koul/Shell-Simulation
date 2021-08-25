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

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ListIterator;
import driver.SaveJShell.root;

public class LoadJShell extends Commands implements Serializable {



  private static final long serialVersionUID = 1L;

  /**
   * Load the contents of the [FileName] provided in the @param "list" and Reinitialize the entire
   * state of system that was saved previously.
   * 
   * @param list is an array of strings corresponding to the users input
   * @param fs the FileSystem
   * @return String
   */
  public String runCommand(ListIterator<String> list, FileSystemI fs) {
    if (History.cmdhistory.size() == 1) {
      String FileName = "";
      try {
        list.add("end");
        if (!list.hasNext()) {
          System.out.println("Erorr: Invald # of arguments");
          return "";
        }
        FileName = list.next();
        if (list.hasNext()) {
          System.out.println("Erorr: Invald # of arguments");
          return "";
        }
        FileInputStream fis = new FileInputStream(FileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
          root main = (root) ois.readObject();
          String temp = History.cmdhistory.get(0);
          History.cmdhistory = main.getCmdhistory();
          History.cmdhistory.add(temp);
          fs.setBaseDirectory(main.getBase());
          fs.setCurrentDirectory(main.getCurrent());
          fs.setMainStack(main.getMainStack());
          ois.close();
        } catch (ClassNotFoundException e) {
          System.out.println("Erorr: file not found");
        }
        return "";
      } catch (FileNotFoundException e) {
        System.out.println("Erorr: file not found");
        return "";
      } catch (IOException e) {
        System.out.println("Erorr: file not found");
        return "";
      }
    } else {
      System.out.println("Error restart JShell to use loadJShell");
      return "";
    }
  }

  public String getMan() {
    return "loadJshell FILENAME:\n" + "\tLoad the contents of the FileName\n"
        + "\tReinitialize the entire " + "state of system that was saved previously\n"
        + "\tinto the FileName.\n";
  }
}
