package org.bloomfilter

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class BloomFilterSpec extends FlatSpec with ShouldMatchers {
  val filter = new BloomFilter(100, 4)
  filter.add("foo".hashCode)
  filter.contains("foo".hashCode) should equal(true)
  filter.contains("bar".hashCode) should equal(false)
}