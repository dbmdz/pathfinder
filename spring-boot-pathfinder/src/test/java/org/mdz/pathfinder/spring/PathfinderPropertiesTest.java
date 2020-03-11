package org.mdz.pathfinder.spring;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mdz.pathfinder.spring.PathfinderProperties.PathPattern;

class PathfinderPropertiesTest {

  @Test
  void shouldDoThings() {
    var patterns = new PathPattern[] {new PathPattern("a", "b"), new PathPattern("x", "y")};
    var pathfinderProperties = new PathfinderProperties(List.of(patterns));
    Assertions.assertThat(pathfinderProperties.getPatterns()).containsExactly(patterns);
  }
}
