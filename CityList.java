public class CityList {
    
    /**
     * Inner class to represent a node in the linked list
     */
	private class Node {
        int city;     // City index
        Node next;    // Pointer to the next node

        public Node(int city) {
            this.city = city;
            this.next = null;
        }
    }

    private Node head;   // Head of the list (first city)
    private int size;    // Number of nodes (cities) in the list

    /**
     * Constructor to initialise an empty CityList 
     */
    public CityList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Add a city to the end of the linked list
     * @param city The index of the city in the distance matrix
     */
    public void add(int city) {
        Node newNode = new Node(city);
        
        if (head == null) {
            head = newNode;  // If the list is empty, new node becomes the head
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;  // Add new node to the end of the list
        }
        size++;  // Increment the size
    }

    /**
     * Get the city at a specific index 
     * @param index
     * @return
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node current = head;
        int count = 0;

        // Traverse to the desired index
        while (current != null) {
            if (count == index) {
                return current.city;
            }
            count++;
            current = current.next;
        }

        // This point should never be reached due to the check above
        return -1;
    }

    /**
     * Remove the city at a specific index and return its value
     * @param index
     * @return
     */
    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node current = head;
        Node previous = null;

        if (index == 0) {
            int city = head.city;  // Get the city value at the head
            head = head.next;      // Remove the head by pointing to the next node
            size--;
            return city;
        }

        int count = 0;

        // Traverse to the node to remove
        while (current != null) {
            if (count == index) {
                previous.next = current.next;  // Bypass the node to remove it
                size--;
                return current.city;
            }
            previous = current;
            current = current.next;
            count++;
        }

        // This point should never be reached due to the check above
        return -1;
    }

    /**
     * Return the number of cities (size of the list)
     * essentially a getter method for a dependent attribute
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * 
     * @return is it empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clear the list (remove all cities)
     */
    public void clear() {
        head = null;  
        size = 0;     
    }

    /**
     * Print all cities in the list
     */
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.city + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}
