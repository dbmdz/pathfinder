package com.github.dbmdz.pathfinder.spring;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.lang.Nullable;

/** Spring properties file to load Pathfinder configuration <code>application.yml</code>. */
@ConstructorBinding
@ConfigurationProperties(prefix = "pathfinder")
public class PathfinderProperties {

  public static class PathPattern {
    private final String pattern;
    private final List<String> templates;

    public PathPattern(
        String pattern, @Nullable String template, @Nullable List<String> templates) {
      if (template == null && (templates == null || templates.isEmpty())) {
        throw new RuntimeException("PathPattern needs either template or templates to be set");
      }
      if (template != null && templates != null && !templates.isEmpty()) {
        throw new RuntimeException("PathPattern can only use template or templates, not both");
      }
      this.pattern = pattern;
      if (template != null) {
        this.templates = List.of(template);
      } else {
        this.templates = templates;
      }
    }

    public String getPattern() {
      return pattern;
    }

    public List<String> getTemplates() {
      return templates;
    }
  }

  private final List<PathPattern> patterns;

  public PathfinderProperties(List<PathPattern> patterns) {
    this.patterns = patterns;
  }

  public List<PathPattern> getPatterns() {
    return patterns;
  }

  @Override
  public String toString() {
    return "PathfinderProperties{" + "patterns=" + patterns + '}';
  }
}
