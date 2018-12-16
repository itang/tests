package demo;

import java.util.ArrayList;
import java.util.List;

//generic type for parameter as producer: extends
//generic type as consumer: super

/*
总结PECS原则

如果你只需要从集合中获得类型T , 使用<? extends T>通配符
如果你只需要将类型T放到集合中, 使用<? super T>通配符
如果你既要获取又要放置元素，则不使用任何通配符
PECS即 Producer extends Consumer super
 */
public class Main {
    public static void main(String[] args) {
        Car car = new Car() {
        };
        SmallCar smallCar = new SmallCar() {
        };
        BmwX5 bmwX5 = new BmwX5();

        List<Car> cars = new ArrayList<Car>(); //非协变

        produce_consume(cars);
    }

    //读写， 使用非协变版本
    public static void produce_consume(List<Car> carList) {
        carList.add(new Car() {

        });
        carList.add(new BmwX5());//consumer
        Car car = carList.get(0);//producer
        System.out.println(car);
    }

    public static void consume(List<? super Car> carList) {
        //Car car = carList.get(0);
        carList.add(new BmwX5());//consumer
        //System.out.println(car);
    }


    public static void producer(List<? extends Car> carList) {
        Car car = carList.get(0);//producer
        //carList.add(new BmwX5());//consumer
        //System.out.println(car);
    }

    interface Car {
    }

    static abstract class SmallCar implements Car {
    }

    static class BmwX5 extends SmallCar {
    }
}
