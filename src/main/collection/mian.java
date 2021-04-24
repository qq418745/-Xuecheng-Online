package main.collection;

import java.util.*;


public class mian {
    public static void main(String[] args) {

    //实现一个HashMap
        Node[] table = new Node[16];

         table[4] = new Node("哇哈哈",new Node("哈哈哈",new Node(666, null)));

    }




}

class Node {
    public Node(Object item, Node next) {
        this.item = item;
        this.next = next;
    }

    Object item;
    Node next;
}
class Dog {
private  String name;

    public Dog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Book {
    private String name;
    private int  price;

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
