package io.gitlab.vitalij_r2.ijhttp_tools.spring_boot_test.it.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import org.apache.commons.exec.Executor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import io.gitlab.vitalij_r2.ijhttp_tools.command_line.HttpClientCommandLine;
import io.gitlab.vitalij_r2.ijhttp_tools.command_line.LogLevel;

@Import(HttpClientCommandLineConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class HttpClientCommandLineApplicationTests {

  @Autowired
  private Executor executor;

  @Autowired
  private HttpClientCommandLine httpClientCommandLine;

  @Value("${ijhttp.timeout}")
  private int timeout;

  @Test
  void httpClientCommandLine() throws IOException {
    // given
    httpClientCommandLine.files(Path.of("echo.http"));
    httpClientCommandLine.logLevel(LogLevel.VERBOSE);
    httpClientCommandLine.environmentVariable(String.format("timeout=%s", timeout));
    httpClientCommandLine.report(true);

    // when
    var exitCode = executor.execute(httpClientCommandLine.getCommandLine());

    // then
    assertEquals(0, exitCode);
  }

}