import java.util.*;

public class Solutions {

	public Solutions() {
		// TODO Auto-generated constructor stub
	}

	/*
	 *                        Array 
	 */	
	
	//codility test case
	public int solution(int[] A) {
		Arrays.parallelSort(A);
		int ans = 1;
        for(int i=0; i<A.length; i++) {
        	if(A[i]<=0) {
        		continue;
        	} else if(i>0 && A[i]==A[i-1]) {
        		continue;
        	} else if(A[i]==ans) {
        		ans++;
        	} else if(A[i]>ans){
        		break;
        	}
        }
        return ans;
    }

	//Q88     time: O(m+n)   space: O(1)
	//Two pointer, travel from back to front to save space
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		int p1 = m-1;
		int p2 = n-1;
		int curr = m+n-1;
		while (p1>=0 || p2>=0) {
			if (p1<0) {
				nums1[curr] = nums2[p2];
				p2--;
			} else if (p2<0) {
				nums1[curr] = nums1[p1];
				p1--;
			} else {
				if (nums1[p1]>nums2[p2]) {
					nums1[curr] = nums1[p1];
					p1--;
				} else {
					nums1[curr] = nums2[p2];
					p2--;
				}
			}
			curr--;
		}
	}
	//Q621     time: O(n)  space: O(1)
	//Greedy -- always first calculate the tasks with most number
	 public int leastInterval(char[] tasks, int n) {
		 int maxOccur = 0; // the most amount of times a task occur
		 int maxOccurCount = 0; // how many characters are with the max occurence
		 int[] occurs = new int[26];
		 for (char t: tasks) {
			 occurs[t-'A']++;
			 if (occurs[t-'A']>maxOccur) {
				 maxOccur = occurs[t-'A'];
				 maxOccurCount = 1;
			 } else if (occurs[t-'A']==maxOccur) {
				 maxOccurCount++;
			 }
		 }
		 int emptyslots = (maxOccur-1)*(n+1-maxOccurCount);
		 // idles are the least number of empty slots we must leave for cooling down, if we cannot fill all of them by extra tasks
		 // extra tasks leave after filling empty slots can be inserted into anywhere as we satisfied the minimum cool down interval
		 int idles = Math.max(0, emptyslots - (tasks.length - maxOccur*maxOccurCount));
		 return tasks.length+idles;
	 }
	 
	//Q48     time: O(n^2)  space: O(1)
    public void rotate(int[][] matrix) {
  
        int start = 0;
        int end = matrix.length-1;
//        swap up and down
        while(start<end) {
        	int[] temp = matrix[start];
        	matrix[start] = matrix[end];
        	matrix[end] = temp;
        	start++;
        	end--;
        }
//        swap symmetrically
//        only do upper triangle without diagonal
        for(int i = 0; i<matrix.length; i++) {
    		for(int j=i+1; j<matrix[i].length; j++) {
    			int temp = matrix[i][j];
    			matrix[i][j] = matrix[j][i];
    			matrix[j][i] = temp;
    		}
    	}
        
        
    }
	
    //Q56     time: O(nlogn)  space: without considering result space
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (int[] a, int[] b) -> a[0] - b[0]);
        List<int []> temp = new LinkedList<>();
        for (int i = 0; i< intervals.length; i++) {
        	if (temp.isEmpty() || temp.get(temp.size()-1)[1] < intervals[i][0]) {
        		temp.add(intervals[i]);
        	} else {
        		temp.get(temp.size()-1)[1] = Math.max(temp.get(temp.size()-1)[1], intervals[i][1]);
        	}
        }
        int[][] ans = new int[temp.size()][];
        for (int i = 0; i<temp.size(); i++) {
        	ans[i] = temp.get(i); 
        }
        return ans;
    }

    //Q163 Missing Range     time: O(n)  space: without considering result space
 	public List<String> findMissingRanges(int[] nums, int lower, int upper) {
 		 List<String> ans = new LinkedList<>();
 		 int cur = lower;
 		 for (int i = 0; i<nums.length; i++) {
 			 if (cur < nums[i]-1) {
 				 ans.add(String.valueOf(cur)+"->"+String.valueOf(Math.min(nums[i]-1, upper)));
 				 cur = nums[i]+1;		 
 			 } else if (cur==nums[i]-1) {
 				 ans.add(String.valueOf(cur));
 				 cur = nums[i]+1;
 			 } else if (cur==nums[i]) {
 				 cur++;
 			 }
 			 if (nums[i]>=upper) {
 				 break;
 			 }
 		 }
 		 if (nums[nums.length-1]<upper && cur<upper-1) {
 			 ans.add(String.valueOf(cur)+"->"+String.valueOf(upper));
 		 } else if (nums[nums.length-1]<upper && cur == upper-1) {
 			 ans.add(String.valueOf(upper));
 		 }
 		 return ans;
 	}
 	
 	//Q238
 	
 	// solution1 with division   time: O(n)  space: O(1)
 	public int[] productExceptSelf(int[] nums) {
         int mul = 1; // multiplication without all zeros
         int countZero = 0;
         for (int i : nums) {
        	 if (i == 0) {
        		 countZero++;
        	 } else {
        		 mul *= i;
        	 }
        	 
         }
         for (int j = 0; j<nums.length; j++) {
        	 if (countZero>1) {
        		 nums[j] = 0;
        	 } else if (countZero == 0) {
        		 nums[j] = mul / nums[j];
        	 } else {
        		 // countZero == 1
        		 if (nums[j] == 0) {
        			 nums[j] = mul;
        		 } else {
        			 nums[j] = 0;
        		 }
        	 }
        	 
         }
         return nums;
    }

    // solution2 without division   time: O(n)  space: O(1) without considering result space
 	// multiply from left to right, and then from right to left
  	public int[] productExceptSelf2(int[] nums) {
  		  int[] res = new int[nums.length];
          int left = 1; 
          int right = 1; 
          for (int i = 0; i<nums.length; i++) {
        	  res[i] = left;
        	  left = left*nums[i];
          }
          for (int j = nums.length-1; j>=0; j--) {
         	  res[j] = res[j]*right;
         	  right = right*nums[j];
          }
          return res;
     }
  	
 	//Q560
    public int subarraySum(int[] nums, int k) {
    	int count = 0, sum = 0;
    	// two pointer start and end
    	// time complexity O(n^2), space complexity O(1)
    	for (int start =0; start<nums.length; start++) {
    		sum = 0;
    		for (int end = start; end <nums.length; end++) {
    			sum += nums[end];
    			if (sum == k) {
    				count++;
    			}
    		}
    	}
        // use HashMap for hold all existed sum
    	// time complexity O(n), space complexity O(n)
//        HashMap < Integer, Integer > map = new HashMap < > ();
//        map.put(0, 1);
//        for (int i = 0; i < nums.length; i++) {
//            sum += nums[i];
//            if (map.containsKey(sum - k))
//                count += map.get(sum - k);
//            map.put(sum, map.getOrDefault(sum, 0) + 1);
//        }
        return count;
    }
    
    //Q581     time: O(n)  space: O(1)
    public int findUnsortedSubarray(int[] nums) {
    	int start = 0;
    	while(start<nums.length-1 && nums[start]<=nums[start+1]) {
    		start++;
    	}
    	int end = nums.length-1;
    	while(end>0 && nums[end]>=nums[end-1]) {
    		end--;
    	}
    	if (start==nums.length-1) {
    		// originally correct in order
    		return 0;
    	} else {
    		// find the minimum after the first fall from peak
    		int min = nums[start+1];
    		for (int i = start+2; i<nums.length; i++) {
    			if (nums[i]<min) {
    				min = nums[i];
    			}
    		}
    		// switch it with the first number larger than it
    		while(start>0 && nums[start-1]>min) {
    			start--;
    		}
    		// find the maximum after the first rise from valley
    		int max = nums[end-1];
    		for (int i=end-2; i>=0; i--) {
    			if (nums[i]>max) {
    				max = nums[i];
    			}
    		}
    		// switch it with the last number smaller than it
    		while (end < nums.length-1 && nums[end+1]<max) {
    			end++;
    		}
    		return end-start+1;
    	}
    }
    
    //Q66     time: O(n)  space: O(1) without considering result space
    public int[] plusOne(int[] digits) {
    	int i = digits.length-1;
    	digits[i] = digits[i]+1;
        while(i>0 && digits[i]>9) {
        	digits[i] = 0;
        	digits[i-1] = digits[i-1]+1;
        	i--;
        }
        if (i==0 && digits[i]>9) {
        	int[] ans = new int[digits.length+1];
        	ans[0] = 1;
            ans[1] = 0;
        	for(int j=1; j<digits.length; j++) {
        		ans[j+1] = digits[j];
        	}
        	digits = ans;
        }
        return digits;
    }

    //Q4
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    	if (nums1.length == 0 && nums2.length == 0) {
    		return -1;
    	} else if (nums1.length == 0) {
    		return nums2.length % 2 == 0 ? (double)(nums2[nums2.length/2-1] + nums2[nums2.length/2])/2 : nums2[nums2.length/2];
    	} else if (nums2.length == 0) {
    		return nums1.length % 2 == 0 ? (double)(nums1[nums1.length/2-1] + nums1[nums1.length/2])/2 : nums1[nums1.length/2];
    	} else {
    		boolean doublemid = (nums1.length+nums2.length) % 2 == 0;
    		// if double median, midCount point to the larger median
    		// if single median, midCount point to median
    		int midCount = (nums1.length+nums2.length) / 2 + 1;
    		// a == nums1, b == nums2
    		int nums1MinCount = Math.max(0, midCount-nums2.length);
    		int nums1MaxCount = Math.min(midCount, nums1.length);
    		int nums1Count = -1; //numbers of left half numbers included in nums1
    		while(nums1MinCount<=nums1MaxCount) {
    			nums1Count = (nums1MinCount + nums1MaxCount)/2;
    			int nums2Count = midCount - nums1Count;
    			if (nums2Count>0 && nums1Count< nums1.length && nums1[nums1Count-1+1] < nums2[nums2Count-1]) {
    				nums1MinCount = nums1Count+1;
    			} else if (nums1Count>0 && nums2Count< nums2.length && nums2[nums2Count-1+1] < nums1[nums1Count-1]) {
    				nums1MaxCount = nums1Count-1;
    			} else {
    				// if no need to modify anymore, break
    				break;
    			}
    		}
    		//calculate mid from the left half information
    		if (nums1Count > 0 && midCount - nums1Count>0) {
    			int first = nums1[nums1Count-1];
    			int second = nums2[midCount-nums1Count-1];
    			if (first > second) {
    				if (nums1Count>1) {
    					second = Math.max(second, nums1[nums1Count-2]);
    				}
    			} else {
    				if (midCount-nums1Count>1) {
    					first = Math.max(first, nums2[midCount-nums1Count-2]);
    				}
    			}
    			return doublemid? (double)(first + second)/2 : Math.max(first, second);
    		} else if (nums1Count == 0) {
    			return doublemid? (double)(nums2[midCount-1] + nums2[midCount-2])/2 : nums2[midCount-1];
    		} else {
    			return doublemid? (double)(nums1[midCount-1] + nums1[midCount-2])/2 : nums1[midCount-1];
    		}
    	}
    }

    //Q26     Two pointer
    public int removeDuplicates(int[] nums) {
    	int slow = 0;
    	for (int fast = 1; fast<nums.length; fast++) {
    		if (nums[slow]!=nums[fast]) {
    			slow++;
    			nums[slow] = nums[fast];
    		}
    	}
    	return slow+1;
    }
    
    //Q75     time: O(n)  space: O(1)
    public void sortColors(int[] nums) {
    	int firstOne = 0; int firstTwo = nums.length-1;
        for(int i=0; i<=firstTwo; i++) {
        	if(nums[i]==0) {
        		nums[i] = nums[firstOne];
        		nums[firstOne] = 0;
        		//must go through every 0 to accumulate firstOne
        		firstOne++;
        	} else if(nums[i]==2) {
        		nums[i] = nums[firstTwo];
        		nums[firstTwo] = 2;
        		firstTwo--;
        		i--;
        	}
        }
    }

    //Q283
    public void moveZeroes(int[] nums) {
        int pset = 0;
        for (int psearch = 0; psearch < nums.length; psearch++) {
        	if (nums[psearch] != 0) {
        		// always swap the first zero and the first non-zero after it
        		if (pset < psearch) {
        			nums[pset] = nums[psearch];
            		nums[psearch] = 0;
        		}
        		pset++;
        	}
        }
    }

    //Q904
    public int totalFruit(int[] tree) {
    	int max = Integer.MIN_VALUE, start = 0, count = 0, slow =0, fast = 0;
    	HashMap<Integer, Integer> hm = new HashMap<>(); // hashMap holds <fruit id, count of fruits>
    	while(fast<tree.length) {
    		if (hm.containsKey(tree[fast])) {
    			int n = hm.get(tree[fast]);
    			hm.put(tree[fast], n+1);
    		} else {
    			
    			hm.put(tree[fast], 1);
    			count++;
    		}
    		while(count>2) {
    			int temp = fast-slow; //fast-1-slow+1
    			if (temp>max) {
    				max = temp;
    				start = slow;
    			}
//    			System.out.println("slow = " + slow + "   fast = "+ fast+"  max ="+max);
    			
    			if (hm.get(tree[slow])>1) {
    				int m = hm.get(tree[slow]);
        			hm.put(tree[slow], m-1);
    			} else {
    				hm.remove(tree[slow]);
    				count--;
    			}
    			slow++;
    		}
    		fast++;
    	}
    	int temp = fast-slow; //fast-1-slow+1
		if (temp>max) {
			max = temp;
			start = slow;
		}
		return max;
        
    }
    
    //Q406
    public int[][] reconstructQueue(int[][] people) {
        if (people.length == 0){
            return people;
        }
        // sort the list first by height, and then by numbesr of people in front
        Arrays.parallelSort(people, ((int[] a, int[] b) -> {
        	if (a[0]==b[0]) {
        		return a[1]-b[1];
        	} else {
        		return b[0]-a[0];
        	}
        }));
        // push back to the queue by order of people in front
        List<int[]> mylist = new ArrayList<>();
        int max = people[0][0];
        for (int i=0; i<people.length; i++) {
        	if(people[i][0]==max) {
        		mylist.add(people[i]);
        	} else {
        		mylist.add(people[i][1], people[i]);
        	}
        }
        for (int i =0; i<people.length; i++) {
        	people[i] = mylist.get(i);
        }
    	return people;
    }

    //Q475 Two-pointer
    //starting from left, keep the pointer of the heater array to the cloest heater of the current house
    //time: O(m+n) without sort  space: O(1)
    public int findRadius(int[] houses, int[] heaters) {
    	Arrays.parallelSort(houses);
    	Arrays.parallelSort(heaters);
        int ans = 0;
        for(int i=0; i<houses.length; i++) {
        	int j = 0;
        	while( j<heaters.length-1 && Math.abs(heaters[j+1]-houses[i])<=Math.abs(heaters[j]-houses[i])) {
        		// if next heater is closer to the current house, move to that heater
        		j++;
        	}
        	ans = Math.max(ans, Math.abs(heaters[j]-houses[i]));
        }
        return ans;
    }

    //Q1007
    public int minDominoRotations(int[] A, int[] B) {
    	if(A.length!=B.length || A.length==0) {
    		return -1;
    	}
    	int min = -1;
    	// because we want to make all values the same on a surface, so the value can only be A[0] or B[0]
    	int countAA0 = 0; // count for filping all in A to A[0]
    	int countAB0 = 0; // count for filping all in A to B[0]
    	int countBA0 = 0; // count for filping all in B to A[0]
    	int countBB0 = 0; // count for filping all in B to B[0]
    	//try to filp all in A or B to A[0]
    	for (int i=0; i<A.length; i++) {
    		if (A[i]!=A[0] && B[i]!=A[0]) {
    			// if neither A[0] nor B[0], cannot get all values the same
    			break;
    		} else if(A[i]!=A[0]) {
    			countAA0++; //need flip A[i]
    		} else if (B[i]!= A[0]) {
    			countBA0++; //need flip B[i]
    		}
    		if(i==A.length-1) {
    			// insist until the last, update value
    			min = Math.min(countAA0, countBA0);
    		}
    	}
    	//try to filp all in A or B to B[0]
    	for (int i=0; i<A.length; i++) {
    		if (A[i]!=B[0] && B[i]!=B[0]) {
    			// if neither A[0] nor B[0], cannot get all values the same
    			break;
    		} else if(A[i]!=B[0]) {
    			countAB0++; //need flip A[i]
    		} else if (B[i]!=B[0]) {
    			countBB0++; //need flip B[i]
    		}
    		if(i==A.length-1) {
    			// insist until the last, update value
    			min = min ==-1? Math.min(countAB0, countBB0) : Math.min(min, Math.min(countAB0, countBB0));
    		}
    	}
    	return min;
    }
    
	/*
	 *                  String -- Two Pointer Sliding Window 
	 */	
    
    //Q3
    // m  -- number of characters, n -- number of different characters
    // time: O(n), fast visit each index
    // space: O(min(m, n))
    public int lengthOfLongestSubstring(String s) {
    	HashMap<Character, Integer> map = new HashMap<>();
        int slow = 0, fast = 0, max = 0;
        while(slow < s.length() && fast<s.length()) {
        	if (map.containsKey(s.charAt(fast))) {
        		//compare the new duplication start and the current xtart
                slow = Math.max(map.get(s.charAt(fast))+1, slow);
        	}
            map.put(s.charAt(fast), fast);
            max = Math.max(max, fast-slow+1);
            fast++;
            
        }
        return max;
    }

    //Q8
    public int myAtoi(String str) {
    	char[] arr = str.toCharArray();
    	int i = 0;
    	//clean spaces before number
    	while(i<arr.length && arr[i]==' ') {
    		i++;
    	}
    	if (i==arr.length) {
    		return 0;
    	}
    	//check sign
    	int sign = 1;
    	if (arr[i]=='+') {
    		sign = 1;
    		i++;
    	} else if (arr[i]=='-') {
    		sign = -1;
    		i++;
    	} else if (!Character.isDigit(arr[i])) {
    		return 0;
    	}
    	
    	//get digits
    	int num = 0;
    	while(i<arr.length && Character.isDigit(arr[i])) {
    		int digit = arr[i] - '0';
    		//both num and digit may overflow INT_MAX
    		//num*10+digit<INT_MAX  ==>   num<(INT_MAX-digit)/10
    		if (num > (Integer.MAX_VALUE-digit)/10) {
    			return sign == 1? Integer.MAX_VALUE : Integer.MIN_VALUE;
    		}
    		num = num*10+digit;
    		i++;
    	}
    	return num*sign;
    }
    
    //Q76
    public String minWindow(String s, String t) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (Character c: t.toCharArray()) {
        	if (!map.containsKey(c)) {
        		map.put(c, 1);
        	} else {
        		map.put(c, map.get(c)+1);
        	}
        }
        int counter = t.length(), slow = 0, fast = 0, min = Integer.MAX_VALUE;
        String ans = "";
        while(fast < s.length()) {
        	if(map.containsKey(s.charAt(fast))) {
        		if(map.get(s.charAt(fast))>0) {
            		counter--;
        		}
        		map.put(s.charAt(fast), map.get(s.charAt(fast))-1);
        	}
        	//after satisfying requirement, increasing start to get minimum
        	while(counter<=0) {
        		if(fast-slow+1<min) {
        			min = fast-slow+1;
        			ans = s.substring(slow, fast+1);
        		}
        		if(map.containsKey(s.charAt(slow))) {
        			map.put(s.charAt(slow), map.get(s.charAt(slow))+1);
            		if(map.get(s.charAt(slow))>0) {
                		counter++;
            		}
            	}
        		slow++;
        	}
        	
        	fast++;
        }
        
        return ans;
    }
    
    //Q482
	public String FormatLicenseKey(String S, int K) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for(int i = S.length()-1; i>=0; i--) {
			if (S.charAt(i) != '-') {
				sb.append(Character.toUpperCase(S.charAt(i)));
				count++;
				if (count% K == 0) {
					sb.append('-');
				}
			}
		}
		
		if (sb.length() > 0 && sb.charAt(sb.length()-1)== '-') {
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.reverse().toString();
	}
	
	//Q11
	// Go over the upper right triangle of matrix     time: O(n^2/2)   space: O(1)
	// Two pointer     time: O(n)   space: O(1)
	public int maxArea(int[] height) {
		int max = 0;
		
		// Go over the upper right triangle of matrix
//		for (int start=0; start<height.length-1; start++) {
//			for (int end=start+1; end<height.length; end++) {
//				int temp = Math.min(height[start], height[end])*(end-start);
//				max = Math.max(temp, max);
//			}
//		}
		
		//Two pointer
		int low = 0;
		int high = height.length-1;
		while (low<high) {
            int temp = Math.min(height[low], height[high])*(high-low);
			max = Math.max(temp, max);
			if (height[high]>=height[low]) {
				low++;
			} else {
				high--;
			}
		}
		return max;
    }
	
	/*
	 *                        LinkedList 
	 */	
	
	class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { val = x; }
	}
	
	//Q21 O(N) for N as the total number of nodes in two lists
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    	if (l1 == null && l2 == null) {
    		return null;
    	} else if (l1 == null) {
    		return l2;
    	} else if (l2 == null) {
    		return l1;
    	} else {
    		ListNode head = new ListNode(-1);
    		head.next = l1;
    		ListNode pre = head;
    		ListNode pointer2 = l2; // pointer for the current node in list2 need to be merged into list1
    		while (pointer2 != null) {
                if (pre.next == null) {
    				pre.next = pointer2;
    				break;
                } else if (pointer2.val < pre.next.val) {
    				ListNode temp = pointer2;
    				pointer2 = pointer2.next;
    				temp.next = pre.next;
    				pre.next = temp;
    				pre = pre.next;
    			} else {
    				pre = pre.next;
    			}
    		}
    	    return head.next;
    	}
       
    }
    
    //Q19
    public ListNode removeNthFromEnd(ListNode head, int n) {
    	ListNode dummy = new ListNode(-1);
    	dummy.next = head;
    	ListNode fast = dummy;
    	while(n>0) {
    		fast = fast.next;
    		n--;
    	}
    	ListNode slow = dummy;
    	while(fast.next!=null) {
    		fast = fast.next;
    		slow = slow.next;
    	}
    	slow.next = slow.next.next;
    	return dummy.next;
    }
 
    //Q23
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
        	return null;
        } else if (lists.length == 1) {
        	return lists[0];
        } else {
        	return merge(Arrays.asList(lists)).get(0);
        }
    }
    
    // divide and conquer O(NlogK)
    // K -- number of lists
    // N -- total number of nodes in the two merged list
    // merge two lists in O(N) time, repeat merging for logK times 
    public List<ListNode> merge(List<ListNode> mylists){
    	for (int i = 0; i < mylists.size()/2; i++) {
    		mylists.set(i, mergeTwoLists(mylists.get(i), mylists.get(mylists.size()-1-i)));
    	}
    	if (mylists.size() % 2 == 0) {
    		mylists = mylists.subList(0, mylists.size()/2);
    	} else {
    		mylists = mylists.subList(0, mylists.size()/2+1);
    	}
    	return mylists.size()==1? mylists : merge(mylists);
    }

    //Q141     time: O(n)   space: O(1)
    public boolean hasCycle(ListNode head) {
    	ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null) {        
        	fast = fast.next.next;
        	slow = slow.next;
        	if (slow == fast) {
                return true;
            }
        }
        return false;
    }
    
    /*
     * Q142
     * slow pointer travels one step at once, fast pointer travels two steps at once
     * s_to_e: start to entry of cycle
     * e_to_m: entry of cycle to first meeting point of fast and slow
     * lenc: length of cycle
     * n: number of cycles traveled by the fast pointer more than the slow pointer before they meet
     * When they meet,
     * 	  slow pointer travels * 2 = fast pointer travels
     *     ( s_to_e + e_to_m ) * 2 =  s_to_e + e_to_m + lenc * n
     *             s_to_e + e_to_m = lenc * n
     *             s_to_e = lenc * (n-1) + ( lenc - e_to_m )
     * A pointer travels from start will meet with another point travels from the meeting point at the entry point
    */
    public ListNode detectCycle(ListNode head) {
    	ListNode slow = head;
        ListNode fast = head;
        while (fast!=null && fast.next!=null) {
        	fast = fast.next.next;
        	slow = slow.next;
        	if (slow == fast) {
        		// create a new node move from start to entry
                ListNode se = head;
                while(se != slow) {
                	slow = slow.next;
                	se = se.next;
                }
                return se;
            }
        }
        return null;
    }
    
	//Q24
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(-1);
    	dummy.next = head;
        ListNode pre = dummy;
        ListNode cur = head;
        while(cur!=null && cur.next!=null) {
            ListNode next = cur.next;
        	cur.next = next.next;
            pre.next = next;
        	next.next = cur;
        	pre = cur;
        	cur = cur.next;
        }
        return dummy.next;
    }
    
    //Q160
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<ListNode>();
        ListNode cur = headA;
        while (cur != null) {
        	set.add(cur);
        	cur = cur.next;
        }
        cur = headB;
        while (cur != null) {
        	if (set.contains(cur)) {
        		break;
        	}
        	cur = cur.next;
        }
    	return cur;
    }

    //Q445     time: O(max(m, n))   space: O(m+n)
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	Stack<Integer> s1 = new Stack<>();
    	while (l1.next != null ) {
    		s1.push(l1.val);
    		l1 = l1.next;
    	}
    	s1.push(l1.val);
    	
    	Stack<Integer> s2 = new Stack<>();
    	while (l2.next != null ) {
    		s2.push(l2.val);
    		l2 = l2.next;
    	}
    	s2.push(l2.val);
    	
    	int carry = 0;
    	ListNode dummy = new ListNode(-1);
    	while (s1.size() > 0 || s2.size() > 0 || carry > 0) {
    		int part1 = 0;
    		int part2 = 0;
    		if (s1.size() > 0) {
    			part1 = s1.pop().intValue();
    		}  
    		if (s2.size() > 0) {
    			part2 = s2.pop().intValue();
    		}
    		int curr = part1 + part2 + carry;
    		carry = curr/10;
    		curr = curr%10;
    		ListNode currnode = new ListNode(curr);
    		currnode.next = dummy.next;
    		dummy.next = currnode;
    	}
    	
    	return dummy.next;
    }
    
    //Q234
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
        	return true;
        } else {
        	ListNode fast = head;
        	ListNode slow = head;
        	int length = 1;
        	//can also be written as
//        	while(fast.next!=null) {
//        		fast = fast.next;
//        		length++;
//        		if (length % 2 == 1) {
//        			slow = slow.next;
//        		}
//        			
//        	}
        	while(fast!=null && fast.next!=null) {
                if(fast.next.next !=null){
                    fast = fast.next.next;
                    length+=2;
                    slow = slow.next;
                } else{
                    fast = fast.next;
                    length++;
                }
        	}
            //reverse the second half
        	ListNode newPre = null;
        	ListNode newStart = slow.next;
        	while (newStart!=null) {
        		ListNode temp = newStart.next;
        		newStart.next = newPre;
        		newPre = newStart;
        		newStart = temp;
        	}
        	//compare half of the list
        	int count = length/2;
        	while (count>0) {
        		if (head.val != fast.val) {
        			return false;
        		}
        		head = head.next;
        		fast = fast.next;
        		count--;
        	}
        	return true;
        }           
    }
    
    private ListNode createSampleLinkedList() {
    	ListNode head = new ListNode(-1);
    	head.next = new ListNode(5);
    	head.next.next = new ListNode(3);
    	head.next.next.next = new ListNode(4);
    	head.next.next.next.next = new ListNode(0);
    	return head;
    }
    
    //Q148 legitimate solution time complexity O(nlogn), space complexity O(1)
    public ListNode sortList(ListNode head) {
    	if (head == null) {
    		return null;
    	}
    	//create dummy head
    	ListNode dummy = new ListNode(-1000);
    	dummy.next = head;
    	int size = 0;
    	ListNode cur = head;
    	//get the size of list
    	while(cur!=null) {
    		size++;
    		cur = cur.next;
    	}
    	System.out.println("size = " + size);
//    	print(dummy);
    	//start with merge two item into a sorted sublist, then increase the size of sorted sublist
    	for(int step_length=2; step_length<size; step_length*=2) {
    		print(dummy);
    		ListNode pre = dummy;
    		cur = dummy.next;
    		ListNode next_start = null;
    		while(cur!=null) {
    			ListNode start = cur;
    			ListNode half = cur;
    			int k = 0;
    			// refresh start point of the second sublist
    			k = 0;
    			while(k<step_length/2 && half!=null) {
    				half = half.next;
    				k++;
    			}
    			// get the new end point (next current)
    			if (half!=null) {
    				next_start = half;
    				k = 0;
        			while(k<step_length/2 && next_start!=null) {
        				next_start = next_start.next;
        				k++;
        			}
    			}
    			// merge two sublist
    			int pointer1 = 0;
    			int pointer2 = 0;
    			while ((start!=null || half!=null) && (pointer1<step_length/2 || pointer2<step_length/2)) {
    				if (start!=null && half!=null && pointer1<step_length/2 && pointer2<step_length/2) {
    					if (start.val<=half.val) {
    						ListNode temp = start.next;
    						pre.next = start;
    						start = temp;
    						pointer1++;
    					}else {
    						ListNode temp = half.next;
    						pre.next = half;
    						half = temp;
    						pointer2++;
    					}
    				}else if(half==null || pointer2>=step_length/2) {
    					pre.next = start;
						start = start.next;
						pointer1++;
    				} else if(start==null || pointer1>=step_length/2) {
    					pre.next = half;
						half = half.next;
						pointer2++;
    				}
    				pre = pre.next;
    			}
    			pre.next = next_start;
    			cur = next_start;
    		}
    		
    	}
    	
    	return dummy.next;
    }
    
    
    
    //Q148 QuickSort time complexity O(nlogn), but close to O(n^2) for the sample data
//    public ListNode sortList(ListNode head) {
//    	if (head == null) {
//    		return null;
//    	}
//    	ListNode dummy = new ListNode(-1000);
//    	dummy.next = head;
//    	partition(head, dummy, null);
//    	return dummy.next;
//    }

//    private void partition(ListNode pivot, ListNode pre, ListNode next) {
//    	ListNode cur = pivot;
//    	while(cur.next!=next) {
//    		ListNode temp = cur.next;
//    		cur = cur.next;
//    		if (cur.next.val<=pivot.val) {
//    			cur.next = cur.next.next;
//    			temp.next = pre.next;
//    			pre.next = temp;
//    		}
//    	}
//    	if (pre.next!=pivot) {
//    		partition(pre.next, pre, pivot);
//    	}
//    	while(pivot.next!=null && pivot.val == pivot.next.val) {
//    		pivot = pivot.next;
//    	}
//    	if(pivot.next!=next) {
//    		partition(pivot.next,pivot,next);
//    	}
//    }
    
    //Q347
    public List<Integer> topKFrequent(int[] nums, int k) {
    	
    	HashMap<Integer, Integer> nummap = new HashMap<Integer, Integer>();
    	for (int i : nums) {
    		if (nummap.containsKey(i)) {
    			nummap.put(i, nummap.get(i)+1);
    		}else {
    			nummap.put(i, 1);
    		}
    	}
    	
    	//use bucket sort O(n)
    	List<Integer>[] freqs = new List[nums.length+1];
    	for (Integer n : nummap.keySet()) {
    		if (freqs[nummap.get(n)]==null) {
    			freqs[nummap.get(n)] = new LinkedList<Integer>();
    		}
    		freqs[nummap.get(n)].add(n);
    	}
    	
    	List<Integer> ans = new LinkedList<Integer>();
    	int pointer = freqs.length-1;
    	while(k>0 && pointer >0) {
    		if (freqs[pointer]!=null) {
    			for(Integer n : freqs[pointer]) {
        			ans.add(n);
        			k--;
        		}
    		}
    		pointer--;
    	}
    	
    	return ans;
    	// use maxheap O(nlogn), transfer to minheap can decrease time complexity to O(nlogk)
//    	PriorityQueue<Integer> queue = new PriorityQueue<Integer>((n1, n2) -> -1*(nummap.get(n1) - nummap.get(n2)));
//    	
//    	for (Integer n : nummap.keySet()) {
//    		queue.add(n);
//    	}
//    	
//    	List<Integer> ans = new LinkedList<Integer>();
//    	while(k>0 && !queue.isEmpty()) {
//    		ans.add(queue.poll());
//    		k--;
//    	}
//    	return ans;
//    	
//    	can also solve by sorting by TreeSet
    }
    
    
	/*
	 *                        Stacks 
	 */	
	
	//Q1     time: O(n)   space: O(n)
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int complement = -1;
        int i = 0;
        while (i < nums.length) {
            complement = target - nums[i];
            if (map.containsKey(complement)) {
                break;
            }
            map.put(nums[i], i);
            i++;
        }
        return new int[] { map.get(complement), i };
    }
    
    //Q167     two pointer    time: wrost O(N)   space: O(1)
    public int[] twoSumWithSortedArray(int[] numbers, int target) {
    	int small = 0;
    	int large = numbers.length-1;
    	while (small < large) {
    		if (numbers[small] + numbers[large] > target) {
        		large--;
        	} else if (numbers[small] + numbers[large] < target) {
        		small++;
        	} else {
        		break;
        	}
    	}
    	int[] ans = {-1, -1};
    	if (small < large) {
    		ans[0] = small+1;
    		ans[1] = large+1;
    	} 
    	return ans;
    }
    
	//Q71
    public String simplifyPath(String path) {
        String[] parts = path.split("/");
        Stack<String> stack = new Stack<String>();
        for (String p : parts) {
            if (p.equals("..") ){
                if ((!stack.empty())){
                    stack.pop();
                }
            }else if(p.length()>0 && (!p.equals("."))){
                stack.push(p);
            }
        }
        String ans = "";
        for (String s:stack){
            ans += "/"+s;
        }
        if (ans.equals("")){
            return "/";
        }else{
            return ans;
        }
    }
	
    //Q20
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        char[] clist = s.toCharArray();
        for (int i=0; i<clist.length; i++) {
        	if (!stack.isEmpty() && clist[i] == ')' && stack.peek() == '(') {
        		stack.pop();
        	} else if (!stack.isEmpty() && clist[i] == ']' && stack.peek() == '[') {
        		stack.pop();
        	} else if (!stack.isEmpty() && clist[i] == '}' && stack.peek() == '{') {
        		stack.pop();
        	} else {
        		stack.push(clist[i]);
        	}
        }
        return stack.isEmpty();
    }
    
    //Q844
    // m -- length of String S, n-- length of String T
    // time: O(m+n)  space: O(m+n)
    public boolean backspaceCompare1(String S, String T) {
    	char[] str1 = S.toCharArray();
    	StringBuilder s = new StringBuilder(S.length());
    	for (char c : str1) {
    		if (c!='#') {
    			s.append(c);
    		} else if (c=='#' && s.length()>0){
    			s.deleteCharAt(s.length()-1);
    		}
    	}
    	char[] str2 = T.toCharArray();
    	StringBuilder t = new StringBuilder(T.length());
    	for (char c : str2) {
    		if (c!='#') {
    			t.append(c);
    		} else if (c=='#' && t.length()>0){
    			t.deleteCharAt(t.length()-1);
    		}
    	}
    	return s.toString().compareTo(t.toString())==0;
    }
    
    // use two pointer
    // time: O(m+n)  space: O(1)
    public boolean backspaceCompare2(String S, String T) {
    	int i = S.length() - 1, j = T.length() - 1;
        int skipS = 0, skipT = 0;

        while (i >= 0 || j >= 0) { // While there may be chars in build(S) or build (T)
            while (i >= 0) { // Find position of next possible char in build(S)
                if (S.charAt(i) == '#') {
                	skipS++; 
                	i--;
                } else if (skipS > 0) {
                	skipS--; 
                	i--;
                } else break;
            }
            while (j >= 0) { // Find position of next possible char in build(T)
                if (T.charAt(j) == '#') {
                	skipT++; 
                	j--;
                } else if (skipT > 0) {
                	skipT--; 
                	j--;
                } else break;
            }
            // If two actual characters are different
            if (i >= 0 && j >= 0 && S.charAt(i) != T.charAt(j))
                return false;
            // If expecting to compare char vs nothing
            if ((i >= 0) != (j >= 0))
                return false;
            i--; j--;
        }
        return true;
    }
    
    /*
     *                 Dynamic Programming 
     *                 
     *                 top-down: recursion, cost of going through every for loop
     *                 bottom-up: filling matrix, cost of space and call built-in functions
     */	
	
    //Q53
    public int maxSubArray(int[] nums) {
    	// O(n^2) solution
//        int[] sums = new int[nums.length];
//        int max = Integer.MIN_VALUE;
//        for (int i = 0; i<nums.length; i++) {
//            for (int j = i; j>=0; j--){
//                sums[j] = sums[j] + nums[i];
//                if (sums[j] > max){
//                    max = sums[j];
//                }
//            }
//        }
//        return max;
       // O(n) solution
    	int[] maxEndHere = new int[nums.length];
    	int max = nums[0];
    	maxEndHere[0] = nums[0];
    	for(int i=1; i<nums.length; i++) {
    		// give up subarray before i == the sum of whole subarray+nums[i] is smaller than nums[i] itself
    		maxEndHere[i] = Math.max(maxEndHere[i-1]+nums[i], nums[i]);
    		max = Math.max(max, maxEndHere[i]);
    	}
    	return max;
    }

    //Q62
    
   public int uniquePaths(int m, int n) {
	   // backtracking (top-down) solution -- exceeds time limit
	   // return  backtrack( 1, 1, m, n) ;
	   if (m == 0 || n == 0) {
		   return 0;
	   }
	   int[][] grid = new int[n][m];
	   for (int i = n-1; i>=0; i--) {
		   for (int j = m-1; j>=0; j--) {
			   if (i == n-1 || j == m-1) {
				   grid[i][j] = 1;
			   } else {
				   grid[i][j] = grid[i+1][j] + grid[i][j+1];
			   }
		   }
	   }
	   return grid[0][0];
    }
//
//   private int backtrack( int cur_x, int cur_y, int max_x, int max_y) {
//	   if (cur_x == max_x || cur_y == max_y) {
//		   return 1;
//	   } else {
//		   int cumulate_x = backtrack(cur_x+1, cur_y, max_x, max_y);
//		   int cumulate_y = backtrack(cur_x, cur_y+1, max_x, max_y);
//		   return cumulate_x + cumulate_y;
//	   }
//   }
    
    //Q152
    public int maxProduct(int[] nums) {
    	if (nums.length==0) {
    		return 0;
    	} else {
    		// give up subarray before i == the product of whole subarray+nums[i] is smaller than nums[i] itself
    		// because it is product, we need to concern that the minimum previous value will become the largest one after sign change
    		// so keep track maxglobal, maxlocal and minlocal
    		int maxEndHere = nums[0];
    		int minEndHere = nums[0];
    		int maxGlobal = nums[0];
    		for (int i = 1; i<nums.length; i++) {
    			int p1 = maxEndHere*nums[i];
    			int p2 = minEndHere*nums[i];
    			// compute p1, p2 before set the new maxEndHere (ortherwise will affect the comaprison for minEndHere)
    			maxEndHere = Math.max(nums[i], Math.max(p1, p2));
    			minEndHere = Math.min(nums[i], Math.min(p1, p2));
    			if (maxEndHere > maxGlobal) {
    				maxGlobal = maxEndHere;
    			}
    		}
    		return maxGlobal;
    	}
    }
    
	//Q139
    public boolean wordBreak(String s, List<String> wordDict) {
//        init
        boolean[] states = new boolean[s.length()+1];
        states[0] = true;
//        transistion
        for(int i=1; i< states.length; i++) {
        	for(int j=i-1; j>=0; j--) {
        		if(states[j] && wordDict.contains(s.substring(j,i))) {
        			states[i] = true;
        		}
        	}
            }
        return states[s.length()];
    }
    
    //Q70
    public int climbStairs(int n) {
//    	steps = steps to 1 stair lower + steps to 2 stairs lower
    	if (n==1) {
    		return 1;
    	}else if(n==2) {
    		return 2;
    	}else {
    		int less2 = 1;
    		int less1 = 2;
    		while(n>3) {
    			int temp = less2+less1;
    			less2 = less1;
    			less1 = temp;
    			n--;
    		}
    		 return less2+less1;
    	}
    }
    
    //Q96
    public int numTrees(int n) {
        int[] states = new int[n+1];
        //states[n] represents the number of possible BST with n nodes
        //init
        states[0] = 1;
        states[1] = 1;
        //transaction, fill out each state
        for (int i = 2; i<=n; i++) {
        	// for each states[n], sum up the different possible BST with node 1...n as root
        	for (int root = 1; root <=i; root++) {
        		// the number of possible BST with node i as root is the multiplication of the number of its possible left subtrees and possible number of right subtrees
            	// root-1 = number of nodes in left subtree
        		// i-root = number of nodes in right subtree
        		states[i] += states[root-1]*states[i-root];
        	}
        }
        return states[n];
    }

    //Q322
    // n -- total amount, m -- number of different types of coins
    // time: O(nm)   space: O(n+1) = O(n)
    public int coinChange(int[] coins, int amount) {
    	int[] count = new int[amount+1];
    	Arrays.fill(count, amount+1);
    	count[0] = 0;
        
    	for (int i=1; i< count.length; i++) {
    		for (int c : coins) {
    			if (c <= i) {
    				count[i] = Math.min(count[i], count[i-c]+1);
    			}
    		}
    	}
    	return count[amount] < amount+1 ? count[amount] : -1;
    }
    
    //Q518
    // n -- total amount, m -- number of different types of coins
    // time: O(nm)   space: O(n+1) = O(n)
    public int change(int amount, int[] coins) {
        int count[] = new int[amount+1];
        count[0] = 1;
        for (int c : coins) {
        	// count[i] represent the number of ways to approach amount i with coins with value less than c (if sorted)
    		for (int i=1; i< count.length; i++) {
    			if (c <= i) {
    				count[i] += count[i-c];
    			} 
    		}
    	}
    	return count[amount];
    }
    
    //Q279
    public int numSquares(int n) {
    	//states: least number of perfect squares needed to make up n
    	int count[] = new int[n+1];
    	Arrays.fill(count, Integer.MAX_VALUE-1);
    	count[0] = 0;
    	for(int i = 1; i<n+1; i++) {
    		for(int j=0; j*j<=i; j++) {
    			count[i] = Math.min(count[i], count[i-j*j]+1);
    		}
    	}
    	return count[n];
    	
    	//math solution: four square theorem
    }
    
    //Q64
    public int minPathSum(int[][] grid) {
    	if (grid.length == 0 || grid[0].length == 0) {
    		return -1;
    	}
        for (int i=0; i<grid.length; i++) {
        	for(int j=0; j<grid[i].length; j++) {
        		 if(i==0 && j!=0) {
        			grid[i][j] = grid[i][j-1]+grid[i][j];
        		}else if(j==0 && i!=0) {
        			grid[i][j] = grid[i-1][j]+grid[i][j];
        		}else if(j!=0 && i!=0) {
        			grid[i][j] = Math.min(grid[i][j-1]+grid[i][j], grid[i-1][j]+grid[i][j]);
        		}
        	}
        }
//        for (int i=0; i<grid.length; i++) {
//        	for(int j=0; j<grid[i].length; j++) {
//        		System.out.print(grid[i][j]+" ");
//        	}
//        	System.out.println();
//        }
        return grid[grid.length-1][grid[0].length-1];
    }
    
    //Q494
    public int findTargetSumWays(int[] nums, int S) {
    	 List<HashMap<Integer, Integer>> map = new ArrayList<>();
         int sum = 0;
         for(int i= 0; i<nums.length; i++) {
         	map.add(new HashMap<Integer, Integer>());
         	sum = sum + nums[i];
         	if (i==0) {
         		map.get(i).put(nums[i], 1);
                 // exception: -0=+0
                 if (nums[i]!=0){
                     map.get(i).put(-1*nums[i], 1);
                 }else{
                     map.get(i).put(-1*nums[i], 2);
                 }
         	} else {
         		for (int j = -1*sum; j<=sum; j++) {
         			int countPlusTo = 0;
         			int countMinusTo = 0;
         			if (map.get(i-1).containsKey(j-nums[i])) {
         				countPlusTo = map.get(i-1).get(j-nums[i]);
         			}
         			if (map.get(i-1).containsKey(j+nums[i])) {
         				countMinusTo = map.get(i-1).get(j+nums[i]);
         			}
         			if (countPlusTo + countMinusTo > 0) {
         				map.get(i).put(j, countPlusTo + countMinusTo);
         			}
         		}
         	}
         }
               
         return map.get(map.size()-1).containsKey(S) ? map.get(map.size()-1).get(S) : 0;
    }
    
    //Q55
    public boolean canJump(int[] nums) {
    	int[] ans = new int[nums.length];
    	ans[nums.length-1] = 1;
    	for (int i = nums.length-2; i>=0; i--) {
    		for(int j = i+1; j<=Math.min(i+nums[i], nums.length-1); j++) {
    			if(ans[j]==1) {
    				ans[i] = 1;
    				break;
    			}
    		}
    	}
    	return ans[0] == 1;
    	
//    	also solve by greedy algorithm
//    	when the new node can approach the most left approachable node, set it as the new most left approachable node
//    	int mostleft = nums.length-1;
//    	for(int i=nums.length-2; i>=0; i--) {
//    		if (i+nums[i]>=mostleft) {
//    			mostleft = i;
//    		}
//    	}
//    	return mostleft == 0;
         
    }
 
    //Q121     time: O(n)   space: O(n) 
    public int maxProfit1(int[] prices) {
    	if (prices.length==0) {
    		return 0;
    	}
        int min = prices[0];
        prices[0] = 0;
        for (int i=1; i<prices.length; i++) {
        	if (prices[i]<=min) {
        		min = prices[i];
        	} 
        	prices[i] = Math.max(prices[i-1], prices[i]-min);
        }
        return prices[prices.length-1];
    }

    //Q122
    //Greedy algorithm, only count when today's price is higher than yesterday
    //time: O(n), space: O(1)
    public int maxProfit2(int[] prices) {
    	int maxprofit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
            	maxprofit += prices[i] - prices[i - 1];
            }
        }
        return maxprofit;
    }

    //Q123     time: O((N-1)*2) = O(N)   space : O(N*3*2) = O(N)
    public int maxProfit3(int[] prices) {
    	if (prices.length == 0) {
    		return 0;
    	}
    	// create a 3D matrix profit[i][j][k]
    	// i represent each day, max i = prices.length
    	// j represent the times of transaction, max j = 2 + 1
    	// j = 0: not buy any stock yet, j = N: the period after bought stock for the Nth time and before bought it for the N+1th time
    	// k represent not holding stock (0) or holding stock (1)
    	int maxTrans = 2;
    	int[][][] profit = new int[prices.length][maxTrans+1][2]; 
    	profit[0][0][0] = 0;
    	profit[0][0][1] = Integer.MIN_VALUE;
    	profit[0][1][1] = -prices[0];
    	// use 0 instead of  Integer.MIN_VALUE to avoid be reversed to  Integer.MAX_VALUE
    	profit[0][1][0] = 0;  // assume finish the 1st bought before game start, but profit is 0 because it is the inital value
    	profit[0][2][1] = Integer.MIN_VALUE;
    	profit[0][2][0] = 0; // assume finish the 2nd bought before game start, but profit is 0 because it is the inital value
        
    	for (int i = 1; i<prices.length; i++) {
    		for (int j = 1; j< maxTrans+1; j++) {
    			// for not holding stock at the current time, keep the previous not holding, or sell the stock held
    			profit[i][j][0] = Math.max(profit[i-1][j][0], profit[i-1][j][1]+prices[i]);
    			// for getting stock at the current time, keep holding stock, or buy in stock
    			profit[i][j][1] = Math.max(profit[i-1][j][1], profit[i-1][j-1][0]-prices[i]);
    		}
    	}
    	
    	// select maxprofit from the last day, may not used up all transfers
    	int maxprofit = Integer.MIN_VALUE;
    	for (int j = 1; j< maxTrans+1; j++) {
    		maxprofit = Math.max(maxprofit, profit[prices.length-1][j][0]);
    	}
    	return maxprofit;
    }
    
    //Q188     time: if greedy, O(N), otherwise O(Nk)   space: if greedy, O(1), otherwise O(N*(k+1)*2)
    public int maxProfit(int k, int[] prices) {
    	if (prices.length <= 1 || k==0) {
    		return 0;
    	}
    	int maxprofit = 0;
    	if (k > prices.length/2 || (k==prices.length/2 && prices.length%2==0)) {
    		// a transfer needs at least two days. Indeed, if k >= prices.length/2, transfers can cover all days flexibly
    		// Use Greedy
    		for (int i = 1; i < prices.length; i++) {
    			 if (prices[i] > prices[i - 1]) {
    	            	maxprofit += prices[i] - prices[i - 1];
    	         }
    		}
    	} else {
    		// create a 3D matrix profit[i][j][k]
        	// i represent each day, max i = prices.length
        	// j represent the times of transaction, max j = 2 + 1
        	// j = 0: not buy any stock yet, j = N: the period after bought stock for the Nth time and before bought it for the N+1th time
        	// k represent not holding stock (0) or holding stock (1)
        	int maxTrans = k;
        	int[][][] profit = new int[prices.length][maxTrans+1][2]; 
        	
        	for (int i = 0; i<prices.length; i++) {
        		for (int j = 1; j< maxTrans+1; j++) {
        			if (i== 0) {
        				if (j==0) {
        					profit[i][j][0] = 0;
        			    	profit[i][j][1] = Integer.MIN_VALUE;
        				} else if (j==1) {
        					profit[i][j][1] = -prices[0];
        			    	// use 0 instead of  Integer.MIN_VALUE to avoid be reversed to  Integer.MAX_VALUE
        			    	profit[i][j][0] = 0;  // assume finish the 1st bought before game start, but profit is 0 because it is the inital value
        				} else {
        					profit[i][j][1] = Integer.MIN_VALUE;
        		        	profit[i][j][0] = 0; // assume finish the 2nd bought before game start, but profit is 0 because it is the inital value
        				}
        			} else {
        				// for not holding stock at the current time, keep the previous not holding, or sell the stock held
            			profit[i][j][0] = Math.max(profit[i-1][j][0], profit[i-1][j][1]+prices[i]);
            			// for getting stock at the current time, keep holding stock, or buy in stock
            			profit[i][j][1] = Math.max(profit[i-1][j][1], profit[i-1][j-1][0]-prices[i]);
        			}
        			// select maxprofit from the last day, may not used up all transfers
        			if (i == prices.length-1) {
        				maxprofit = Math.max(maxprofit, profit[i][j][0]);
        			}
        		}
        	}
    	}
    	return maxprofit;
    }
    
    //Q309
    //time: O(N)   space: O(3N) = O(N)
    //Draw state machine transition
    /*
     *           +-----------+   rest    +---------+
     *    +-> | beforebuy  | <-------| aftersell | <--+
     *    |      +-----------+               +--------+       | sell
     *    |   rest  |           |                                            |
     *    +------+   buy |          +-------------------+
     *                             |          |                
     *                   +-----------+              
     *                    | holdstock | <-+  
     *                   +-----------+    |
     *                                   |  rest  |
     *                                   +-----+ 
     */
    public int maxProfitWithCooldown(int[] prices) {
    	if (prices.length==0) {
    		return 0;
    	}
    	int[] beforebuy = new int[prices.length];
    	int[] holdstock = new int[prices.length];
    	int[] aftersell = new int[prices.length];
    	beforebuy[0] = 0;
    	holdstock[0] = -1*prices[0];
    	aftersell[0] = Integer.MIN_VALUE;
    	for (int i=1; i<prices.length; i++) {
    		beforebuy[i] = Math.max(beforebuy[i-1], aftersell[i-1]);
    		holdstock[i] = Math.max(holdstock[i-1], beforebuy[i-1]-prices[i]);
    		aftersell[i] = holdstock[i-1] + prices[i];
    		//can decrease space complexity to O(1) by use only a int to represent pre_beforebuy, pre_holdstock, pre_aftersell
    	}
        return Math.max(beforebuy[prices.length-1], aftersell[prices.length-1]);    
    }
    
    //Q714     time: O(N)   space: O(N*2) = O(N)
    public int maxProfit(int[] prices, int fee) {
    	if (prices.length==0) {
    		return 0;
    	}
    	// profit[i][0] represent not holding stock at time i
    	// profit[i][1] represent holding stock at time i
    	int[][] profit = new int[prices.length][2];
    	profit[0][0] = 0;
    	profit[0][1] = -1*prices[0];
    	for (int i = 1; i<prices.length; i++) {
    		profit[i][0] = Math.max(profit[i-1][0], profit[i-1][1] + prices[i]-fee);
    		profit[i][1] = Math.max(profit[i-1][0]- prices[i], profit[i-1][1]);
    	}
    	return profit[prices.length-1][0];
    }
    
    //Q10
    public boolean isMatch(String s, String p) {
//    	states[i][j]:
//    		Whether s[0:i] can be matched with pattern p[0:j]
//    	init: 
//    	    results[0][0] = true
//    	    p = "", s not empty, false
//    	    s = "", p depends on '*'
//    	transition:
//    		if p[j] != '*':
//    			if s[i] == p[j] or p[j] == '.': states[i][j] == states[i-1][j-1]
//    		else:
//    			1. '*' removes the previous char: states[i][j] == states[i][j-2]
//    			2. '*' repeats the previous char 0 times: states[i][j] == states[i][j-1]		
//    			3. '*' repeats the previous char >= 1 time: 
//    	            current char == previous char || previous char == '.'
//    	            states[i-1][j] && i>=2 && (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.')
    	
    	boolean[][] results = new boolean[s.length()+1][p.length()+1];
    	results[0][0] = true;
    	
    	for (int i=1; i< s.length()+1; i++) {
    		results[i][0] = false;
    	}
    	
        // case:
        // s = "ab", p = ".*"
    	
        for (int j=1; j< p.length()+1; j++) {
    		results[0][j] = false;
    		if (j>=2 && p.charAt(j-1)=='*') {
    			results[0][j] = results[0][j-2];
    		}
    	}
        
    	for (int i = 1; i < s.length()+1; i++) {
    		for (int j = 1; j < p.length()+1; j++) {
    			if (p.charAt(j-1)== '*') {
    				results[i][j] = (j>=2 && results[i][j-2]) || results[i][j-1] || (results[i-1][j] && i>=2 && (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.'));
    			}else {
    				results[i][j] = results[i-1][j-1] && (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.');
    			}
    		}
    	}
        return results[s.length()][p.length()];
    }
    
    /*                Knapsack Problem                */
    
    //Q416
    //Normal backtrack will exceed time limit
    //Need to backtrack in the O-1 knapsack way by one-dimension array
    public boolean canPartition(int[] nums) {
    	if (nums.length==0) {
     		return false;
     	}
     	int target = 0;
     	for (int i = 0; i<nums.length; i++) {
     		target+=nums[i];
        }
        if (target % 2 != 0) {
         	return false;
        } else {
         	target = target/2;
         	boolean[][] dp = new boolean[target+1][nums.length+1];
         	dp[0][0] = true;
         	for (int i=1; i<dp.length; i++) {
         		for(int j = 1; j<dp[0].length; j++) {
         			if (i-nums[j-1]>=0) {
         				dp[i][j] = dp[i][j-1] || dp[i-nums[j-1]][j-1];
         			} else {
         				dp[i][j] = dp[i][j-1];
         			}
         			
         		}
            }
         	return dp[dp.length-1][dp[0].length-1];
        }
    }
    
    //Q198
    public int rob1(int[] nums) {
        if (nums.length==0){
            return 0;
        }
        
        for(int i = 1; i<nums.length; i++){
            if(i==1){
                nums[1] =  Math.max(nums[0], nums[1]);
            }else{
                nums[i] = Math.max(nums[i-2]+nums[i], nums[i-1]);
            }
        }
        return nums[nums.length-1];
    }
    
    //Q213
    public int rob2(int[] nums) {
//    	become a circle
//    	split the array into [0:len-1] and [1:len]
        if (nums.length==0){
            return 0;
        }else if(nums.length==1) {
        	return nums[0];
        }else {
        	int[] circle1 = new int[nums.length-1]; 	 
            for(int i = 0; i<nums.length-1; i++){
                if(i==0) {
                	circle1[i] = nums[i];
                }else if(i==1){
                	circle1[i] =  Math.max(nums[i-1], nums[i]);
                }else{
                	circle1[i] = Math.max(circle1[i-2]+nums[i], circle1[i-1]);
                }
            }
        	int[] circle2 = new int[nums.length-1];
            for(int i = 1; i<nums.length; i++){
                if(i==1) {
                	circle2[i-1] = nums[i];
                }else if(i==2){
                	circle2[i-1] =  Math.max(nums[i-1], nums[i]);
                }else{
                	circle2[i-1] =  Math.max(circle2[i-3]+nums[i], circle2[i-2]);
                }
            }       
            // for(int j =0; j<circle1.length; j++){
            //     System.out.print(circle1[j]+", ");
            // }
            // System.out.println();
            // for(int j =0; j<circle2.length; j++){
            //     System.out.print(circle2[j]+", ");
            // }            
        	return Math.max(circle1[nums.length-2], circle2[nums.length-2]);
        }
    }
    
    /*
     *                 Tree
     */	
    
    // Q116 
    // O(1) space  O(n) time  BFS
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val,Node _left,Node _right,Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };
    
    public Node connect(Node root) {
    	Node level = root;
    	while (level != null) {
    		Node cur = level;
    		// move on the same level
    		while (cur != null) {
    			if (cur.left != null) {
    				cur.left.next = cur.right;
    			}
    			if (cur.right !=null && cur.next!=null) {
    				cur.right.next = cur.next.left;
    			}
    			cur = cur.next;
    		}
    		// switch to next level
    		level = level.left;
    	}
    	return root;
    }

    /*               Binary Tree           */
    class TreeNode {
    	int val;
    	TreeNode left;
    	TreeNode right;
    	TreeNode(int x) { val = x; }
    }
    
    //Q617
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
        	return null;
        } else if (t1 == null) {
        	return t2;
        } else if (t2 == null) {
        	return t1;
        } else {
        	t1.val = t1.val + t2.val;
        	t1.left = mergeTrees(t1.left, t2.left);
        	t1.right = mergeTrees(t1.right, t2.right);
        	return t1;
        }
    }

    //Q94
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new LinkedList<>();
//        recursion solution
//        if (root != null) {
//        	DFS(ans, root);
//        }
        // iterative solution
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();
        while(cur!=null || !stack.empty()){
            while(cur!=null){
                stack.add(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            ans.add(cur.val);
            cur = cur.right;
        }
        return ans;
    }
    
    private void DFS(List<Integer> ans, TreeNode cur) {
    	if (cur.left != null) {
    		DFS(ans, cur.left);
    	}
    	ans.add(cur.val);
    	if (cur.right != null) {
    		DFS(ans, cur.right);
    	}
    }
    
    //Q102     N -- numnber of tree nodes  
    //BFS time: O(N)  space: O(N)
    public List<List<Integer>> levelOrder(TreeNode root) {
    	List<List<Integer>> ans = new LinkedList<>();
    	Queue<TreeNode> cur_level = new LinkedList<>();
    	cur_level.add(root);
    	while (!cur_level.isEmpty()) {
    		int cur_size = cur_level.size();
    		List<Integer> level_ans = new LinkedList<>();
    		while (cur_size>0) {
        		TreeNode cur_node = cur_level.poll();
        		if (cur_node != null) {
        			level_ans.add(cur_node.val);
        			cur_level.add(cur_node.left);
        			cur_level.add(cur_node.right);
        		}
        		cur_size--;
        	}
    		if (level_ans.size() > 0) {
   			 ans.add(level_ans);
   		    }
    	}
    	return ans;
    }
    
    //Q107     N -- numnber of tree nodes  
    //BFS time: O(N)  space: O(N)
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
    	List<List<Integer>> ans = new LinkedList<>();
    	Queue<TreeNode> cur_level = new LinkedList<>();
    	cur_level.add(root);
    	while (!cur_level.isEmpty()) {
    		int cur_size = cur_level.size();
    		List<Integer> level_ans = new LinkedList<>();
    		while (cur_size>0) {
        		TreeNode cur_node = cur_level.poll();
        		if (cur_node != null) {
        			level_ans.add(cur_node.val);
        			cur_level.add(cur_node.left);
        			cur_level.add(cur_node.right);
        		}
        		cur_size--;
        	}
    		if (level_ans.size() > 0) {
   			 ans.add(0, level_ans);
   		    }
    	}
    	return ans;
    }
    
    //Q101
    public boolean isSymmetric(TreeNode root) {    	
        if (root == null) {
        	return true;
        }else {
        	// recursion solution
//        	return BFS(root.left, root.right);
        	// iterative solution
        	// stack can hold null !!!
        	Stack<TreeNode> stack = new Stack<>();
        	stack.push(root.left);
        	stack.push(root.right);
        	while(stack.size()>0) {
        		if (stack.size()%2 != 0) {
        			return false;
        		}else {
        			TreeNode right = stack.pop();
        			TreeNode left = stack.pop();
        			if (left!=null && right==null) {
        				return false;
        			}else if (left==null && right!=null) {
        				return false;
        			}else if (left!=null && right!=null) {
        				if (left.val == right.val) {
        					// store outer pair
        					stack.push(left.left);
        					stack.push(right.right);
        					// store inner pair
        					stack.push(left.right);
        					stack.push(right.left);
        				}else {
        					return false;
        				}
        			}
        		}
        	}
        	return true;
        }
    }

    private boolean BFS(TreeNode left, TreeNode right) {
    	if (left==null && right==null) {
    		return true;
    	}else if (left!=null && right!=null && left.val == right.val) {
    		boolean outer = BFS(left.left, right.right);
    		boolean inner = BFS(left.right, right.left);
    		return outer&&inner;
    	}else {
    		return false;
    	}
    }
    
    //Q337
    public int rob3(TreeNode root) {
    	return robDFS(root)[1];
    }
    
    int[] robDFS(TreeNode node) {
//    	create an array to store results
//    	results[0]: maximum value without rob current node
//    	results[1]: maximum value with or without rob current node (best solution)
    	int[] results = new int[2];
    	if (node != null) {
    		int[] left_v = robDFS(node.left);
    		int[] right_v = robDFS(node.right);
    		results[0] = left_v[1] + right_v[1];
    		results[1] = Math.max(results[0], left_v[0] + right_v[0] + node.val);
    	}
    	return results;
    }
 
    //Q98
    public boolean isValidBST(TreeNode root) {
    	return isValidBFS(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    public boolean isValidBFS(TreeNode node, long low, long high) {
    	if (node == null) {
    		return true;
    	}else if(node.val>=high || node.val<=low) {
    		return false;
    	}else {
    		boolean left = isValidBFS(node.left, low, node.val);
    		boolean right = isValidBFS(node.right, node.val, high);
    		return left&&right;
    	}
    }
    
    //Q104
    public int maxDepth(TreeNode root) {
        return maxDepthDFS(0, 0, root);
    }
    
    public int maxDepthDFS(int max, int count, TreeNode node) {
    	if (node==null) {
    		return max;
    	}else {
    		count++;
    		if (max<count) {
    			max = count;
    		}
    		int left = maxDepthDFS(max, count, node.left);
    		int right = maxDepthDFS(max, count, node.right);
    		return Math.max(left, right);
    	}
    }

    //Q437
    int count = 0;
    public int pathSum(TreeNode root, int sum) {
      //Integer count = new Integer(0);
      //BFS
      List<TreeNode> queue = new LinkedList<>();
      if (root != null) {
      	queue.add(root);
          while(queue.size()>0) {
          	TreeNode cur = queue.remove(0);
          	if (cur.left!=null) {
          		queue.add(cur.left);
          	}
          	if (cur.right!=null) {
          		queue.add(cur.right);
          	}
          	sumup(cur, sum);
          }
      }
  	  return count;
    }
  
    private void sumup(TreeNode node, int cur) {
  	  if(cur-node.val==0) {
  		  count++;
      }
  	  if (node.left!=null) {
  		  sumup(node.left, cur-node.val);
  	  }
  	  if (node.right!=null) {
  		  sumup(node.right, cur-node.val);
  	  }
    }
    
    //Q15
    public List<List<Integer>> threeSum(int[] nums) {
    	List<List<Integer>> ans = new LinkedList<>();
    	Arrays.parallelSort(nums);
    	for (int i = 0; i<nums.length-2; i++) {
    		int low = i+1;
    		int high = nums.length-1;
    		while (low<high) {
    			int sum = nums[i] + nums[low] + nums[high];
    			if (sum > 0) {
    				high--;
    			}else if (sum < 0) {
    				low++;
    			}else {
    				ans.add(Arrays.asList(nums[i], nums[low], nums[high]));
    				// update after first hit because may miss duplication of numbers
       				while (low < nums.length-1 && nums[low] == nums[low+1]) {
        				low++;
        			}
        			while (high>0 && nums[high] == nums[high-1]) {
        				high--;
        			}
        			low++; high--;
    			}
    		}
    		// update after first hit because may miss duplication of numbers
    		while (i<nums.length-2 && nums[i] == nums[i+1]) {
				i++;
			}
    	}
    	return ans;
    }
    
    /*
     *                 Graph
     */
    
    //Q207
    
    // Topological sort     
    // N -- number of nodes, M -- number of edges
    //time: O(N+M)   space: O(N+M)
    public boolean canFinishTopological(int numCourses, int[][] prerequisites) {
    	//indegrees[i]: how many node should be visited before visit i
    	int[] indegrees = new int[numCourses];
    	//key: the start node   value: a list of the end node
    	HashMap<Integer, ArrayList<Integer>> edges = new HashMap<>(); 
    	
    	//initialize indegrees and edges
    	for (int[] p : prerequisites) {
    		indegrees[p[0]]++;
    		if (!edges.containsKey(p[1])) {
    			edges.put(p[1], new ArrayList<Integer>());
    		} 
    		ArrayList<Integer> destList = edges.get(p[1]);
    		destList.add(p[0]);
    	}
    	
    	//push nodes with not income to queue for poping them first
    	Queue<Integer> queue = new LinkedList<>();
    	for (int i=0; i<indegrees.length; i++) {
    		if (indegrees[i] == 0) {
    			queue.add(i);
    		}
    	}
    	
    	//topological sort
    	while (!queue.isEmpty()) {
    		int curr = queue.poll();
    		numCourses--;
    		if (edges.containsKey(curr)) {
    			for (int next : edges.get(curr)) {
    				indegrees[next]--;
        			if (indegrees[next] == 0) {
        				queue.add(next);
        			}
    			}
    		}
    	}
    	return numCourses == 0;
    }
    
    //DFS
    // N -- number of nodes, M -- number of edges
    //time: O(N+M)   space: O(N+M)
    public boolean canFinishDFS(int numCourses, int[][] prerequisites) {
    	// 0: has not been visited yet
    	// -1: has been visited by other nodes
    	// 1: has been visited by the current path
    	int[] hasVisited = new int[numCourses];
    	//key: the start node   value: a list of the end node
    	HashMap<Integer, ArrayList<Integer>> edges = new HashMap<>(); 
    	//initialize edges
    	for (int[] p : prerequisites) {
    		if (!edges.containsKey(p[1])) {
    			edges.put(p[1], new ArrayList<Integer>());
    		} 
    		ArrayList<Integer> destList = edges.get(p[1]);
    		destList.add(p[0]);
    	}
    	//DFS
    	for (int i=0; i<numCourses; i++) {
    		if (!dfs(i, edges, hasVisited)) {
				return false;
			}
    	}
    	return true;
    }
    
    public boolean dfs(int curr, HashMap<Integer, ArrayList<Integer>> edges, int[] hasVisited) {
    	// current node has been checked, do not need to go again
    	if (hasVisited[curr] == -1) {
    		return true;
    	}
    	// current trip has a circle
    	if (hasVisited[curr] == 1) {
    		return false;
    	}
    	//goes down
    	hasVisited[curr] = 1;
    	if (edges.containsKey(curr)) {
			for (int i : edges.get(curr)) {
				if (!dfs(i, edges, hasVisited)) {
					return false;
				}
			}
		}
    	//finish, back up
    	hasVisited[curr] = -1;
		return true;
    }
    
    //Q547
    
    //DFS, similar to num of island
    //time: O(n^2) (go over the whole matrix)   space: O(1)
    public int findCircleNumDFS(int[][] M) {
    	//M[i][j] = -1 represent that place has been visited, no need to check again
    	if (M.length == 0) {
    		return 0;
    	}
    	int count = 0;
    	for (int i = 0; i<M.length; i++) {
    		if (M[i][0] != -1) {
    			dfs (M, i);
        		count++;
    		}
    	}
    	return count;
    }
    
    public void dfs(int[][] M, int i) {
    	for (int j = 0; j<M.length; j++) {
    		if (i!=j && M[i][j]==1 && M[j][0] != -1) {
    			dfs (M, j);
    		}
    		M[i][j] = -1; //set to visited
    	}
    }
    
    //Disjoint Set Union
    //time: O(n^2) go over the matrix   space: O(n) for parent
    
    //find the root parent of index i, it represent the whole cluster
    public int find(int[] parent, int i) {
    	if (parent[i] == i)
            return parent[i];
    	// 路径压缩 Path Compression
    	// not use return return parent[i]
        return find(parent, parent[i]);
    }
    
    //merge two node to the same parent because they are correlated
    public void union(int[] parent, int i, int j) {
    	int ip = find(parent, i);
    	int jp = find(parent, j);
    	if (ip != jp) {
    		// 按秩合并 Union by Rank
    		// choose to merge to the root with more children
    		parent[jp] = ip;  
    	}
    }
    
    public int findCircleNumDSU(int[][] M) {
    	if (M.length == 0) {
    		return 0;
    	}
    	int[] parent = new int[M.length];
    	for (int i=0; i< M.length; i++) {
    		parent[i] = i;
    	}
    	for (int i=0; i< M.length; i++) {
    		for (int j=0; j<M.length; j++) {
    			if (i!=j && M[i][j]==1) {
    				union(parent, i, j);
    			}
    		}
    	}
    	int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == i)
                count++;
        }
        return count;
    }
    
    //Q684    implement DSU in one function
    //time: O(n) go over all edges   space: O(n) for parent
    public int[] findRedundantConnection(int[][] edges) {
    	//only one redundant edge, so number of nodes = edges.length
    	int[] parent = new int[edges.length];
    	for (int i = 0; i<edges.length; i++) {
    		parent[i] = i;
    	}
        for (int i = 0; i<edges.length; i++) {
        	// because the node start at 1, parent[i] represent the parent of node i+1
        	// minus 1 for check and modification, plus 1 when return results
    		int start = edges[i][0]-1;
    		int end = edges[i][1]-1;
    		// ---------------- equivalent to find() function --------------------
    		// recursively get the root of the union of start
    		int sroot = start;
    		while (sroot!=parent[sroot]) {
    			sroot = parent[sroot];
    		}
    		// recursively get the root of the union of end
    		int eroot = end;
    		while (eroot!=parent[eroot]) {
    			eroot = parent[eroot];
    		}
    		// ---------------- equivalent to union() function --------------------
    		if (sroot == eroot) {
    			// is already in the same union, current edge is redundant
    			int[] ans = {start+1, end+1};
    			return ans;
    		} else {
    			// merge to the same union
    			parent[eroot] = sroot;
    		}
    	}
        return null;
    }
    
    //Q210     time: O(N)   space: O(N)
    public int[] findOrder(int numCourses, int[][] prerequisites) {
    	//indegrees[i]: how many node should be visited before visit i
    	int[] indegrees = new int[numCourses];
    	//key: the start node   value: a list of the end node
    	HashMap<Integer, ArrayList<Integer>> edges = new HashMap<>(); 
    	int[] ans = new int[numCourses];
    	int pointer = 0;
    	
    	//initialize indegrees and edges
    	for (int[] p : prerequisites) {
    		indegrees[p[0]]++;
    		if (!edges.containsKey(p[1])) {
    			edges.put(p[1], new ArrayList<Integer>());
    		} 
    		ArrayList<Integer> destList = edges.get(p[1]);
    		destList.add(p[0]);
    	}
    	
    	//push nodes with not income to queue for poping them first
    	Queue<Integer> queue = new LinkedList<>();
    	for (int i=0; i<indegrees.length; i++) {
    		if (indegrees[i] == 0) {
    			queue.add(i);
    		}
    	}
    	
    	while (!queue.isEmpty()) {
    		int curr = queue.poll();
    		//add the current pop out to outputs
    		ans[pointer] = curr;
    		pointer++;
    		if (edges.containsKey(curr)) {
    			for (int next : edges.get(curr)) {
    				indegrees[next]--;
    				if (indegrees[next]==0) {
    					queue.add(next);
    				}
    			}
    		}
    	}
    	return pointer==numCourses? ans : new int[0];
    }
    
    //Q417
    //DFS     time: O(mn)   space: O(2mn) = O(mn)
    //Can also BFS, use two more queues
    public List<List<Integer>> pacificAtlanticDFS(int[][] matrix) {
    	List<List<Integer>> ans = new LinkedList<List<Integer>>();
    	if (matrix.length == 0 || matrix[0].length == 0) {
    		return ans;
    	}
    	int[][] pac = new int[matrix.length][matrix[0].length];
    	int[][] atl = new int[matrix.length][matrix[0].length];
    	for (int row = 0; row < matrix.length; row++) {
    		//check pacific
    		dfs (matrix, pac, row, 0, matrix[row][0]);
    		//check atlantic
    		dfs (matrix, atl, row, matrix[0].length-1, matrix[row][matrix[0].length-1]);
    	}
    	for (int column = 0; column < matrix[0].length; column++) {
    		//check pacific
    		dfs (matrix, pac, 0, column, matrix[0][column]);
    		//check atlantic
    		dfs (matrix, atl, matrix.length-1, column, matrix[matrix.length-1][column]);
    	}
    	for (int i = 0; i<matrix.length; i++) {
    		for (int j = 0; j<matrix[0].length; j++) {
    			if (pac[i][j] == 1 && atl[i][j] == 1) {
    				List<Integer> point = new LinkedList<Integer>();
    				point.add(i);
    				point.add(j);
    				ans.add(point);
    			}
    		}
    	}
    	return ans;
    }
    
    public void dfs (int[][] matrix, int[][] resultMatrix, int row, int column, int pre) {
    	if (row >= 0 && row < matrix.length && column >= 0 && column < matrix[0].length 
    			&& resultMatrix[row][column] == 0 && matrix[row][column] >= pre) {
    		resultMatrix[row][column]=1;
    		dfs (matrix, resultMatrix, row-1, column, matrix[row][column]);
    		dfs (matrix, resultMatrix, row+1, column, matrix[row][column]);
    		dfs (matrix, resultMatrix, row, column-1, matrix[row][column]);
    		dfs (matrix, resultMatrix, row, column+1, matrix[row][column]);
    	} 
    }
    
    
    /*
     *                 Palindrome
     */	
    
    //Q9
    public boolean isPalindrome(int x) {
        String str = String.valueOf(x);
        int start = 0;
        int end = str.length() - 1;
        while(start < end){
            if(str.charAt(start++) != str.charAt(end--)) return false;
        }
        return true;
    }
    
    //Q5 O(N^2)
    public String longestPalindrome(String s) {
    	//compare the string with its reversed string, space O(2N) = O(N)
//        String rev = new StringBuilder(s).reverse().toString();
//        int start = 0;
//        int max = 0;
//        for (int i = 0; i < s.length(); i++) {
//        	for (int l = max+1; i+l<=s.length(); l++) {
//        		if (rev.substring(rev.length()-i-l, rev.length()-i).compareTo(s.substring(i, i+l)) == 0) {
//        			start = i;
//            		max = l;
//        		}
//        	}
//        }
//        return s.substring(start, start+max);
    	
    	//Dynamic Programming solution, faster, more space O(N^2)
    	int max = 0;
    	int start = 0;
    	boolean[][] check = new boolean[s.length()][s.length()];
        for (int i = s.length()-1; i>=0; i--) {
        	for (int j = i; j<s.length(); j++) {
        		// check[i][j] shows whether the substring(j, i) is a palindrome
         		check[i][j] = s.charAt(i) == s.charAt(j) && (j-i<3 || check[i+1][j-1]);
         		if (check[i][j] && j-i+1>max) {
         			start = i;
         			max = j-i+1;
         		}
         	}
        }
        return s.substring(start, start+max);
        
    }
 
    //Q647
    //DP solution -- filling the upper right triangle from bottom to top
    //j-1<3 -- the situation for string length = 1, 2, 3
    public int countSubstrings(String s) {
        boolean[][] check = new boolean[s.length()][s.length()];
        int count = 0;
        for (int i = s.length()-1; i>=0; i--) {
        	for (int j = i; j<s.length(); j++) {
        		check[i][j] = s.charAt(i) == s.charAt(j) && (j-i<3 || check[i+1][j-1]);
        		if (check[i][j]) {
        			count++;
        		}
        	}
        }
        return count;
    }
    
    /*
     *                 Backtracking 
     */	
       
    //Q78
    public List<List<Integer>> subsets(int[] nums) {
    	List<List<Integer>> results = new LinkedList<>();
    	backtrack(results, nums, new ArrayList<Integer>(), 0);
    	return results;
    }
 
    private void backtrack(List<List<Integer>> results, int[] resources, List<Integer> current, int start) {
    	results.add(new ArrayList<>(current));
    	for(int i=start; i<resources.length; i++) {
    		current.add(resources[i]);
    		backtrack(results, resources, current, i+1);
    		current.remove(current.size()-1);
    	}
    }
    
    //Q90   O(2^n)
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> list = new LinkedList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }
    
    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int start){
        list.add(new ArrayList<>(tempList));
        for(int i = start; i < nums.length; i++){
        	// when switch to the next choice of an element
        	// if this element = last element, ignore this duplicated choice
            if(i==start || (i > start && nums[i] != nums[i-1])) {
                tempList.add(nums[i]);
                backtrack(list, tempList, nums, i + 1);
                tempList.remove(tempList.size() - 1);
            }
        }
    }
    
    //Q22
    public List<String> generateParenthesis(int n) {
    	List<String> results = new LinkedList<>();
    	backtrack(results, n, 0, 0, "");
    	return results;
    }
    
    private void backtrack(List<String> results, int max, int left, int right, String cur_str) {
    	if (cur_str.length()==max*2) {
    		results.add(cur_str);
    	}else {
    		if (left<max) {
    			backtrack(results, max, left+1, right, cur_str+"(");
    		}
    		if (right<left) {
    			backtrack(results, max, left, right+1, cur_str+")");
    		}
    	}
    }
 
    //Q46
    public List<List<Integer>> permute(int[] nums) {
    	List<List<Integer>> results = new LinkedList<>();
    	// because need contains, so ArrayList is much faster than LinkedList
    	backtrack(results, nums, new ArrayList<Integer>());
    	return results;
    	
    }

    private void backtrack(List<List<Integer>> results, int[] resources, List<Integer> current) {
    	if (current.size() == resources.length) {
    		results.add(new ArrayList<Integer>(current));
    	}else {
    		for (int i = 0; i<resources.length; i++) {
    			if (!current.contains(resources[i])) {
    				current.add(resources[i]);
    				backtrack(results, resources, current);
        			current.remove(current.size()-1);
    			}
    		}
    	}
    }
    
    //Q47
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> results = new LinkedList<>();
        Arrays.parallelSort(nums);
        backtrack(results, nums, new ArrayList<Integer>(), new boolean[nums.length]);
        return results;
    }
    
    private void backtrack(List<List<Integer>> results, int[] resources, List<Integer> current, boolean[] used) {
    	if (current.size() == resources.length) {
    		results.add(new ArrayList<Integer>(current));
    	}else {
    		for (int i = 0; i<resources.length; i++) {
    			// Add nums[i] to list when
    			// not used and i = 0
    			// not used and different from nums[i-1]
    			// not used and the same as nums[i-1] and  nums[i-1] has already been used
    			if (!used[i] && (i == 0 || ( i>0 && (resources[i]!=resources[i-1] || (resources[i]==resources[i-1] && used[i-1]))))) {
    				current.add(resources[i]);
    				used[i] = true;
    				backtrack(results, resources, current, used);
        			current.remove(current.size()-1);
        			used[i] = false;
    			}
    		}
    	}
    }
    
    //Q39
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
    	List<List<Integer>> results = new LinkedList<>();
    	backtrack(results, candidates,new ArrayList<Integer>(),0, target);
    	return results;
    }

    private void backtrack(List<List<Integer>> results, int[] candidates, List<Integer> current, int start, int sum) {
    	if (sum < 0) {
    		return;
    	}else if (sum==0) {
    		results.add(new ArrayList<Integer>(current));
    	}else if (sum>0) {
    		for (int i=start; i<candidates.length; i++) {
    			current.add(candidates[i]);
    			// start is not i + 1 because we can reuse same elements
    			backtrack(results, candidates, current, i, sum-candidates[i]);
    			current.remove(current.size()-1);
    		}
    	}
    }
    
    //Q40
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    	List<List<Integer>> results = new LinkedList<>();
    	//sort to ensure no duplication with different orders
    	Arrays.parallelSort(candidates);
    	helper(results, candidates,new ArrayList<Integer>(),0, target);
    	return results;
    }

    private void helper(List<List<Integer>> results, int[] candidates, List<Integer> current,int start, int sum){
    	if (sum < 0) {
    		return;
    	}else if (sum == 0) {
    		results.add(new ArrayList<Integer>(current));
    	}else if (sum > 0) {
    		for (int i=start; i<candidates.length; i++) {
    			// first case cover all cases of the following duplicated value
    			// so skip all duplication
    			if(i==start || (i>start && candidates[i]!=candidates[i-1])) {
    				current.add(candidates[i]);
        			helper(results, candidates, current, i+1, sum-candidates[i]);
        			current.remove(current.size()-1);
    			}
    		}
    	}
    }
    
    //Q200     time:O(mn)   space: O(1)
    public int numIslands(char[][] grid) {
    	if (grid.length==0) {
    		return 0;
    	} else {
    		int count = 0;
            for (int i=0; i<grid.length; i++) {
            	for (int j=0; j<grid[0].length; j++) {
            		if (grid[i][j] == '1') {
            			dfs(grid, i, j);
            			count++;
            		}
            	}
            }
            return count;
    	}      
    }
    
    private void dfs(char[][] grid, int row, int column) {
    	if (0<=row && row<grid.length && 0<=column && column<grid[0].length && grid[row][column] == '1') {
    		grid[row][column] = '#';
    		dfs(grid, row-1, column);
    		dfs(grid, row+1, column);
    		dfs(grid, row, column-1);
    		dfs(grid, row, column+1);
    	}
    }
    
    //Q1162
    //BFS each grid only be added into queue and visited once
    //time: O(n^2)   space: at most O(n^2), at least O(1) for queue
    public int maxDistance(int[][] grid) {
    	if (grid.length==0 || grid[0].length == 0) {
    		return -1;
    	}
    	int m = grid.length;
    	int n = grid[0].length;
    	Queue<int[]> queue = new ArrayDeque<>();
    	
    	//pick up lands
    	for (int row=0; row<m; row++) {
        	for (int column=0; column<n; column++) {
        		if (grid[row][column] == 1) {
        			queue.add(new int[]{row, column});
        		}
        	}
        }
    	
    	// all lands or all seas
    	if (queue.size() == 0 || queue.size() == m*n) {
    		return -1;
    	}
    	
    	//pop out from queue
    	//because all distances are 1, so the order of grid get into queue is sorted
    	//the first grids get into the queue are always closer to the land
    	int[] curr = null;
    	while (!queue.isEmpty()) {
    		curr = queue.poll();
    		//curr[0] is current row, curr[1] is current column
    		//add its up grid into queue if valid
    		if (curr[0]-1>=0 && grid[curr[0]-1][curr[1]] == 0) {
    			grid[curr[0]-1][curr[1]] = grid[curr[0]][curr[1]]+1;
    			queue.add(new int[] {curr[0]-1, curr[1]});
    		}
    		//add its down grid into queue if valid
    		if (curr[0]+1<m && grid[curr[0]+1][curr[1]] == 0) {
    			grid[curr[0]+1][curr[1]] = grid[curr[0]][curr[1]]+1;
    			queue.add(new int[] {curr[0]+1, curr[1]});
    		}
    		//add its left grid into queue if valid
    		if (curr[1]-1>=0 && grid[curr[0]][curr[1]-1] == 0) {
    			grid[curr[0]][curr[1]-1] = grid[curr[0]][curr[1]]+1;
    			queue.add(new int[] {curr[0], curr[1]-1});
    		}
    		//add its left grid into queue if valid
    		if (curr[1]+1<n && grid[curr[0]][curr[1]+1] == 0) {
    			grid[curr[0]][curr[1]+1] = grid[curr[0]][curr[1]]+1;
    			queue.add(new int[] {curr[0], curr[1]+1});
    		}
    	}
    	
    	//did not decrease the original 1 representing land, so need to minus 1 for final result
    	return grid[curr[0]][curr[1]]-1;
    }
    
    //Q79
    //convert String to charArray to save time
    public boolean exist(char[][] board, String word) {
        if (board.length == 0 && board[0].length == 0) {
        	return false;
        } else {
        	for(int i=0; i<board.length; i++) {
        		for(int j=0; j<board[0].length; j++) {
        			if (backtrack(board, i, j, word)) {
        				return true;
        			}
        		}
        	}
        	return false;
        }
    }
    
    private boolean backtrack(char[][] board, int i, int j, String cur) {
    	if (cur.length()==0) {
    		return true;
    	} else if(i<0 || i>=board.length || j<0 || j>=board[0].length || board[i][j] != cur.charAt(0)){
    		return false;
    	} else {
    		char temp = board[i][j];
    		board[i][j] = '*';
    		boolean exist = backtrack(board, i+1, j, cur.substring(1)) 
    				|| backtrack(board, i-1, j, cur.substring(1)) 
    				|| backtrack(board, i, j+1, cur.substring(1)) 
    				||backtrack(board, i, j-1, cur.substring(1));
    		board[i][j] = temp;
    		return exist;
    	}
    }
    
//    If we take XOR of zero and some bit, it will return that bit: a ⊕ 0 = a
//    If we take XOR of two same bits, it will return 0: a ⊕ a= 0
//    a ⊕ b ⊕ a = (a ⊕ a) ⊕ b= 0 ⊕ b = b
//    so we just XOR all numbers in the list [O(1) memory]
    public int singleNumber(int[] nums) {
    	int ans = 0;
    	for(int i : nums) {
    		ans^=i;
    	}
    	return ans;
    }
    
    /*
     *                 Main Method 
     */	
    
    private void print(ListNode head) {
    	ListNode cur = head;
    	StringBuilder sb = new StringBuilder();
    	while(cur!=null) {
    		sb.append(cur.val);
    		sb.append(" -> ");
    		cur = cur.next;
    	}
    	sb.delete(sb.length()-4, sb.length()-1);
    	System.out.println(sb.toString());
    }
    
    private void print2DMatrix(int[][] matrix) {
    	for(int i = 0; i<matrix.length; i++) {
    		for(int j=0; j<matrix[0].length; j++) {
    			System.out.print(matrix[i][j]+" ");
    		}
    		System.out.println();
    	}
    }
    
    private void print2DMatrix(char[][] matrix) {
    	for(int i = 0; i<matrix.length; i++) {
    		for(int j=0; j<matrix[0].length; j++) {
    			System.out.print(matrix[i][j]+" ");
    		}
    		System.out.println();
    	}
    }
       
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Solutions s = new Solutions();
//        String str = "/home///foo/b/";
//        String[] parts = str.split("/");
//        for (String i : parts) {
//        	System.out.println(i);
//        	if (i.equals("home")) {
//        		System.out.println("Yes");
//        	}
//        }
		
		//test remove function of an integer list
//		List<Integer> mylist = new LinkedList();
//		mylist.add(3);
//		mylist.add(6);
//		mylist.add(2);
//		mylist.add(1);
//		mylist.add(8);
//		System.out.println(mylist);
//		//remove 6
//		mylist.remove(1);
//		System.out.println(mylist);
//		//remove 1
//		mylist.remove(new Integer(1));
//		System.out.println(mylist);
		
//		System.out.println(Integer.MAX_VALUE);
//		System.out.println(Integer.MAX_VALUE+1);
		
		//boolean are originally set false
//		char[][] board = new char[][]{ {'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'O', 'X', 'X'},{'X', 'X', 'O', 'X'}};
//		s.print2DMatrix(board);
//      System.out.println(s.totalFruit(new int[]{3,3,3,1,2,1,1,2,3,3,4}));
//		ListNode n = s.createSampleLinkedList();
//		s.print(n);
//		ListNode a = s.sortList(n);
//		System.out.println( s.sortList(n)==null);
//		s.print(a);
		System.out.println(s.uniquePaths(51, 9));
		
	}

}