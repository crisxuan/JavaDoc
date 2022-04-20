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

package java.lang;

/**
 * The <code>Runnable</code> interface should be implemented by any
 * class whose instances are intended to be executed by a thread. The
 * class must define a method of no arguments called <code>run</code>.
 * <p>
 * 实现Runnable 接口的实例应该被用来执行一个线程。这个类必须定义一个无参数的run方法作为
 * 线程启动的入口。
 *
 * This interface is designed to provide a common protocol for objects that
 * wish to execute code while they are active. For example,
 * <code>Runnable</code> is implemented by class <code>Thread</code>.
 * Being active simply means that a thread has been started and has not
 * yet been stopped.
 * 这个接口被设计为一个公共接口，它希望在活跃时执行代码。例如，线程 Thread 类就实现了
 * Runnable 接口，变活跃也就是意味着一个线程是就绪状态而不是中断状态。
 *
 * <p>
 * In addition, <code>Runnable</code> provides the means for a class to be
 * active while not subclassing <code>Thread</code>. A class that implements
 * <code>Runnable</code> can run without subclassing <code>Thread</code>
 * by instantiating a <code>Thread</code> instance and passing itself in
 * as the target.  In most cases, the <code>Runnable</code> interface should
 * be used if you are only planning to override the <code>run()</code>
 * method and no other <code>Thread</code> methods.
 * This is important because classes should not be subclassed
 * unless the programmer intends on modifying or enhancing the fundamental
 * behavior of the class.
 * 此外，Runnable 提供了一个类活动而不是继承 Thread 的方法。实现了 Runnable 的类可以不用
 * 继承 Thread 类就能运行一个线程，因为 Thread 类也实现了Runnable 接口。
 * 在大多数情况下，如果你只打算覆盖 方法而不是其他 Thread 中的方法，则应使用Runnable接口。
 * 这很重要，因为除非程序员打算修改或增强类的基本行为，否则不应对类进行子类化。
 *
 * @author  Arthur van Hoff
 * @see     java.lang.Thread
 * @see     java.util.concurrent.Callable
 * @since   JDK1.0
 */
@FunctionalInterface
public interface Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * 抽象方法，当对象实现了此接口，会重写此方法作为线程执行的调用入口。
     *
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
