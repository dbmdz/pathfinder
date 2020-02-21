package org.mdz.pathfinder;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pathfinder")
public class PathfinderTest {

  private Pathfinder pathfinder;

  private FileSystemFixture fs;

  @BeforeEach
  void setUp() {
    fs = new FileSystemFixture();
    pathfinder =
        new Pathfinder(fs.getFileSystem())
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})$", "/path/to/%2$s/%1$s%2$s%3$s_hocr.xml")
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})-(\\w{16})$", "/other/path/to/%2$s/%4$s.xml")
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})$", "/path/to/%2$s/%1$s%2$s%3$s.txt");
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
  @DisplayName("findExisting should find nothing for missing file")
  void shouldNotFindPathForNotExistingFile() throws IOException {
    Optional<Path> actual = pathfinder.findExisting("bsb40041234");
    assertThat(actual).isEmpty();
  }
}
