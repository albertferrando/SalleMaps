package utils;

import model.Connexio;

public class List {
    private Node cap;
    private int numNodes;

    List(Object dat) {
        cap = new Node(dat);
    }

    void add(Object dat) {
        Node temp = cap;
        while(temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node(dat);
        numNodes++;
    }

    void setAtIndex(int index, Object dat) {
        if(index != -1) {
            Node temp = cap;
            for (int i = 1; i < index; i++) {
                temp = temp.next;
            }
            Connexio c = ((Connexio)dat);
            ((Connexio)temp.next.getData()).setDistance(c.getDistance());
            ((Connexio)temp.next.getData()).setDuration(c.getDuration());
        } else {
            System.out.println("Invalid index.");
        }
    }

    public void deleteAtIndex(int index) {
        Node temp = cap;
        for(int i=0; i< index - 1 && temp.next != null; i++) {
            temp = temp.next;
        }
        temp.next = temp.next.next;
        numNodes--;
    }

    public int get(Node n) {
        Node t = cap;
        int index = 0;
        while(t != n) {
            index++;
            t = t.next;
        }
        return index;
    }

    Object get(int index) {
        Node temp = cap;
        if(index != -1) {
            for (int i = 0; i < index; i++) {
                if(temp.next != null) {
                    temp = temp.next;
                }
            }
        } else {
            System.out.println("Invalid index.");
        }
        return temp.getData();
    }

    public void printList() {
        Node temp = cap;
        while(temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    int getSize() {
        return numNodes;
    }

    class Node {
        private Node next;
        private Object data;
        Node(Object dat) {
            data = dat;
        }
        Object getData()
        {
            return data;
        }
    }
}
