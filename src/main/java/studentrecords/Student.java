package studentrecords;

// this class is for the student data
public class Student {
    private String id;
    private String name;
    private String course;
    private int yearLevel;
    private String email;

    public Student(String id, String name, String course, int yearLevel, String email) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.yearLevel = yearLevel;
        this.email = email;
    }

    // getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public int getYearLevel() { return yearLevel; }
    public String getEmail() { return email; }
}