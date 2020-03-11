package org.mdz.pathfinder.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mdz.pathfinder.Pathfinder;
import org.mdz.pathfinder.spring.PathfinderProperties.PathPattern;

class PathfinderConfigurationTest {

  @Test
  void pathfinder() {
    var properties =
        new PathfinderProperties(
            List.of(
                new PathPattern("^x(\\d+)$", "/x/%1$s"),
                new PathPattern("^y(\\d+)$", "/y/%1$s"),
                new PathPattern("^z(\\d+)$", "/z/%1$s")));

    var pathfinderConfiguration = new PathfinderConfiguration();

    Pathfinder pathfinder = pathfinderConfiguration.pathfinder(properties);

    assertThat(pathfinder.find("x1234")).contains(Path.of("/x/1234"));
    assertThat(pathfinder.find("y1234")).contains(Path.of("/y/1234"));
    assertThat(pathfinder.find("z1234")).contains(Path.of("/z/1234"));
    assertThat(pathfinder.find("no_id")).isEmpty();
  }
}
