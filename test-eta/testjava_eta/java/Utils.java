package eta.first;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

  /* This helper method lets us avoid variadic arguments which
     are a bit cumbersome to work with in Eta. */

  public static void createFile(String path) throws Exception {
     Files.createFile(Paths.get(path));
  }
}
