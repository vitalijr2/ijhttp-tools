/*-
 * ---------------LICENSE_START-----------------
 * ijhttp tools
 * ---------------------------------------------
 * Copyright (C) 2023 - 2025 Vitalij Berdinskih
 * ---------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ---------------LICENSE_END-------------------
 */
package io.gitlab.vitalijr2.ijhttptools.junitextension;

import static java.util.Objects.nonNull;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.time.Duration;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class HttpClientExecutorExtension implements ParameterResolver {

  private static final Logger LOGGER = System.getLogger(
      HttpClientCommandLineExtension.class.getName());

  private static DefaultExecutor getExecutor() {
    return DefaultExecutor.builder().get();
  }

  @Override
  public Object resolveParameter(@NotNull ParameterContext parameterContext,
      @NotNull ExtensionContext extensionContext) throws ParameterResolutionException {
    var annotationTimeout = parameterContext.getAnnotatedElement()
        .getAnnotation(HttpClientExecutor.class).timeout();
    var executor = getExecutor();
    var duration = getDuration(annotationTimeout);

    if (nonNull(duration)) {
      LOGGER.log(Level.DEBUG, String.format("Set the watchdog (%s) s", duration.getSeconds()));
      executor.setWatchdog(ExecuteWatchdog.builder().setTimeout(duration).get());
    }

    return executor;
  }

  @Override
  public boolean supportsParameter(@NotNull ParameterContext parameterContext,
      @NotNull ExtensionContext extensionContext) throws ParameterResolutionException {
    return Executor.class.isAssignableFrom(parameterContext.getParameter().getType())
        && parameterContext.isAnnotated(HttpClientExecutor.class);
  }

  @VisibleForTesting
  Duration getDuration(int annotationTimeout) {
    Duration duration = null;

    if (0 < annotationTimeout) {
      duration = Duration.ofMillis(annotationTimeout);
    }

    return duration;
  }

}
