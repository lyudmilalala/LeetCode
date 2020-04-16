//Q706
public class MyHashMap {

	ListNode[] bucket = new ListNode[10000];
	/** Initialize your data structure here. */
    
	private int getBucketIndex(int key){	
		return Integer.hashCode(key) % bucket.length;
	}
	
    /** value will always be non-negative. */
    public void put(int k, int v) {
    	int index = getBucketIndex(k);	
     	if (bucket[index] == null) {
     		bucket[index] = new ListNode(k, v);
     	} else if (bucket[index].key == k){
     		bucket[index].value = v;
     	} else {
             ListNode pre = bucket[index];
     		while(pre.next != null && pre.next.key != k) {
             	pre = pre.next;
             }
             if (pre.next != null) {
             	pre.next.value = v;
             }else {
             	pre.next = new ListNode(k, v);
             }
     	}
    }
    
    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int k) {
    	int index = getBucketIndex(k);
    	ListNode cur = bucket[index];
        while(cur != null && cur.key != k) {
        	cur = cur.next;
        }
        if (cur != null) {
        	return cur.value;
        }else {
        	return -1;
        }
//        printMap();
    }
    
    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int k) {
    	int index = getBucketIndex(k);
    	ListNode pre = bucket[index];
    	if (pre != null && pre.key == k) {
    		bucket[index] = pre.next;
    	} else {
            while(pre != null && pre.next != null && pre.next.key != k) {
            	pre = pre.next;
            }
            if (pre != null && pre.next != null) {
            	pre.next = pre.next.next;
            }
    	}
    }

//    public void printMap() {
////    	ListNode cur = head;
//    	System.out.println("MyMap");
//    	while (cur!=null) {
//    		System.out.println("<"+cur.key+" , "+cur.value+">");
//    		cur = cur.next;
//    	}
//    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyHashMap map = new MyHashMap();
		map.put(1, 1);
		map.put(2, 2);
		map.put(2, 3);
	}

	class ListNode {
		int key;
		int value;
		ListNode next;
		
		ListNode(int myk, int myv){
			key = myk;
			value = myv;
			next = null;
		}
	}
}
