class TestAOP implements GroovyInterceptable {

    def invokeMethod(String name, args) {
        System.out.println("bengin $name")

        def result = TestAOP.metaClass.getMetaMethod(name, args).invoke(this, args)
        System.out.println("result: $result")

        System.out.println "end $name"

        return result
    }

    static void main(args) {
        def a = new TestAOP()
        a.test()
    }

    def test() { return "HELLO" }
}
