package org.mdz.pathfinder.spring;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mdz.pathfinder.spring.PathfinderProperties.PathPattern;

@DisplayName("PathfinderProperties")
class PathfinderPropertiesTest {

  @Test
  @DisplayName("should transport PathPattern")
  void shouldTransportPathPatterns() {
    var patterns = new PathPattern[] {new PathPattern("a", "b"), new PathPattern("x", "y")};
    var pathfinderProperties = new PathfinderProperties(List.of(patterns));
    Assertions.assertThat(pathfinderProperties.getPatterns()).containsExactly(patterns);
  }
}
