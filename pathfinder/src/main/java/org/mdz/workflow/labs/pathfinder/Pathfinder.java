package org.mdz.workflow.labs.pathfinder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/** Find paths matching identifiers. */
public class Pathfinder {

  private List<PathSpec> pathSpecs;

  public Pathfinder() {
    this.pathSpecs = new ArrayList<>();
  }

  /**
   * Adds a new pattern. Priority is in order of addition, first added have highest priority.
   *
   * @param pattern regular expression to match identifiers (Java syntax)
   * @param template Java template string to generate {@link Path}s
   * @return the same Pathfinder instance to allow chaining
   * @see java.util.Formatter
   * @see java.util.regex.Pattern
   */
  public Pathfinder addPattern(String pattern, String template) {
    pathSpecs.add(new PathSpec(pattern, template));
    return this;
  }

  /**
   * Returns a {@link Path} for the first pattern matching <code>id</code>.
   *
   * <p><em>There are no guarantees that a corresponding file exists.</em>
   *
   * @param id identifier to match
   * @return the corresponding {@link Path}
   */
  public Optional<Path> find(String id) {
    return pathSpecs.stream()
        .map(pathSpec -> pathSpec.pathFor(id))
        .filter(Objects::nonNull)
        .findFirst();
  }

  /**
   * Returns a {@link List} of all {@link Path}s with patterns matching <code>id</code>.
   *
   * <p><em>There are no guarantees that corresponding files exist.</em>
   *
   * @param id identifier to match
   * @return all corresponding {@link Path}s
   */
  public List<Path> findAll(String id) {
    return pathSpecs.stream()
        .map(pathSpec -> pathSpec.pathFor(id))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  /**
   * Returns a {@link Path} for the first pattern matching <code>id</code> only if the corresponding
   * file exists.
   *
   * @param id identifier to match
   * @return the corresponding {@link Path}
   */
  public Optional<Path> findExisting(String id) {
    return find(id).filter(Files::exists);
  }
}
