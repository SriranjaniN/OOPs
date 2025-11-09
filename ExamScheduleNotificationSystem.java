import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

class Exam {
    String name;
    String date;
    String time;

    public Exam(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Exam: " + name + ", Date: " + date + ", Time: " + time;
    }
}

class ScheduleManager {
    private static final String FILE_NAME = "exams.txt";

    public void addExam(Exam exam) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(exam.name + "," + exam.date + "," + exam.time);
            System.out.println("Exam added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public List<Exam> getAllExams() {
        List<Exam> exams = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                exams.add(new Exam(parts[0], parts[1], parts[2]));
            }
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
        return exams;
    }

    public void displayExams() {
        List<Exam> exams = getAllExams();
        if (exams.isEmpty()) {
            System.out.println("No exams scheduled.");
        } else {
            System.out.println("Scheduled Exams:");
            for (Exam exam : exams) {
                System.out.println(exam);
            }
        }
    }
}

class NotificationSystem {
    private ScheduleManager scheduleManager = new ScheduleManager();

    public void sendNotifications() {
        List<Exam> exams = scheduleManager.getAllExams();
        if (exams.isEmpty()) {
            System.out.println("No exams to notify.");
            return;
        }

        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        System.out.println("Today's Notifications:");
        boolean found = false;
        for (Exam exam : exams) {
            if (exam.date.equals(today)) {
                System.out.println("Reminder: " + exam.name + " exam is today at " + exam.time);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No exams today.");
        }
    }
}

public class ExamScheduleNotificationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ScheduleManager scheduleManager = new ScheduleManager();
        NotificationSystem notificationSystem = new NotificationSystem();

        while (true) {
            System.out.println("\n--- Exam Schedule and Notification System ---");
            System.out.println("1. Add Exam");
            System.out.println("2. View Exams");
            System.out.println("3. Send Notifications");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Exam Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Exam Date (dd-MM-yyyy): ");
                    String date = sc.nextLine();
                    System.out.print("Enter Exam Time (HH:mm): ");
                    String time = sc.nextLine();
                    scheduleManager.addExam(new Exam(name, date, time));
                    break;
                case 2:
                    scheduleManager.displayExams();
                    break;
                case 3:
                    notificationSystem.sendNotifications();
                    break;
                case 4:
                    System.out.println("Exiting system.");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
