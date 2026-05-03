// EventManager.java
// This class is the "brain" of the app.
// It handles: adding, viewing, deleting events + conflict detection + file save/load.
 
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
 
public class EventManager {
 
    // A list that holds all your events in memory while the app is running
    private List<Event> events = new ArrayList<>();
 
    // The file where events are saved permanently
    private static final String FILE_NAME = "events.txt";
 
    // Constructor: when EventManager is created, immediately load saved events from file
    public EventManager() {
        loadFromFile();
    }
 
    // -----------------------------------------------------------------------
    // ADD EVENT
    // -----------------------------------------------------------------------
    // Returns a message: either success or which event it conflicts with
    public String addEvent(String name, LocalDate date, LocalTime start, LocalTime end, String desc) {
 
        // Validate: end time must be after start time
        if (!end.isAfter(start)) {
            return " End time must be after start time!";
        }
 
        // Create a temporary event to check for conflicts
        Event newEvent = new Event(name, date, start, end, desc);
 
        // Loop through all existing events and check for time clashes
        for (Event existing : events) {
            if (newEvent.conflictsWith(existing)) {
                return " Conflict detected with: \"" + existing.getName() + "\" (" +
                        existing.getStartTime() + " - " + existing.getEndTime() + ")";
            }
        }
 
        // No conflict — add the event
        events.add(newEvent);
        saveToFile(); // immediately save to file after adding
        return " Event \"" + name + "\" added successfully!";
    }
 
    // -----------------------------------------------------------------------
    // VIEW ALL EVENTS (sorted by date, then by start time)
    // -----------------------------------------------------------------------
    public void viewAllEvents() {
        if (events.isEmpty()) {
            System.out.println(" No events scheduled yet.");
            return;
        }
 
        // Sort: first by date, then by start time within the same date
        events.sort(Comparator.comparing(Event::getDate).thenComparing(Event::getStartTime));
 
        System.out.println("\n========== YOUR EVENTS ==========");
        for (int i = 0; i < events.size(); i++) {
            // Print index (1-based) next to each event so user can reference it
            System.out.println((i + 1) + ". " + events.get(i));
        }
        System.out.println("=================================\n");
    }
 
    // -----------------------------------------------------------------------
    // VIEW EVENTS FOR A SPECIFIC DATE
    // -----------------------------------------------------------------------
    public void viewEventsByDate(LocalDate date) {
        System.out.println("\n===== EVENTS ON " + date.format(Event.DATE_FORMAT) + " =====");
        boolean found = false;
 
        for (Event e : events) {
            if (e.getDate().equals(date)) {
                System.out.println("• " + e);
                found = true;
            }
        }
 
        if (!found) System.out.println("No events on this date.");
        System.out.println();
    }
 
    // -----------------------------------------------------------------------
    // DELETE EVENT by index number
    // -----------------------------------------------------------------------
    public String deleteEvent(int index) {
        // index from user is 1-based, ArrayList is 0-based — so subtract 1
        if (index < 1 || index > events.size()) {
            return " Invalid event number.";
        }
 
        String name = events.get(index - 1).getName();
        events.remove(index - 1);
        saveToFile(); // update file after deletion
        return "Event \"" + name + "\" deleted.";
    }
 
    // -----------------------------------------------------------------------
    // SEARCH EVENTS by keyword (checks name and description)
    // -----------------------------------------------------------------------
    public void searchEvents(String keyword) {
        System.out.println("\n===== SEARCH RESULTS FOR \"" + keyword + "\" =====");
        boolean found = false;
        String lower = keyword.toLowerCase(); // case-insensitive search
 
        for (Event e : events) {
            if (e.getName().toLowerCase().contains(lower) ||
                e.getDescription().toLowerCase().contains(lower)) {
                System.out.println("• " + e);
                found = true;
            }
        }
 
        if (!found) System.out.println("No matching events found.");
        System.out.println();
    }
 
    // -----------------------------------------------------------------------
    // SAVE TO FILE
    // Each event = one line in the file
    // -----------------------------------------------------------------------
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Event e : events) {
                writer.write(e.toFileString());
                writer.newLine(); // go to next line for next event
            }
        } catch (IOException ex) {
            System.out.println(" Could not save to file: " + ex.getMessage());
        }
    }
 
    // -----------------------------------------------------------------------
    // LOAD FROM FILE
    // Reads the file line by line and rebuilds Event objects
    // -----------------------------------------------------------------------
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return; // first time running, no file yet — that's fine
 
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    events.add(Event.fromFileString(line)); // rebuild each event
                }
            }
        } catch (IOException ex) {
            System.out.println("  Could not load events: " + ex.getMessage());
        }
    }
 
    // Returns total number of events (used in Main for display)
    public int getTotalEvents() {
        return events.size();
    }
}