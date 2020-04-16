//Q707
public class MyLinkedList {

	private ListNode head;
	
	/** Initialize your data structure here. */
	public MyLinkedList() {
		// TODO Auto-generated constructor stub
	}

	/** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
    	ListNode cur = head;
        while(index > 0 && cur !=null) {
        	cur = cur.next;
        	index--;
        }
        // be careful about negative index
        return index!=0 || cur == null ? -1 : cur.value;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
    	if (head == null) {
        	head = new ListNode(val); 
        } else {
        	// temp points to the address of head
        	ListNode temp = head;
            // head points to the address of newly created node in memory
        	//temp points to the address of old head
        	head = new ListNode(val);
        	head.next = temp;
        }
    }
    
//    Incorrect implementation
//    When a == null, b = a only passes the null value, not reference 
//    public void addAtTailFailed(int val) {
//        ListNode cur = head;
//        while (cur != null) {
//        	cur = cur.next;
//        }
//        cur = new ListNode(val);    
//    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        if (head == null) {
        	head = new ListNode(val); 
        } else {
        	 ListNode cur = head;
             while (cur.next!=null) {
             	cur = cur.next;
             }
             cur.next = new ListNode(val); 
        }
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index < 0 || index == 0) {
        	ListNode temp = head;
        	head = new ListNode(val);
        	head.next = temp;
        } else {
        	ListNode pre = head; // track the index-1 Node
        	if (pre == null) {
        		return;
        	}
        	while (index>1 && pre.next !=null) {
        		index--;
        		pre = pre.next;	
    		}
        	if (index == 1) {
        		if (pre.next==null) {
        			pre.next = new ListNode(val);
        		} else {
        			ListNode temp = new ListNode(val);
        			temp.next = pre.next;
        			pre.next = temp;
        		}
    		}
        }
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
    	if (index < 0) {
    		return;
    	} else if (index == 0) {
    		head = head.next;
    	} else {
    		ListNode pre = head; // track the index-1 Node
    		while (index>1 && pre.next !=null) {
        		index--;
        		pre = pre.next;	
    		}
    		if (index == 1 && pre.next!=null) {
    			pre.next = pre.next.next;
    		}
    	}  	
    }
    
    public String toString() {
    	 ListNode cur = head;
    	 String s = "";
         while (cur!=null) {
        	s += cur.value + " -> ";
         	cur = cur.next;
         }
         return s.substring(0, s.length()-4);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyLinkedList list1 = new MyLinkedList();
		list1.addAtTail(1);
		list1.addAtTail(8);
		list1.addAtTail(5);
		list1.addAtHead(2);
		list1.addAtHead(4);
		System.out.println(list1);
		list1.deleteAtIndex(2);
		System.out.println(list1);
//		list1.deleteAtIndex(4);
//		System.out.println(list1);
//		list1.deleteAtIndex(7);
//		System.out.println(list1);
		list1.addAtIndex(0, 1);
		System.out.println(list1);
		list1.addAtIndex(2, 9);
		System.out.println(list1);
		list1.addAtIndex(6, 2);
		System.out.println(list1);
		list1.addAtIndex(10, 3);
		System.out.println(list1);
		list1.addAtIndex(-10, 3);
		System.out.println(list1);
	}

	class ListNode {
		int value;
		ListNode next;
		
		ListNode(int myv){
			value = myv;
			next = null;
		}
	}
}
