package org.bloomfilter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.io.Source

class BloomFilterSpec extends FlatSpec with ShouldMatchers {
  val filter = new BloomFilter(1000000, 250000)

  val source = Source.fromURL(getClass.getResource("/words.txt"))
  for (line <- source.getLines()) {
    filter.add(line.hashCode)
  }
  filter.contains("foo".hashCode) should equal(true)
  filter.contains("ffoooo".hashCode) should equal(false)
}