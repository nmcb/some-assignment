package assignment

import scala.io.{Codec, Source}

object Importer {

  def importCSV(filename: String)(codec: Codec): Payments = {
    val processed = for {
      record <- Source.fromFile(filename)(codec).getLines().drop(1)
    } yield Payment.fromFields(record.split(',').map(_.trim))
    new Payments(processed.toList)
  }
}
