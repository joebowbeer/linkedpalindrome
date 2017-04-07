package com.joebowbeer.linkedpalindrome;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A singly-linked list representing a character sequence.
 * <p>
 * Note: <code>Node</code>'s <i>natural ordering is inconsistent with equals</i>.
 */
class Node implements Comparable<Node>, Iterable<Node> {
  final char ch;
  Node link;

  Node(char ch) {
    this.ch = ch;
  }

  static Node fromString(String s) {
    if (s.isEmpty()) {
      return null;
    }
    Node node = new Node(s.charAt(0));
    Node prev = node;
    for (char ch : s.substring(1).toCharArray()) {
      prev.link = new Node(ch);
      prev = prev.link;
    }
    return node;
  }

  String asString() {
    StringBuilder sb = new StringBuilder();
    for (Node next : this) {
      sb.append(next.ch);
    }
    return sb.toString();
  }

  int length() {
    int numNodes = 0;
    for (Node node: this) {
      numNodes++;
    }
    return numNodes;
  }

  Node nth(int n) {
    if (n < 0) {
      throw new IllegalArgumentException(String.valueOf(n));
    }
    for (Node node: this) {
      if (n-- == 0) {
        return node;
      }
    }
    throw new NoSuchElementException();
  }

  Node reverse() {
    Node prev = null;
    for (Node node = this; node != null; ) {
        Node next = node.link;
        node.link = prev;
        prev = node;
        node = next;
    }
    return prev;
  }

  boolean isPalindrome() {
    int length = length();
    if (length < 2) {
      return true;
    }
    Node left = nth(length / 2 - 1); // zero-based index
    Node center = left.link;
    // split at center and reverse left half
    left.link = null;
    reverse();
    // skip over odd center node if necessary 
    Node right = ((length & 1) == 0) ? center : center.link;
    // compare left and right
    boolean result = left.compareTo(right) == 0;
    // restore left half and return result
    left.reverse();
    left.link = center;
    return result;
  }

  @Override
  public int compareTo(Node node) {
    Iterator<Node> iter = iterator();
    Iterator<Node> other = node.iterator();
    while (iter.hasNext() && other.hasNext()) {
      int result = Character.compare(iter.next().ch, other.next().ch);
      if (result != 0) {
        return result;
      }
    }
    if (iter.hasNext()) {
      return 1;
    }
    if (other.hasNext()) {
      return -1;
    }
    return 0;
  }

  @Override
  public Iterator<Node> iterator() {
    return new NodeIterator(this);
  }

  private static class NodeIterator implements Iterator<Node> {
    private Node next;

    NodeIterator(Node head) {
      this.next = head;
    }

    @Override
    public boolean hasNext() {
      return next != null;
    }

    @Override
    public Node next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Node node = next;
      next = next.link;
      return node;
    }
  }
}
