/**
 * Copyright © 2019 admin (admin@infrastructurebuilder.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nerdsofprey;

import org.nerdsofprey.util.AOCIntegerResults;

import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Named("Day04")
public class Day04 implements AOCIntegerResults {
  public final static String AOC2019_DAY04 = "AOC2019_DAY04";


  private boolean checkMinCompliance(int c, int min) {
    return c >= min;
  }

  private boolean isCompliant(String p) {
    int min = p.charAt(0);
    boolean foundDouble = false;
    for(int i = 1; i < 6; i++) {
      if(!checkMinCompliance(p.charAt(i), min)) {
        return false;
      }
      if(p.charAt(i) == p.charAt(i - 1)) {
        foundDouble = true;
      }
      min = p.charAt(i);
    }
    return foundDouble;
  }

  private int findCompliantPasswords(int low, int high) {
    int result = 0;
    for (int i = low; i < high; i++) {
      if (isCompliant(String.format("%d", i))) {
        System.out.println(String.format("Found compliant password %d", i));
        result++;
      }
    }
    return result;
  }

  int part1(Map<String, String> env) throws IOException {
    String[] range = Files.readAllLines(Paths.get(env.get(AOC2019_DAY04))).get(0).split("-");

    return findCompliantPasswords(Integer.parseInt(range[0]), Integer.parseInt(range[1]));
  }

  private int findHighestDouble(String p) {
    int highestDouble = -1;
    for (int i = 1; i < 6; i++) {
      if (p.charAt(i) == p.charAt(i - 1)) {
        highestDouble = p.charAt(i);
      }
    }
    System.out.println(String.format("Highest double in %s is %d", p, highestDouble));
    return highestDouble;
  }

  private boolean repeatingAdjacentCharsExceeding(String p, int h) {
    int repeating = 0;
    for (int i = 0; i < 6; i++) {
      if (p.charAt(i) == h) {
        repeating++;
      }
      if (repeating > 2) {
        return true;
      }
    }
    return false;
  }

  private boolean isHarshlyCompliant(String p) {
    boolean compliant = false;
    if (isCompliant(p)) {
      int hd = findHighestDouble(p);
      compliant = !repeatingAdjacentCharsExceeding(p, hd);
    }
    return compliant;
  }

  private int findCompliantPasswordsHarsher(int low, int high) {
    int result = 0;
    for (int i = low; i <= high; i++) {
      if (isHarshlyCompliant(String.format("%d", i))) {
        System.out.println(String.format("Found harshly compliant password %d", i));
        result++;
      }
    }
    return result;
  }

  int part2(Map<String, String> env) throws IOException {
    String[] range = Files.readAllLines(Paths.get(env.get(AOC2019_DAY04))).get(0).split("-");

    return findCompliantPasswordsHarsher(Integer.parseInt(range[0]), Integer.parseInt(range[1]));
  }

  @Override
  public Map<String, Integer> get() {
    Map<String, Integer> results = new HashMap<>();
    try {
      results.put("Day 4, part 1: ", part1(System.getenv()));
      results.put("Day 4, part 2: ", part2(System.getenv()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return results;
  }
}
