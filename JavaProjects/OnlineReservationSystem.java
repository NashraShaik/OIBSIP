import java.util.*;

class OnlineReservationSystem {

    // Simulated database
    private static Map<String, String> users = new HashMap<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static int pnrCounter = 1000; // to generate unique PNRs
    private static Scanner scanner = new Scanner(System.in);

    // Reservation class to hold booking info
    static class Reservation {
        String pnr;
        String name;
        String trainNumber;
        String trainName;
        String travelClass;
        String from;
        String to;
        String date;

        public Reservation(String pnr, String name, String trainNumber, String trainName,
                           String travelClass, String from, String to, String date) {
            this.pnr = pnr;
            this.name = name;
            this.trainNumber = trainNumber;
            this.trainName = trainName;
            this.travelClass = travelClass;
            this.from = from;
            this.to = to;
            this.date = date;
        }
    }

    public static void main(String[] args) {
        // Predefined users
        users.put("admin", "admin123");
        users.put("Nashra", "Nash123");

        System.out.println("===== Welcome to Online Reservation System =====");
        if (!login()) {
            System.out.println("Too many failed attempts! Exiting...");
            return;
        }

        while (true) {
            System.out.println("\n------ Main Menu ------");
            System.out.println("1. Make Reservation");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. View All Reservations");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    makeReservation();
                    break;
                case 2:
                    cancelReservation();
                    break;
                case 3:
                    viewAllReservations();
                    break;
                case 4:
                    System.out.println("Thank you for using the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Enter 1-4.");
            }
        }
    }

    // Login Module
    private static boolean login() {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter login ID: ");
            String loginId = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (users.containsKey(loginId) && users.get(loginId).equals(password)) {
                System.out.println("Login successful! Welcome, " + loginId);
                return true;
            } else {
                System.out.println("Invalid login ID or password. Try again.");
                attempts++;
            }
        }
        return false;
    }

    // Reservation Module
    private static void makeReservation() {
        System.out.println("\n--- Reservation Form ---");
        System.out.print("Enter Passenger Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        String trainName = getTrainName(trainNumber); // auto fetch train name

        System.out.print("Enter Travel Class (Sleeper/AC/First): ");
        String travelClass = scanner.nextLine();

        System.out.print("Enter From Station: ");
        String from = scanner.nextLine();

        System.out.print("Enter To Station: ");
        String to = scanner.nextLine();

        System.out.print("Enter Date of Journey (dd-mm-yyyy): ");
        String date = scanner.nextLine();

        String pnr = "PNR" + pnrCounter++;
        Reservation reservation = new Reservation(pnr, name, trainNumber, trainName, travelClass, from, to, date);
        reservations.add(reservation);

        System.out.println("Reservation Successful! Your PNR is: " + pnr);
    }

    // Cancellation Module
    private static void cancelReservation() {
        System.out.println("\n--- Cancellation Form ---");
        System.out.print("Enter PNR Number to Cancel: ");
        String pnr = scanner.nextLine();

        Reservation found = null;
        for (Reservation r : reservations) {
            if (r.pnr.equalsIgnoreCase(pnr)) {
                found = r;
                break;
            }
        }

        if (found == null) {
            System.out.println("PNR not found!");
            return;
        }

        System.out.println("\nReservation Details:");
        System.out.println("PNR: " + found.pnr);
        System.out.println("Name: " + found.name);
        System.out.println("Train: " + found.trainNumber + " - " + found.trainName);
        System.out.println("Class: " + found.travelClass);
        System.out.println("From: " + found.from + " To: " + found.to);
        System.out.println("Date: " + found.date);

        System.out.print("Confirm Cancellation? (yes/no): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            reservations.remove(found);
            System.out.println("Reservation cancelled successfully.");
        } else {
            System.out.println("Cancellation aborted.");
        }
    }

    // View all reservations (admin function)
    private static void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        System.out.println("\n--- All Reservations ---");
        for (Reservation r : reservations) {
            System.out.println("PNR: " + r.pnr + ", Name: " + r.name + ", Train: " + r.trainNumber + " - " + r.trainName +
                    ", Class: " + r.travelClass + ", From: " + r.from + ", To: " + r.to + ", Date: " + r.date);
        }
    }

    // Helper: get train name from train number (dummy data)
    private static String getTrainName(String trainNumber) {
        switch (trainNumber) {
            case "101":
                return "Express A";
            case "102":
                return "Express B";
            case "103":
                return "Express C";
            default:
                return "Unknown Train";
        }
    }

    // Helper: safe integer input
    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
}
