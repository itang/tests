package demo;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// simple beautiful strong immutable thread - safe
public class Main {
    public static void main(String[] args) {
        // 使用 Instant 代替 Date
        Instant now = Instant.now();
        System.out.println(now.getEpochSecond());

        // LocalDateTime 代替 Calendar
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.getHour());

        // DateTimeFormatter 代替 SimpleDateFormat
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = formatter.format(localDateTime);
        System.out.println(str);
    }
}
