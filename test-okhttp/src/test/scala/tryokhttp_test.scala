package tryokhttp

import org.scalatest.FunSpec

class TryOkHttpSpec extends FunSpec {

  describe("echo") {
    it("echo return the same from args") {
      assert(TryOkHttp.echo("hello") === "hello")
    }
  }
}
