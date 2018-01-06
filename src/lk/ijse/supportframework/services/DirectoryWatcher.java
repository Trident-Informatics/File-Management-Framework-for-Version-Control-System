package lk.ijse.supportframework.services;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public interface DirectoryWatcher {
    /**
     * Adds an directory to the set of watched directories for this object, provided that it is not the same as some directory already in the set.
     * Specified directory will be monitored for all possible <code>WatchEvents</code>
     *
     * @param path Path of the directory to be watched
     * @throws NullPointerException If the pathname argument is null
     * @throws InvalidPathException If the path declared by pathname does not exist
     * @throws IOException If any other I/O Exception is caused
     */
    void registerDirectory(Path path) throws NullPointerException, InvalidPathException, IOException;

    /**
     * Adds an directory to the set of watched directories for this object, provided that it is not the same as some directory already in the set.
     * Specified directory will be monitored for all possible <code>WatchEvents</code>
     *
     * @param pathname Absolute or Relative path string to the directory to be watched
     * @throws NullPointerException If the pathname argument is null
     * @throws InvalidPathException If the path declared by pathname does not exist
     */
    void registerDirectory(String pathname) throws NullPointerException, InvalidPathException;

    /**
     * Removes the directory designated by the path.
     *
     * @param path Path of the directory to be removed.
     * @return true if the watcher contained the specified element
     */
    boolean removeDirectory(Path path) throws NullPointerException;

    /**
     * Retrieves and removes the directory designated by the pathname, or null if none are registered or no such path found.
     *
     * @param pathname Absolute or relative path string to the directory to be watched.
     * @return <code>Path</code> of the removed directory.
     * @throws NullPointerException If the pathname argument is null.
     */
    Path removeDirectory(String pathname) throws NullPointerException;
}
