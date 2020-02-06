package org.mdz.workflow.labs.pathfinder.spring.boot;

import org.mdz.workflow.labs.pathfinder.PathSpec;
import org.mdz.workflow.labs.pathfinder.Pathfinder;
import org.mdz.workflow.labs.pathfinder.spring.boot.PathfinderProperties.PathPattern;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(PathfinderProperties.class)
public class PathfinderConfig {

  public Pathfinder pathfinder(PathfinderProperties pathfinderProperties) {
    Pathfinder pathfinder = new Pathfinder();
    for (PathPattern pattern : pathfinderProperties.getPatterns()) {
      pathfinder.add(new PathSpec(pattern.getPattern(), pattern.getDirectory()));
    }
    return new Pathfinder();
  }

}
