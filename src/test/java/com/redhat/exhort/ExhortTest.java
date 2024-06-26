/*
 * Copyright © 2023 Red Hat, Inc.
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
package com.redhat.exhort;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ExhortTest {

  protected String getStringFromFile(String... list) {
    byte[] bytes = new byte[0];
    try {
      InputStream resourceAsStream = getResourceAsStreamDecision(this.getClass(), list);
      bytes = resourceAsStream.readAllBytes();
      resourceAsStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return new String(bytes);
  }

  public static InputStream getResourceAsStreamDecision(
      Class<? extends ExhortTest> theClass, String[] list) throws IOException {
    InputStream resourceAsStreamFromModule =
        theClass.getModule().getResourceAsStream(String.join("/", list));
    if (Objects.isNull(resourceAsStreamFromModule)) {
      return theClass.getClassLoader().getResourceAsStream(String.join("/", list));
    }
    return resourceAsStreamFromModule;
  }

  protected String getFileFromResource(String fileName, String... pathList) {
    Path tmpFile;
    try {
      var tmpDir = Files.createTempDirectory("exhort_test_");
      tmpFile = Files.createFile(tmpDir.resolve(fileName));
      try (var is = getResourceAsStreamDecision(this.getClass(), pathList)) {
        if (Objects.nonNull(is)) {
          Files.write(tmpFile, is.readAllBytes());
        } else {
          InputStream resourceIs =
              getClass().getClassLoader().getResourceAsStream(String.join("/", pathList));
          Files.write(tmpFile, resourceIs.readAllBytes());
          resourceIs.close();
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return tmpFile.toString();
  }

  protected String getFileFromString(String fileName, String content) {
    Path tmpFile;
    try {
      var tmpDir = Files.createTempDirectory("exhort_test_");
      tmpFile = Files.createFile(tmpDir.resolve(fileName));
      Files.write(tmpFile, content.getBytes());

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return tmpFile.toString();
  }
}
