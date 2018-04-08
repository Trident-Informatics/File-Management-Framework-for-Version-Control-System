package test;

import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import lk.ijse.supportframework.services.DirectoryWatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryWatcherDemo {

    public static void main(String[] args) {
        DirectoryWatcher watcher = DirectoryWatcher.getDefault();
        ObservableSet<Path> created=watcher.getCreatedSet();

        SetChangeListener<Path> createdListener=(change)->{
            Path p=change.getElementAdded();

            System.out.println(p.toString());
            File f=p.toFile();
            System.out.println("Listener path: "+f.getAbsolutePath());
            System.out.println("Listener exists: "+f.exists());
            System.out.println("Listener isDirectory: "+f.isDirectory());
        };

        created.addListener(createdListener);
        try {
            watcher.registerDirectory(Paths.get("test"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f=new File("./test/abc");
        f.mkdir();
        System.out.println("Is Directory: "+f.isDirectory());

    }
}
