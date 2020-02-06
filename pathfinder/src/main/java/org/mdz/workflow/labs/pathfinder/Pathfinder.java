package org.mdz.workflow.labs.pathfinder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

public class Pathfinder {

  private List<PathSpec> pathSpecs;

  public Pathfinder() {
    this.pathSpecs = new ArrayList<>();
  }


  public Pathfinder(PathSpec... pathSpecs) {
    this.pathSpecs = new ArrayList<>(List.of(pathSpecs));
  }

  public Pathfinder add(PathSpec pathSpec) {
    pathSpecs.add(pathSpec);
    return this;
  }

  public Optional<Path> find(String id) {
    for (PathSpec pathSpec : pathSpecs) {
      Matcher matcher = pathSpec.match(id);
      if (!matcher.matches()) {
        continue;
      }
      String[] args = new String[matcher.groupCount()];
      for (int i = 0; i < matcher.groupCount(); i++) {
        args[i] = matcher.group(i + 1);
      }
      return Optional.of(pathSpec.path(args));
    }
    return Optional.empty();
  }

  public Optional<Path> findExisting(String id) {
    return find(id).filter(Files::exists);
  }

}
