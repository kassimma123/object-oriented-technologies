package pl.edu.agh.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class GradeBook {
    @PostConstruct
    public void onServiceStarted() {
        System.out.println("Grade book is up");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("Grade book is down");
    }
}
