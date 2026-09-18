public class Task3 {
    
    static class Node {
        int data;
        Node next;
        
        Node(int data) { this.data = data; }
    }

    static class MyStack {
        private Node top;

        public void push(int data) {
            Node newNode = new Node(data);
            newNode.next = top;
            top = newNode;
        }

        public int pop() {
            if (top == null) {
                throw new RuntimeException("Stack is empty");
            }
            int val = top.data;
            top = top.next;
            return val;
        }
    }

    public static void main(String[] args) {
        MyStack stack = new MyStack();
        
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Popped: " + stack.pop()); 
        System.out.println("Popped: " + stack.pop()); 
        System.out.println("Popped: " + stack.pop()); 
    }
}