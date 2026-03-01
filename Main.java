import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

record Book(String title, int pages, int year) {}

class Student {
    String name;
    List<Book> books;

    Student(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    @Override
    public String toString() { 
        return "Student: " + name; 
    }
}

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        List<Student> students = new ArrayList<>();
        Scanner scanner = new Scanner(new File("students.txt"));
        
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(";");
            List<Book> books = new ArrayList<>();
            
            for (int i = 1; i < parts.length; i++) {
                String[] b = parts[i].split(",");
                books.add(new Book(b[0], Integer.parseInt(b[1]), Integer.parseInt(b[2])));
            }
            students.add(new Student(parts[0], books));
        }
        scanner.close();

        System.out.println("--- Start ---");

        students.stream()
            .peek(s -> System.out.println(s))
            .map(s -> s.books)                    
            .flatMap(list -> list.stream())
            .sorted((b1, b2) -> Integer.compare(b1.pages(), b2.pages()))
            .distinct()
            .filter(b -> b.year() > 2000)
            .limit(3)
            .map(b -> b.year())
            .findFirst()
            .ifPresentOrElse(
                year -> System.out.println("Found year: " + year),
                () -> System.out.println("Book not found")
            );
    }
}