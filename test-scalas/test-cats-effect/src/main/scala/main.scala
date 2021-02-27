import cats.effect.{ExitCode, IO, IOApp, Resource}
import cats.syntax.all._
import language.postfixOps
import java.io._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    if (args.headOption.contains("--do-it")) {
      IO(println("I dit it!")) >> copy(new File("build.sbt"), new File("build.sbt.bak")) as (ExitCode.Success)
    } else {
      IO(println("Didn't do it")).as(ExitCode(-1))
    }
  }

  def inputStream(f: File): Resource[IO, FileInputStream] = {
    Resource.make {
      IO(new FileInputStream(f))
    } { inStream =>
      IO(inStream.close()).handleErrorWith(_ => IO.unit) // release
    }
  }

  def inputStream2(f: File): Resource[IO, FileInputStream] = {
    Resource.fromAutoCloseable(IO(new FileInputStream(f)))
  }

  def outputStream(f: File): Resource[IO, FileOutputStream] = {
    Resource.make {
      IO(new FileOutputStream(f))
    } { outStream =>
      IO(outStream.close()).handleErrorWith(_ => IO.unit) // release
    }
  }

  def inputOutputStream(in: File, out: File): Resource[IO, (InputStream, OutputStream)] = {
    for {
      inStream <- inputStream(in)
      outStream <- outputStream(out)
    } yield (inStream, outStream)
  }

  def transmit(origin: InputStream, destination: OutputStream, buffer: Array[Byte], acc: Long): IO[Long] = {
    for {
      amount <- IO(origin.read(buffer, 0, buffer.size))
      count <- if (amount > -1) IO(destination.write(buffer, 0, amount)) >> transmit(origin, destination, buffer, acc + amount) else IO.pure(acc)
    } yield count
  }

  def transfer(origin: InputStream, destination: OutputStream): IO[Long] = {
    for {
      buffer <- IO(new Array[Byte](1024 * 10))
      total <- transmit(origin, destination, buffer, 0L)
    } yield total

    /*
    val total: IO[Long] = IO(new Array[Byte](1024 * 10)).flatMap(buffer => transmit(origin, destination, buffer, 0L))
    total
    */
  }

  def copy(origin: File, destination: File) = {
    inputOutputStream(origin, destination).use {
      case (in, out) => transfer(in, out)
    }
  }

  def copy2(origin: File, destination: File): IO[Long] = {
    val inIO: IO[InputStream] = IO(new FileInputStream(origin))
    val outIO: IO[OutputStream] = IO(new FileOutputStream(destination))

    (inIO, outIO) // Stage 1: Getting resources
      .tupled // From (IO[InputStream], IO[OutputStream]) to IO[(InputStream, OutputStream)]
      .bracket {
        case (in, out) => transfer(in, out) // Stage 2: Using resources (for copying data, in this case)
      } {
        case (in, out) => // Stage 3: Freeing resources
          (IO(in.close()), IO(out.close()))
            .tupled //// From (IO[Unit], IO[Unit]) to IO[(Unit, Unit)]
            .handleErrorWith(_ => IO.unit).void
      }
  }
}
