package foo

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files

object Example {
  def main(args: Array[String]): Unit = {
    import Config._
    val targetDir = {
      val it = new File(targetDirname)
      it.mkdir()
      it
    }
    IO.write(new File(targetDir, settingsFilename), vsContent)
    IO.write(new File(mainFilename), mainContent)
  }
}

object Config {
  val targetDirname = ".vscode"
  val settingsFilename = "settings.json"
  val vsContent =
    s"""// .vscode/settings.json
       |{
       |  "deno.enable": true,
       |}
       |""".stripMargin

  val mainFilename = "main.ts"
  val mainContent =
    s"""import { serve } from "https://deno.land/std/http/server.ts";
       |
       |const s = serve({ port: 8000 });
       |console.log("http://localhost:8000/");
       |for await (const req of s) {
       |  req.respond({ body: "Hello World\\n" });
       |}
       |""".stripMargin
}

object IO {
  def write(file: File, content: String): Unit =
    Files.write(file.toPath, content.getBytes(StandardCharsets.UTF_8))
}
