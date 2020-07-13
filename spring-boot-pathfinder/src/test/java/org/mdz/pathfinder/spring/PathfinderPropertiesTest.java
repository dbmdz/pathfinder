package org.mdz.pathfinder.spring;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mdz.pathfinder.spring.PathfinderProperties.PathPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = PathfinderPropertiesConfiguration.class)
@DisplayName("The PathfinderProperties")
class PathfinderPropertiesTest {

  @Autowired private PathfinderProperties pathfinderProperties;

  @Test
  void shouldLoadSingleTemplate() {
    var actual = pathfinderProperties.getPatterns().get(0);
    assertThat(actual.getPattern()).isEqualTo("^(\\w{3})(\\d{4})(\\d{4})$");
    assertThat(actual.getTemplates()).containsExactly("%1$s%2$s%3$s_hocr.xml");
  }

  @Test
  void shouldLoadListOfTemplates() {
    var actual = pathfinderProperties.getPatterns().get(1);
    assertThat(actual.getPattern()).isEqualTo("^(\\w{3})(\\d{4})(\\d{4})$.list");
    assertThat(actual.getTemplates()).containsExactly("%1$s%2$s%3$s_hocr.xml", "%1$s%2$s%3$.txt");
  }

  @Test
  void shouldNeverSpecifyListAndTemplates() {
    assertThatExceptionOfType(RuntimeException.class)
        .isThrownBy(() -> new PathfinderProperties.PathPattern("pattern", "abc", List.of("xzy")));
  }

  @Test
  @DisplayName("should transport PathPattern")
  void shouldTransportPathPatterns() {
    var patterns =
        new PathPattern[] {
          new PathPattern("a", "b", emptyList()), new PathPattern("x", "y", emptyList())
        };
    var pathfinderProperties = new PathfinderProperties(List.of(patterns));
    assertThat(pathfinderProperties.getPatterns()).containsExactly(patterns);
  }
}
