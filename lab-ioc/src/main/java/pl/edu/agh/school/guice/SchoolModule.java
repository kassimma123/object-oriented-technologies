package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;
import com.google.inject.name.Names;
import pl.edu.agh.logger.Logger;

public class SchoolModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(PersistenceManager.class).to(SerializablePersistenceManager.class);
        bind(String.class).annotatedWith(Names.named("teachersFileName")).toInstance("guice-teachers.dat");
        bind(String.class).annotatedWith(Names.named("classesFileName")).toInstance("guice-classes.dat");
    }

    @Provides
    @Singleton
    public Logger provideLogger(){
        Logger logger = new Logger();
        logger.registerSerializer(new FileMessageSerializer("persistence.log"));
        return logger;
    }
}