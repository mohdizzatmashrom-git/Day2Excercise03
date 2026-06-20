package com.fullstack.demo.service;

import com.fullstack.demo.model.Course;
import com.fullstack.demo.model.Instructor;
import com.fullstack.demo.repository.InMemoryCourseRepository;

import java.util.List;

public class TestCourseService {
    public static void main(String[] args) {
        InMemoryCourseRepository repo = new InMemoryCourseRepository();
        CourseService service = new CourseService(repo);

        Course c1 = new Course("C001", "Java Basics", 10, "Beginner");
        Course c2 = new Course("C002", "Advanced Java", 20, "Advanced");

        repo.save(c1);
        repo.save(c2);

        Instructor inst = new Instructor("I002", "Bob Smith", "React");
        service.assignInstructor("C002", inst);

        // verify assignment
        Course updated = service.getCourseById("C002");
        if (updated.getInstructor() == null) {
            System.err.println("FAIL: Instructor was not assigned to C002");
            System.exit(1);
        }
        if (!"Bob Smith".equals(updated.getInstructor().getInstructorName())) {
            System.err.println("FAIL: Instructor name mismatch: " + updated.getInstructor().getInstructorName());
            System.exit(1);
        }

        // search by instructor name (case-insensitive, partial)
        List<Course> results1 = service.searchByInstructorName("bob");
        if (results1.size() != 1 || !"C002".equals(results1.get(0).getCourseId())) {
            System.err.println("FAIL: searchByInstructorName('bob') expected C002, got: " + results1);
            System.exit(1);
        }

        // search with null -> should be treated as empty string and return courses with instructors
        List<Course> results2 = service.searchByInstructorName(null);
        if (results2.size() != 1) {
            System.err.println("FAIL: searchByInstructorName(null) expected 1, got: " + results2.size());
            System.exit(1);
        }

        // search name that doesn't exist
        List<Course> results3 = service.searchByInstructorName("alice");
        if (results3.size() != 0) {
            System.err.println("FAIL: searchByInstructorName('alice') expected 0, got: " + results3.size());
            System.exit(1);
        }

        System.out.println("All tests passed.");
        System.exit(0);
    }
}

