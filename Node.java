/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmanage;

/**
 *
 * @author admin
 */
public class Node {
     Book info;
    Node next;
    
    public Node() {
    }

    public Node(Book info, Node next) {
        this.info = info;
        this.next = next;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Node) {
            Node p2 = (Node)obj;
            if (p2.info == this.info) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    
}
