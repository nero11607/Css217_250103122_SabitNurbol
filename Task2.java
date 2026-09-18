public class Task2 {
    
    static class Node {
        int data;
        Node next;
        
        Node(int data) { 
            this.data = data; 
        }
    }
    public static Node rotateLeft(Node head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        Node tail = head;
        int len = 1;
        while (tail.next != null) {
            tail = tail.next;
            len++;
        }

        k = k % len;
        if (k == 0) return head;

    
        tail.next = head;

        Node newTail = head;
        for (int i = 1; i < k; i++) {
            newTail = newTail.next;
        }

        Node newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    public static void printList(Node head) {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + (curr.next != null ? " -> " : " -> NULL\n"));
            curr = curr.next;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(10);
        head.next = new Node(20);
        head.next.next = new Node(30);
        head.next.next.next = new Node(40);
        head.next.next.next.next = new Node(50);

        int k = 4;
        System.out.print("Input (k = " + k + "): ");
        printList(head);

        Node rotated = rotateLeft(head, k);
        
        System.out.print("Output:          ");
        printList(rotated);
    }
}