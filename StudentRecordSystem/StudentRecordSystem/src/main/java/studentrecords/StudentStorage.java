package studentrecords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// saves and loads the students in students.csv
public class StudentStorage {

    // reads the csv and makes a Student for every line
    public static List<Student> load() throws IOException {
        List<Student> list = new ArrayList<>();

        // if there is no file yet just return the empty list
        if (!Files.exists(Paths.get("students.csv"))) {
            return list;
        }

        List<String> lines = Files.readAllLines(Paths.get("students.csv"));
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] p = line.split(",", -1);
            if (p.length < 5) continue;
            Student s = new Student(p[0], p[1], p[2], Integer.parseInt(p[3].trim()), p[4]);
            list.add(s);
        }
        return list;
    }

    // writes all the students back to the csv (overwrites the old one)
    public static void save(List<Student> students) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Student s : students) {
            String line = clean(s.getId()) + "," + clean(s.getName()) + "," + clean(s.getCourse())
                    + "," + s.getYearLevel() + "," + clean(s.getEmail());
            lines.add(line);
        }
        Files.write(Paths.get("students.csv"), lines);
    }

    // removes commas and new lines so the csv doesnt get messed up
    private static String clean(String value) {
        return value.replace(",", " ").replace("\n", " ").trim();
    }
}