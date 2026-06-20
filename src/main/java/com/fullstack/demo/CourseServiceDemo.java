package com.fullstack.demo;

import com.fullstack.demo.exception.InvalidCourseException;
import com.fullstack.demo.model.Course;
import com.fullstack.demo.repository.InMemoryCourseRepository;
import com.fullstack.demo.service.CourseService;

public class CourseServiceDemo {

    public static void main(String[] args) {
        // Create repository and service
        InMemoryCourseRepository repository = new InMemoryCourseRepository();
        CourseService courseService = new CourseService(repository);

        // === Valid Course Test ===
        System.out.println("=== Valid Course Test ===");
        testValidCourse(courseService);

        System.out.println();

        // === Create and List Courses ===
        System.out.println("=== Create Courses ===");
        testCreateMultipleCourses(courseService);

        System.out.println();
        System.out.println("=== All Courses ===");
        courseService.getAllCourses().forEach(course -> {
            System.out.println(course.getCourseId() + " - " + course.getTitle());
        });

        System.out.println();

        // === Search and Filter Demo ===
        System.out.println("=== Search by title: 'java' ===");
        courseService.searchByTitle("java").forEach(c -> System.out.println(c.getCourseId() + " - " + c.getTitle()));

        System.out.println();
        System.out.println("=== Filter by level: 'Beginner' ===");
        courseService.filterByLevel("Beginner").forEach(c -> System.out.println(c.getCourseId() + " - " + c.getTitle()));

        // === Invalid Course Tests ===
        System.out.println("=== Invalid Course Tests ===");
        testInvalidCourses(courseService);
    }

    private static void testValidCourse(CourseService courseService) {
        try {
            Course validCourse = new Course("C010", "Java Advanced Topics", 40, "Advanced");
            Course saved = courseService.createCourse(validCourse);
            System.out.println("✓ Course saved successfully: " + saved.getCourseId());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static void testCreateMultipleCourses(CourseService courseService) {
        try {
            Course course1 = new Course("C001", "Java Fundamentals", 30, "Beginner");
            courseService.createCourse(course1);
            System.out.println("✓ Course saved: " + course1.getCourseId());

            Course course2 = new Course("C002", "React Frontend Development", 35, "Intermediate");
            courseService.createCourse(course2);
            System.out.println("✓ Course saved: " + course2.getCourseId());

            Course course3 = new Course("C003", "MongoDB Basics", 25, "Beginner");
            courseService.createCourse(course3);
            System.out.println("✓ Course saved: " + course3.getCourseId());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static void testInvalidCourses(CourseService courseService) {
        // Test 1: Null course
        System.out.println("\nTest 1: Null course");
        try {
            courseService.createCourse(null);
            System.out.println("✗ Should have thrown exception");
        } catch (InvalidCourseException e) {
            System.out.println("✓ Validation error: " + e.getMessage());
        }

        // Test 2: Empty ID
        System.out.println("\nTest 2: Course with empty ID");
        try {
            Course invalidCourse = new Course("", "Test Course", 20, "Beginner");
            courseService.createCourse(invalidCourse);
            System.out.println("✗ Should have thrown exception");
        } catch (Exception e) {
            System.out.println("✓ Validation error: " + e.getMessage());
        }

        // Test 3: Empty title
        System.out.println("\nTest 3: Course with empty title");
        try {
            Course invalidCourse = new Course("C100", "", 20, "Beginner");
            courseService.createCourse(invalidCourse);
            System.out.println("✗ Should have thrown exception");
        } catch (Exception e) {
            System.out.println("✓ Validation error: " + e.getMessage());
        }

        // Test 4: Duration zero
        System.out.println("\nTest 4: Course with duration 0");
        try {
            Course invalidCourse = new Course("C101", "Invalid Duration", 0, "Beginner");
            courseService.createCourse(invalidCourse);
            System.out.println("✗ Should have thrown exception");
        } catch (Exception e) {
            System.out.println("✓ Validation error: " + e.getMessage());
        }

        // Test 5: Duration negative
        System.out.println("\nTest 5: Course with negative duration");
        try {
            Course invalidCourse = new Course("C102", "Invalid Duration", -10, "Beginner");
            courseService.createCourse(invalidCourse);
            System.out.println("✗ Should have thrown exception");
        } catch (Exception e) {
            System.out.println("✓ Validation error: " + e.getMessage());
        }

        // Test 6: Empty level
        System.out.println("\nTest 6: Course with empty level");
        try {
            Course invalidCourse = new Course("C103", "No Level Course", 20, "");
            courseService.createCourse(invalidCourse);
            System.out.println("✗ Should have thrown exception");
        } catch (Exception e) {
            System.out.println("✓ Validation error: " + e.getMessage());
        }
    }
}

