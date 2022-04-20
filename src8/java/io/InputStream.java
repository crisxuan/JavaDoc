/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This abstract class is the superclass of all classes representing
 * an input stream of bytes.
 * 这个抽象类是代表所有字节流输入的父类
 *
 * <p> Applications that need to define a subclass of <code>InputStream</code>
 * must always provide a method that returns the next byte of input.
 * 应用程序需要定义一个InputStream 的子类，该子类需要提供一个返回下一个输入字节的方法。
 *
 * @author  Arthur van Hoff
 * @see     java.io.BufferedInputStream
 * @see     java.io.ByteArrayInputStream
 * @see     java.io.DataInputStream
 * @see     java.io.FilterInputStream
 * @see     java.io.InputStream#read()
 * @see     java.io.OutputStream
 * @see     java.io.PushbackInputStream
 * @since   JDK1.0
 */
public abstract class InputStream implements Closeable {

    // MAX_SKIP_BUFFER_SIZE is used to determine the maximum buffer size to
    // use when skipping.
    // MAX_SKIP_BUFFER_SIZE 用来确定最大能够跳过的字节缓冲，用于 skip()方法
    private static final int MAX_SKIP_BUFFER_SIZE = 2048;

    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     * 从输入流读取下一个字节的数据。byte字节的值作为int返回，其范围在0 - 255 之间。
     * 如果到达流的末尾而没有可用的字节的话，则返回 -1， 一般用 -1 来判断字节流是否到了末尾
     * 这个方法会锁住直到有输入数据的时候，检测到流的末尾或抛出异常
     *
     * <p> A subclass must provide an implementation of this method.
     * 子类必须提供此方法的实现。
     *
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream is reached.
     * @exception  IOException  if an I/O error occurs.
     */
    public abstract int read() throws IOException;

    /**
     * Reads some number of bytes from the input stream and stores them into
     * the buffer array <code>b</code>. The number of bytes actually read is
     * returned as an integer.  This method blocks until input data is
     * available, end of file is detected, or an exception is thrown.
     * 从输入流中读取一定数量的字节并把它们存储在缓冲数组中。实际读取的字节数以整数形式返回
     *
     * <p> If the length of <code>b</code> is zero, then no bytes are read and
     * <code>0</code> is returned; otherwise, there is an attempt to read at
     * least one byte. If no byte is available because the stream is at the
     * end of the file, the value <code>-1</code> is returned; otherwise, at
     * least one byte is read and stored into <code>b</code>.
     * 如果参数 b 的长度是0 ，就不会读取字节并且返回 0 ；否则，这个方法会尝试读取至少一个字节
     * 因为流到达末尾，而没有可以获得的字节，则返回 -1，否则，至少读取一个字节并存储在b 中。
     *
     * <p> The first byte read is stored into element <code>b[0]</code>, the
     * next one into <code>b[1]</code>, and so on. The number of bytes read is,
     * at most, equal to the length of <code>b</code>. Let <i>k</i> be the
     * number of bytes actually read; these bytes will be stored in elements
     * <code>b[0]</code> through <code>b[</code><i>k</i><code>-1]</code>,
     * leaving elements <code>b[</code><i>k</i><code>]</code> through
     * <code>b[b.length-1]</code> unaffected.
     * 第一个读取并存储的字节是 b[0]，下一个是 b[1] 以此类推。读取的字节最多等于 b 的长度。
     *
     *
     * <p> The <code>read(b)</code> method for class <code>InputStream</code>
     * has the same effect as: <pre><code> read(b, 0, b.length) </code></pre>
     *
     * @param      b   the buffer into which the data is read.
     * 参数         b   读取数据的缓冲区
     *
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * 返回         返回从缓冲区读取的字节数量，或者 -1 如果由于已到达流的末尾而没有更多数据时。
     *
     * @exception  IOException  If the first byte cannot be read for any reason
     * other than the end of the file, if the input stream has been closed, or
     * if some other I/O error occurs.
     * @exception  NullPointerException  if <code>b</code> is <code>null</code>.
     * @see        java.io.InputStream#read(byte[], int, int)
     */
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * Reads up to <code>len</code> bytes of data from the input stream into
     * an array of bytes.  An attempt is made to read as many as
     * <code>len</code> bytes, but a smaller number may be read.
     * The number of bytes actually read is returned as an integer.
     * 将输入流中的 len 长度的字节数据读入到字节数组。尝试读取 len 字节的数量，但可以读取较小的数字。
     * 实际读取的字节数以整数形式返回。
     *
     * <p> This method blocks until input data is available, end of file is
     * detected, or an exception is thrown.
     * 此方法将阻塞，直到输入数据可用，检测到文件结尾或引发异常。
     *
     * <p> If <code>len</code> is zero, then no bytes are read and
     * <code>0</code> is returned; otherwise, there is an attempt to read at
     * least one byte. If no byte is available because the stream is at end of
     * file, the value <code>-1</code> is returned; otherwise, at least one
     * byte is read and stored into <code>b</code>.
     * 如果 len 是0，就不会读取任何字节并返回 0 ；除此之外，试图去读取至少一个字节。如果到达文件
     * 末尾导致没有字节可以读取的话，就返回 -1 。另外，至少会有一个字节读取并存储在 b 中。
     *
     * <p> The first byte read is stored into element <code>b[off]</code>, the
     * next one into <code>b[off+1]</code>, and so on. The number of bytes read
     * is, at most, equal to <code>len</code>. Let <i>k</i> be the number of
     * bytes actually read; these bytes will be stored in elements
     * <code>b[off]</code> through <code>b[off+</code><i>k</i><code>-1]</code>,
     * leaving elements <code>b[off+</code><i>k</i><code>]</code> through
     * <code>b[off+len-1]</code> unaffected.
     * 第一个读取的字节被存储在元素 b[off] 中，下一个存储的就是 b[off + 1]等等。最多读取 len 个字节
     * 的数据。 假设 k 是实际读取的字节数，这些字节被存储在 b[off] 到 b[off + k -1]
     * 通过b[off + len - 1]保留元素b[off + k] 不受影响。
     *
     * <p> In every case, elements <code>b[0]</code> through
     * <code>b[off]</code> and elements <code>b[off+len]</code> through
     * <code>b[b.length-1]</code> are unaffected.
     * 任何情况下，b[0] 到 b[off] 和 b[off+len] 到 b[b.length-1] 不受影响
     *
     * <p> The <code>read(b,</code> <code>off,</code> <code>len)</code> method
     * for class <code>InputStream</code> simply calls the method
     * <code>read()</code> repeatedly. If the first such call results in an
     * <code>IOException</code>, that exception is returned from the call to
     * the <code>read(b,</code> <code>off,</code> <code>len)</code> method.  If
     * any subsequent call to <code>read()</code> results in a
     * <code>IOException</code>, the exception is caught and treated as if it
     * were end of file; the bytes read up to that point are stored into
     * <code>b</code> and the number of bytes read before the exception
     * occurred is returned. The default implementation of this method blocks
     * until the requested amount of input data <code>len</code> has been read,
     * end of file is detected, or an exception is thrown. Subclasses are encouraged
     * to provide a more efficient implementation of this method.
     * InputStream 类的 read(b,off,len) 方法只是重复调用 read() 方法。如果在调用期间发生 IOException，
     * 那么此异常将从 read(b,off,len) 方法返回。如果对 read() 方法的任何后续调用导致 IOException，
     * 则会捕获该异常并将其视为文件结束。读取到该点的字节并存储在 b 中，并返回异常异常前读取的字节数。
     * 此方法的默认实现会进行上锁直到有一定数量的数据被读取后，检测到该文件结尾或抛出异常为止。
     * 鼓励子类去提供更有效的方法实现。
     *
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in array <code>b</code>
     *                   at which the data is written.
     * @param      len   the maximum number of bytes to read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException If the first byte cannot be read for any reason
     * other than end of file, or if the input stream has been closed, or if
     * some other I/O error occurs.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @see        java.io.InputStream#read()
     */
    public int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte)c;
            }
        } catch (IOException ee) {
        }
        return i;
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from this input
     * stream. The <code>skip</code> method may, for a variety of reasons, end
     * up skipping over some smaller number of bytes, possibly <code>0</code>.
     * This may result from any of a number of conditions; reaching end of file
     * before <code>n</code> bytes have been skipped is only one possibility.
     * The actual number of bytes skipped is returned. If {@code n} is
     * negative, the {@code skip} method for class {@code InputStream} always
     * returns 0, and no bytes are skipped. Subclasses may handle the negative
     * value differently.
     * 跳过并丢弃此输入流中的字节数据。由于各种原因，skip方法最终可能会跳过一些较小的字节数，可能是0
     * 这可能是由许多条件中的任何一个引起的；只有一种可能性就是在跳过 n 个字节之前到达末尾。
     * 此方法会返回跳过的实际字节数。如果 n 是负数，skip 方法总是返回 0，并且不跳过任何字节。
     * 子类或许会有不同处理负数的方法。
     *
     * <p> The <code>skip</code> method of this class creates a
     * byte array and then repeatedly reads into it until <code>n</code> bytes
     * have been read or the end of the stream has been reached. Subclasses are
     * encouraged to provide a more efficient implementation of this method.
     * For instance, the implementation may depend on the ability to seek.
     * 类的skip 方法创建了一个字节数组并且重复读取直到到达流的末端。
     *
     * @param      n   the number of bytes to be skipped.
     * @return     the actual number of bytes skipped.
     * @exception  IOException  if the stream does not support seek,
     *                          or if some other I/O error occurs.
     */
    public long skip(long n) throws IOException {

        long remaining = n;
        int nr;

        if (n <= 0) {
            return 0;
        }

        int size = (int)Math.min(MAX_SKIP_BUFFER_SIZE, remaining);
        byte[] skipBuffer = new byte[size];
        while (remaining > 0) {
            nr = read(skipBuffer, 0, (int)Math.min(size, remaining));
            if (nr < 0) {
                break;
            }
            remaining -= nr;
        }

        return n - remaining;
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. The next invocation
     * might be the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     * 返回此输入流可以读取（或跳过）的字节数的估计值，而不会被下一次调用此输入流的方法阻塞
     * 下一次调用可能是同一个线程或另一个线程，单个读取或跳过这么多字节不会阻塞，但可以读取或跳过更少的字节。
     *
     * <p> Note that while some implementations of {@code InputStream} will return
     * the total number of bytes in the stream, many will not.  It is
     * never correct to use the return value of this method to allocate
     * a buffer intended to hold all data in this stream.
     * 注意，InputStream 的某些实现将返回流中的总字节数，但是许多实现则不会。
     * 使用这个方法的返回值去分配一个缓冲区为了保存流中的所有数据是绝对错误的。
     *
     * <p> A subclass' implementation of this method may choose to throw an
     * {@link IOException} if this input stream has been closed by
     * invoking the {@link #close()} method.
     * 如果输入流通过调用 close() 方法关闭后，此方法的子类实现可能选择抛出 IOException
     *
     * <p> The {@code available} method for class {@code InputStream} always
     * returns {@code 0}.
     * InputStream 类的 available 方法总是返回 0 。
     *
     * <p> This method should be overridden by subclasses.
     *
     * @return     an estimate of the number of bytes that can be read (or skipped
     *             over) from this input stream without blocking or {@code 0} when
     *             it reaches the end of the input stream.
     * @exception  IOException if an I/O error occurs.
     */
    public int available() throws IOException {
        return 0;
    }

    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.
     * 关闭这个输入流并且释放流分配的系统资源。
     *
     * <p> The <code>close</code> method of <code>InputStream</code> does
     * nothing.
     * InputStream 的 close 方法不做任何操作。
     *
     * @exception  IOException  if an I/O error occurs.
     */
    public void close() throws IOException {}

    /**
     * Marks the current position in this input stream. A subsequent call to
     * the <code>reset</code> method repositions this stream at the last marked
     * position so that subsequent reads re-read the same bytes.
     * 标记输入流当前的位置。随后对 reset 方法的调用会在最后标记的位置处定位此流的位置。
     * 以便后续重新读取相同的字节。
     *
     * <p> The <code>readlimit</code> arguments tells this input stream to
     * allow that many bytes to be read before the mark position gets
     * invalidated.
     * readlimit 参数告诉输入流允许在标记位置失效之前读取多个字节。
     *
     * <p> The general contract of <code>mark</code> is that, if the method
     * <code>markSupported</code> returns <code>true</code>, the stream somehow
     * remembers all the bytes read after the call to <code>mark</code> and
     * stands ready to supply those same bytes again if and whenever the method
     * <code>reset</code> is called.  However, the stream is not required to
     * remember any data at all if more than <code>readlimit</code> bytes are
     * read from the stream before <code>reset</code> is called.
     * mark 的一般约定是，如果方法 markSupported 返回 true，在调用mark方法后，流以某种方式记录所有读取的字节
     * 当调用reset方法后，能够再次提供同样的字节。
     * 但是，在调用reset之前，流不必记录readlimit以外的字节。
     *
     * <p> Marking a closed stream should not have any effect on the stream.
     * 在已经关闭的流上调用mark方法对流没有影响
     *
     * <p> The <code>mark</code> method of <code>InputStream</code> does
     * InputStream 的 mark 方法不会做任何事情
     * nothing.
     *
     * @param   readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     * @see     java.io.InputStream#reset()
     */
    public synchronized void mark(int readlimit) {}

    /**
     * Repositions this stream to the position at the time the
     * <code>mark</code> method was last called on this input stream.
     * 将此流重新定位到上次在此输入流上调用 mark 方法的位置。
     *
     * <p> The general contract of <code>reset</code> is:
     * reset 的一般规定是
     *
     * <ul>
     * <li> If the method <code>markSupported</code> returns
     * <code>true</code>, then:
     * 如果 markSupported 方法返回 true，则
     *
     *     <ul><li> If the method <code>mark</code> has not been called since
     *     the stream was created, or the number of bytes read from the stream
     *     since <code>mark</code> was last called is larger than the argument
     *     to <code>mark</code> at that last call, then an
     *     <code>IOException</code> might be thrown.
     *     如果自从流被创建后没有调用 mark 方法，或者 mark方法从最后一次调用后从流中读取的字节数量
     *     要大于最后一次调用时 mark 的参数的话，可能抛出 IOException。
     *
     *     <li> If such an <code>IOException</code> is not thrown, then the
     *     stream is reset to a state such that all the bytes read since the
     *     most recent call to <code>mark</code> (or since the start of the
     *     file, if <code>mark</code> has not been called) will be resupplied
     *     to subsequent callers of the <code>read</code> method, followed by
     *     any bytes that otherwise would have been the next input data as of
     *     the time of the call to <code>reset</code>. </ul>
     *     如果没有抛出 IOException 的话，这时流会重置为一种状态，这时从最近一次调用 mark 以来
     *     所读取的所有字节将被提供给 read 方法的后续调用者。
     *
     *
     * <li> If the method <code>markSupported</code> returns
     * <code>false</code>, then:
     * 如果 markSupported 返回 false 后
     *
     *     <ul><li> The call to <code>reset</code> may throw an
     *     <code>IOException</code>.
     *     这个 reset 调用可能抛出 IOException
     *
     *     <li> If an <code>IOException</code> is not thrown, then the stream
     *     is reset to a fixed state that depends on the particular type of the
     *     input stream and how it was created. The bytes that will be supplied
     *     to subsequent callers of the <code>read</code> method depend on the
     *     particular type of the input stream. </ul></ul>
     *     如果没有抛出 IOException 的话，则将流重置为固定状态，该状态取决于输入流的特定类型及其创建方式。
     *     将提供给 read 方法的后续调用者的字节取决于输入流的特定类型
     *
     * <p>The method <code>reset</code> for class <code>InputStream</code>
     * does nothing except throw an <code>IOException</code>.
     * InputStream 类的 reset 除了抛出异常之外不会做任何事。
     *
     * @exception  IOException  if this stream has not been marked or if the
     *               mark has been invalidated.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.IOException
     */
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    /**
     * Tests if this input stream supports the <code>mark</code> and
     * <code>reset</code> methods. Whether or not <code>mark</code> and
     * <code>reset</code> are supported is an invariant property of a
     * particular input stream instance. The <code>markSupported</code> method
     * of <code>InputStream</code> returns <code>false</code>.
     * markSupported 方法就是一个判断是否支持 mark 和 reset 方法的标示。
     *
     *
     * @return  <code>true</code> if this stream instance supports the mark
     *          and reset methods; <code>false</code> otherwise.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.InputStream#reset()
     */
    public boolean markSupported() {
        return false;
    }

}
