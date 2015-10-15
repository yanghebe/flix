package ca.uwaterloo.flix.util.perf

import scala.util.Random

/**
 * Reference:
 *
 * https://en.wikipedia.org/wiki/Erd%C5%91s%E2%80%93R%C3%A9nyi_model
 */

object RandomGraph {

  def main(args: Array[String]): Unit = {

    // If np = 1, then a graph in G(n, p) will almost surely have a largest component whose size is of order n2/3.
    val N = args(0).toInt
    val P = 1.0 / N.toDouble

    val r = new Random()

    println(s"namespace Graph_N$N {")
    println()
    println(s"    // N = $N, P = $P")
    println()
    println(s"    rel Edge(x: Int, y: Int);")

    println(s"    Edge(y, x) :- Edge(x, y).")
    println(s"    Edge(x, z) :- Edge(x, y), Edge(y, z).")
    println()

    for (i <- 0 until N) {
      for (j <- 0 until N) {
        if (r.nextInt(Int.MaxValue).toDouble < (P * Int.MaxValue.toDouble))
          println(s"    Edge($i, $j).")
      }
    }

    println()
    println("}")
    println()

  }


  def connectedness(N: Double, P: Double): String = {
    if (P < (((1.0 - math.E) * Math.log(N)) / N))
      "Likely unconnected."
    else if (P > (((1.0 + math.E) * Math.log(N)) / N))
      "Likely connected."
    else
      "Unknown"
  }

}