package org.mdz.pathfinder;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemFixture {

  private final FileSystem fileSystem;

  public FileSystemFixture() {
    this.fileSystem = Jimfs.newFileSystem(Configuration.unix());
  }

  public Path path(String name) {
    return fileSystem.getPath(name);
  }

  public Path createFile(String name) throws IOException {
    var path = path(name);
    Files.createDirectories(path.getParent());
    Files.createFile(path);
    return path;
  }

  public FileSystem getFileSystem() {
    return fileSystem;
  }

  public Path createDirectory(String name) throws IOException {
    var path = path(name);
    Files.createDirectories(path);
    return path;
  }

  public Path[] createDirectories(String... names) throws IOException {
    Path[] result = new Path[names.length];
    for (int i = 0; i < names.length; i++) {
      result[i] = createDirectory(names[i]);
    }
    return result;
  }
}
