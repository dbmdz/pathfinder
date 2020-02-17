package org.mdz.workflow.labs.pathfinder;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specifies a path pattern to match existing files and a template to generae a corresponding {@link
 * java.nio.file.Path}.
 */
class PathSpec {

  private Pattern pattern;

  private String template;

  private FileSystem fileSystem;

  /**
   * @param regex regular expression to match an file identifier
   * @param template Java template String used to generate the expected corresponding {@link Path}.
   *     There are no guarantees that this path does exist.
   * @param fileSystem the {@link FileSystem} to work on
   */
  public PathSpec(String regex, String template, FileSystem fileSystem) {
    this.pattern = Pattern.compile(regex);
    this.template = template;
    this.fileSystem = fileSystem;
  }

  /**
   * @param id the identifier to match
   * @return the result of matching the pattern with <code>id</code>
   */
  private Matcher match(String id) {
    return pattern.matcher(id);
  }

  /**
   * Generate the path.
   *
   * @param args the individual path parts used to generate the path
   * @return corresponding {@link Path}. There are no guarantees that this path does exist.
   */
  private Path path(String... args) {
    var string = String.format(template, args);
    return fileSystem.getPath(string);
  }

  /**
   * Generate the path for a given identifier
   *
   * @param id the identifier to generate the {@link Path} for.
   * @return the corresponding {@link Path} if the pattern matches, otherwise <code>null</code>.
   */
  public Path pathFor(String id) {
    Matcher matcher = match(id);
    if (!matcher.matches()) {
      return null;
    }
    String[] args = new String[matcher.groupCount()];
    for (int i = 0; i < matcher.groupCount(); i++) {
      args[i] = matcher.group(i + 1);
    }
    return path(args);
  }
}
