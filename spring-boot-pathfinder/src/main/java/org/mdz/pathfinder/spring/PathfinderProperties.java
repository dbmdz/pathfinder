package org.mdz.pathfinder.spring;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/** Spring properties file to load Pathfinder configuration <code>application.yml</code>. */
@ConstructorBinding
@ConfigurationProperties(prefix = "pathfinder")
public class PathfinderProperties {

  public static class PathPattern {
    private String pattern;
    private String template;

    public PathPattern(String pattern, String template) {
      this.pattern = pattern;
      this.template = template;
    }

    public String getPattern() {
      return pattern;
    }

    public String getTemplate() {
      return template;
    }
  }

  private List<PathPattern> patterns;

  public PathfinderProperties(List<PathPattern> patterns) {
    this.patterns = patterns;
  }

  public List<PathPattern> getPatterns() {
    return patterns;
  }

  @Override
  public String toString() {
    return "PathfinderPropertysource{" + "patterns=" + patterns + '}';
  }
}
