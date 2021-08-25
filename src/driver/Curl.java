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
import java.net.*;
import java.io.*;

public class Curl extends Commands {

  /**
   * Curl class is a subclass of Command that inherits the runCommand(String[]) method in order to
   * run the users input for curl URL
   *
   */


  /**
   * This variable stores the standard Output of the Curl Command
   */
  String stdOut = "";

  /**
   * This function is used to read the contents of the url, gives back appropraite error if invalid
   * url
   * 
   * @param urlAsString The url that the user passes
   * @param location The current location in the FileSystemI
   */
  public void readUrl(String urlAsString, FileSystemI location) {
    StringBuilder content = new StringBuilder();

    try {
      URL url = new URL(urlAsString);

      URLConnection urlCon = url.openConnection();
      InputStreamReader i = new InputStreamReader(urlCon.getInputStream());
      BufferedReader bufferedReader = new BufferedReader(i);

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        content.append(line + "\n");
      }
      bufferedReader.close();
      createFile(urlAsString, content.toString(), location);
    } catch (Exception e) {
      System.out.println("curl: Invalid url");
      // System.out.println("curl: Invalid url");
    }

  }

  /**
   * This function is used to check if a file with the same name already exists or not
   * 
   * @param name The name of the file
   * @param location The current location in the FileSystemI
   * @return Whether or not a file with same exists
   */

  public boolean checkForDuplicates(String name, FileSystemI location) {
    ArrayList<File> allSubFiles = location.getAllSubFiles();
    boolean exists = false;
    for (File f : allSubFiles) {
      if (f.getName().equals(name)) {
        exists = true;
      }
    }
    return exists;
  }


  /**
   * This function is used to create a file
   * 
   * @param url The url that the user passes
   * @param content The content inside the url
   * @param location The current location in the FileSystemI
   */
  public void createFile(String url, String content, FileSystemI location) {
    File newFile = new File();
    String[] urlAsArray = url.split("/");
    String fileName = urlAsArray[urlAsArray.length - 1];
    if (fileName.contains(".")) {
      fileName = fileName.replace(".", "");
    }
    if (!checkForDuplicates(fileName, location)) {
      newFile.setName(fileName);
      newFile.setContent(content);
      location.addFile(newFile);
    } else {
      System.out.println("curl: File " + fileName + " already exists");
    }
  }

  /**
   * This function runs the curl command. It also checks whether the no.of arguments that user
   * passes if correct or not
   * 
   * @return String The standard Output of the Curl class
   */

  public String runCommand(ListIterator<String> list, FileSystemI location) {
    String url = "";
    if (!list.hasNext()) {
      // System.out.println("curl: Needs one argument");
      System.out.println("curl: Needs one argument");
      return stdOut;
    }
    if (list.hasNext()) {
      url = list.next();
    }

    if (list.hasNext()) {
      // System.out.println("curl: Takes only one argument");
      System.out.println("curl: Takes only one argument");
      return stdOut;
    }

    readUrl(url, location);
    return stdOut;
  }

  /**
   * This function that can be used to get the description of the curl command
   */

  public String getMan() {
    return "curl URL:\n" + "URL is a web address. Retrieve the file at that "
        + "URL and add it to the current working directory.";
  }
}
