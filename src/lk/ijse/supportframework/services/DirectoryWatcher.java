package lk.ijse.supportframework.services;

import javafx.collections.ObservableSet;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public interface DirectoryWatcher {
    /**
     * Adds an directory to the set of watched directories for this object, provided that it is not the same as some directory already in the set.
     * Specified directory will be monitored for all possible <code>WatchEvents</code>
     *
     * @param path Path of the directory to be watched
     * @throws NullPointerException If the pathname argument is null
     * @throws NoSuchFileException If the file declared by <code>Path</code> does not exist
     * @throws IOException If any other I/O Exception is caused
     */
    void registerDirectory(Path path) throws NullPointerException, NoSuchFileException, IOException;

    /**
     * Adds an directory to the set of watched directories for this object, provided that it is not the same as some directory already in the set.
     * Specified directory will be monitored for all possible <code>WatchEvents</code>
     *
     * @param pathname Absolute or Relative path string to the directory to be watched
     * @throws InvalidPathException If the path declared by pathname isn't valid
     * @throws NoSuchFileException If the file declared by <code>Path</code> does not exist
     * @throws IOException If any other I/O Exception is caused
     */
    void registerDirectory(String pathname) throws InvalidPathException, NoSuchFileException, IOException;

    /**
     * Removes the directory designated by the path.
     *
     * @param path Path of the directory to be removed.
     * @return true if the watcher contained the specified element
     * @throws NullPointerException If the <code>Path</code> is null
     */
    boolean removeDirectory(Path path) throws NullPointerException;

    /**
     * Retrieves and removes the directory designated by the pathname, or null if none are registered or no such path found.
     *
     * @param pathname Absolute or relative path string to the directory to be watched.
     * @return <code>Path</code> of the removed directory.
     */
    Path removeDirectory(String pathname);

    /**
     * Provides a <code>ObservableSet</code> with the directories of files which were newly created.
     * <p>
     * The <code>ObservableSet</code> returned should be provided with a valid <code>ListChangeListener</code>
     *
     * @return JavaFX <code>ObservableSet</code> with no valid Listener
     */
    ObservableSet<Path> getCreatedSet();

    /**
     * Provides a <code>ObservableSet</code> with the directories of files which were deleted.
     * <p>
     * The <code>ObservableSet</code> returned should be provided with a valid <code>ListChangeListener</code>
     *
     * @return JavaFX <code>ObservableSet</code> with no valid Listener
     */
    ObservableSet<Path> getDeletedSet();

    /**
     * Provides a <code>ObservableSet</code> with the directories of files which were modified.
     * <p>
     * The <code>ObservableSet</code> returned should be provided with a valid <code>ListChangeListener</code>
     *
     * @return JavaFX <code>ObservableSet</code> with no valid Listener
     */
    ObservableSet<Path> getModifiedSet();

    /**
     * Provides a <code>ObservableSet</code> with the directories of files with any type of change.
     * <p>
     * The <code>ObservableSet</code> returned should be provided with a valid <code>ListChangeListener</code>
     *
     * @return JavaFX <code>ObservableSet</code> with no valid Listener
     */
    ObservableSet<Path> getGeneralSet();
}
