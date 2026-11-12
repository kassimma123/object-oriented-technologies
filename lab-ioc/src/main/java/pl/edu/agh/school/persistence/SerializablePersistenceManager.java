package pl.edu.agh.school.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.SchoolClass;
import pl.edu.agh.school.Teacher;

public final class SerializablePersistenceManager implements PersistenceManager {

    private final String teachersStorageFileName;
    private final String classStorageFileName;
    private final Logger log;

    @Inject
    public SerializablePersistenceManager(@Named("teachersFileName") String teachersStorageFileName,
                                          @Named("classesFileName") String classStorageFileName,
                                          Logger log) {
        this.teachersStorageFileName = teachersStorageFileName;
        this.classStorageFileName = classStorageFileName;
        this.log = log;
    }

    @Override
    public void saveTeachers(List<Teacher> teachers) {
        if (teachers == null) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(teachersStorageFileName))) {
            oos.writeObject(teachers);
            log.log("Saved " + teachers.size() + " teachers to " + teachersStorageFileName);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            log.log("There was an error while saving the teachers data", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Teacher> loadTeachers() {
        ArrayList<Teacher> res = null;
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(teachersStorageFileName))) {
            res = (ArrayList<Teacher>) ios.readObject();
            log.log("Loaded " + res.size() + " teachers from " + teachersStorageFileName);
        } catch (FileNotFoundException e) {
            res = new ArrayList<>();
        } catch (IOException e) {
            log.log("There was an error while loading the teachers data", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return res;
    }

    @Override
    public void saveClasses(List<SchoolClass> classes) {
        if (classes == null) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(classStorageFileName))) {
            oos.writeObject(classes);
            log.log("Saved " + classes.size() + " classes to " + classStorageFileName);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            log.log("There was an error while saving the classes data", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SchoolClass> loadClasses() {
        ArrayList<SchoolClass> res = null;
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(classStorageFileName))) {
            res = (ArrayList<SchoolClass>) ios.readObject();
            log.log("Loaded " + res.size() + " classes from " + classStorageFileName);
        } catch (FileNotFoundException e) {
            res = new ArrayList<>();
        } catch (IOException e) {
            log.log("There was an error while loading the classes data", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return res;
    }
}