package pl.edu.agh.iisg.to.dao;

import org.hibernate.Session;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.SessionService;

import java.util.List;
import java.util.Optional;

public class StudentDao extends GenericDao<Student> {

    public StudentDao(SessionService sessionService) {
        super(sessionService, Student.class);
    }

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        // TODO - implement - done
        Student student = new Student(firstName, lastName, indexNumber);
        return save(student);

    }

    public List<Student> findAll() {
        // TODO - implement - done
        Session session = currentSession();
        return session.createQuery("SELECT s FROM Student s ORDER BY s.lastName, s.firstName", Student.class)
                .getResultList();

    }

    public Optional<Student> findByIndexNumber(final int indexNumber) {
        Session session = currentSession();
        // TODO - implement, bez wyjątku tutaj optional załatwia całkiem
        return session.createQuery("SELECT s FROM Student s WHERE s.indexNumber = :indexNumber", Student.class)
                .setParameter("indexNumber", indexNumber)
                .uniqueResultOptional();
    }
}
