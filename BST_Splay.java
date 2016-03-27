import java.io.*;
import java.util.*;


class BST_Splay {
 public static void main (String[] args) {
    Scanner sc = new Scanner(new InputStreamReader(System.in));
    if (args.length == 0){
      System.out.println("Next command?");
      String cmd = sc.next();
      String assocData;

      bST bst_1 = new bST();
      while(cmd != "q") {
        switch (cmd) {
          case "new": bst_1 = new bST();
            break;
          case "i":
            if (bst_1 != null) {
              System.out.println("Enter String to insert");
              assocData = sc.next();
              bst_1.insert(assocData);
            }
            else {
              throw new RuntimeException("No cell found");
            }
            break;
          case "r":
            if (bst_1 == null) {
              throw new RuntimeException("No cell found");
            }
            else {
              System.out.println("Enter String to remove");
              assocData = sc.next();
              bst_1.remove(assocData);
            }
            break;
          case "c":
            System.out.println("Enter String");
            assocData = sc.next();
            System.out.println(bst_1.contains(assocData));
            break;
          case "g":
            System.out.println("Enter String");
            assocData = sc.next();
            System.out.println(bst_1.get(assocData));
            break;
          case "x":
            System.out.println(bst_1.findMax());
            break;
          case "n":
            System.out.println(bst_1.findMin());
            break;
          case "v":
            System.out.println(bst_1.val());
            break;
          case "e":
            System.out.println(bst_1.empty());
            break;
          case "s":
            System.out.println(bst_1.size());
            break;
          case "h":
            System.out.println(bst_1.height());
            break;
          case "q":
            return;
          case "p":
            bst_1.print();
            break;
          case "f":
            break;
        }
        System.out.println("Next command?");
        cmd = sc.next();
      }



    }
    else {
      String cmd;
      String assocData;
      int na = args.length;
      bST bst_1 = new bST();
      for (int i=0; i < na; i++) {
        cmd = args[i];

        switch (cmd) {
          case "new": bst_1 = new bST();
            break;
          case "i":
            if (bst_1 != null) {
              i++;
              bst_1.insert(args[i]);
            }
            else {
              throw new RuntimeException("No cell found");
            }
            break;
          case "r":
            if (bst_1 == null) {
              throw new RuntimeException("No cell found");
            }
            else {
              i++;
              bst_1.remove(args[i]);
            }
            break;
          case "c":
            i++;
            System.out.println("cONTAINS: " + bst_1.contains(args[i]));
            break;
          case "g":
            i++;
            System.out.println("GET: " + bst_1.get(args[i]));
            break;
          case "x":
            System.out.println("MAX: " + bst_1.findMax());
            break;
          case "n":
            System.out.println("MIN: " + bst_1.findMin());
            break;
          case "v":
            System.out.println("VAL: " + bst_1.val());
            break;
          case "e":
            System.out.println("EMPTY: " + bst_1.empty());
            break;
          case "s":
            System.out.println("SIZE: " + bst_1.size());
            break;
          case "h":
            System.out.println("HEIGHT: " + bst_1.height());
            break;
          case "q":
            break;
          case "p":
            bst_1.print();
            break;
          case "f":
            break;
        }
      }
    }
 }
}

class bST {
  BinCell root = null;
  int size = 0;

  public bST() {
    this.root = null;
    this.size = 0;
  }

  public void splay(BinCell x){
    // cell b, c for storing left child and right child of x
    BinCell b, c;

    // store parent, grandparent, and great-grandparent cells
    BinCell p = x.root;
    BinCell g = ( p == null ) ? null : p.root;
    BinCell gg = ( g == null ) ? null : g.root;

    // if no parent, no need to splay
    if (p == null) { return; }

    // zig pattern
    if (g == null) {
      if (p.getLeft() == x) {
        b = x.getRight();
        x.right = p;
        p.root = x;
        p.left = b;
        if (b!=null) { b.root=p; }
        x.root = null;
      } else {
        b = x.getLeft();
        x.left = p;
        p.root = x;
        p.right = b;
        if (b != null) { b.root = p; }
        x.root = null;
      }
      this.root = x;
      return;
    }

    if (x == p.getRight() && p == g.getRight()) {
      c = p.getLeft();
      b = x.getLeft();
      x.root = gg;
      if (gg != null) {
        if (gg.getLeft() == g) {
          gg.left = x;
        } else {
          gg.right = x;
        }
      }
      p.root = x;
      x.left = p;
      g.root = p;
      p.left = g;
      p.right = b;
      if (b != null) { b.root = p; }
      g.right = c;
      if (c != null) { c.root = g; }
    }
    else if (x==p.left && p==g.left) {  // zig-zig-L
      b = x.right; c = p.right; 
      x.root=gg; if (gg !=null) { if (gg.left==g) { gg.left=x; } else { gg.right=x; } }
      x.right=p; p.root=x;
      p.right=g; g.root=p; 
      g.left=c; if (c!=null) { c.root=g; }
      p.left=b; if (b!=null) { b.root=p; }
    }
    else if (x==p.left && p==g.left) {  // zig-zig-L
      b=x.right; c=p.right; 
      x.root=gg; if (gg!=null) { if (gg.left==g) { gg.left=x; } else { gg.right=x; } }
      x.right=p; p.root=x;
      p.right=g; g.root=p; 
      g.left=c; if (c!=null) { c.root=g; }
      p.left=b; if (b!=null) { b.root=p; }
    }
    else if (x==p.right && p==g.left) {  // zig-zag-L
      b=x.left; c=x.right; 
      x.root=gg; if (gg!=null) { if (gg.left==g) { gg.left=x; } else { gg.right=x; } }
      x.left=p; p.root=x; 
      x.right=g; g.root=x;
      g.left=c; if (c!=null) { c.root=g; }
      p.right=b; if (b!=null) { b.root=p; }
    } 
    else if (x==p.left && p==g.right) {  // zig-zag-R
      b=x.left; c=x.right; 
      x.root=gg; 
      if (gg!=null) { 
        if (gg.left==g) { gg.left=x; } else { gg.right=x; } 
      }
      x.left=g; g.root=x;
      x.right=p; p.root=x;
      g.right=b; if (b!=null) { b.root=g; }
      p.left=c; if (c!=null) { c.root=p; }
    } 
    else { 
      System.out.println("Impossible operation.");
      return; 
    }
      
    // are we done?  is x at the root?
    if (x.root ==null) { this.root=x; return; }
    
    // not done, so recurse... keep splaying
    this.splay(x);  
  }

  public void print(){
    if(this.root != null) {
      this.print_r(this.root, 0);
    }
  }

  public void print_r( BinCell rn, int ind ) {
    if (rn == null) {
      System.out.println("");
      for(int i = 0; i < ind; i++){
        System.out.print("          ");
      }
      return;
    }

    this.print_r( rn.getRight(), ind + 1 );

    System.out.println("");
    for(int i = 0; i < ind; i++){
      System.out.print("          ");
    }
    System.out.print(rn.getKey());

    this.print_r( rn.getLeft(), ind + 1 );
  }


  public void insert(String elt) {
    BinCell c = new BinCell(elt, null, null, null);

    if (this.root == null) {
      this.root = c;
      this.size++;
      return;
    } 

    BinCell parent, current = this.root;
    
    while (true) {
      parent = current;

      if (elt.toLowerCase().compareTo(parent.getKey().toLowerCase()) < 0) {
        current = current.getLeft();
        if (parent.getLeft() == null) {
          parent.left = c;
          c.root = parent;
          this.size++;
          splay(c);
          return;
        }
      } 
      else if (elt.toLowerCase().compareTo(parent.getKey().toLowerCase()) == 0) {
        return;
      }
      else {
        current = current.getRight();
        if(parent.getRight() == null) {
          parent.right = c;
          c.root = parent;
          this.size++;
          splay(c);
          return;
        }
      }
    }
  }

  public void remove( String elt ) {

    if (this.root == null) { throw new RuntimeException("No cell found"); }
    if (this.size() == 1){
      if (this.root.getKey().equals(elt)) { 
        this.root = null;
        this.size--;
        return;
      }
      else { return; }
    }
    this.remove_r(this.root, null, elt);

  }

  public void remove_r( BinCell rn, BinCell pn, String elt) {
    if (rn == null) { 
      return;
    }
    if (rn.getKey().compareTo(elt) > 0) { 
      this.remove_r(rn.getLeft(), rn, elt);
      return; 
    }
    if (rn.getKey().compareTo(elt) < 0) { 
      this.remove_r(rn.getRight(), rn, elt); 
      return;
    }

    if (rn.getLeft() == null && rn.getRight() == null && pn != null){
      if(pn.getLeft() == rn) { 
        pn.left = null;
        this.size--;
        splay(pn);
        return;
      }
      else { 
        pn.right = null; 
        this.size--;
        splay(pn);
        return;
      }
    }

    if (rn.getLeft() != null && rn.getRight() != null) {
      String temp = this.findMin_r(rn.getRight());
      this.remove_r(rn.getRight(), rn, temp);
      rn.key = temp;
      this.size--;
      return;
    }

    if (rn.getLeft() != null && rn.getRight() == null) {
      if (pn != null) {
        if (pn.getLeft() == rn) {
          pn.left = rn.getLeft();
          rn.getLeft().root = pn;
          this.size--;
          splay(pn);
          return;
        }
        else {
          pn.right = rn.getLeft();
          rn.getLeft().root = pn;
          this.size--;
          splay(pn);
          return;
        }
      } 
    }
    else {
      if (pn != null) {
        if (pn.getLeft() == rn) {
          pn.left = rn.getRight();
          rn.getRight().root = pn;
          this.size--;
          splay(pn);
          return;
        }
        else {
          pn.right = rn.getRight();
          rn.getRight().root = pn;
          this.size--;
          splay(pn);
          return;
        }
      }
    } 
  }

  public String findMin() {
    BinCell current = this.root;

    while(true) {
      if (current.getLeft() != null) {
        current = current.getLeft();
      } else {
        splay(current);
        return current.getKey();
      }
    }
  }

  public String findMin_r( BinCell rn ) {
    if(rn.getLeft() == null) {
      splay(rn); 
      return rn.getKey(); 
    }
    return this.findMin_r(rn.getLeft());
  }

  public String findMax() {
    BinCell parent = this.root;
    BinCell current = this.root;

    while(true) {
      if (parent.getRight() != null) {
        if (current.right != null) {
          current = current.getRight();
        }
        else {
          splay(current);
          return current.getKey();
        }
      } else {
        splay(current);
        return parent.getKey();
      }
    }

  }

  public boolean contains(String elt) {

    if (this.root == null) {
      return false;
    } 

    BinCell parent, current = this.root;
    
    while (true) {
      parent = current;

      if ( elt.equals(parent.getKey()) ) {
        splay(parent);
        return true;
      }

      if (elt.compareTo(parent.getKey()) < 0) {
        current = current.getLeft();
        if (parent.getLeft() == null) {
          return false;
        }
      } else {
        current = current.getRight();
        if (parent.getRight() == null) {
          return false;
        }
      }
    }

  }

  public BinCell get(String elt) {
    if (this.root == null) {
      throw new RuntimeException("No cell found");
    } 

    BinCell parent, current = this.root;
    
    while (true) {
      parent = current;

      if ( elt.equals(parent.getKey()) ) {
        return parent;
      }

      if (elt.compareTo(parent.getKey()) < 0) {
        current = current.getLeft();
        if (parent.getLeft() == null) {
          throw new RuntimeException("No cell found");
        }
      } else {
        current = current.getRight();
        if (parent.getRight() == null) {
          throw new RuntimeException("No cell found");
        }
      }
    }
  }

  public String val() {
    return this.root.getKey();
  }

  public int size() {
    return this.size;
  }

  public boolean empty() {
    if (this.root == null) {
      return true; 
    }
    else { return false; }
  }

  public int height() {
    if (this.root == null) {
      return 0;
    } else {
      return 1 + Math.max(height_r(this.root.left), height_r(this.root.right));
    }
  }

  public int height_r(BinCell b) {
    if (b == null) {
        return 0;
    } else {
      return 1 + Math.max(height_r(b.left), height_r(b.right));
    }
  }

}

class BinCell {
  String key;
  BinCell left, right, root;

  public BinCell(String key, BinCell left, BinCell right, BinCell parent){
    this.key = key;
    this.left = left;
    this.right = right;
    this.root = root;
  }

  String getKey() { return this.key; }
  BinCell getLeft() { return this.left; }
  BinCell getRight() { return this.right; }

}