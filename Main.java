// Main.java
// This is where the program STARTS (the main method).
// It shows the menu, takes user input, and calls EventManager methods.
 
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
 
public class Main {
 
    // Scanner reads whatever the user types in the terminal
    static Scanner sc = new Scanner(System.in);
 
    // EventManager is our brain — all logic lives there
    static EventManager manager = new EventManager();
 
    public static void main(String[] args) {
        printBanner();
 
        // The app keeps running until user chooses to exit
        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();
 
            switch (choice) {
                case "1" -> addEvent();
                case "2" -> manager.viewAllEvents();
                case "3" -> viewByDate();
                case "4" -> deleteEvent();
                case "5" -> searchEvent();
                case "6" -> {
                    System.out.println("\n Goodbye! Your events are saved.");
                    System.exit(0); // cleanly exit the program
                }
                default -> System.out.println(" Invalid choice. Enter 1-6.\n");
            }
        }
    }
 
    // -----------------------------------------------------------------------
    // MENU DISPLAY
    // -----------------------------------------------------------------------
    static void printBanner() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       SmartSchedule - Event Manager  ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("  Events saved: " + manager.getTotalEvents() + " loaded from memory.\n");
    }
 
    static void printMenu() {
        System.out.println("------ MENU ------");
        System.out.println("1. Add Event");
        System.out.println("2. View All Events");
        System.out.println("3. View Events by Date");
        System.out.println("4. Delete Event");
        System.out.println("5. Search Events");
        System.out.println("6. Exit");
        System.out.print("Your choice: ");
    }
 
    // -----------------------------------------------------------------------
    // ADD EVENT — collects input from user step by step
    // -----------------------------------------------------------------------
    static void addEvent() {
        System.out.println("\n--- Add New Event ---");
 
        System.out.print("Event name: ");
        String name = sc.nextLine().trim();
        if (name.isBlank()) { System.out.println("Name can't be empty.\n"); return; }
 
        // Parse date — keep asking until valid format entered
        LocalDate date = readDate("Event date (dd-MM-yyyy): ");
        if (date == null) return;
 
        // Parse start time
        LocalTime start = readTime("Start time (HH:mm, 24hr): ");
        if (start == null) return;
 
        // Parse end time
        LocalTime end = readTime("End time   (HH:mm, 24hr): ");
        if (end == null) return;
 
        System.out.print("Description (optional, press Enter to skip): ");
        String desc = sc.nextLine().trim();
        if (desc.isBlank()) desc = "No description";
 
        // Send everything to EventManager and print result
        String result = manager.addEvent(name, date, start, end, desc);
        System.out.println(result + "\n");
    }
 
    // -----------------------------------------------------------------------
    // VIEW BY DATE
    // -----------------------------------------------------------------------
    static void viewByDate() {
        LocalDate date = readDate("Enter date to view (dd-MM-yyyy): ");
        if (date != null) manager.viewEventsByDate(date);
    }
 
    // -----------------------------------------------------------------------
    // DELETE EVENT
    // -----------------------------------------------------------------------
    static void deleteEvent() {
        manager.viewAllEvents(); // show events first so user knows the index
        if (manager.getTotalEvents() == 0) return;
 
        System.out.print("Enter event number to delete: ");
        try {
            int index = Integer.parseInt(sc.nextLine().trim());
            System.out.println(manager.deleteEvent(index) + "\n");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.\n");
        }
    }
 
    // -----------------------------------------------------------------------
    // SEARCH EVENTS
    // -----------------------------------------------------------------------
    static void searchEvent() {
        System.out.print("Enter keyword to search: ");
        String keyword = sc.nextLine().trim();
        if (!keyword.isBlank()) manager.searchEvents(keyword);
    }
 
    // -----------------------------------------------------------------------
    // HELPER: Read a date safely (keeps asking on wrong format)
    // -----------------------------------------------------------------------
    static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return LocalDate.parse(input, Event.DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println(" Wrong format. Use dd-MM-yyyy (e.g. 25-04-2025). Try again.");
            }
        }
    }
 
    // -----------------------------------------------------------------------
    // HELPER: Read a time safely
    // -----------------------------------------------------------------------
    static LocalTime readTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return LocalTime.parse(input, Event.TIME_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Wrong format. Use HH:mm (e.g. 09:30 or 14:00). Try again.");
            }
        }
    }
}