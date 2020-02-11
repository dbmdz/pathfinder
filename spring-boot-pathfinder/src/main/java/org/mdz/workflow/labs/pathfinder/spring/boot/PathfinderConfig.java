package org.mdz.workflow.labs.pathfinder.spring.boot;

import org.mdz.workflow.labs.pathfinder.Pathfinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(PathfinderProperties.class)
public class PathfinderConfig {

  public Pathfinder pathfinder(PathfinderProperties pathfinderProperties) {
    Pathfinder pathfinder = new Pathfinder();
    pathfinderProperties
        .getPatterns()
        .forEach(p -> pathfinder.addPattern(p.getPattern(), p.getTemplate()));
    return new Pathfinder();
  }
}
