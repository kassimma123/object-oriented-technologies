package pl.edu.agh.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.NotificationService;

@Service
public class GradeService {

    private final GradeBook gradeBook;
    private final NotificationService notificationService;

    public GradeService(GradeBook gradeBook, NotificationService notificationService) {
        this.gradeBook = gradeBook;
        this.notificationService = notificationService;
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("Grade service is up");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("Grade service is down");
    }
}
