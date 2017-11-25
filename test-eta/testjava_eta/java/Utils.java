package eta.first;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Utils {

  /* This helper method lets us avoid variadic arguments which
     are a bit cumbersome to work with in Eta. */

  public static void createFile(String path) throws Exception {
    System.out.println("Hello");
    Path p = Paths.get(path);
    if (p.toFile().exists()) {
      System.out.println("File exists");
      return;
    }
    Files.createFile(p);
  }
}
