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

package java.lang;
import java.util.*;

/**
 * This interface imposes a total ordering on the objects of each class that
 * implements it.  This ordering is referred to as the class's <i>natural
 * ordering</i>, and the class's <tt>compareTo</tt> method is referred to as
 * its <i>natural comparison method</i>.<p>
 * 这个接口使实现它的类具有了排序功能，这个排序更客观来讲是自然排序，类的排序方法参考它组成了
 * 自然排序方法
 *
 * Lists (and arrays) of objects that implement this interface can be sorted
 * automatically by {@link Collections#sort(List) Collections.sort} (and
 * {@link Arrays#sort(Object[]) Arrays.sort}).  Objects that implement this
 * interface can be used as keys in a {@linkplain SortedMap sorted map} or as
 * elements in a {@linkplain SortedSet sorted set}, without the need to
 * specify a {@linkplain Comparator comparator}.<p>
 * 实现此接口的列表(和数组)通过Collections.sort()方法或者Arrays.sort()方法自动被排序
 * SortedMap重的key 、 SortedSet中的元素都实现了此接口，而不是需要指定特殊的Comparator比较器
 *
 * The natural ordering for a class <tt>C</tt> is said to be <i>consistent
 * with equals</i> if and only if <tt>e1.compareTo(e2) == 0</tt> has
 * the same boolean value as <tt>e1.equals(e2)</tt> for every
 * <tt>e1</tt> and <tt>e2</tt> of class <tt>C</tt>.  Note that <tt>null</tt>
 * is not an instance of any class, and <tt>e.compareTo(null)</tt> should
 * throw a <tt>NullPointerException</tt> even though <tt>e.equals(null)</tt>
 * returns <tt>false</tt>.<p>
 * 类中的自然排序表示的相等是说当且仅当 e1.compareTo(e2) == 0 与 e1.equals(e2) 具有相同的
 * 返回值。注意null 不是任何类的实例， 并且e.compareTo(null) 会抛出空指针异常即使
 * e.equals(null) 返回false。
 *
 * It is strongly recommended (though not required) that natural orderings be
 * consistent with equals.  This is so because sorted sets (and sorted maps)
 * without explicit comparators behave "strangely" when they are used with
 * elements (or keys) whose natural ordering is inconsistent with equals.  In
 * particular, such a sorted set (or sorted map) violates the general contract
 * for set (or map), which is defined in terms of the <tt>equals</tt>
 * method.<p>
 * 强烈推荐(尽管不是必须的)自然排序与equals保持一致，这是因为sorted set 和(sorted map )
 * 没有因为指定comparator 会变得很奇怪，自然排序与equals不一致的元素(或键)
 * 特别的，像这样排序集和排序映射违反了一般的规则，在当他们中定义了equals的方法的时候
 *
 * For example, if one adds two keys <tt>a</tt> and <tt>b</tt> such that
 * {@code (!a.equals(b) && a.compareTo(b) == 0)} to a sorted
 * set that does not use an explicit comparator, the second <tt>add</tt>
 * operation returns false (and the size of the sorted set does not increase)
 * because <tt>a</tt> and <tt>b</tt> are equivalent from the sorted set's
 * perspective.<p>
 * 举个例子，如果两个键进行相加就像这样 (!a.equals(b) && a.compareTo(b) == 0) 对于
 * 排序集来说没有使用呢特殊的比较器，第二个操作返回false(这个排序集的大小不再增加)
 * 因为a 和 b与排序集相当观点??
 *
 * Virtually all Java core classes that implement <tt>Comparable</tt> have natural
 * orderings that are consistent with equals.  One exception is
 * <tt>java.math.BigDecimal</tt>, whose natural ordering equates
 * <tt>BigDecimal</tt> objects with equal values and different precisions
 * (such as 4.0 and 4.00).<p>
 * 实质上Java所有实现了Comparable接口的核心类的自然排序都和equals相同，一个例外是BigDecimal类
 * 它的自然排序有相同的值和不同的精度。
 *
 *
 * For the mathematically inclined, the <i>relation</i> that defines
 * the natural ordering on a given class C is:<pre>
 *       {(x, y) such that x.compareTo(y) &lt;= 0}.
 * </pre> The <i>quotient</i> for this total order is: <pre>
 *       {(x, y) such that x.compareTo(y) == 0}.
 * </pre>
 *
 * It follows immediately from the contract for <tt>compareTo</tt> that the
 * quotient is an <i>equivalence relation</i> on <tt>C</tt>, and that the
 * natural ordering is a <i>total order</i> on <tt>C</tt>.  When we say that a
 * class's natural ordering is <i>consistent with equals</i>, we mean that the
 * quotient for the natural ordering is the equivalence relation defined by
 * the class's {@link Object#equals(Object) equals(Object)} method:<pre>
 *     {(x, y) such that x.equals(y)}. </pre><p>
 *
 *
 *
 * This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <T> the type of objects that this object may be compared to
 *
 * @author  Josh Bloch
 * @see java.util.Comparator
 * @since 1.2
 */
public interface Comparable<T> {
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * 将此对象与指定对象进行比较以获得顺序，返回 -1， 0 ， 1 的结果依次代表小于， 等于， 大于
     *
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * 它的实现必须确保(x.compareTo(y))的符号函数 与 -(y.compareTo(x))的符号函数相同
     * 这意味着x.compareTo(y) 需要抛出与 y.compareTo(x)相同的异常
     *
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * 实现此接口的方法必须确保关系是可传递的。
     * if x.compareTo(y) > 0  && y.compareTo(z) > 0
     * x.compareTo(z) > 0
     *
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * 最后，实现此方法必须确保x.compareTo(y) == 0 意味着 符号函数(x.compareTo(z) ==
     * (y.compareTo(z)) ，综上
     *
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * 强烈推荐但不是必须的是 x.compareTo(y) == 0  ==  x.equals(y)
     * 大致来讲，任何实现了Comparable接口的类并且违反了这种情况应该明确的阐述事实。
     *
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     * 在前描述的是，这个注释sgn 描述的是一种数学正负号的功能，
     * 返回 -1， 0 ， 1 依次代表小于， 等于， 大于
     *
     *
     * @param   o the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     */
    public int compareTo(T o);
}
