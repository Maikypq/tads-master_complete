package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class NodeDE {
    private Kid data;
    private Node next;
    private Node prev;

    public NodeDE(Kid data, Node prev, Node next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}