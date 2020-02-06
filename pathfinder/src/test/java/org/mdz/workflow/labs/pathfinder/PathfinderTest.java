package org.mdz.workflow.labs.pathfinder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PathfinderTest {

  private Pathfinder pathfinder;

  @BeforeEach
  void setUp() {
    pathfinder = new Pathfinder();
  }

  @Test
  void magicShouldDo() {
    var regex = "^(\\w{3})(\\d{4})(\\d{4})$";
    var template = "/bsbstruc/content/bsb_content%1$s/bsb%1$s%2$s/xml/hocr/1.0/bsb%1$s%2$s_hocr.xml";
    var mdzID = "bsb10000001";
    var path = pathfinder.magic(regex, template, mdzID);
    System.out.println(path);
  }

  @Test
  void match() {
    var regex = "^(\\w{3})(\\d{4})(\\d{4})$";
    Pattern pattern = java.util.regex.Pattern.compile(regex);
    Matcher matcher = pattern.matcher("bsb10000001");
    matcher.matches();
    System.out.println("groupCount: " + matcher.groupCount());
    System.out.println("0: " + matcher.group(0));
    System.out.println("1: " + matcher.group(1));
    System.out.println("2: " + matcher.group(2));
    System.out.println("3: " + matcher.group(3));
  }


}