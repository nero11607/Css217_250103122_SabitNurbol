public class Task4 {
    
    static class Node {
        int data;
        Node next;
        
        Node(int data) { this.data = data; }
    }

    static class MyQueue {
        private Node front;
        private Node rear;

        public void enqueue(int data) {
            Node newNode = new Node(data);
            if (rear == null) {
                front = rear = newNode;
                return;
            }
            rear.next = newNode;
            rear = newNode;
        }

        public int dequeue() {
            if (front == null) {
                throw new RuntimeException("Queue is empty");
            }
            int val = front.data;
            front = front.next;
            
            if (front == null) {
                rear = null;
            }
            return val;
        }
    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        System.out.println("Dequeued: " + queue.dequeue()); 
        System.out.println("Dequeued: " + queue.dequeue()); 
        System.out.println("Dequeued: " + queue.dequeue()); 
    }
}
