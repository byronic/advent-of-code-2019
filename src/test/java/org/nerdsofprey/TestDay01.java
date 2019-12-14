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

import org.infrastructurebuilder.util.IBUtils;
import org.infrastructurebuilder.util.config.WorkingPathSupplier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestDay01 {
  private WorkingPathSupplier wps;
  private Path path;

  @Before
  public void setUp() throws Exception {
    wps = new WorkingPathSupplier();
    Path source = wps.getRoot().resolve("test-classes").resolve("test.md");
    path = wps.get().resolve(UUID.randomUUID().toString());
    IBUtils.copy(source, path);
  }

  @After
  public void teardown() {
    wps.finalize();
  }

  @Test
  public void testGivenEquations() throws IOException {
    Map<String, String> env = new HashMap<>();
    env.put(Day01.AOC2019_DAY01, path.toString());
    assertEquals("Example math from the site should add to 34241", 34241, new Day01().part1(env));
  }

  @Test (expected = RuntimeException.class)
  public void testWithException() throws Exception {
    new Day01().part1(new HashMap<>());
  }
}
