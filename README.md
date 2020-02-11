# Pathfinder path matching and utilities

*Warning: This is still experimental code*

Draft of a simple and elegant library to generate Paths from identifier patterns. It builds on the
strong `java.nio` features (esp. `java.nio.Path`) with file processing in mind.

Example:

```java

public class PathfinderTest {

  @Test
  void shouldMatchPattern() {
    Pathfinder pathfinder = new Pathfinder()
        .addPattern("^(\\w{3})(\\d{4})(\\d{4})$",
            "/path/to/%2$s/%1$s%2$s%3$s_hocr.xml")
        .addPattern("^(\\w{3})(\\d{4})(\\d{4})-(\\w{16})$",
            "/other/path/to/%2$s/%4$s.xml");

    assertThat(pathfinder.find("bsb40041234"))
        .contains(Path.of("/path/to/4004/bsb40041234_hocr.xml"));

    assertThat(pathfinder.find("bsb40041234-hasvalue87654321"))
        .contains(Path.of("/other/path/to/4004/hasvalue87654321.xml"));
  }

}
``` 


Spring:

```java
@EnablePathfinder
class Application {

  private Pathfinder pathfinder;

  public Application(Pathfinder pathfinder) {
    this.pathfinder = pathfinder;
  }

  // ...

}
```