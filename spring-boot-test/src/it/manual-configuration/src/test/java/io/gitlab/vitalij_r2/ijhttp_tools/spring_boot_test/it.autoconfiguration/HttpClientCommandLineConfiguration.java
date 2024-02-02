package io.gitlab.vitalij_r2.ijhttp_tools.spring_boot_test.it.autoconfiguration;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import io.gitlab.vitalij_r2.ijhttp_tools.command_line.HttpClientCommandLine;
@TestConfiguration
public class HttpClientCommandLineConfiguration {

  @Bean
  static Executor executor() {
    return new DefaultExecutor();
  }

  @Bean
  static HttpClientCommandLine httpClientCommandLine() {
    return new HttpClientCommandLine();
  }

}