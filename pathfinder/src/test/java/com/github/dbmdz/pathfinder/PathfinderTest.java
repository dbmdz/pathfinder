package com.github.dbmdz.pathfinder;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pathfinder")
public class PathfinderTest {

  private Pathfinder pathfinder;

  private FileSystemFixture fs;

  @BeforeAll
  static void setUpClass() {
    System.setProperty("user.home", "/home/tester");
  }

  @BeforeEach
  void setUp() {
    fs = new FileSystemFixture();
    pathfinder =
        new Pathfinder(fs.getFileSystem())
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})$", "/path/to/%2$s/%1$s%2$s%3$s_hocr.xml")
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})-(\\w{16})$", "/other/path/to/%2$s/%4$s.xml")
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})$", "/path/to/%2$s/%1$s%2$s%3$s.txt")
            .addPattern("^test$", "/tmp/test")
            .addPattern("repeated", "path_1", "path_2");
  }

  @Test
  void shouldReplaceTilde() {
    pathfinder.addPattern("123", "~/subdir1/subdir2/filename.xml");
    Optional<Path> actual = pathfinder.find("123");
    Path expected = fs.path("/home/tester/subdir1/subdir2/filename.xml");
    assertThat(actual).contains(expected);

    pathfinder.addPattern("456", "file:~/subdir1/subdir2/filename.xml");
    actual = pathfinder.find("456");
    expected = fs.path("file:/home/tester/subdir1/subdir2/filename.xml");
    assertThat(actual).contains(expected);
  }

  @Test
  @DisplayName("find should return first matching path for identifier")
  void shouldMatchPattern() {
    var actual = pathfinder.find("bsb40041234-hasvalue87654321");
    var expected = fs.path("/other/path/to/4004/hasvalue87654321.xml");
    assertThat(actual).contains(expected);
  }

  @Test
  @DisplayName("find should find nothing if identifier doesn't match")
  void noMatchesShouldReturnEmpty() {
    assertThat(pathfinder.find("this-does-not-match-anything")).isEmpty();
  }

  @Test
  @DisplayName("findAll should return all matching paths")
  void shouldFindAllMatchingPatterns() {
    var paths = pathfinder.findAll("bsb40041234");
    assertThat(paths)
        .containsExactly(
            fs.path("/path/to/4004/bsb40041234_hocr.xml"),
            fs.path("/path/to/4004/bsb40041234.txt"));
  }

  @Test
  @DisplayName("findExisting should find path for existing file")
  void shouldFindPathForExistingFile() throws IOException {
    Path expected = fs.createFile("/path/to/4004/bsb40041234_hocr.xml");
    Path actual = pathfinder.findExisting("bsb40041234").orElseThrow();
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("findExisting should find path for multiple existing files")
  void shouldFindPathForMultipleExistingFiles() throws IOException {
    Path[] expected = fs.createDirectories("path_1", "path_2");
    List<Path> actual = pathfinder.findAllExisting("repeated");
    assertThat(actual).containsExactly(expected);
  }

  @Test
  @DisplayName("findExisting should find nothing for missing file")
  void shouldNotFindPathForNotExistingFile() {
    Optional<Path> actual = pathfinder.findExisting("bsb40041234");
    assertThat(actual).isEmpty();
  }

  @Test
  @DisplayName("find should be able to return a directory")
  void shouldReturnDirectory() throws IOException {
    Path expected = fs.createDirectory("/tmp/test");
    Optional<Path> actual = pathfinder.find("test");
    assertThat(actual).isNotEmpty();
    assertThat(actual.get()).isEqualTo(expected);
  }
}
