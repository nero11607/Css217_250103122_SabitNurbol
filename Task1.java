public class Task1 {
    static class Node {
        int data;
        Node next;
        Node prev;
        
        Node(int data) { 
            this.data = data; 
        }
    }

    public static Node reverseDLL(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node current = head;
        Node temp = null;

        while (current != null) {
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            current = current.prev;
        }
        return temp.prev;
    }
    public static void printList(Node head) {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + (curr.next != null ? " <-> " : " -> NULL\n"));
            curr = curr.next;
        }
    }
    public static void main(String[] args) {
        
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);

        n1.next = n2; n2.prev = n1;
        n2.next = n3; n3.prev = n2;

        System.out.print("Input:  ");
        printList(n1);

        Node reversedHead = reverseDLL(n1);
        
        System.out.print("Output: ");
        printList(reversedHead);
    }
}
