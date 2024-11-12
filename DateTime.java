import java.time.format.DateTimeFormatter;

public class DateTime {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

    public DateTime(int day, int month, int year, int hour, int minute) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }
}
