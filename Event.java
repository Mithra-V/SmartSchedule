// Event.java
// This class is a "blueprint" for what an Event looks like.
// Every event you create will be an object made from this blueprint.
 
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
 
public class Event {
 
    // These are the fields (properties) every event will have
    private String name;
    private LocalDate date;   // e.g. 2025-04-22
    private LocalTime startTime; // e.g. 10:00
    private LocalTime endTime;   // e.g. 11:30
    private String description;
 
    // Formatter: tells Java how to display/read dates and times
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
 
    // Constructor: runs when you do "new Event(...)"
    public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime, String description) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }
 
    // ---- Getters: used to read private fields from outside this class ----
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getDescription() { return description; }
 
    // Checks if THIS event overlaps with another event
    // Logic: two events clash if one starts before the other ends (on the same date)
    public boolean conflictsWith(Event other) {
        if (!this.date.equals(other.date)) return false; // different days = no conflict
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }
 
    // How this event looks when printed
    @Override
    public String toString() {
        return String.format(" %-20s | %s | %s - %s | %s",
                name,
                date.format(DATE_FORMAT),
                startTime.format(TIME_FORMAT),
                endTime.format(TIME_FORMAT),
                description);
    }
 
    // Converts event to a single line for saving to file
    // Format: name|date|startTime|endTime|description
    public String toFileString() {
        return name + "|" + date + "|" + startTime + "|" + endTime + "|" + description;
    }
 
    // Reads a line from file and turns it back into an Event object
    public static Event fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Event(
                parts[0],
                LocalDate.parse(parts[1]),
                LocalTime.parse(parts[2]),
                LocalTime.parse(parts[3]),
                parts[4]
        );
    }
}