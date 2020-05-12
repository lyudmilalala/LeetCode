package main

import (
	"fmt"
)

/*
 *    String
 */

// Q14-
//time: O(s), s is the total number of characters in strs   space: O(1)
func longestCommonPrefix(strs []string) string {
	if len(strs) == 0 || len(strs[0]) == 0 {
		return ""
	}
	for i := 0; i < len(strs[0]); i++ {
		for j := 1; j < len(strs); j++ {
			if i == len(strs[j]) || strs[0][i] != strs[j][i] {
				return strs[0][0:i]
			}
		}
	}
	return strs[0]
}

//Q6     time: O(n)   space: O(n)
func convert(s string, numRows int) string {
	if numRows == 1 {
		return s
	}
	// strings are immutable
	// use []byte as a stringBuilder can make it faster
	strs := make([][]byte, numRows)
	cur_row := 0
	godown := true
	for i := 0; i < len(s); i++ {
		strs[cur_row] = append(strs[cur_row], s[i])
		if cur_row == numRows-1 {
			godown = false
		}
		if cur_row == 0 {
			godown = true
		}
		if godown {
			cur_row++
		} else {
			cur_row--
		}
	}
	ans := string(strs[0])
	for j := 1; j < len(strs); j++ {
		ans += string(strs[j])
	}
	return ans
}

/*
 *    HashMap
 */
func twoSum(nums []int, target int) []int {
	componentMap := make(map[int]int)
    for i := 0; i < len(nums); i++ {
		if _, ok := componentMap[nums[i]]; ok {
			return []int{i, componentMap[nums[i]]}
		} else {
			componentMap[target-nums[i]] = i
		}
	}
	return []int{-1,-1}
}


/*
 *    LinkedList
 */
type ListNode struct {
    Val int
    Next *ListNode
}

// Q141     time: O(n)   space: O(1)
func hasCycle(head *ListNode) bool {
	slow := head
	fast := head
	for fast != nil && fast.Next != nil {
		slow = slow.Next
		fast = fast.Next.Next
		if (slow == fast) {
			return true
		}
	}
	return false
}

/*
 *    Dynamic Programming
 */

 //Q55

 //Top-down Dynamic Programming     time: O(n)   space: O(n)
func canJumpDP(nums []int) bool {
	canApproach := make([]int, len(nums))
	// 1 if can approach, 0 if cannot
	canApproach[len(nums)-1] = 1
	for i := len(nums)-2; i >= 0; i-- {
		farthest := len(nums)-1
		if i+nums[i] < farthest {
			farthest = i+nums[i]
		}
		for j := i+1; j <= farthest; j++ {
			if (canApproach[j] == 1) {
				canApproach[i] = 1
				break
			}
		}
	}
	return canApproach[0] == 1
}


//Greedy algorithm
//When the new node can approach the most left approachable node, set it as the new most left approachable node
//time: O(n)   space: O(1)
func canJumpGreedy(nums []int) bool {
	mostleft := len(nums)-1
	for i:=len(nums)-2; i>=0; i-- {
		if i+nums[i] >= mostleft {
			mostleft = i
		}
	}
	return mostleft == 0
}

//Q45
// Greedy -- always find the range can approach for the next step
// time: O(n)   space: O(1)
func jump(nums []int) int {
    if len(nums)==1 {
      return 0;
    }
    start,end,count := 0,1,0
		//end = the farthest index can approach
    nextend := end
		//if end >= len(nums), the end of the array can already be approached
    for end<len(nums)-1 {
      for i := start; i<=end; i++ {
        if (i+nums[i] > nextend) {
          nextend = i+nums[i]
        }
      }
			//start from the next index of the current end
      start = end+1
      end = nextend
      count++
    }
    return count
}

/*
 *    Main
 */

func contains(array []int, val int) bool {
	contains := false
	for _, value := range array {
		if val == value {
			contains = true
			break
		}
	}
	return contains
}

func containsKey(myMap map[int]int, key int) bool {
	if _, ok := myMap[key]; ok {
		return true
	} else {
		return false
	}
}

func main() {
	// var strs = []string{"flower", "flow", "flight"}
	// fmt.Println(longestCommonPrefix(strs))
	fmt.Println(convert("PAYPALISHIRING", 3))
}
