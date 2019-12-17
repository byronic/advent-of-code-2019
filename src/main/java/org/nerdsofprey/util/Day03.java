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
package org.nerdsofprey.util;

import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Named("Day02")
public class Day03 implements AOCIntegerResults {
  public final static String AOC2019_DAY03 = "AOC2019_DAY03";

  private final Map<Integer, List<Integer>> path1 = new HashMap<>();
  private final Map<Integer, List<Integer>> path2 = new HashMap<>();

  private class Pair<T, V> {
    private final T t;
    private final V v;

    public Pair(T t, V v) {
      this.t = t;
      this.v = v;
    }
    public T getKey() {
      return t;
    }

    public V getValue() {
      return v;
    }
  }

  // I know this is silly
  private int calculateManhattanDistance(int x, int y) {
    return x + y;
  }

  private int checkForCollisions(int max, int result, int x) {
    if (path1.containsKey(max) && path2.containsKey(max)) {
      if (path1.get(max).contains(x) && path2.get(max).contains(x)) {
        int r = calculateManhattanDistance(max, x);
        if (result == -1 || result > r) {
          result = r;
        }
      }
    }
    return result;
  }

  private Optional<Integer> searchShortestCollision(int max) {
    int result = -1;
    for(int x = 0; x < max; x++) {
      // check (x, max)
      result = checkForCollisions(x, result, max);

      // check (max, x)
      result = checkForCollisions(max, result, x);
    }

    if (result == -1) {
      return Optional.empty();
    }

    return Optional.of(result);
  }



  private int findNearestCollision() {
    int max = 1;
    int result = -1;
    while(result < 0) {
      Optional<Integer> shortestCollision = searchShortestCollision(max);
      if (shortestCollision.isPresent()) {
        result = shortestCollision.get();
      }
      max += 1;
    }
    return result;
  }

  private Pair<Integer, Integer> drawLine(Map<Integer, List<Integer>> p, char direction, int x, int y, int remaining) {
    if (remaining == 0) {
      return new Pair<>(x, y);
    }
    if (!p.containsKey(x)) {
      p.put(x, new ArrayList<>());
    }
    if (!p.get(x).contains(y)) {
      p.get(x).add(y);
    }
    switch (direction) {
      case 'U': return drawLine(p, direction, x, y + 1, remaining - 1);
      case 'D': return drawLine(p, direction, x, y - 1, remaining - 1);
      case 'R': return drawLine(p, direction, x + 1, y, remaining - 1);
      case 'L': return drawLine(p, direction, x - 1, y, remaining - 1);
    }
    throw new RuntimeException("I should never make it this far in drawLine!");
  }

  private void traverse(Map<Integer, List<Integer>> p, String line) {
    String[] directions = line.split(",");
    Pair<Integer, Integer> xy = new Pair<>(0, 0);
    for (String d : directions) {
      xy = drawLine(p, d.charAt(0), xy.getKey(), xy.getValue(), Integer.parseInt(d.substring(1)));
    }
  }

  private void loadMaps(Path p) throws IOException {
    List<String> lines = Files.readAllLines(p);
    traverse(path1, lines.get(0));
    traverse(path2, lines.get(1));
  }

  int part1(Map<String, String> env) throws IOException {
    loadMaps(Paths.get(env.get(AOC2019_DAY03)));
    return findNearestCollision();
  }

  @Override
  public Map<String, Integer> get() {
    Map<String, Integer> results = new HashMap<>();
    try {
      results.put("Day 3, part 1: ", part1(System.getenv()));
      // results.put("Day 3, part 2: ", part2(System.getenv()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return results;
  }
}
