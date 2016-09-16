package assignment

import scala.io.Codec

object Main extends App {

  val filename = "src/test/resources/records.csv"
  val run      = Importer.importCSV(filename)(Codec.ISO8859)

  show("VALID      PAYMENTS:", run.validPayments)
  show("UNBALANCED PAYMENTS:", run.unbalancedPayments)
  show("AMBIGUOUS  PAYMENTS:", run.ambiguousPayments)

  /** println utility */
  private def show(heading: String, payments: Seq[Payment]) = {
    println(heading)
    if (payments.size > 0) println(payments.mkString("> ", "\n> ", ""))
    println()
  }
}


