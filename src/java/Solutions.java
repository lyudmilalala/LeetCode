package java;

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
	
	//Q48
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
    
    //Q75
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

	/*
	 *                  String -- Two Pointer Sliding Window 
	 */	
    
    //Q3
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
	
	//Q1
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
    
    /*
     *                 Dynamic Programming 
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

    //152
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
    
    //Q322
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
    public int change(int amount, int[] coins) {
        int count[] = new int[amount+1];
        count[0] = 1;
        for (int c : coins) {
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
     *                 Binary Tree
     */	
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
    
    //Q200
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
    
    public void solve(char[][] board) {
        for (int i=0; i<board.length; i++) {
        	for(int j=0; j<board[i].length; j++) {
        		if(board[i][j]=='O') {
        			
        		}
        	}
        }
    }
    
    private void surrounded(char[][] grid, int row, int column) {
    	
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
    /*
     *                 Main Method 
     */	
    
    public void print2DMatrix(int[][] matrix) {
    	for(int i = 0; i<matrix.length; i++) {
    		for(int j=0; j<matrix[0].length; j++) {
    			System.out.print(matrix[i][j]+" ");
    		}
    		System.out.println();
    	}
    }
    
    public void print2DMatrix(char[][] matrix) {
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

		int[] nums2 = new int[] {1, 3, 3, 4, 5, 6};
		int[] nums1 = new int[0];
		double a = s.findMedianSortedArrays(nums1, nums2);
		System.out.println(a);
	}

}