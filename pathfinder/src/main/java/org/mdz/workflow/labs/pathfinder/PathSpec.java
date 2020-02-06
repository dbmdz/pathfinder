package org.mdz.workflow.labs.pathfinder;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathSpec {

  private Pattern pattern;

  private String template;

  public PathSpec(String regex, String template) {
    this.pattern = Pattern.compile(regex);
    this.template = template;
  }

  public Matcher match(String id) {
    return pattern.matcher(id);
  }

  public String getRegex() {
    return pattern.pattern();
  }

  public Pattern getPattern() {
    return pattern;
  }

  public String getTemplate() {
    return template;
  }

  public Path path(String... args) {
    var string = String.format(template, args);
    return Path.of(string);
  }
}
