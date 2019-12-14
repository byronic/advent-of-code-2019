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

import com.google.inject.Guice;
import org.eclipse.sisu.EagerSingleton;
import org.eclipse.sisu.space.SpaceModule;
import org.eclipse.sisu.space.URLClassSpace;
import org.eclipse.sisu.wire.WireModule;
import org.nerdsofprey.util.AOCIntegerResults;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

@Named
@EagerSingleton
public class Executor implements Runnable {

  @Inject
  Executor(Map<String, AOCIntegerResults> results) {
    results.values().forEach(result -> {
      result.get().forEach((k, v) -> {
        System.out.println(String.format("%s %d", k, v));
      });
    });
  }

  public static void main(String[] args) throws IOException {
    ClassLoader classloader = Executor.class.getClassLoader();
    Guice.createInjector(
      new WireModule(                       // auto-wires unresolved dependencies
        new SpaceModule(                     // scans and binds @Named components
          new URLClassSpace(classloader)    // abstracts class/resource finding
        )));
  }

  @Override
  public void run() {

  }
}
