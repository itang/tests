namespace MyFile {
    type alias File = ##java.io.File

    pub def openFile(s: String): File & Impure = 
        import new java.io.File(String) as newFile;
        newFile(s)

    pub def exists(f: File): Bool & Pure =
        import java.io.File.exists();
        exists(f) as & Pure

    pub def create(f: File): Bool & Impure =
        import java.io.File.createNewFile();
        createNewFile(f)
}

// The main entry point.
def main(_args: Array[String]): Int32 & Impure =
  Console.printLine("Hello World!");
  let f = MyFile.openFile("./test.txt");
  f |> MyFile.exists |> println;
  f |> MyFile.create;

  0 // exit code
