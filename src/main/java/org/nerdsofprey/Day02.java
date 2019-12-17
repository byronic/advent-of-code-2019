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
import org.nerdsofprey.util.FileUtils;

import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Named("Day02")
public class Day02 implements AOCIntegerResults {
  public final static String AOC2019_DAY02 = "AOC2019_DAY02";
  private int[] program;

  private void resetProgram(String path) throws IOException {
    this.program = new int[1];
    this.program = FileUtils.readIntegerArray(Paths.get(path), ",");
  }

  private int apply(int opcode, int a, int b) {
    switch(opcode) {
      case 1: return a + b;
      case 2: return a * b;
      default: throw new RuntimeException(String.format("invalid opcode '%d'!", opcode));
    }
  }

  void executeProgram(int startPos) {
    if (program[startPos] == 99) {
      return;
    }
    program[program[startPos + 3]] = apply(program[startPos], program[program[startPos + 1]], program[program[startPos + 2]]);
    executeProgram(startPos + 4);
  }

  int part1(Map<String, String> env) throws IOException {
    resetProgram(env.get(AOC2019_DAY02));
    program[1] = 12;
    program[2] = 2;
    executeProgram(0);
    return program[0];
  }

  int part2(Map<String, String> env) throws IOException {
    for(int a = 0; a < 100; a++) {
      for(int b = 0; b < 100; b++) {
        resetProgram(env.get(AOC2019_DAY02));
        program[1] = a;
        program[2] = b;
        executeProgram(0);
        if (program[0] == 19690720) {
          return 100 * a + b;
        }
      }
    }
    executeProgram(0);
    return program[0];
  }

  @Override
  public Map<String, Integer> get() {
    Map<String, Integer> results = new HashMap<>();
    try {
      results.put("Day 2, part 1: ", part1(System.getenv()));
      results.put("Day 2, part 2: ", part2(System.getenv()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return results;
  }
}
