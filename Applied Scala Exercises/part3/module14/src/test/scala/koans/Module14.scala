
/* Copyright (C) 2010-2018 Escalate Software, LLC. All rights reserved. */

package koans
import org.scalatest.FunSuite
import org.scalatest.Matchers
import org.scalatest.SeveredStackTraces
import support.BlankValues._
import support.StopOnFirstFailure

class Module14 extends FunSuite with Matchers with StopOnFirstFailure with SeveredStackTraces {

  // for the first part of this exercise, we are going to continue the DNA exercise from the previous
  // example and extend it. Perhaps out of interest you might like to look at the implementations of
  // the methods below and compare them to your own solutions to exercise 14.

  def stringToSetOfChars(s1: String) = s1.toList.map { c => Set(c) }

  def combineZippedSetsAndString(s1: List[Set[Char]], seq: String): List[Set[Char]] =
    for ((set, char) <- (s1 zip seq.toList)) yield set + char

  def comboSetsForSequences(sequences: List[String]) = {
    val head :: rest = sequences
    val startingSet = stringToSetOfChars(head)
    rest.foldLeft(startingSet) {combineZippedSetsAndString}
  }

  val listOfSeqs = List("GTAAGCTTAC", "GACAGCT-AC", "G-AACCTAAC", "C-AACCTAAC")

  // if you remember, we wanted the following test to pass:
  test("Find all combinations from sequences") {
    val allCombos = comboSetsForSequences(listOfSeqs)
    allCombos.toString should be ("List(Set(G, C), Set(T, A, -), Set(A, C), Set(A), Set(G, C), Set(C), Set(T), Set(T, -, A), Set(A), Set(C))")
  }

  // now for your extensions:
  //
  // We are intending to filter out any sets with only one possible allele
  // in them, but before we can do that we need to save the original position
  // in the sequence, otherwise when we remove the single item sets we won't
  // know where the locations for the retained sets were originally.
  //
  // With this in mind, we want to get the index of the position for each Set
  // in the list, then filter out the Sets of size one (and indices for those)
  //
  // So, starting with the list of sets, zip them with an index so that they
  // become a list pairs (tuple2) of the set with an index number for each position.
  // You can do this inline in the test or write your own function, your call...
  // Check the test below if the description is confusing to see what result is expected

  test("Combo list zipped with index") {
    // this is the part you have to fill in...
    val comboListWithIndex = comboSetsForSequences(listOfSeqs).zipWithIndex
    
    comboListWithIndex.toString should be ("List((Set(G, C),0), (Set(T, A, -),1), (Set(A, C),2), (Set(A),3), (Set(G, C),4), (Set(C),5), (Set(T),6), (Set(T, -, A),7), (Set(A),8), (Set(C),9))")
  }

  // Next, we want to filter out all those where the set size is 1.
  // You can do this in-line in the test, rather than writing out another function.
  // Note that you have a Set,Index tuple now, so you have to filter over
  // the first item in the tuple (the Set) having a size greater than 1
  // For convenience later, we also want to swap the order of the tuple
  // so that the index is the first thing, and the set is the second.
  // Again, check the expected results in the test for more clarity

  test("Combo list with index filtered") {
    // make the filteredListWithIndex hold the filtered list, with the indices - either as an inline
    // solution, or as a function, your choice. At the same time as you are filtering, reverse the order
    // so that the index is first, and the set is second
    val comboListWithIndex = comboSetsForSequences(listOfSeqs).zipWithIndex
    val filteredListWithIndex = comboListWithIndex.collect { case (s, index) if s.size > 1 => (index, s)}

    filteredListWithIndex.toString should be ("List((0,Set(G, C)), (1,Set(T, A, -)), (2,Set(A, C)), (4,Set(G, C)), (7,Set(T, -, A)))")
  }

  // we are almost there, but now we really want these results in a map, with the index as the key, and the
  // set as the value. Make the test below pass.
  // Hint, you can add a list of tuples to a Map of the right type using ++
  // perhaps this will help you? On the other hand, maybe there is a method
  // on List that can help convert to a Map directly?
  //
  // If you have trouble, try some combinations in the REPL to see if you can get it to work - try with
  // simpler types to begin with (say Int -> String)

  test("Get a mutation map from a list of sequences") {
    val comboListWithIndex = comboSetsForSequences(listOfSeqs).zipWithIndex
    val filteredListWithIndex = comboListWithIndex.collect { case (s, index) if s.size > 1 => (index, s)}
    // need to change this so that instead of an empty map, you get the mutation map in it
    val mutationMap = filteredListWithIndex.toMap

    mutationMap should be (Map(0 -> Set('G', 'C'), 1 -> Set('T', 'A', '-'), 2 -> Set('A', 'C'), 4 -> Set('G', 'C'), 7 -> Set('T', '-', 'A')))
  }


  // Alright - enough with the DNA examples - let's play with collections some more.

  test("Convert various types to a List") {
    // starting from the following objects, convert each of them to a List
    // Use the REPL to have a look at the methods on the List object, and the methods on the types
    // to see if you can come up with combinations that make it easier.

    val t = (1,3,5,"seven")  // a tuple

    // convert it below
    val tAsList = t.productIterator.toList

    tAsList should be (List(1,3,5,"seven"))


    val a = Array(3,5,'7',"nine")

    // convert it below
    val aAsList = a.toList

    aAsList should be (List(3,5,'7',"nine"))


    val s = Set(2,3,5,7,11)

    // tricky one - convert s to a list and then write a test for it - does it work? If not, why not, and
    // can you find a safe way to make it work?
    val sAsList = s.toList.sorted
    sAsList should be (List(2,3,5,7,11))
  }

}
