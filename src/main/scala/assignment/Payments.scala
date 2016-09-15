package assignment

import scala.util.{Success, Try}

class Payments(private val processedPayments: Seq[Try[Payment]]) {
  import Payments._

  /** payments that could be parsed but are not validated */
  private val parsedPayments: Seq[Payment] =
    processedPayments.collect {
      case Success(p) => p
    }

  /** number of times a transaction reference was observed */
  private val referenceCount: ReferenceCount = {
    val referenceCounter: (ReferenceCount, Reference) => ReferenceCount =
      (rcs, ref) => rcs.updated(ref, rcs.getOrElse(ref, 0) + 1)

    parsedPayments
      .map(_.transactionReference)
      .foldLeft(ReferenceCount.empty)(referenceCounter)
  }


  /** payments that are unbalanced */
  val unbalancedPayments: Seq[Payment] =
    parsedPayments.filterNot(_.isBalanced)

  /** payments that are ambiguously referenced */
  val ambiguousPayments: Seq[Payment] =
    parsedPayments.filter(p => referenceCount(p.transactionReference) > 1)

  /** invalid payments */
  val invalidPayments: Seq[Payment] =
    unbalancedPayments ++ ambiguousPayments

  /** valid payments */
  val validPayments =
    parsedPayments.filter(p => !invalidPayments.contains(p))
}

object Payments {
  type ReferenceCount = Map[Reference, Int]
  object ReferenceCount {
    val empty: ReferenceCount = Map.empty[Reference, Int]
  }
}