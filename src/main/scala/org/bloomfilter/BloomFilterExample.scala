package org.bloomfilter

import scala.io.Source

object BloomFilterExample {
  def main(args: Array[String]): Unit = {
    val filter = new BloomFilter(1000000, 250000)

    for (line <- Source.fromFile("/usr/share/dict/words").getLines()) {
      filter.add(line.hashCode)
    }
    System.out.println("[FilterInfo] filter-bit-size:%d, number of hash:%d, max false positive prob.: %1.7f".format(filter.size, filter.k, filter.expectedFalsePositiveProbability))
    println(filter.contains("foo".hashCode))
    println(filter.contains("bar".hashCode))
    println(filter.contains("baz".hashCode))
    println(filter.contains("Birchfield".hashCode))
    println(filter.bitArray.data.length)

  }
}