package lk.ijse.supportframework.services.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import lk.ijse.supportframework.services.DirectoryWatcher;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryWatcherImpl implements DirectoryWatcher {
    WatchService watcher;
    Map<Path, WatchKey> watchList = new Hashtable<>();
    private ObservableSet<Path> createdSet = FXCollections.synchronizedObservableSet(FXCollections.observableSet(new HashSet<Path>()));
    private ObservableSet<Path> deletedSet = FXCollections.synchronizedObservableSet(FXCollections.observableSet(new HashSet<Path>()));
    private ObservableSet<Path> modifiedSet = FXCollections.synchronizedObservableSet(FXCollections.observableSet(new HashSet<Path>()));
    private ObservableSet<Path> generalSet = FXCollections.synchronizedObservableSet(FXCollections.observableSet(new HashSet<Path>()));


    public DirectoryWatcherImpl() {
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while (true) {
                WatchKey key = null;
                try {
                    key = watcher.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Path path = (Path) key.watchable();
                path = path.toAbsolutePath();


                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = watchEvent.kind();

                    WatchEvent<Path> event = (WatchEvent<Path>) watchEvent;

                    if (kind == OVERFLOW) {
                        continue;
                    } else if (kind == ENTRY_CREATE) {
                        createdSet.add(event.context());
                    } else if (kind == ENTRY_DELETE) {
                        deletedSet.add(event.context());
                    } else if (kind == ENTRY_MODIFY) {
                        modifiedSet.add(event.context());
                    }
                    generalSet.add(event.context());

                }


                boolean valid = key.reset();
                if (!valid) {
                    break;
                }

            }

        }).start();
    }


    @Override
    public void registerDirectory(Path path) throws NullPointerException, NoSuchFileException, IOException {
        if (path == null) {
            throw new NullPointerException();
        } else {
            path = path.toAbsolutePath();
            if (!path.toFile().exists()) {
                throw new NoSuchFileException(path.toFile().toString());
            } else {
                WatchKey key = path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                watchList.put(path,key);
            }
        }
    }

    @Override
    public void registerDirectory(String pathname) throws InvalidPathException, NoSuchFileException, IOException {
        registerDirectory(Paths.get(pathname));
    }

    @Override
    public boolean removeDirectory(Path path) throws NullPointerException {
        if (path == null) {
            throw new NullPointerException();
        }
        path = path.toAbsolutePath();
        if (watchList.containsKey(path)) {
            watchList.get(path).cancel();
            watchList.remove(path);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Path removeDirectory(String pathname) {
        Path path=Paths.get(pathname);
        path=path.toAbsolutePath();
        if(removeDirectory(path)){
            return path;
        }else
            return null;
    }

    public ObservableSet<Path> getCreatedSet() {
        return createdSet;
    }

    public void setCreatedSet(ObservableSet<Path> createdSet) {
        this.createdSet = createdSet;
    }

    public ObservableSet<Path> getDeletedSet() {
        return deletedSet;
    }

    public void setDeletedSet(ObservableSet<Path> deletedSet) {
        this.deletedSet = deletedSet;
    }

    public ObservableSet<Path> getModifiedSet() {
        return modifiedSet;
    }

    public void setModifiedSet(ObservableSet<Path> modifiedSet) {
        this.modifiedSet = modifiedSet;
    }

    public ObservableSet<Path> getGeneralSet() {
        return generalSet;
    }

    public void setGeneralSet(ObservableSet<Path> generalSet) {
        this.generalSet = generalSet;
    }
}
