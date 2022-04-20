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

package java.lang;

import java.io.IOException;

/**
 * A <tt>Readable</tt> is a source of characters. Characters from
 * a <tt>Readable</tt> are made available to callers of the read
 * method via a {@link java.nio.CharBuffer CharBuffer}.
 * Readable 是一个字符来源。来自 Readable 的字符通过 CharBuffer 提供给 read
 * 方法的调用者
 *
 * @since 1.5
 */
public interface Readable {

    /**
     * Attempts to read characters into the specified character buffer.
     * The buffer is used as a repository of characters as-is: the only
     * changes made are the results of a put operation. No flipping or
     * rewinding of the buffer is performed.
     * 尝试去从特定的字符缓冲区中读取字符。缓冲区被用来作为字符存储库：唯一的变化是put操作的结果。
     * 不执行缓冲器的翻转或倒带。
     *
     * @param cb the buffer to read characters into
     * @return The number of {@code char} values added to the buffer,
     *                 or -1 if this source of characters is at its end
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if cb is null
     * @throws java.nio.ReadOnlyBufferException if cb is a read only buffer
     */
    public int read(java.nio.CharBuffer cb) throws IOException;
}
