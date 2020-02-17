package org.mdz.workflow.labs.pathfinder;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PathfinderTest {

  private Pathfinder pathfinder;

  private FileSystem fileSystem;

  @BeforeEach
  void setUp() {
    fileSystem = Jimfs.newFileSystem(Configuration.unix());
    pathfinder =
        new Pathfinder(fileSystem)
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})$", "/path/to/%2$s/%1$s%2$s%3$s_hocr.xml")
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})-(\\w{16})$", "/other/path/to/%2$s/%4$s.xml");
  }

  @Test
  void shouldMatchFirstPattern() {
    assertThat(pathfinder.find("bsb40041234"))
        .contains(path("/path/to/4004/bsb40041234_hocr.xml"));
  }

  @Test
  void shouldMatchSecondPattern() {
    assertThat(pathfinder.find("bsb40041234-hasvalue87654321"))
        .contains(path("/other/path/to/4004/hasvalue87654321.xml"));
  }

  @Test
  void noMatchesShouldReturnEmpty() {
    assertThat(pathfinder.find("this-does-not-match-anything")).isEmpty();
  }

  @Test
  void shouldWorkWithTemporaryFileSystem() throws IOException {
    createNewFile("/path/to/4004/bsb40041234_hocr.xml");
    Path actualPath = pathfinder.find("bsb40041234").orElseThrow();
    assertThat(actualPath).exists();
  }

  private void createNewFile(String pathToFile) throws IOException {
    Path path = fileSystem.getPath(pathToFile);
    Files.createDirectories(path.getParent());
    Files.createFile(path);
  }

  private Path path(String path) {
    return fileSystem.getPath(path);
  }
}
