package org.barino3d.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.barino3d.repositories.ApplicationRepository;
import org.barino3d.repositories.CommandRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        final Application application3 = new Application("Windows");
        final Application application4 = new Application("CentOS");
        final Application application5 = new Application("MongoDB");
        final Application application6 = new Application("Git");

        applicationRepository.save(application1);
        applicationRepository.save(application2);
        applicationRepository.save(application3);
        applicationRepository.save(application4);
        applicationRepository.save(application5);
        applicationRepository.save(application6);

        final Command command1 = new Command("Double 'SHIFT' button press", "searching Everywhere", application1);
        final Command command2 = new Command("docker run mongo", "run docker container from image named 'mongo'", application2);
        final Command command3 = new Command("docker ps", "list containers", application2);
        final Command command4 = new Command("docker images", "list images", application2);
        final Command command5 = new Command("docker help", "list available commands", application2);
        final Command command6 = new Command( "version: \"3.9\"  # optional since v1.27.0\n" +
                "services:\n" +
                "  web:\n" +
                "    build: .\n" +
                "    ports:\n" +
                "      - \"5000:5000\"\n" +
                "    volumes:\n" +
                "      - .:/code\n" +
                "      - logvolume01:/var/log\n" +
                "    links:\n" +
                "      - redis\n" +
                "  redis:\n" +
                "    image: redis\n" +
                "volumes:\n" +
                "  logvolume01: {}","docker compose example", application2);
        final Command command7 = new Command("'Ctrl' + 'Shift' + 'F8'", "searching Everywhere", application1);
        final Command command8 = new Command("test", "test", application6);

        commandRepository.save(command1);
        commandRepository.save(command2);
        commandRepository.save(command3);
        commandRepository.save(command4);
        commandRepository.save(command5);
        commandRepository.save(command6);
        commandRepository.save(command7);
        commandRepository.save(command8);


        List<Command> list1 =  new ArrayList<>();
        list1.add(command1);
        list1.add(command7);
        List<Command> list2 =  new ArrayList<>();
        list2.add(command2);
        list2.add(command3);
        list2.add(command4);
        list2.add(command5);
        application1.addCommands(list1);
        application2.addCommands(list2);
        application6.addCommand(command8);
        applicationRepository.save(application1);
        applicationRepository.save(application2);
        applicationRepository.save(application6);
    }
}
