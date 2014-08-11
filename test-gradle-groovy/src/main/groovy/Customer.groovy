
class Customer {
    // properties
    Integer id
    String name
    Date dob

    // sample code
    static void main(args) {
        def customer = new Customer(id:1, name:"Gromit", dob:new Date())
        println("Hello ${customer.name}")
        println("hello ${customer.getName()}")

        //customer.somep = "hello"
        //println customer.somep
    }
}