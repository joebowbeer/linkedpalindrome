package com.joebowbeer.linkedpalindrome;

import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NodeTest {

  @Rule
  public ExpectedException exceptionGrabber = ExpectedException.none();

  @Test
  public void testIterator() {
    Node head = new Node('0');
    Node tail = new Node('1');
    head.link = tail;
    Iterator<Node> iter = head.iterator();
    assertThat(iter.hasNext(), is(true));
    assertThat(iter.next(), is(sameInstance(head)));
    assertThat(iter.hasNext(), is(true));
    assertThat(iter.next(), is(sameInstance(tail)));
    assertThat(iter.hasNext(), is(false));
    exceptionGrabber.expect(NoSuchElementException.class);
    iter.next();
  }

  @Test
  public void testLength() {
    Node node = new Node('a');
    assertThat(node.length(), is(1));
    node.link = new Node('b');
    assertThat(node.length(), is(2));
  }

  @Test
  public void testNth() {
    Node head = new Node('0');
    Node tail = new Node('1');
    head.link = tail;
    assertThat(head.nth(0), is(sameInstance(head)));
    assertThat(tail.nth(0), is(sameInstance(tail)));
    assertThat(head.nth(1), is(sameInstance(tail)));
  }

  @Test
  public void testNthIllegalArgument() {
    Node node = new Node('0');
    exceptionGrabber.expect(IllegalArgumentException.class);
    node.nth(-1);
  }

  @Test
  public void testNthNoSuchElement() {
    Node node = new Node('0');
    exceptionGrabber.expect(NoSuchElementException.class);
    node.nth(1);
  }

  @Test
  public void testFromString() {
    assertThat(Node.fromString(""), is(nullValue()));
    Node result = Node.fromString("abc");
    assertThat(result.length(), is(3));
    assertThat(result.nth(0).ch, is('a'));
    assertThat(result.nth(1).ch, is('b'));
    assertThat(result.nth(2).ch, is('c'));
  }

  @Test
  public void testAsString() {
    Node node = new Node('a');
    assertThat(node.asString(), is("a"));
    node.link = new Node('b');
    assertThat(node.asString(), is("ab"));
  }

  @Test
  public void testCompareTo() {
    assertThat(Node.fromString("a"), comparesEqualTo(Node.fromString("a")));
    assertThat(Node.fromString("a"), is(lessThan(Node.fromString("b"))));
    assertThat(Node.fromString("b"), is(greaterThan(Node.fromString("a"))));
    assertThat(Node.fromString("a"), is(lessThan(Node.fromString("aa"))));
    assertThat(Node.fromString("aa"), is(greaterThan(Node.fromString("a"))));
  }

  @Test
  public void testReverse() {
    Node node = new Node('a');
    Node reverse = node.reverse();
    assertThat(reverse, is(sameInstance(node)));
    assertThat(reverse.asString(), is("a"));
    node.link = new Node('b');
    reverse = node.reverse();
    assertThat(reverse.asString(), is("ba"));
    assertThat(reverse.reverse(), is(sameInstance(node)));
    assertThat(node.asString(), is("ab"));
  }

  @Test
  public void testIsPalindrome() {
    assertThat(Node.fromString("a").isPalindrome(), is(true));
    assertThat(Node.fromString("ab").isPalindrome(), is(false));
    assertThat(Node.fromString("bb").isPalindrome(), is(true));
    assertThat(Node.fromString("abc").isPalindrome(), is(false));
    assertThat(Node.fromString("aba").isPalindrome(), is(true));
    assertThat(Node.fromString("abba").isPalindrome(), is(true));
    assertThat(Node.fromString("baba").isPalindrome(), is(false));
  }
}
