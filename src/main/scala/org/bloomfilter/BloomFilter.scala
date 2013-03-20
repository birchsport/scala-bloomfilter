package org.bloomfilter

import scala.math._

/**
 * An implementation of the Bloom Filter algorithm (http://en.wikipedia.org/wiki/Bloom_filter)
 */
class BloomFilter(val size: Int, val expectedElements: Int) {
  require(size > 0)
  require(expectedElements > 0)

  val bitArray = new BitArray(size)
  val k = ceil((bitArray.size / expectedElements) * log(2.0)).toInt
  val expectedFalsePositiveProbability = pow(1 - exp(-k * 1.0 * expectedElements / bitArray.size), k)

  /**
   * Add any object to the filter.
   * Will invoke the 'hashCode' method on the passed in object
   */
  def add(any: Any) {
    add(any.hashCode)
  }

  /**
   * Add a hash value to the filter
   */
  def add(hash: Int) {
    def add(i: Int, seed: Int) {
      if (i == k) return
      val next = xorRandom(seed)
      bitArray.set(next)
      add(i + 1, next)
    }
    add(0, hash)
  }

  /**
   * Checks to see if any object is in the filter.
   * Will invoke the 'hashCode' method on the passed in object
   */
  def contains(any: Any): Boolean = {
    contains(any.hashCode)
  }

  /**
   * Checks to see if a given hash value is in the filter
   */
  def contains(hash: Int): Boolean = {
    def contains(i: Int, seed: Int): Boolean = {
      if (i == k) return true
      val next = xorRandom(seed)
      if (!bitArray.get(next)) return false
      return contains(i + 1, next)
    }
    contains(0, hash)
  }

  private def xorRandom(i: Int) = {
    var y = i
    y ^= y << 13
    y ^= y >> 17
    y ^ y << 5
  }

}