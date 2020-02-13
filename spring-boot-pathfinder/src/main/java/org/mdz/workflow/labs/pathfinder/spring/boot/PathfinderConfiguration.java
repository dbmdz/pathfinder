package org.mdz.workflow.labs.pathfinder.spring.boot;

import static java.util.Objects.requireNonNull;

import org.mdz.workflow.labs.pathfinder.Pathfinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PathfinderPropertiesConfiguration.class)
public class PathfinderConfiguration {

  @Bean
  public Pathfinder pathfinder(PathfinderProperties pathfinderProperties) {
    var pathfinder = new Pathfinder();
    var patterns =
        requireNonNull(
            pathfinderProperties.getPatterns(),
            "Pathfinder configuration in application.yml is missing patterns");
    patterns.forEach(p -> pathfinder.addPattern(p.getPattern(), p.getTemplate()));
    return pathfinder;
  }
}
