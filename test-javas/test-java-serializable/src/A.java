import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class Foo implements Serializable {
    private static final long serialVersionUID = 1L;

    public Foo() {
        System.out.println("Foo");
    }

    public Foo(String name) {
        System.out.println("Foo name");
    }

    public void p() {
        System.out.println(this);
    }
}

class Bar extends Foo {
    private static final long serialVersionUID = 1L;

    // public Bar() {
    // System.out.println("Bar");
    // }

    public Bar(String name) {
        super(name);

        System.out.println("Bar name");
    }
}

public class A {
    private static final String OUTFILE = "tst.ob";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Bar bar = new Bar();
        System.out.println(".....................");
        Bar bar2 = new Bar("itang");

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(OUTFILE));
        out.writeObject(bar2);
        out.close();

        int i = 0;
        while (i < 10) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(OUTFILE));
            Bar bar_new = (Bar) (in.readObject());
            bar_new.p();
            i++;
            in.close();
        }
    }
}
