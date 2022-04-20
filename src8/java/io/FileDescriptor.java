/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of the file descriptor class serve as an opaque handle
 * to the underlying machine-specific structure representing an open
 * file, an open socket, or another source or sink of bytes. The
 * main practical use for a file descriptor is to create a
 * <code>FileInputStream</code> or <code>FileOutputStream</code> to
 * contain it.
 * FileDescriptor 是“文件描述符”。FileDescriptor 可以被用来表示开放文件、开放套接字等。
 * 以FileDescriptor表示文件来说：当FileDescriptor表示某文件时，我们可以通俗的将FileDescriptor看成是该文件。
 * 但是，我们不能直接通过FileDescriptor对该文件进行操作；
 * 若需要通过FileDescriptor对该文件进行操作，则需要新创建FileDescriptor对应的FileOutputStream，再对文件进行操作。
 *
 * <p>
 * Applications should not create their own file descriptors.
 * 应用程序不应该创建他们自己的文件描述符。
 *
 * @author  Pavani Diwanji
 * @see     java.io.FileInputStream
 * @see     java.io.FileOutputStream
 * @since   JDK1.0
 */
public final class FileDescriptor {

    private int fd;

    private Closeable parent;
    private List<Closeable> otherParents;
    private boolean closed;

    /**
     * Constructs an (invalid) FileDescriptor
     * object.
     * 构建一个无效的 FileDescriptor 对象。
     */
    public /**/ FileDescriptor() {
        fd = -1;
    }

    private /* */ FileDescriptor(int fd) {
        this.fd = fd;
    }

    /**
     * A handle to the standard input stream. Usually, this file
     * descriptor is not used directly, but rather via the input stream
     * known as <code>System.in</code>.
     * 一个标准输入流的句柄。通常情况下，这个文件描述符不会直接使用，而是通过称为
     * System.in 的输入流。
     *
     * @see     java.lang.System#in
     */
    public static final FileDescriptor in = new FileDescriptor(0);

    /**
     * A handle to the standard output stream. Usually, this file
     * descriptor is not used directly, but rather via the output stream
     * known as <code>System.out</code>.
     * 一个输出流的句柄。同上，使用 System.out 输出
     *
     * @see     java.lang.System#out
     */
    public static final FileDescriptor out = new FileDescriptor(1);

    /**
     * A handle to the standard error stream. Usually, this file
     * descriptor is not used directly, but rather via the output stream
     * known as <code>System.err</code>.
     * 一个标准错误流的句柄，通过 System.err 展示
     *
     * @see     java.lang.System#err
     */
    public static final FileDescriptor err = new FileDescriptor(2);

    /**
     * Tests if this file descriptor object is valid.
     * 测试 filedescriptor 对象是有效的
     *
     * @return  <code>true</code> if the file descriptor object represents a
     *          valid, open file, socket, or other active I/O connection;
     *          <code>false</code> otherwise.
     *          如果 filedescriptor 对象代表着 有效的，打开文件，套接字或者其他有效的 I/O 连接 返回true ，
     *          false 则是其他。
     */
    public boolean valid() {
        return fd != -1;
    }

    /**
     * Force all system buffers to synchronize with the underlying
     * device.  This method returns after all modified data and
     * attributes of this FileDescriptor have been written to the
     * relevant device(s).  In particular, if this FileDescriptor
     * refers to a physical storage medium, such as a file in a file
     * system, sync will not return until all in-memory modified copies
     * of buffers associated with this FileDescriptor have been
     * written to the physical medium.
     * 强制所有系统缓冲区与基础设备同步。此 FileDescriptor 修改后的数据和属性都已写入
     * 相关设备后，此方法返回。特别是，如果此 FileDescriptor 引用物理存储介质，例如文件系统中的文件，
     * 则在与该 FileDescriptor 关联的缓冲区的所有内存中已修改副本已写入物理介质之前，同步不会返回。
     *
     * sync is meant to be used by code that requires physical
     * storage (such as a file) to be in a known state  For
     * example, a class that provided a simple transaction facility
     * might use sync to ensure that all changes to a file caused
     * by a given transaction were recorded on a storage medium.
     * sync用于要求物理存储（如文件）处于已知状态的代码。
     * 例如，提供简单事务功能的类可以使用 sync 来确保由给定事务引起的对文件的所有更改都记录在存储介质上。
     *
     * sync only affects buffers downstream of this FileDescriptor.  If
     * any in-memory buffering is being done by the application (for
     * example, by a BufferedOutputStream object), those buffers must
     * be flushed into the FileDescriptor (for example, by invoking
     * OutputStream.flush) before that data will be affected by sync.
     * 同步仅影响此文件描述符下游的缓冲区。
     * 如果应用程序正在执行任何内存缓冲（例如，通过 BufferedOutputStream 对象），
     * 则必须将这些缓冲区刷新到文件描述符中（例如，通过调用OutputStream.flush），然后数据才会受同步影响。
     *
     * @exception SyncFailedException
     *        Thrown when the buffers cannot be flushed,
     *        or because the system cannot guarantee that all the
     *        buffers have been synchronized with physical media.
     * @since     JDK1.1
     */
    public native void sync() throws SyncFailedException;

    /* This routine initializes JNI field offsets for the class */
    // 这个例程初始化类的jni字段偏移量。
    private static native void initIDs();

    static {
        initIDs();
    }

    // Set up JavaIOFileDescriptorAccess in SharedSecrets
    static {
        sun.misc.SharedSecrets.setJavaIOFileDescriptorAccess(
            new sun.misc.JavaIOFileDescriptorAccess() {
                public void set(FileDescriptor obj, int fd) {
                    obj.fd = fd;
                }

                public int get(FileDescriptor obj) {
                    return obj.fd;
                }

                public void setHandle(FileDescriptor obj, long handle) {
                    throw new UnsupportedOperationException();
                }

                public long getHandle(FileDescriptor obj) {
                    throw new UnsupportedOperationException();
                }
            }
        );
    }

    /*
     * Package private methods to track referents.
     * If multiple streams point to the same FileDescriptor, we cycle
     * through the list of all referents and call close()
     */
    // 封装私有方法以追踪引用。
    // 如果多个流指向同一个相同的描述符，我们会循环list 中的每个引用，并且把它们关闭。

    /**
     * Attach a Closeable to this FD for tracking.
     * parent reference is added to otherParents when
     * needed to make closeAll simpler.
     * 在这个FD上安装一个闭合器进行跟踪。当需要使closeAll更简单时，父引用将添加到其他父引用。
     *
     */
    synchronized void attach(Closeable c) {
        if (parent == null) {
            // first caller gets to do this
            parent = c;
        } else if (otherParents == null) {
            otherParents = new ArrayList<>();
            otherParents.add(parent);
            otherParents.add(c);
        } else {
            otherParents.add(c);
        }
    }

    /**
     * Cycle through all Closeables sharing this FD and call
     * close() on each one.
     * 循环查看此 fd 可以共享的关闭文件，并对每个文件调用 close()方法。
     *
     * The caller closeable gets to call close0().
     */
    @SuppressWarnings("try")
    synchronized void closeAll(Closeable releaser) throws IOException {
        if (!closed) {
            closed = true;
            IOException ioe = null;
            try (Closeable c = releaser) {
                if (otherParents != null) {
                    for (Closeable referent : otherParents) {
                        try {
                            referent.close();
                        } catch(IOException x) {
                            if (ioe == null) {
                                ioe = x;
                            } else {
                                ioe.addSuppressed(x);
                            }
                        }
                    }
                }
            } catch(IOException ex) {
                /*
                 * If releaser close() throws IOException
                 * add other exceptions as suppressed.
                 */
                if (ioe != null)
                    ex.addSuppressed(ioe);
                ioe = ex;
            } finally {
                if (ioe != null)
                    throw ioe;
            }
        }
    }
}
