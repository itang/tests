import org.apache.curator.test.TestingServer;

public class Main {
  public static void main(String[] args) throws Exception{
    System.out.println("start...");
    new TestingServer(3181).start();
  }
}