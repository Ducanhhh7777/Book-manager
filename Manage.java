/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmanage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class Manage {

    Scanner sc = new Scanner(System.in);
    Node head, tail;

    boolean isEmpty() {
        return head == null;
    }

    void clear() {
        head = tail = null;
    }

    public Manage(String url) {
        head = tail = null;
        loadData(url);
    }

    public void loadData(String url) {
        try {
            File f = new File(url);
            if (!f.exists()) {
                System.out.println("File not exists");
                return;
            }
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String s[] = line.split(",");
                 int id = Integer.parseInt(s[0]);
                int quantity = Integer.parseInt(s[2]);
                double price = Double.parseDouble(s[3]);
                addLast(id, s[1], quantity, price);
            }
            fr.close();
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void addLast(int id, String name, int quantity, double price) {
        Book x = new Book(id, name, quantity, price);
        if (isEmpty()) {
            head = tail = new Node(x, null);
        } else {
            Node p = new Node(x, null);
            tail.next = p;
            tail = p;
        }
    }

    public void display(String url) {
        head = tail = null;

        loadData(url);
        Node tmp = head;
        System.out.println("=============Book Manage=============");
        while (tmp != null) {
            System.out.println(tmp.info.toString());
            tmp = tmp.next;
        }
    }

    public void search() {
        System.out.print("Enter ID:");
        int id = sc.nextInt();
        boolean check = true;
        Node tmp = head;
        while (tmp != null) {
            if (tmp.info.getId()==id) {
                check = false;
                System.out.println(tmp.info.toString());
                return;
            }
            tmp = tmp.next;
        }
        if (check) {
            System.out.println("Not found id");
        }
    }

    public void saveData(String url) {
        try {
            File f = new File(url);
            if (!f.exists()) {
                return;
            }
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            Node tmp = head;
            while (tmp != null) {
                pw.println(tmp.info.getId() + "," + tmp.info.getName() + "," + tmp.info.getQuantity() + "," + tmp.info.getPrice());
                tmp = tmp.next;
            }
            fw.close();
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

public void add(int mode) {
    int id;
    while (true) {
        Node tmp = head;
        System.out.print("Enter ID:");
        id = sc.nextInt();
        
        boolean check = true;
        while (tmp != null) {
            if (tmp.info.getId()==id) {
                check = false;
                break;
            }
            tmp = tmp.next;
        }
        
        if (check) {
            break;
        } else {
            System.out.println("ID already exists. Please enter a different ID.");
        }
    }
    sc.nextLine(); 
    System.out.print("Enter Name:");
    String name = sc.nextLine().trim(); 
    if (name.isEmpty()) {
        System.out.println("Name cannot be empty. Aborting addition.");
        return;
    }
    int quantity;
    while (true) {
        System.out.print("Enter quantity:");
        try {
            quantity = Integer.parseInt(sc.nextLine().trim());
            if (quantity < 0) {
                throw new NumberFormatException(); 
            }
            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Quantity must be a non-negative integer.");
        }
    }
    double price;
    while (true) {
        System.out.print("Enter price:");
        try {
            price = Double.parseDouble(sc.nextLine().trim()); 
            if (price < 0) {
                throw new NumberFormatException(); 
            }
            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Price must be a non-negative number.");
        }
    }
    if (mode == 1) {
        addLast(id, name, quantity, price);
    } else if (mode == 2) {
        addFirst(id, name, quantity, price);
    } else if (mode == 3) {
        int k;
           while (true) {
                System.out.print("Position:");
                k = sc.nextInt();
                if (k <= 0) {
                    System.out.println("Position must be a positive integer.");
                } else {
                    break;
                }
            }
            addAfter(k, new Book(id, name, quantity, price));
        }
    }
    public void delete() {
        System.out.print("Enter ID:");
        int id = sc.nextInt();
        Node p = head;
        Node q = null;
        boolean check = false;
        while (p != null) {
            if (p.info.getId() == id) {
                check = true;
                break;
            }
            q = p;
            p = p.next;
        }
        if (!check) {
            System.out.println("ID not found");
            return;
        }

         if (isEmpty()) {
            System.out.println("The list has no elements");
            return;
        }

        if (q == null) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            q.next = p.next;
            if (q.next == null) {
                tail = q;
            }
        }
    }


    public Node get(int k) {
        Node p = head;
        int c = 0;
        while (p != null && c < k) {
            c++;
            p = p.next;
        }
        return p;
    }

    public void sort(String url) {
        head = tail = null;

        loadData(url);
        int n = 0;
        Node t = head;
        while (t != null) {
            n++;
            t = t.next;
        }
        for (int i = 0; i < n-1 ; i++) {
            for (int j = i + 1; j <n; j++) {
                Node pi = get(i), pj = get(j);
                if (pi.info.getId()>pj.info.getId()) {
                    Book b = pi.info;
                    pi.info = pj.info;
                    pj.info = b;
                }
            }
        }
        
        Node tmp = head;
        System.out.println("=============Book Manage=============");
        while (tmp != null) {
            System.out.println(tmp.info.toString());
            tmp = tmp.next;
        }
    }

    public void addFirst(int id, String name, int quantity, double price) {
        Book x = new Book(id, name, quantity, price);
        if (isEmpty()) {
            head = tail = new Node(x, null);
        } else {
            Node p = new Node(x, null);
            p.next = head;
            head = p;
        }
    }

    void addAfter(int k, Book x) {
       if (k <= 0) {
        System.out.println("Position must be a positive integer.");
        return;
    }

    Node p = head;
    int cnt = 0;
    while (p != null) {
        cnt++;
        if (cnt == k) {
            break;
        }
        p = p.next;
    }
    if (p == null) {
        System.out.println("Position not found in the list.");
        return;
    }

    Node new_node = new Node(x, null);
    new_node.next = p.next;
    p.next = new_node;
}
  

    public void deletepostion(String url) {
        head = tail = null;
    loadData(url);
    Node tmp = head;
    int n = 0;
    while (tmp != null) {
        n++;
        tmp = tmp.next;
    }
    if (n == 0) {
        System.out.println("The list has no elements.");
        return;
    }

    System.out.print("Enter position: ");
    int k = sc.nextInt();
    if (k <= 0 || k > n) {
        System.out.println("Invalid position.");
        return;
    }

    if (k == 1) {
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return;
    }

    int count = 1;
    Node prev = null, curr = head;
    while (count != k) {
        prev = curr;
        curr = curr.next;
        count++;
    }

    prev.next = curr.next;
    if (curr == tail) {
        tail = prev;
    }
}
}
