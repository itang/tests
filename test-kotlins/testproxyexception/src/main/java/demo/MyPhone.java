package demo;

public class MyPhone implements Phone {

    @Override
    public void call(String number) throws ErrorNumber {
        if (number == "119") {
            throw new ErrorNumber("error number fire");
            //throw new IllegalArgumentException("fire"); //RuntimeException
        }
        System.out.println("call " + number + "...");

    }
}
