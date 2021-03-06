package com.github.dbmdz.pathfinder.spring;

import static java.util.Objects.requireNonNull;

import com.github.dbmdz.pathfinder.Pathfinder;
import com.github.dbmdz.pathfinder.spring.PathfinderProperties.PathPattern;
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
    for (PathPattern pattern : patterns) {
      for (String template : pattern.getTemplates()) {
        pathfinder.addPattern(pattern.getPattern(), template);
      }
    }
    return pathfinder;
  }
}
