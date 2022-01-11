// The main entry point.
def main(_args: Array[String]): Int32 & Impure =
  import static java.lang.Thread.currentThread();//as currentThread;
  import static java.lang.Thread.currentThread() as currThread;
  import java.lang.Thread.getName();

  let name = getName(currentThread());
  println(name);
 
  currThread() |> getName |> println;

  0 // exit code
