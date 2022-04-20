/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * This exception may be thrown by methods that have detected concurrent
 * modification of an object when such modification is not permissible.
 * 当不允许对集合类进行并发修改时会检测到该异常
 *
 * <p>
 * For example, it is not generally permissible for one thread to modify a Collection
 * while another thread is iterating over it.  In general, the results of the
 * iteration are undefined under these circumstances.  Some Iterator
 * implementations (including those of all the general purpose collection implementations
 * provided by the JRE) may choose to throw this exception if this behavior is
 * detected.  Iterators that do this are known as <i>fail-fast</i> iterators,
 * as they fail quickly and cleanly, rather that risking arbitrary,
 * non-deterministic behavior at an undetermined time in the future.
 * 例如，通常不允许一个线程修改 Collection 及其实现类的同时，另一个线程对其进行修改。通常，在这种
 * 情况下，迭代的结果是不确定的。一些迭代器实现类可能会选择抛出这个异常来避免这种问题，这种机制也就是 fail-fast 机制
 * 因为他们能干净利落的终止程序运行，以防止程序出现错误的结果。
 *
 * <p>
 * Note that this exception does not always indicate that an object has
 * been concurrently modified by a <i>different</i> thread.  If a single
 * thread issues a sequence of method invocations that violates the
 * contract of an object, the object may throw this exception.  For
 * example, if a thread modifies a collection directly while it is
 * iterating over the collection with a fail-fast iterator, the iterator
 * will throw this exception.
 * 注意这个异常不仅仅会表明对象由不同的线程并发修改才会抛出。如果一个单个线程违反了对象所约定
 * 的序列，也会抛出这个异常。例如，如果一个线程在遍历集合的过程中直接修改了集合类，那么也会
 * 造成 fail-fast。
 *
 * <p>Note that fail-fast behavior cannot be guaranteed as it is, generally
 * speaking, impossible to make any hard guarantees in the presence of
 * unsynchronized concurrent modification.  Fail-fast operations
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>{@code ConcurrentModificationException}
 * should be used only to detect bugs.</i>
 *
 * @author  Josh Bloch
 * @see     Collection
 * @see     Iterator
 * @see     Spliterator
 * @see     ListIterator
 * @see     Vector
 * @see     LinkedList
 * @see     HashSet
 * @see     Hashtable
 * @see     TreeMap
 * @see     AbstractList
 * @since   1.2
 */
public class ConcurrentModificationException extends RuntimeException {
    private static final long serialVersionUID = -3666751008965953603L;

    /**
     * Constructs a ConcurrentModificationException with no
     * detail message.
     */
    public ConcurrentModificationException() {
    }

    /**
     * Constructs a {@code ConcurrentModificationException} with the
     * specified detail message.
     *
     * @param message the detail message pertaining to this exception.
     */
    public ConcurrentModificationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}.
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.7
     */
    public ConcurrentModificationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A {@code null} value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since 1.7
     */
    public ConcurrentModificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
