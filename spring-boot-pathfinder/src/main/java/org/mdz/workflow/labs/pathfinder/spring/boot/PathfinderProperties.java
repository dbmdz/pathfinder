package org.mdz.workflow.labs.pathfinder.spring.boot;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "pathfinder")
public class PathfinderProperties {

  public class PathPattern {
    private String pattern;
    private String directory;

    public PathPattern(String pattern, String directory) {
      this.pattern = pattern;
      this.directory = directory;
    }

    public String getPattern() {
      return pattern;
    }

    public String getDirectory() {
      return directory;
    }
  }

  private List<PathPattern> patterns;

  public PathfinderProperties(
      List<PathPattern> patterns) {
    this.patterns = patterns;
  }

  public List<PathPattern> getPatterns() {
    return patterns;
  }

  @Override
  public String toString() {
    return "PathfinderPropertysource{" +
        "patterns=" + patterns +
        '}';
  }
}
