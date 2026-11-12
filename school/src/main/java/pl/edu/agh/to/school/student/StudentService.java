package pl.edu.agh.to.school.student;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.grade.GradeService;

@Service
public class StudentService {

    private final GradeService gradeService;

    public StudentService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("Student service is up");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("Student service is down");
    }

}
