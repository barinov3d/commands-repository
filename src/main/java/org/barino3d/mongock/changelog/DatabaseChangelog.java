package org.barino3d.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.barino3d.repositories.ApplicationRepository;
import org.barino3d.repositories.CommandRepository;

import java.util.Arrays;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "dmitry", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertData", author = "dmitry")
    public void insertData(ApplicationRepository applicationRepository, CommandRepository commandRepository) {

        final Application application1 = new Application("IntelliJ IDEA");
        final Application application2 = new Application("Docker");

        final Application application3 = new Application("MongoDB");
        final Application application4 = new Application("Git");

        applicationRepository.save(application1);
        applicationRepository.save(application2);

        applicationRepository.save(application3);
        applicationRepository.save(application4);

        final Command command1 = new Command("Double 'SHIFT' button press", "searching Everywhere", application1);
        final Command command2 = new Command("'Ctrl' + mouse click", "go to declaration", application1);
        final Command command3 = new Command("docker run mongo", "run docker container from image named 'mongo'", application2);
        final Command command4 = new Command("docker ps", "show running containers", application2);

        final Command command5 = new Command("use DATABASE_NAME", "use exact database", application3);
        final Command command6 = new Command("show dbs", "listing all the databases", application3);

        final Command command7 = new Command("git status", "show current branch status", application4);
        final Command command8 = new Command("git pull", "pull from remote branch", application4);

        commandRepository.save(command1);
        commandRepository.save(command2);
        commandRepository.save(command3);
        commandRepository.save(command4);
        commandRepository.save(command5);
        commandRepository.save(command6);
        commandRepository.save(command7);
        commandRepository.save(command8);

        application1.addCommands(Arrays.asList(command1, command2));
        application2.addCommands(Arrays.asList(command3, command4));
        application3.addCommands(Arrays.asList(command5, command6));
        application4.addCommands(Arrays.asList(command7, command8));

        applicationRepository.save(application1);
        applicationRepository.save(application2);
        applicationRepository.save(application3);
        applicationRepository.save(application4);
    }
}
