package assignment

import scala.util.{Success, Try}

class Payments(private val processedPayments: Seq[Try[Payment]]) {

  /** payments that could be parsed but are not validated */
  private val parsedPayments = processedPayments.collect{ case Success(p) => p }

  /** number of times a transaction reference was observed */
  private val referenceCounters = parsedPayments
    .map(_.transactionReference)
    .foldLeft(Map.empty[Reference, Int]) { (rcs, ref) =>
      rcs.updated(ref, rcs.getOrElse(ref, 0) + 1)
    }

  /** payments that are unbalanced */
  val unbalancedPayments = parsedPayments.filterNot(_.isBalanced)

  /** payments that are ambiguously referenced */
  val ambiguousPayments = parsedPayments.filter(p => referenceCounters(p.transactionReference) > 1)

  /** payments that are invalid */
  val invalidPayments = unbalancedPayments ++ ambiguousPayments

  /** payments that are valid */
  val validPayments = parsedPayments.filter(p => !invalidPayments.contains(p))
}