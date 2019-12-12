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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day01 {
  public final static String AOC2019_DAY01_PART1 = "AOC2019_DAY01_PART1";

  private int fuelRequired(int mass) {
    return (mass / 3) - 2;
  }

  private int sumFuel(Set<Integer> masses) {
    return masses.parallelStream().map(mass -> fuelRequired(mass)).mapToInt(Integer::intValue).sum();
  }

  private int execute(Path path) throws IOException {
    return sumFuel(Files.readAllLines(path).parallelStream().map(line -> Integer.parseInt(line)).collect(Collectors.toSet()));
  }

  /**
   * Execute day 1, part 1!
   * @param env The system environment variables (or, if you are testing this function, a custom map).
   * @return Returns the sum of the values as indicated by AOC day 1, part 1.
   * @throws IOException If the environment variable doesn't contain a valid / readable file.
   */
  public int part1(Map<String, String> env) throws IOException {
    if (!env.containsKey(AOC2019_DAY01_PART1)) {
      throw new RuntimeException(String.format("Missing required environment variable %s", AOC2019_DAY01_PART1));
    }
    return execute(Paths.get(env.get(AOC2019_DAY01_PART1)));
  }

  public static void main(String[] args) throws IOException {
    System.out.println(new Day01().part1(System.getenv()));
  }
}
