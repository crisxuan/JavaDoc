/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util;

/**
 * A collection designed for holding elements prior to processing.
 * Besides basic {@link java.util.Collection Collection} operations,
 * queues provide additional insertion, extraction, and inspection
 * operations.  Each of these methods exists in two forms: one throws
 * an exception if the operation fails, the other returns a special
 * value (either {@code null} or {@code false}, depending on the
 * operation).  The latter form of the insert operation is designed
 * specifically for use with capacity-restricted {@code Queue}
 * implementations; in most implementations, insert operations cannot
 * fail.
 * 设计用来在处理之前保持元素的访问次序。
 * 除了Collection基础的操作之外，队列提供了额外的插入，读取，检查操作。
 * 这些当中每一个方法都会有两种形式：如果失败就抛出异常，其他方式返回特殊的值(null 或者 false)，
 * 后者组成的插入操作被设计成用来严格控制队列容量的实现；在大部分的实现中，插入操作不能直接fail。
 *
 *
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 * <caption>Summary of Queue methods</caption>
 * 总结几个Queue方法
 *  <tr>
 *    <td></td>
 *    <td ALIGN=CENTER><em>Throws exception</em></td>
 *    <td ALIGN=CENTER><em>Returns special value</em></td>
 *    第一个抛出异常。
 *    第二个返回特殊值。
 *  </tr>
 *  <tr>
 *    <td><b>Insert</b></td>
 *    <td>{@link Queue#add add(e)}</td>
 *    <td>{@link Queue#offer offer(e)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Remove</b></td>
 *    <td>{@link Queue#remove remove()}</td>
 *    <td>{@link Queue#poll poll()}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Examine</b></td>
 *    <td>{@link Queue#element element()}</td>
 *    <td>{@link Queue#peek peek()}</td>
 *  </tr>
 * </table>
 *
 * <p>Queues typically, but do not necessarily, order elements in a
 * FIFO (first-in-first-out) manner.  Among the exceptions are
 * priority queues, which order elements according to a supplied
 * comparator, or the elements' natural ordering, and LIFO queues (or
 * stacks) which order the elements LIFO (last-in-first-out).
 * Whatever the ordering used, the <em>head</em> of the queue is that
 * element which would be removed by a call to {@link #remove() } or
 * {@link #poll()}.  In a FIFO queue, all new elements are inserted at
 * the <em>tail</em> of the queue. Other kinds of queues may use
 * different placement rules.  Every {@code Queue} implementation
 * must specify its ordering properties.
 * 队列通常（但不一定）以FIFO（先进先出）方式对元素进行排序。PriorityQueues 就是一个特例。
 * 它的元素的顺序是遵从提供的比较器，或者元素的自然排序，以及对元素进行排序的LIFO队列（或堆栈）（后进先出）
 * 不论使用顺序如何，调用remove()或者poll()都会移除队列的头元素。
 * 在FIFO队列中，所有新添加的元素都会插入到队列的末尾。
 * 其他类型的队列可能使用不同的放置规则。每个Queue的实现都必须指定其排序属性。
 *
 * <p>The {@link #offer offer} method inserts an element if possible,
 * otherwise returning {@code false}.  This differs from the {@link
 * java.util.Collection#add Collection.add} method, which can fail to
 * add an element only by throwing an unchecked exception.  The
 * {@code offer} method is designed for use when failure is a normal,
 * rather than exceptional occurrence, for example, in fixed-capacity
 * (or &quot;bounded&quot;) queues.
 * offer方法会插入一个元素在允许的情况下，否则返回false。
 * 这与Collection.add 方法不同，后者只能通过抛出未经检查的异常来添加元素
 * offer方法被设计成，添加失败是一个正常的情况，而不是异常的出现。
 * 例如，在固定容量("有界")队列中。
 *
 * <p>The {@link #remove()} and {@link #poll()} methods remove and
 * return the head of the queue.
 * Exactly which element is removed from the queue is a
 * function of the queue's ordering policy, which differs from
 * implementation to implementation. The {@code remove()} and
 * {@code poll()} methods differ only in their behavior when the
 * queue is empty: the {@code remove()} method throws an exception,
 * while the {@code poll()} method returns {@code null}.
 * remove()和poll()方法移除并返回队列的头元素
 * 确切地说，从队列中删除哪个元素是队列排序策略的一个功能，它与实现不同。
 * remove()和poll()方法不同的地方在于： remove()方法在队列为null时抛出异常
 * 而poll()方法则返回null。
 *
 * <p>The {@link #element()} and {@link #peek()} methods return, but do
 * not remove, the head of the queue.
 * element()方法和peek()方法返回，但不移除队列的头元素。
 *
 * <p>The {@code Queue} interface does not define the <i>blocking queue
 * methods</i>, which are common in concurrent programming.  These methods,
 * which wait for elements to appear or for space to become available, are
 * defined in the {@link java.util.concurrent.BlockingQueue} interface, which
 * extends this interface.
 * Queue接口没有定义 blocking queue方法，这在并发编程中很常见。这些等待元素出现
 * 或者空间可用的方法在BlockingQueue 接口中定义，该接口扩展了此接口。
 *
 * <p>{@code Queue} implementations generally do not allow insertion
 * of {@code null} elements, although some implementations, such as
 * {@link LinkedList}, do not prohibit insertion of {@code null}.
 * Even in the implementations that permit it, {@code null} should
 * not be inserted into a {@code Queue}, as {@code null} is also
 * used as a special return value by the {@code poll} method to
 * indicate that the queue contains no elements.
 * Queue的实现不允许插入null元素，即使一些实现，像是LinkedList，没有限制插入空元素。
 * 即使实现允许，null元素也不应该插入到队列中，因为null也被poll方法用作特殊的返回值。
 * 以指示队列包含输任何的返回值。
 *
 * <p>{@code Queue} implementations generally do not define
 * element-based versions of methods {@code equals} and
 * {@code hashCode} but instead inherit the identity based versions
 * from class {@code Object}, because element-based equality is not
 * always well-defined for queues with the same elements but different
 * ordering properties.
 * Queue实现大体上没有定义基于元素的方法版本equals和hashCode，相反的而是继承了
 * Object类的版本，因为基于元素的相等对于具有相同元素但具有不同排序属性的队列，并不总是定义良好。
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @see java.util.Collection
 * @see LinkedList
 * @see PriorityQueue
 * @see java.util.concurrent.LinkedBlockingQueue
 * @see java.util.concurrent.BlockingQueue
 * @see java.util.concurrent.ArrayBlockingQueue
 * @see java.util.concurrent.LinkedBlockingQueue
 * @see java.util.concurrent.PriorityBlockingQueue
 * @since 1.5
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */
public interface Queue<E> extends Collection<E> {
    /**
     * Inserts the specified element into this queue if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * {@code true} upon success and throwing an {@code IllegalStateException}
     * if no space is currently available.
     *
     * @param e the element to add
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws IllegalStateException if the element cannot be added at this
     *         time due to capacity restrictions
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null and
     *         this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this queue
     */
    boolean add(E e);

    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions.
     * When using a capacity-restricted queue, this method is generally
     * preferable to {@link #add}, which can fail to insert an element only
     * by throwing an exception.
     *
     * @param e the element to add
     * @return {@code true} if the element was added to this queue, else
     *         {@code false}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null and
     *         this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this queue
     */
    boolean offer(E e);

    /**
     * Retrieves and removes the head of this queue.  This method differs
     * from {@link #poll poll} only in that it throws an exception if this
     * queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    E remove();

    /**
     * Retrieves and removes the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    E poll();

    /**
     * Retrieves, but does not remove, the head of this queue.  This method
     * differs from {@link #peek peek} only in that it throws an exception
     * if this queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    E element();

    /**
     * Retrieves, but does not remove, the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    E peek();
}
