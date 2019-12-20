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

@Named("Day02")
public class Day03 implements AOCIntegerResults {
  public final static String AOC2019_DAY03 = "AOC2019_DAY03";

  private final Map<Integer, List<Integer>> path1 = new HashMap<>();
  private final Map<Integer, List<Integer>> path2 = new HashMap<>();

  private final Map<Integer, List<Pair<Integer, Integer>>> pathSteps1 = new HashMap<>();
  private final Map<Integer, List<Pair<Integer, Integer>>> pathSteps2 = new HashMap<>();

  // I know this is silly
  private class Pair<T, V> {
    private final T t;
    private final V v;

    public Pair(T t, V v) {
      Objects.requireNonNull(t);
      Objects.requireNonNull(v);
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

  // yes, yes, silly
  private class Triplet<A, B, C> {
    private final A a;
    private final B b;
    private final C c;

    public Triplet(A a, B b, C c) {
      Objects.requireNonNull(a);
      Objects.requireNonNull(b);
      Objects.requireNonNull(c);
      this.a = a;
      this.b = b;
      this.c = c;
    }

    public A x() {
      return a;
    }

    public B y() {
      return b;
    }

    public C z() {
      return c;
    }
  }

  // I know this is also silly
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

  private Optional<Integer> containsKey(List<Pair<Integer, Integer>> l, int y) {
    if(l.parallelStream().anyMatch(k -> k.getKey().equals(y)))
      return Optional.of(l.stream().filter(t -> t.getKey().equals(y)).collect(Collectors.toList()).get(0).getValue());
    return Optional.empty();
  }

  // part two needed different calculations all the way up :/
  private Triplet<Integer, Integer, Integer> drawLineWithSteps(Map<Integer, List<Pair<Integer, Integer>>> p, char direction, int x, int y, int remaining, int steps) {
    if (remaining == 0) {
      return new Triplet<>(x, y, steps);
    }
    if (!p.containsKey(x)) {
      p.put(x, new ArrayList<>());
    }
    if (!containsKey(p.get(x), y).isPresent()) {
      p.get(x).add(new Pair<>(y, steps));
    }
    switch (direction) {
      case 'U': return drawLineWithSteps(p, direction, x, y + 1, remaining - 1, steps + 1);
      case 'D': return drawLineWithSteps(p, direction, x, y - 1, remaining - 1, steps + 1);
      case 'R': return drawLineWithSteps(p, direction, x + 1, y, remaining - 1, steps + 1);
      case 'L': return drawLineWithSteps(p, direction, x - 1, y, remaining - 1, steps + 1);
    }
    throw new RuntimeException("I should never make it this far in drawLine!");
  }

  private void traverseWithSteps(Map<Integer, List<Pair<Integer, Integer>>> p, String line) {
    String[] directions = line.split(",");
    Triplet<Integer, Integer, Integer> xySteps = new Triplet<>(0, 0, 0);
    for (String d : directions) {
      xySteps = drawLineWithSteps(p, d.charAt(0), xySteps.x(), xySteps.y(), Integer.parseInt(d.substring(1)), xySteps.z());
    }
  }

  private void loadMapsWithSteps(Path p) throws IOException {
    List<String> lines = Files.readAllLines(p);
    traverseWithSteps(pathSteps1, lines.get(0));
    traverseWithSteps(pathSteps2, lines.get(1));
  }

  // Map<Integer, List<Pair<Integer, Integer>>> (x, List((y, steps), (y, steps)[...]))
  private int findNearestCollisionLowSteps() {
    List<Triplet<Integer, Integer, Integer>> collisionList = pathSteps1.keySet().parallelStream().flatMap(key -> {
      List<Triplet<Integer, Integer, Integer>> collisions = new ArrayList<>();
      if (pathSteps2.containsKey(key)) {
        // collisions contain x, y, steps
        collisions.addAll(pathSteps1.get(key).parallelStream().map(pair -> {
          int steps = containsKey(pathSteps2.get(key), pair.getKey()).orElse(-1);
          if (steps >= 0) {
            if (key == 0 && pair.getKey() == 0) {
              return new Triplet<>(-1, -1, -1);
            }
            return new Triplet<>(key, pair.getKey(), pair.getValue() + steps);
          } else {
            return new Triplet<>(-1, -1, -1);
          }
        }).filter(t -> t.z() >= 0).collect(Collectors.toList()));
      }
      return collisions.stream();
    }).collect(Collectors.toList());
    collisionList.forEach(t -> {
      System.out.println(String.format("Collision at (%d, %d) : %d steps", t.x(), t.y(), t.z()));
    });
    return collisionList.stream().min(Comparator.comparing(Triplet::z)).get().z();
  }

  private int part2(Map<String, String> env) throws IOException {
    loadMapsWithSteps(Paths.get(env.get(AOC2019_DAY03)));
    return findNearestCollisionLowSteps();
  }



  @Override
  public Map<String, Integer> get() {
    Map<String, Integer> results = new HashMap<>();
    try {
      results.put("Day 3, part 1: ", part1(System.getenv()));
      results.put("Day 3, part 2: ", part2(System.getenv()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return results;
  }
}
