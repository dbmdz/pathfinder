# Pathfinder path matching and utilities

Resolve paths for identifier patterns.
 
*Example:* Hierarchical paths for many files

```
bsb12345678_hocr.xml → /storage/12/34/56/78.xml
```

*Example:* Find the matching formats on disk:
```
bsb12345678 → /storage/12/34/56/78.jp2
bsb12345678 → /storage/12/34/56/78.tif
bsb12345678 → /storage/12/34/56/78.jpg
```
Pathfinder can yield the first filepath for that ID that exists.


## Usage 

### Java-only

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


### Spring Boot

In `application.yml`:
```yml
pathfinder:
  patterns:
    - pattern: '^(\w{3})(\d{4})(\d{4})$'
      template: '/bsbstruc/content/bsb_content%2$s/%1$s%2$s%3$s/xml/hocr/1.0/%1$s%2$s%3$s_hocr.xml'
    - pattern: '^(\w{3})(\d{2})(\d{2})(\d{2})(\d{2})-(\w{16})$'
      template: '/bsb_fastocr/%1$s%2$s/%3$s/%4$s/%5$s/hocr_%6$s.html'
```

To specify patterns, use [Java regular expression syntax](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/Pattern.html).
For the path templates the full power of [`java.util.Formatter`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Formatter.html)
is available. Templates support the `~/` abbreviation for user home directories.

```java
@EnablePathfinder
class Application {

  private final Pathfinder pathfinder;

  public Application(Pathfinder pathfinder) {
    this.pathfinder = pathfinder;
  }

  // ...

}
```