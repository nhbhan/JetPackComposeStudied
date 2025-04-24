package com.hannhb.myapplication

import com.hannhb.myapplication.screen.main.valueToDefault
import java.util.ArrayDeque
import java.util.Arrays
import java.util.Collections
import java.util.Deque

class Test {
    fun searchWithBubble(listA: MutableList<Int>, target: Int, left: Int, right:Int): Int {
        val mid = (left + right)/2

         if (right - left < 0) {
             println("${listA[mid]}")
             println("${mid}")
             return if (target > listA[mid])
                  -mid - 1

            else  (mid -1)
        }else if (target == listA[mid]) {
            return mid
        }else if (target > listA[mid]) {
            return searchWithBubble(listA, target, mid +1, right)
        } else if (target < listA[mid]) {
            return searchWithBubble(listA, target, left, mid -1)
        }
        return -1
    }

    fun maximumOccurringCharacter(text: String): Char {
        val hashmap: HashMap<Char, Int> = hashMapOf()
        val lengthOfString = text.length
        for(index in 0..lengthOfString - 1) {
            var char = text.toCharArray().get(index)
            hashmap[char] = hashmap.getOrDefault(char, 0) + 1
        }

        var max = Collections.max(hashmap.values)
        loop@for (entry in hashmap.entries) {
            if (entry.value == max) {
                return entry.key
                break@loop

            }
        }



//        println(hashmap.toString())
        return ' '

    }



    //tim kiem so lon nhat va nho nhat
    fun ternarySearch(listA: MutableList<Int>, target: Int): Int {
        var left = 0
        var right = listA.size - 1
        while (right - left >= 0) {
            val partion = (right - left) / 3
            val firstMid = left + partion
            val secondMid = right - partion

            if (target == listA[firstMid]) {
                return firstMid
            } else if (target == listA[secondMid]) {
                return secondMid
            } else if (target < listA[firstMid]) {
                right = firstMid - 1
            } else if (target > listA[secondMid]) {
                left = secondMid + 1
            } else {
                left = firstMid + 1
                right = secondMid - 1
            }
        }
        return -1
//        Arrays.binarySearch()
    }

    fun countDivisiblePairs(arr: Array<Int>, k: Int, x: Int): Long {
        arr.sort()
        var answer = 0
        for(index in 0 .. arr.size -1) {
            var numberDivisor = (arr[index] - 1)/x
            var fistElement = Arrays.binarySearch(arr, (numberDivisor + k )*x)
            var secondElement = Arrays.binarySearch(arr, (numberDivisor + k + 1)*x)
            answer += fistElement - secondElement
        }
        return answer.toLong()
    }

    fun jumpSearch(listA: MutableList<Int>, target: Int): Int {
        val  blockSize = Math.sqrt(listA.size.toDouble()).toInt()
        var start = 0
        var next = blockSize.toInt()

        while( start < listA.size && target > listA[next-1] )
        {
            start = next;
            next += blockSize

            if ( next >= listA.size )
                next = listA.size
        }

        for(index in start until next )
        {
            if ( target == listA[index])
                return index
        }

        return -1
    }

    fun selectionSort(listA: MutableList<Int>): MutableList<Int> {
        var temp = -1
        var min = -1
        for (index in 0 until listA.size -1) {
            min = index
            for (position in index + 1 until listA.size ) {
                if (listA[position] < listA[min]) {
                    min = position
                }
            }
            val a: Deque<Int> = ArrayDeque<Int>()
            temp = listA[index]
            listA[index] = listA[min]
            listA[min] = temp
        }
        return listA
    }

    fun insertionSort(listA: MutableList<Int>): MutableList<Int> {
        var position = -1
        var last = -1
        for (index in 1 until listA.size) {
            last = listA[index]
            position = index
            while (((position>0) && listA[position-1] > last)){
                listA[position] = listA[position-1]
                position = position -1
            }
            listA[position] = last
        }
        return listA
    }




    fun findArgument(listA: MutableList<Int>, total: Int) {
        val hashMap: HashMap<Int, Int> = hashMapOf()
        for(index in 0..listA.size -1) {
            hashMap[index] = total - listA[index]
            if (hashMap.containsValue(listA[index])) {
                println("The position of element is $index and ${hashMap.filter { it.value ==  listA[index]}.keys?.firstOrNull()} voi gia tri lan luot la ")
            }

        }
    }

    fun findMissingElement(listA: MutableList<Int>, listB: MutableList<Int>): Int {
        var smallestMissing = -1
        for (indexA in 0..listA.size - 1) {
            loop@for (indexB in 0 .. listB.size - 1) {
                if (listA[indexA] == listB[indexB]) {
                    if (listB[indexB] == smallestMissing) {
                        smallestMissing = -1
                    }
                    continue
                }

                if (listA[indexA]< listB[indexB]) {
                    break@loop
                }

                if (listA[indexA] > listB[indexB]) {
                    smallestMissing = findSmallestNumber(smallestMissing, listA[indexA])
                }

            }
        }
        return smallestMissing
    }

    fun findSmallestNumber(a: Int, b: Int) : Int {
        if (a == -1) return b
        else return if(a>b) return b else a
    }

    fun test(listA: MutableList<Int>, k: Int): Int {
        var potential = -1
        val hashMap : HashMap<Int, Int> = hashMapOf()
        for (index in  0..listA.size- 1) {
            hashMap[listA[index]] = 0
        }

        var isLooped = true
       while(isLooped ) {
           if (listA[0] > listA[ 1]) {
               hashMap[listA[0]] = hashMap[listA[0]]!! + 1
               listA.add(listA[1])
               listA.removeAt(1)
               print(hashMap[listA[0]])
           } else {
               hashMap[listA[1]] = hashMap[listA[1]]!! + 1
               listA.add(listA[0])
               listA.removeAt(0)
           }
           var index = 1
           loop@for (entry in hashMap.entries) {
               if (entry.value == k) {
                   println(index)
                   potential = entry.key

                   isLooped = false
                   break@loop
               }
               index ++
           }
//           println(listA.toString())
//           isLooped --
        }

        return potential
    }

    fun test2(listA: MutableList<Int>, k: Int): Int {
        val deque: Deque<Int> = ArrayDeque<Int>()
        for (index in 0 until listA.size - 1) {
            deque.add(index)
        }

        var consecutiveWin = 0
        var potentialWin = 0
        while (consecutiveWin < k) {
            var firstPlayer = deque.poll()
            var secondPlayer = deque.poll()
            if (listA.get(firstPlayer - 1) > listA.get(secondPlayer -1)) {
                deque.addFirst(firstPlayer)
                deque.addLast(secondPlayer)
                consecutiveWin ++
                potentialWin = listA.get(firstPlayer - 1)
            } else {
                deque.addFirst(secondPlayer)
                deque.addLast(firstPlayer)
                consecutiveWin = 1
                potentialWin = listA.get(secondPlayer - 1)
            }
        }



        return potentialWin
    }
}



fun main() {
//    Test().findArgument(mutableListOf(0, 1, 2, 5, 4), 6)
    val listA = mutableListOf(5, 7, 1, 3, 8)
    mutableListOf(2, 3, 4, 6, 9)
    val a = mutableListOf(5, 1, 3, 8, 7)

//    val element = Test().findMissingElement(mutableListOf(1, 3, 5, 7, 8), mutableListOf(2, 3, 4, 6, 9))
//    print(" ${Test().selectionSort(listA)}")
//    print(Test().insertionSort(listA).toString())
//    Test().maximumOccurringCharacter("abbbaacc")
//    Test().countDivisiblePairs(arrayOf(1, 3, 5, 7), 2, 2)
    val potential = Test().test(mutableListOf(3,2, 1, 4), 2)
//    val count = Test().countDivisiblePairs(arrayOf(1, 2, 3, 4, 5), 2, 2)

    println("Potential is ${potential}")




}

fun Int.to32bitString(): String =
    Integer.toBinaryString(this).padStart(Int.SIZE_BITS, '0')