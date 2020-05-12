import java.util.HashMap;

//time: O(1) for get, put, and remove
public class LRUCache {

	class DoublyLinkedListNode {
		
		public int key;
		public int value;
		public DoublyLinkedListNode pre;
		public DoublyLinkedListNode next;
		
		public DoublyLinkedListNode() {
			this.key = Integer.MIN_VALUE;
			this.value = Integer.MIN_VALUE;
			this.pre = null;
			this.next = null;
		}
		
		public DoublyLinkedListNode(int key, int value, DoublyLinkedListNode pre, DoublyLinkedListNode next) {
			this.key = key;
			this.value = value;
			this.pre = pre;
			pre.next = this;
			this.next = next;
			next.pre = this;
		}
		
		public void setPre(DoublyLinkedListNode pre) {
			this.next = pre.next;
			if (pre.next != null) {
				this.next.pre = this;
			}
			this.pre = pre;
			pre.next = this;
		}
		
		public void setNext(DoublyLinkedListNode next) {
			this.pre = next.pre;
			if (next.pre != null) {
				this.pre.next = this;
			}
			this.next = next;
			next.pre = this;
		}
	}
	
	public int capacity;
	public int size;
	public HashMap<Integer, DoublyLinkedListNode> cache;
	public DoublyLinkedListNode head; //most recent
	public DoublyLinkedListNode tail; //least recent
	public LRUCache(int capacity) {
		this.capacity = capacity;
		size = 0;
		cache = new HashMap<Integer, DoublyLinkedListNode>();
		head = new DoublyLinkedListNode();
		tail = new DoublyLinkedListNode();
		head.setNext(tail);
    }
    
    public int get(int key) {
    	if (cache.containsKey(key)) {
    		DoublyLinkedListNode cur = cache.get(key);
    		//move this object to the most recent position
    		DoublyLinkedListNode cur_pre = cur.pre;
    		DoublyLinkedListNode cur_next =  cur.next;
    		cur_pre.next = cur_next;
    		cur_next.pre = cur_pre;
    		cur.setPre(head);
    		return cur.value;
    	} else {
    		return -1;
    	}
    }
    
    public void put(int key, int value) {
    	if (cache.containsKey(key)) {
    		DoublyLinkedListNode cur = cache.get(key);
    		cur.value = value;
    		//move this object to the most recent position
    		DoublyLinkedListNode cur_pre = cur.pre;
    		DoublyLinkedListNode cur_next =  cur.next;
    		cur_pre.next = cur_next;
    		cur_next.pre = cur_pre;
    		cur.setPre(head);
    	} else {
    		DoublyLinkedListNode cur = new DoublyLinkedListNode(key, value, head, head.next);
    		cache.put(key, cur);
    		size++;
    	}
    	if (size>capacity) {
    		removeLast();
    	}
    	
    }

    public void removeLast() {
    	cache.remove(tail.pre.key);
    	DoublyLinkedListNode tmp = tail.pre.pre;
    	tail.pre = tmp;
    	tmp.next = tail;
    	size--;
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
        		sb.append("(" + cur.key + ", "+ cur.value + ") -> ");
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
        		sb.append("(" + cur.key + ", "+ cur.value + ") -> ");
    		}
    		cur = cur.pre;
    	}
    	return sb.toString();
    }
    
	public static void main(String[] args) {
		LRUCache c = new LRUCache(2);
		c.put(1, 1);
		System.out.println(c);
//		System.out.println(c.reverse());
		c.put(2, 2);
		System.out.println(c);
//		System.out.println(c.reverse();
		c.put(5, 5);
		System.out.println(c);
		System.out.println("value for key=1 is "+ c.get(1));
		c.put(1, 7);
		System.out.println(c);
		System.out.println("value for key=1 is "+ c.get(1));
		c.put(3, 3);
		System.out.println(c);
	}

}
