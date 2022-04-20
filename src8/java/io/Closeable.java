/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import java.io.IOException;

/**
 * A {@code Closeable} is a source or destination of data that can be closed.
 * The close method is invoked to release resources that the object is
 * holding (such as open files).
 * Closeable 表示一个资源或者数据能够被关闭
 * close 方法被调用用来释放对象持有的资源。
 *
 * @since 1.5
 */
public interface Closeable extends AutoCloseable {

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     * 关闭这个流并且释放系统资源，如果流已经关闭，那么调用这个方法没用。
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     * 像在 AutoCloseable.close() 方法注释的一样。关闭可能失败的情况下，需要仔细注意
     * 强烈建议您放弃基础资源，并在抛出 IOException 之前在内部将 Closeable 标记为已关闭。
     *
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException;
}
