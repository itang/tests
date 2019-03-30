package demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Car {
    private int numberOfSeats;
}
