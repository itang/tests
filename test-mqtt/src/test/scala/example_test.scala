package example

import org.scalatest.FunSpec

class ExampleSpec extends FunSpec {

  describe("echo") {
    it("echo return the same from args") {
      assert(Example.echo("hello") === "hello")
    }
  }
}
