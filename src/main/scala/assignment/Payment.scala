package assignment

import scala.util.Try

case class Payment(
  transactionReference: Reference,
  accountNumber:        IBAN,
  description:          String,
  startBalance:         Amount,
  mutation:             Amount,
  endBalance:           Amount
) {
  val isBalanced = endBalance == startBalance + mutation
}

object Payment {
  def fromFields(fields: Seq[String]): Try[Payment] = {
    Try(Payment(
      // see assignment ```package.scala`` object for type mapping
      fields(0).toLong,
      fields(1).toString,
      fields(2).toString,
      fields(3).toDouble,
      fields(4).toDouble,
      fields(5).toDouble
    ))
  }
}