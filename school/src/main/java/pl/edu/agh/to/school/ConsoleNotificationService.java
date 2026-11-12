package pl.edu.agh.to.school;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class ConsoleNotificationService implements NotificationService{
    @PostConstruct
    public void onServiceStarted() {
        System.out.println("Console notification service is up");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("Console notification service is down");
    }
}
