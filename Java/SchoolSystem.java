package Java;
class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void displayDetails() {
        System.out.println("Name: " + name + " | Age: " + age);
    }
}

class Student extends Person {
    int studentId;

    public Student(String name, int age, int studentId) {
        super(name, age); 
        this.studentId = studentId;
    }

    @Override
    public void displayDetails() {
        System.out.print("Student -> ");
        super.displayDetails();
        System.out.println("Student ID: " + studentId);
    }
}

class Teacher extends Person {
    String subject;

    public Teacher(String name, int age, String subject) {
        super(name, age); 
        this.subject = subject;
    }

    @Override
    public void displayDetails() {
        System.out.print("Teacher -> ");
        super.displayDetails();
        System.out.println("Teaches: " + subject);
    }
}

public class SchoolSystem {
    public static void main(String[] args) {
        Teacher myTeacher = new Teacher("abc", 45, "Mathematics");
        Student myStudent = new Student("def", 15, 1001);

        myTeacher.displayDetails();
        myStudent.displayDetails();
    }
}