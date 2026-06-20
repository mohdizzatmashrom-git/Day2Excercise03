package com.fullstack.demo.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String courseId) {
        // Use message format expected by the assignment sample
        super("Course not found: " + courseId);
    }

}
