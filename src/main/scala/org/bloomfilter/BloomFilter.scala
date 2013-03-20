package org.bloomfilter

import scala.math._

class BloomFilter(val size: Int, val expectedElements: Int) {
  require(size > 0)
  require(expectedElements > 0)

  val bitArray = new BitArray(size)
  val k = ceil((bitArray.size / expectedElements) * log(2.0)).toInt
  val expectedFalsePositiveProbability = pow(1 - exp(-k * 1.0 * expectedElements / bitArray.size), k)

  def add(hash: Int) {
    def add(i: Int, seed: Int) {
      if (i == k) return
      val next = xorRandom(seed)
      bitArray.set(next)
      add(i + 1, next)
    }
    add(0, hash)
  }

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