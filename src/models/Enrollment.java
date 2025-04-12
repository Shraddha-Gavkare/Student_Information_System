package models;

import java.sql.Timestamp;
import exceptions.InvalidStudentDataException;

public class Enrollment {
    private static int idCounter = 1;
    private int enrollmentID;
    private int studentId;
	private int courseId;
	private Timestamp enrollmentDate;
    private Student student;
    private Course course;
    
	

   
    public Enrollment(Student student, Course course) {
        this.enrollmentID = idCounter++;
        this.student = student;
        this.course = course;
        this.enrollmentDate = new Timestamp(System.currentTimeMillis());
    }

    public Enrollment(int enrollmentID, int studentId, int courseId, Timestamp timestamp) {
        this.enrollmentID = enrollmentID;

        this.student = new Student(courseId);       
        student.setId(studentId);            

        this.course = new Course(courseId, null, courseId);          
        course.setCourseId(courseId);

        this.enrollmentDate = timestamp;
    }



    public Enrollment(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId; 
    }

    public int getCourseId() {
        return courseId;
    }


    // Getters
    public int getEnrollmentID() {
        return enrollmentID;
    }

   

  
    public Timestamp getEnrollmentDate() {
        return enrollmentDate;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

	public int setEnrollmentId(int enrollmentID) {
        return	enrollmentID;	
	}

	public int setCourseId(int i) {
		return i;
		
	}
	
	public Course setcourse() {
		return course;
	}

	public Student setStudent(Student student) {
           		return student;
	}
}
