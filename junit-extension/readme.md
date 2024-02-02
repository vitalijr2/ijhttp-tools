# ijhttp tools: jUnit Extension

[![Maven Central](https://img.shields.io/maven-central/v/uk.bot-by.ijhttp-tools/ijhttp-junit-extension)](https://search.maven.org/artifact/uk.bot-by.ijhttp-tools/ijhttp-junit-extension)
[![Javadoc](https://javadoc.io/badge2/uk.bot-by.ijhttp-tools/ijhttp-junit-extension/javadoc.svg)](https://javadoc.io/doc/uk.bot-by.ijhttp-tools/ijhttp-junit-extension)

The jUnit Extension allows to run HTTP requests on the <em>integration-test</em> phase
using the [IntelliJ HTTP Client][http-client].

The [HTTP Request in Editor Specification][specification] describes format these files.

Example of test request:

```http
GET /api/get HTTP/1.1
Accept: application/json
Host: example.com

### Add an item
POST /api/add HTTP/1.1
Host: example.com
Content-Type: application/json

{
  "name": "entity",
  "value": "content"
}
```

## Configuration

**Important!** The plugin does not contain the [HTTP client][cli-tool]:
you need to install it by yourself then add to `PATH`. You can also set the full path to the ijhttp
via the parameter `executable`. The [HTTP Client Demo][demo] has some examples how to download
the HTTP client.

Use annotations `HttpClientExecutor` and `HttpClientCommandLineParameters` to initialise and
configure both executor and command line builder.

Example of full configuration:

```java
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureHttpClientCommandLine(timeout = 7000)
class HttpClientCommandLineApplicationTests {

  @Autowired
  private Executor executor;

  @Autowired
  private HttpClientCommandLine httpClientCommandLine;

  @Test
  void httpClientCommandLine() throws IOException {
    // when
    var exitCode = executor.execute(httpClientCommandLine.getCommandLine());

    // then
    assertEquals(0, exitCode);
  }

}
```

You can play with [HTTP Client Demo][demo].

[http-client]: https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html

[specification]: https://github.com/JetBrains/http-request-in-editor-spec

[cli-tool]: https://www.jetbrains.com/help/idea/http-client-cli.html

[demo]: https://gitlab.com/vitalijr2/ijhttp-demo