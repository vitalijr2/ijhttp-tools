package io.gitlab.vitalijr2.ijhttptools.cli;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.ArrayMatching.arrayContaining;
import static org.hamcrest.collection.ArrayMatching.arrayContainingInAnyOrder;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
public class HttpClientCommandLineSmokeTest {

  private HttpClientCommandLine builder;

  @BeforeEach
  void setUp() {
    builder = new HttpClientCommandLine();
  }

  @DisplayName("Files are required")
  @Test
  void filesAreRequired() {
    // when
    var exception = assertThrows(IllegalStateException.class, builder::getCommandLine);

    // then
    assertEquals("files are required", exception.getMessage());
  }

  @DisplayName("Folder")
  @Test
  void folder() throws IOException {
    // given
    var folder = Paths.get("src/test/resources/folder");
    builder.directories(folder);

    // when
    var commandLine = assertDoesNotThrow(builder::getCommandLine);

    // then
    assertThat(commandLine.getArguments(), arrayContaining(endsWith("google.http")));
  }

  @DisplayName("Directories")
  @Test
  void subdirectory() throws IOException {
    // given
    var folder = Paths.get("src/test/resources/folder");
    var directoryWithSubdirectory = Paths.get("src/test/resources/directory");

    builder.directories(folder, directoryWithSubdirectory);

    // when
    var commandLine = assertDoesNotThrow(builder::getCommandLine);

    // then
    assertThat(commandLine.getArguments(),
        arrayContainingInAnyOrder(endsWith("bing.rest"), endsWith("google.http"),
            endsWith("testportal.http")));
  }

  @DisplayName("Max-depth")
  @Test
  void maxDepth() throws IOException {
    // given
    var directoryWithSubdirectory = Paths.get("src/test/resources/directory");

    builder.directories(directoryWithSubdirectory);
    builder.maxDepth(1);

    // when
    var commandLine = assertDoesNotThrow(builder::getCommandLine);

    // then
    assertThat(commandLine.getArguments(), arrayContaining(endsWith("testportal.http")));
  }

}
