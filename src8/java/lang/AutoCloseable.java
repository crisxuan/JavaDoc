/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * An object that may hold resources (such as file or socket handles)
 * until it is closed. The {@link #close()} method of an {@code AutoCloseable}
 * object is called automatically when exiting a {@code
 * try}-with-resources block for which the object has been declared in
 * the resource specification header. This construction ensures prompt
 * release, avoiding resource exhaustion exceptions and errors that
 * may otherwise occur.
 * 实现了此接口的类能够持有资源直到被关闭的时候。其中的close()方法是自动关闭的，当
 * 离开try-with-resources中的try 语句块的时候。这种方式确保了能够即使释放资源，
 * 避免资源的枯竭和可能出现的错误
 *
 * @apiNote
 * <p>It is possible, and in fact common, for a base class to
 * implement AutoCloseable even though not all of its subclasses or
 * instances will hold releasable resources.  For code that must operate
 * in complete generality, or when it is known that the {@code AutoCloseable}
 * instance requires resource release, it is recommended to use {@code
 * try}-with-resources constructions. However, when using facilities such as
 * {@link java.util.stream.Stream} that support both I/O-based and
 * non-I/O-based forms, {@code try}-with-resources blocks are in
 * general unnecessary when using non-I/O-based forms.
 * 对于基类来说这是可能的，事实上也是常见的，实现了AutoCloseable接口，即使不是它的子类
 * 或实例将拥有可释放的资源。
 *
 *
 * @author Josh Bloch
 * @since 1.7
 */
public interface AutoCloseable {
    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     * 关闭这个资源，舍弃任何潜在的资源，这个方法是自动被调用的在声明
     * try-with-resources块中
     *
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     * 当这个接口方法被声明时，被抛出异常，强调具体的close()方法时，抛出更具体的异常，
     * 若果关闭操作成功，则没有任何异常抛出。
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     *
     *
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * 实现了此接口的类强烈建议不要抛出InterruptedException异常
     *
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * 这个异常与线程的中断状态相关联，如果 Throwable.addSuppressed 抑制了
     * InterruptedException ， 那么可能会出现运行时错误。
     *
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     * 一般来说，如果一个异常被抑制了(那么这个close方法不应该抛出该异常)
     *
     * <p>Note that unlike the {@link java.io.Closeable#close close}
     * method of {@link java.io.Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * 请注意：这个方法与java.io.Closeable中close()方法并不相同，该方法不要求是幂等的。
     * 换句话说，多次调用该方法可能出现不同的效果，而Closeable.close()方法，
     * 多次调用和一次调用效果是一致的。
     *
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     * 然而，强烈要求实现者使用该方法接近幂等。
     *
     * @throws Exception if this resource cannot be closed
     */
    void close() throws Exception;
}
