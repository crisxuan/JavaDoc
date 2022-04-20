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

package java.util.concurrent;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * A synchronization aid that allows one or more threads to wait until
 * a set of operations being performed in other threads completes.
 *
 * 一种同步机制，它允许一组线程等待直到在其他线程中执行的完一组操作为止。
 *
 * <p>A {@code CountDownLatch} is initialized with a given <em>count</em>.
 * The {@link #await await} methods block until the current count reaches
 * zero due to invocations of the {@link #countDown} method, after which
 * all waiting threads are released and any subsequent invocations of
 * {@link #await await} return immediately.  This is a one-shot phenomenon
 * -- the count cannot be reset.  If you need a version that resets the
 * count, consider using a {@link CyclicBarrier}.
 *
 * CountDownLatch 初始化为给定 count 。 await 方法会阻塞直到当前调用了 countDown 方法
 * 把数量变为 0，之后释放所有等待线程，随后返回 await 的任何后续调用。这是一种一次性现象
 * count 不能被重置。如果你需要重置 count，那么你要使用 CyclicBarrier.
 *
 * <p>A {@code CountDownLatch} is a versatile synchronization tool
 * and can be used for a number of purposes.  A
 * {@code CountDownLatch} initialized with a count of one serves as a
 * simple on/off latch, or gate: all threads invoking {@link #await await}
 * wait at the gate until it is opened by a thread invoking {@link
 * #countDown}.  A {@code CountDownLatch} initialized to <em>N</em>
 * can be used to make one thread wait until <em>N</em> threads have
 * completed some action, or some action has been completed N times.
 *
 * CountDownLatch 是一个多功能的同步工具，可以用于多种用途。
 * CountDownLatch 初始化为 count 是一个简单的 on/off 锁存器或门；所有的线程
 * 调用 await 都会等待，直到被调用 countDown 打开。一个初始化为 N 的 CountDownLatch
 * 会等待 N 个线程都执行完某个操作，或者一些行为完成 N 次。
 *
 * <p>A useful property of a {@code CountDownLatch} is that it
 * doesn't require that threads calling {@code countDown} wait for
 * the count to reach zero before proceeding, it simply prevents any
 * thread from proceeding past an {@link #await await} until all
 * threads could pass.
 *
 * CountDownLatch 一个有用的属性是它不需要线程调用 countDown 等待计数变成 0 ，
 * 它只是防止任何线程经过 await，直到所有线程都可以通过。
 *
 * <p><b>Sample usage:</b> Here is a pair of classes in which a group
 * of worker threads use two countdown latches:
 * <ul>
 * <li>The first is a start signal that prevents any worker from proceeding
 * until the driver is ready for them to proceed;
 * <li>The second is a completion signal that allows the driver to wait
 * until all workers have completed.
 * </ul>
 *
 * 简单用法：下面是一个类，这个类中有一组 worker 线程使用了两个 contDown latch
 * 第一个是开始开始信号阻止任意的 worker 执行直到 driver 继续运行
 * 第二个是完成信号允许 driver 等待直到所有工人完成任务。
 *
 *  <pre> {@code
 * class Driver { // ...
 *   void main() throws InterruptedException {
 *     CountDownLatch startSignal = new CountDownLatch(1);
 *     CountDownLatch doneSignal = new CountDownLatch(N);
 *
 *     for (int i = 0; i < N; ++i) // create and start threads
 *       new Thread(new Worker(startSignal, doneSignal)).start();
 *
 *     doSomethingElse();            // don't let run yet
 *     startSignal.countDown();      // let all threads proceed
 *     doSomethingElse();
 *     doneSignal.await();           // wait for all to finish
 *   }
 * }
 *
 * class Worker implements Runnable {
 *   private final CountDownLatch startSignal;
 *   private final CountDownLatch doneSignal;
 *   Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
 *     this.startSignal = startSignal;
 *     this.doneSignal = doneSignal;
 *   }
 *   public void run() {
 *     try {
 *       startSignal.await();
 *       doWork();
 *       doneSignal.countDown();
 *     } catch (InterruptedException ex) {} // return;
 *   }
 *
 *   void doWork() { ... }
 * }}</pre>
 *
 * <p>Another typical usage would be to divide a problem into N parts,
 * describe each part with a Runnable that executes that portion and
 * counts down on the latch, and queue all the Runnables to an
 * Executor.  When all sub-parts are complete, the coordinating thread
 * will be able to pass through await. (When threads must repeatedly
 * count down in this way, instead use a {@link CyclicBarrier}.)
 *
 *  <pre> {@code
 * class Driver2 { // ...
 *   void main() throws InterruptedException {
 *     CountDownLatch doneSignal = new CountDownLatch(N);
 *     Executor e = ...
 *
 *     for (int i = 0; i < N; ++i) // create and start threads
 *       e.execute(new WorkerRunnable(doneSignal, i));
 *
 *     doneSignal.await();           // wait for all to finish
 *   }
 * }
 *
 * class WorkerRunnable implements Runnable {
 *   private final CountDownLatch doneSignal;
 *   private final int i;
 *   WorkerRunnable(CountDownLatch doneSignal, int i) {
 *     this.doneSignal = doneSignal;
 *     this.i = i;
 *   }
 *   public void run() {
 *     try {
 *       doWork(i);
 *       doneSignal.countDown();
 *     } catch (InterruptedException ex) {} // return;
 *   }
 *
 *   void doWork() { ... }
 * }}</pre>
 *
 * <p>Memory consistency effects: Until the count reaches
 * zero, actions in a thread prior to calling
 * {@code countDown()}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions following a successful return from a corresponding
 * {@code await()} in another thread.
 *
 * @since 1.5
 * @author Doug Lea
 */
public class CountDownLatch {
    /**
     * Synchronization control For CountDownLatch.
     * Uses AQS state to represent count.
     *
     * CountDownLatch 的同步控制。
     * 使用 AQS 状态来代表数量
     *
     */
    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 4982264981922014374L;

        Sync(int count) {
            setState(count);
        }

        int getCount() {
            return getState();
        }

        /**
         *
         * 可能会有读者疑惑这个 acquires 有什么用呢？
         * 其实虽然在这个方法中没有什么用处，
         * 但是别忘了，tryAcquireShared 在 AQS 中是模版方法，
         * 也就是说，acquires 这个参数有可能在 Semaphore
         * 和 ReentrantReadWriteLock 中使用
         * 看了一下，实际上只有在 Semaphore 信号量中会使用到。
         * @param acquires
         * @return
         */
        protected int tryAcquireShared(int acquires) {
            return (getState() == 0) ? 1 : -1;
        }

        /**
         * 以共享模式释放
         * releases 这个参数同理，这里也没什么用。
         *
         * @param releases
         * @return
         */
        protected boolean tryReleaseShared(int releases) {
            // Decrement count; signal when transition to zero
            for (;;) {
                int c = getState();
                if (c == 0)
                    return false;
                int nextc = c-1;
                if (compareAndSetState(c, nextc))
                    return nextc == 0;
            }
        }
    }

    private final Sync sync;

    /**
     * Constructs a {@code CountDownLatch} initialized with the given count.
     *
     * 构造一个 CountDownLatch ，它初始化为给定的 count 。
     *
     * @param count the number of times {@link #countDown} must be invoked
     *        before threads can pass through {@link #await}
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public CountDownLatch(int count) {
        if (count < 0) throw new IllegalArgumentException("count < 0");
        this.sync = new Sync(count);
    }

    /**
     * Causes the current thread to wait until the latch has counted down to
     * zero, unless the thread is {@linkplain Thread#interrupt interrupted}.
     *
     * 使当前线程等待直到 latch 调用 countDown 方法减为 0 , 除非线程已经中断。
     *
     * <p>If the current count is zero then this method returns immediately.
     *
     * 如果当前 count 为0 ,那么这个方法会立刻返回。
     *
     * <p>If the current count is greater than zero then the current
     * thread becomes disabled for thread scheduling purposes and lies
     * dormant until one of two things happen:
     * <ul>
     * <li>The count reaches zero due to invocations of the
     * {@link #countDown} method; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread.
     * </ul>
     *
     * 如果当前 count 要比 0 大, 则出于线程调度的目的,当前线程将被禁用,并且在下面这两种情况下
     * 处于休眠状态
     *
     * count 变为 0 由于调用了 countDown 方法;
     * 或者其他线程的 interrupt 方法打断了当前线程
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * 如果当前线程在这个方法上有中断状态设置; 或者正在等待时调用了 Interrupted 方法，
     * 那么会抛出 InterruptedException , 并且清除当前线程的中断状态。
     *
     * @throws InterruptedException if the current thread is interrupted
     *         while waiting
     *
     * 在等待时如果当前线程中断的话抛出 InterruptedException
     */
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    /**
     * Causes the current thread to wait until the latch has counted down to
     * zero, unless the thread is {@linkplain Thread#interrupt interrupted},
     * or the specified waiting time elapses.
     *
     *
     * <p>If the current count is zero then this method returns immediately
     * with the value {@code true}.
     *
     * <p>If the current count is greater than zero then the current
     * thread becomes disabled for thread scheduling purposes and lies
     * dormant until one of three things happen:
     * <ul>
     * <li>The count reaches zero due to invocations of the
     * {@link #countDown} method; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     * <li>The specified waiting time elapses.
     * </ul>
     *
     * <p>If the count reaches zero then the method returns with the
     * value {@code true}.
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the specified waiting time elapses then the value {@code false}
     * is returned.  If the time is less than or equal to zero, the method
     * will not wait at all.
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the {@code timeout} argument
     * @return {@code true} if the count reached zero and {@code false}
     *         if the waiting time elapsed before the count reached zero
     * @throws InterruptedException if the current thread is interrupted
     *         while waiting
     */
    public boolean await(long timeout, TimeUnit unit)
        throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    /**
     * Decrements the count of the latch, releasing all waiting threads if
     * the count reaches zero.
     * countDown 用于减少计数器的数量，如果计数减为 0 的话，就会释放所有的线程。
     *
     * <p>If the current count is greater than zero then it is decremented.
     * If the new count is zero then all waiting threads are re-enabled for
     * thread scheduling purposes.
     *
     * 如果当前计数大于零，则将其递减。如果新计数为零，则将重新启用所有等待线程以进行线程调度。
     *
     * <p>If the current count equals zero then nothing happens.
     */
    public void countDown() {
        sync.releaseShared(1);
    }

    /**
     * Returns the current count.
     *
     * <p>This method is typically used for debugging and testing purposes.
     *
     * @return the current count
     */
    public long getCount() {
        return sync.getCount();
    }

    /**
     * Returns a string identifying this latch, as well as its state.
     * The state, in brackets, includes the String {@code "Count ="}
     * followed by the current count.
     *
     * @return a string identifying this latch, as well as its state
     */
    public String toString() {
        return super.toString() + "[Count = " + sync.getCount() + "]";
    }
}
