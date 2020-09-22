package com.github.dbmdz.pathfinder.spring;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.dbmdz.pathfinder.Pathfinder;
import com.github.dbmdz.pathfinder.spring.PathfinderProperties.PathPattern;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("The PathfinderConfiguration")
class PathfinderConfigurationTest {

  @Test
  @DisplayName("should construct Pathfinder from PathfinderProperties")
  void shouldConstructPathfinderFromPathfinderProperties() {
    var properties =
        new PathfinderProperties(
            List.of(
                new PathPattern("^x(\\d+)$", "/x/%1$s", emptyList()),
                new PathPattern("^y(\\d+)$", "/y/%1$s", emptyList()),
                new PathPattern("^z(\\d+)$", "/z/%1$s", emptyList())));

    var pathfinderConfiguration = new PathfinderConfiguration();

    Pathfinder pathfinder = pathfinderConfiguration.pathfinder(properties);

    assertThat(pathfinder.find("x1234")).contains(Path.of("/x/1234"));
    assertThat(pathfinder.find("y1234")).contains(Path.of("/y/1234"));
    assertThat(pathfinder.find("z1234")).contains(Path.of("/z/1234"));
    assertThat(pathfinder.find("no_id")).isEmpty();
  }
}
