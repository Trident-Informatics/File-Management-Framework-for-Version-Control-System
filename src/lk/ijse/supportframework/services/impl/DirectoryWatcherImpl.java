package lk.ijse.supportframework.services.impl;

import lk.ijse.supportframework.services.DirectoryWatcher;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryWatcherImpl implements DirectoryWatcher {
    WatchService watcher;

    public DirectoryWatcherImpl() {
        try {
            watcher= FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run(){
                while (true){
                    WatchKey key = null;
                    try {
                        key=watcher.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Path path= (Path) key.watchable();
                    path=path.toAbsolutePath();


                    for (WatchEvent<?> watchEvent : key.pollEvents()) {
                        WatchEvent.Kind kind=watchEvent.kind();

                        if (kind==OVERFLOW){
                            continue;
                        }else if (kind==ENTRY_CREATE){

                        }else if (kind==ENTRY_DELETE){

                        }else if (kind==ENTRY_MODIFY){

                        }
                    }

                    

                    boolean valid=key.reset();
                    if (!valid) {
                        break;
                    }

                }
            }
        }.start();
    }

    /**
     * Adds an directory to the set of watched directories for this object, provided that it is not the same as some directory already in the set.
     * Specified directory will be monitored for all possible <code>WatchEvents</code>
     *
     * @param path Path of the directory to be watched
     * @throws NullPointerException If the pathname argument is null
     * @throws InvalidPathException If the path declared by pathname does not exist
     * @throws IOException If any other I/O Exception is caused
     */
    @Override
    public void registerDirectory(Path path) throws NullPointerException, InvalidPathException, IOException {
        WatchKey key=path.register(watcher,ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY);
    }

    /**
     * Adds an directory to the set of watched directories for this object, provided that it is not the same as some directory already in the set.
     * Specified directory will be monitored for all possible <code>WatchEvents</code>
     *
     * @param pathname Absolute or Relative path string to the directory to be watched
     * @throws NullPointerException If the pathname argument is null
     * @throws InvalidPathException If the path declared by pathname does not exist
     */
    @Override
    public void registerDirectory(String pathname) throws NullPointerException, InvalidPathException {

    }

    /**
     * Removes the directory designated by the path.
     *
     * @param path Path of the directory to be removed.
     * @return true if the watcher contained the specified element
     */
    @Override
    public boolean removeDirectory(Path path) throws NullPointerException {
        return false;
    }

    /**
     * Retrieves and removes the directory designated by the pathname, or null if none are registered or no such path found.
     *
     * @param pathname Absolute or relative path string to the directory to be watched.
     * @return <code>Path</code> of the removed directory.
     * @throws NullPointerException If the pathname argument is null.
     */
    @Override
    public Path removeDirectory(String pathname) throws NullPointerException {
        return null;
    }
}
