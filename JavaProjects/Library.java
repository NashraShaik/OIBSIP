import java.util.ArrayList;
import java.util.Scanner;

// Main Library Class
public class Library {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Books ob = new Books();
        Students obStudent = new Students();
        int choice;

        System.out.println("__________________________________________________________________________________________________________");
        System.out.println("_____________________________________DIGITAL LIBRARY MANAGEMENT___________________________________________");

        do {
            ob.dispMenu();
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    ob.addBook();
                    break;
                case 2:
                    ob.upgradeBQuantity();
                    break;
                case 3:
                    ob.searchBook();
                    break;
                case 4:
                    ob.showAllBooks();
                    break;
                case 5:
                    obStudent.addStudent();
                    break;
                case 6:
                    obStudent.showAllStudents();
                    break;
                case 7:
                    obStudent.checkOutBook(ob);
                    break;
                case 8:
                    obStudent.checkInBook(ob);
                    break;
                case 0:
                    System.out.println("EXIT");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 0);

        sc.close();
    }
}

// Book Class
class Book {
    String title;
    String author;
    int serialNo;
    int quantity;

    public Book(int serialNo, String title, String author, int quantity) {
        this.serialNo = serialNo;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public String toString() {
        return "SerialNo: " + serialNo + ", Title: " + title + ", Author: " + author + ", Quantity: " + quantity;
    }
}

// Books Class
class Books {
    ArrayList<Book> bookList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void dispMenu() {
        System.out.println("\n1. Add Book");
        System.out.println("2. Upgrade Book Quantity");
        System.out.println("3. Search Book");
        System.out.println("4. Show All Books");
        System.out.println("5. Add Student");
        System.out.println("6. Show All Students");
        System.out.println("7. Check Out Book");
        System.out.println("8. Check In Book");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    public void addBook() {
        System.out.print("Enter Serial Number: ");
        int sn = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();
        Book b = new Book(sn, title, author, qty);
        bookList.add(b);
        System.out.println("Book added successfully!");
    }

    public void upgradeBQuantity() {
        System.out.print("Enter Serial Number of Book to Upgrade Quantity: ");
        int sn = sc.nextInt();
        for (Book b : bookList) {
            if (b.serialNo == sn) {
                System.out.print("Enter new Quantity: ");
                int newQty = sc.nextInt();
                b.quantity = newQty;
                System.out.println("Quantity updated!");
                return;
            }
        }
        System.out.println("Book not found!");
    }

    public void searchBook() {
        System.out.println("1. Search by Serial Number");
        System.out.println("2. Search by Author");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Enter Serial Number: ");
            int sn = sc.nextInt();
            for (Book b : bookList) {
                if (b.serialNo == sn) {
                    System.out.println(b);
                    return;
                }
            }
            System.out.println("Book not found!");
        } else if (choice == 2) {
            System.out.print("Enter Author Name: ");
            String author = sc.nextLine();
            boolean found = false;
            for (Book b : bookList) {
                if (b.author.equalsIgnoreCase(author)) {
                    System.out.println(b);
                    found = true;
                }
            }
            if (!found) System.out.println("No books found by this author!");
        } else {
            System.out.println("Invalid choice!");
        }
    }

    public void showAllBooks() {
        if (bookList.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book b : bookList) {
                System.out.println(b);
            }
        }
    }

    public Book getBookBySerial(int sn) {
        for (Book b : bookList) {
            if (b.serialNo == sn) return b;
        }
        return null;
    }
}

// Student Class
class Student {
    String name;
    ArrayList<Book> borrowedBooks = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    public String toString() {
        return "Student Name: " + name + ", Borrowed Books: " + borrowedBooks.size();
    }
}

// Students Class
class Students {
    ArrayList<Student> studentList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void addStudent() {
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        studentList.add(new Student(name));
        System.out.println("Student added successfully!");
    }

    public void showAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No students available.");
        } else {
            for (Student s : studentList) {
                System.out.println(s);
            }
        }
    }

    public void checkOutBook(Books books) {
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        Student s = findStudent(name);
        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.print("Enter Book Serial Number to Checkout: ");
        int sn = sc.nextInt();
        sc.nextLine();
        Book b = books.getBookBySerial(sn);
        if (b != null && b.quantity > 0) {
            s.borrowedBooks.add(b);
            b.quantity--;
            System.out.println("Book checked out successfully!");
        } else {
            System.out.println("Book not available!");
        }
    }

    public void checkInBook(Books books) {
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        Student s = findStudent(name);
        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.print("Enter Book Serial Number to Check-in: ");
        int sn = sc.nextInt();
        sc.nextLine();
        for (Book b : s.borrowedBooks) {
            if (b.serialNo == sn) {
                s.borrowedBooks.remove(b);
                b.quantity++;
                System.out.println("Book checked in successfully!");
                return;
            }
        }
        System.out.println("This student did not borrow this book!");
    }

    private Student findStudent(String name) {
        for (Student s : studentList) {
            if (s.name.equalsIgnoreCase(name)) return s;
        }
        return null;
    }
}
