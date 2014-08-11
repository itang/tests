/**
 * Created by itang on 14-7-28.
 */
class TestBuilder {
    def out

    def TestBuilder(out) { this.out = out }

    def invokeMethod(String name, args) {
        out << "<$name>"
        if (args[0] instanceof Closure) {
            args[0].delegate = this
            args[0].call()
        } else {
            out << args[0].toString()
        }
        out << "</$name>"
    }

    static void main(args) {
        def builder = new TestBuilder(new StringBuffer())
        builder.html {
            header {
                title "hello"
            }
            body {
                p "welcome"
            }
        }

        println(builder.out.toString())
    }
}
