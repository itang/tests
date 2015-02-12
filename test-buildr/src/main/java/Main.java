public class Main {
  public static void main(String[] args) throws Exception {
    System.out.println("Hello, World!");

    Thread t = new Thread(()-> System.out.println("Hello, from:" + Thread.currentThread()));
    t.start();
    t.join();
  }
}
