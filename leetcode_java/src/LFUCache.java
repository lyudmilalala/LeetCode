import java.util.HashMap;

//time: O(1) for get, put, and remove
//can be even optimized by replacing the freqMap with a DoublyLinkedList (so we do not need min)
//If use PriorityQueue, takes O(logn) for put
public class LFUCache {
	
	class DoublyLinkedListNode {
		
		public int key;
		public int value;
		public int freq;
		public DoublyLinkedListNode pre;
		public DoublyLinkedListNode next;
		
		public DoublyLinkedListNode() {
			this.key = Integer.MIN_VALUE;
			this.value = Integer.MIN_VALUE;
			this.freq = 1;
			this.pre = null;
			this.next = null;
		}
		
		public DoublyLinkedListNode(int key, int value) {
			this.key = key;
			this.value = value;
			this.freq = 1;
			this.pre = null;
			this.next = null;
		}
	}
	
	class DoublyLinkedList {
		public DoublyLinkedListNode head; //most recent at this frequency
		public DoublyLinkedListNode tail; //least recent at this frequency
		
		public DoublyLinkedList() {
			head = new DoublyLinkedListNode();
			tail = new DoublyLinkedListNode();
			head.next = tail;
			tail.pre = head;
		}
		
		public void addToFront(DoublyLinkedListNode n) {
			n.next = head.next;
			head.next.pre = n;
			n.pre = head;
			head.next = n;
		}
		
		public void remove(DoublyLinkedListNode n) {
			n.pre.next = n.next;
			n.next.pre = n.pre; 
		}
		
		public String toString() {
	    	StringBuilder sb = new StringBuilder();
	    	DoublyLinkedListNode cur = head;
	    	while(cur!=null) {
	    		if (cur==head) {
	    			sb.append("head -> ");
	    		} else if (cur==tail) {
	    			sb.append("tail");
	    		} else {
	        		sb.append("(key = " + cur.key + ", value = "+ cur.value + ", freq = " + cur.freq + ") -> ");
	    		}
	    		cur = cur.next;
	    	}
	    	return sb.toString();
	    }
	    
	    public String reverse() {
	    	StringBuilder sb = new StringBuilder();
	    	DoublyLinkedListNode cur = tail;
	    	while(cur!=null) {
	    		if (cur==head) {
	    			sb.append("head");
	    		} else if (cur==tail) {
	    			sb.append("tail -> ");
	    		} else {
	        		sb.append("(key = " + cur.key + ", value = "+ cur.value + ", freq = " + cur.freq + ") -> ");
	    		}
	    		cur = cur.pre;
	    	}
	    	return sb.toString();
	    }
	    
	}
	
	public int capacity;
	public int size;
	public int min;
	public HashMap<Integer, DoublyLinkedListNode> cache; //key=key, value=Node with that key
	public HashMap<Integer, DoublyLinkedList> freqMap; //key=frequency, value=a list of Nodes at that frequency
	public LFUCache(int capacity) {
		this.capacity = capacity;
		size = 0;
		min = Integer.MAX_VALUE;
		cache = new HashMap<Integer, DoublyLinkedListNode>();
		freqMap = new HashMap<Integer, DoublyLinkedList>();
    }
    
    public int get(int key) {
    	if (cache.containsKey(key)) {
    		DoublyLinkedListNode cur = cache.get(key);
    		//remove from the current LinkedList
    		DoublyLinkedList old = freqMap.get(cur.freq);
    		old.remove(cur);
    		//modify min
    		if (min==cur.freq && old.head.next == old.tail) {
    			min = cur.freq+1;
    		}
    		//increase frequency of the current node
    		cur.freq++;
    		//add to new LinkedList
    		addToFrequencyLinkedList(cur);
    		return cur.value;
    	} else {
    		return -1;
    	}
    }
    
    public void put(int key, int value) {
    	if(capacity == 0) {
    		return;
    	}
    	DoublyLinkedListNode cur;
    	if (cache.containsKey(key)) {
    		//already exists
    		cur = cache.get(key);
    		//remove from the current LinkedList
    		DoublyLinkedList old = freqMap.get(cur.freq);
    		old.remove(cur);
    		//modify min
    		if (min==cur.freq && old.head.next == old.tail) {
    			min = cur.freq+1;
    		}
    		// refresh its frequency and value
    		cur.freq++;
    		cur.value = value;
    	} else {
    		//if the cache is full, remove the least frequent
    		if(size==capacity) {
    			removeLast();
    		}
    		//create a new Node
    		cur = new DoublyLinkedListNode(key, value);
    		cache.put(key, cur);
    		size++;
    		min=1;
    	}
    	addToFrequencyLinkedList(cur);
    }
    
    // add Node to the corresponding FrequencyLinkedList
    public void addToFrequencyLinkedList(DoublyLinkedListNode n) {
    	int freq = n.freq;
    	if (!freqMap.containsKey(freq)) {
    		freqMap.put(freq, new DoublyLinkedList());
    	}
    	freqMap.get(freq).addToFront(n);
    }
    
    // remove the last of the cache
    public void removeLast() {
    	DoublyLinkedList minList = freqMap.get(min);
    	DoublyLinkedListNode last = minList.tail.pre;
    	cache.remove(last.key);
    	minList.remove(last);
    	size--;
    	//do not need to modify min here, because min will always be 1 after the new node is inputted
    }
    
    public void print() {
    	System.out.println("============ print the cache =============");
    	System.out.println("min = "+min);
    	for (Integer i : freqMap.keySet()) {
    		System.out.println("inordered list with key = " + i + ": " + freqMap.get(i).toString());
    		System.out.println("reversed list with key = " + i + ": " + freqMap.get(i).reverse());
    	}
    }
    
    public static void main(String[] args) {
    	LFUCache c = new LFUCache(2);
    	c.put(1, 1);
    	c.print();
    	c.put(2, 2);
    	c.print();
    	System.out.println("get(1) = " + c.get(1));
    	c.print();
    	c.put(3, 3);
    	c.print();
    	System.out.println("get(2) = " + c.get(2));
    	c.print();
    	System.out.println("get(3) = " + c.get(3));
    	c.print();
    	c.put(4, 4);
    	c.print();
    	System.out.println("get(3) = " + c.get(3));
    	c.print();
    	System.out.println("get(4) = " + c.get(4));
    	c.print();
    }
}
