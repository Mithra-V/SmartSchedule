📅 SmartSchedule — Event Manager
A command-line Event Manager built in Java that helps you add, view, search, and delete personal events — with automatic file-based persistence so your data is never lost.

✨ Features

➕ Add Events — Name, date, start/end time, and optional description
📋 View All Events — Neatly formatted table of all scheduled events
🔍 View by Date — Filter events on a specific date
🗑️ Delete Events — Remove any event by number
🔎 Search Events — Find events by keyword
💾 Auto-Save — Events are saved to events.txt and reloaded on next run


🛠️ Tech Stack
TechnologyUsageJavaCore languagejava.timeDate & time handlingFile I/OPersistent event storageScannerCLI user input

📁 Project Structure
SmartSchedule/
├── Main.java           # Entry point, menu, user interaction
├── Event.java          # Event model (name, date, time, description)
├── EventManager.java   # Logic: add, delete, search, file save/load
└── events.txt          # Auto-generated storage file

🚀 How to Run
Prerequisites: Java JDK installed
bash# Clone the repo
git clone https://github.com/Mithra-V/SmartSchedule.git
cd SmartSchedule

# Compile
javac *.java

# Run
java Main

📸 Demo
SmartSchedule - Event Manager
Events saved: 2 loaded from memory.

------ MENU ------
1. Add Event
2. View All Events
3. View Events by Date
4. Delete Event
5. Search Events
6. Exit
Your choice:
View All Events:
========== YOUR EVENTS ==========
1. Study Session   | 08-05-2026 | 11:00 - 14:00 | Exam Preparation
2. Anniversary     | 10-06-2026 | 12:00 - 23:59 | Anniversary Celebration
3. Birthday        | 14-02-2027 | 12:00 - 23:59 | Birthday Bash
=================================

👩‍💻 Author
Mithra V
B.E. Electrical & Electronics Engineering
K. Ramakrishnan College of Engineering, Trichy

📄 License
This project is open source and available under the MIT License.
Mithra V
B.E. Electrical & Electronics Engineering
K. Ramakrishnan College of Engineering, Trichy
