package demo;

import lombok.val;

public class Main {
    public static void main(String[] args) {
        val data = CarMapper.INSTANCE.carToCarDto(new Car(4));
        System.out.println(data); //CarDto(seatCount=4)

        final IMyCar imyCar = CarMapper.INSTANCE.carToImyCar(Car.builder().numberOfSeats(6).build());
        System.out.println(imyCar);
    }
}

