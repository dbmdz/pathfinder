package org.mdz.workflow.labs.pathfinder;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PathfinderTest {

  private Pathfinder pathfinder;

  @BeforeEach
  void setUp() {
    pathfinder =
        new Pathfinder()
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})$", "/path/to/%2$s/%1$s%2$s%3$s_hocr.xml")
            .addPattern("^(\\w{3})(\\d{4})(\\d{4})-(\\w{16})$", "/other/path/to/%2$s/%4$s.xml");
  }

  @Test
  void shouldMatchFirstPattern() {
    assertThat(pathfinder.find("bsb40041234"))
        .contains(Path.of("/path/to/4004/bsb40041234_hocr.xml"));
  }

  @Test
  void shouldMatchSecondPattern() {
    assertThat(pathfinder.find("bsb40041234-hasvalue87654321"))
        .contains(Path.of("/other/path/to/4004/hasvalue87654321.xml"));
  }

  @Test
  void noMatchesShouldReturnEmpty() {
    assertThat(pathfinder.find("this-does-not-match-anything")).isEmpty();
  }
}
