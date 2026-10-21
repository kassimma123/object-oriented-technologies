package pl.edu.agh.iisg.to.service;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.repository.StudentRepository;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;

public class SchoolService {

    private final TransactionService transactionService;

    private final StudentRepository studentRepository;

    private final StudentDao studentDao;

    private final CourseDao courseDao;

    private final GradeDao gradeDao;

    public SchoolService(TransactionService transactionService, StudentRepository studentRepository, StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao) {
        this.transactionService = transactionService;
        this.studentRepository = studentRepository;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
    }

    public boolean enrollStudent(final Course course, final Student student) {
        // TODO - implement - done
        if (course.studentSet().contains(student)){
            return false;
        }
        return transactionService.doAsTransaction(() -> {
            course.studentSet().add(student);
            student.courseSet().add(course);
            return true;
        }).orElse(false);
    }

    public boolean removeStudent(int indexNumber) {
        // TODO - implement - done
        Optional<Student> studentOpt = studentDao.findByIndexNumber(indexNumber);
        if (studentOpt.isEmpty()){
            return false;
        }
        return transactionService.doAsTransaction(() -> {
            Student student = studentOpt.get();
            student.courseSet().forEach(c -> c.studentSet().remove(student));
            student.courseSet().clear();
            studentRepository.remove(student);
            return true;
        }).orElse(false);

    }

    public boolean gradeStudent(final Student student, final Course course, final float gradeValue) {
        // TODO - implement - done
        return transactionService.doAsTransaction(() -> {
            Grade grade = new Grade(student, course, gradeValue);
            gradeDao.save(grade);
            student.gradeSet().add(grade);
            course.gradeSet().add(grade);
            return true;
        }).orElse(false);
    }

    public Map<String, List<Float>> getStudentGrades(String courseName) {
        // TODO - implement - done
        Optional<Course> courseOpt =  courseDao.findByName(courseName);
        if (courseOpt.isEmpty()){
            return Collections.emptyMap();
        }

        Map<String, List<Float>> result = new HashMap<>();
        for (Student student : courseOpt.get().studentSet()) {
            List<Float> grades = student.gradeSet().stream()
                    .filter(g -> g.course().equals(courseOpt.get()))
                    .map(g -> g.grade())
                    .sorted()
                    .toList();
            result.put(student.fullName(), grades);
        }
        return result;
    }
}
