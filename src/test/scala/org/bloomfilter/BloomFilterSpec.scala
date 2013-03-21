package org.bloomfilter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.io.Source

class BloomFilterSpec extends FlatSpec with ShouldMatchers {
  "Loading words from a dictionary" should "work" in {
    val filter = new BloomFilter(1000000, 250000)

    val source = Source.fromURL(getClass.getResource("/words.txt"))
    var i: Int = 0
    for (line <- source.getLines()) {
      i = i + 1;
      if (i % 2 == 0) {
        filter.add(line.hashCode)
      } else {
        filter.add(line)
      }
    }
    filter.contains("foo".hashCode) should equal(true)
    filter.contains("foo") should equal(true)
    filter.contains("bbaarr") should equal(false)
  }

  "Adding words directly " should "work" in {
    val filter = new BloomFilter(100, 4)

    filter.add("foo")
    filter.contains("foo".hashCode) should equal(true)
    filter.contains("bbaarr") should equal(false)
  }
}
