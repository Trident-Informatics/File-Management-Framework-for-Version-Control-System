package lk.ijse.supportframework.services;

public interface FileServices {
    void addDirectoryListener(String absolutePath) throws Exception;

    void cloneDirectory(String absolutePathOfFolder, String path);

    void renameAndMove(String filePath, String toPath, String newName);

    boolean compareSHAcode(String file1, String file2);
}
