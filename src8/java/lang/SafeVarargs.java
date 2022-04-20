/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.annotation.*;

/**
 * A programmer assertion that the body of the annotated method or
 * constructor does not perform potentially unsafe operations on its
 * varargs parameter.  Applying this annotation to a method or
 * constructor suppresses unchecked warnings about a
 * <i>non-reifiable</i> variable arity (vararg) type and suppresses
 * unchecked warnings about parameterized array creation at call
 * sites.
 * 程序员认定带有注释的主体或者构造函数不会对其执行潜在的不安全操作
 * 将此注释应用于未经检查的方法或者构造器在"不可信赖的"的和未经检查的参数类型
 * 警告关于所有数组参数创建的时候
 *
 *
 * <p> In addition to the usage restrictions imposed by its {@link
 * Target @Target} meta-annotation, compilers are required to implement
 * additional usage restrictions on this annotation type; it is a
 * compile-time error if a method or constructor declaration is
 * annotated with a {@code @SafeVarargs} annotation, and either:
 * <ul>
 * <li>  the declaration is a fixed arity method or constructor
 *
 * <li> the declaration is a variable arity method that is neither
 * {@code static} nor {@code final}.
 *
 * </ul>
 * 除了强加使用@Target 元注解的限制之外，编译器还被用在注解类型上来实现额外的限制
 * 下面几种情况会在使用@SafeVarags 注解的时候产生编译时错误：
 * 声明一个固定参数的方法或者构造函数
 *
 * <p> Compilers are encouraged to issue warnings when this annotation
 * type is applied to a method or constructor declaration where:
 *
 * <ul>
 *
 * <li> The variable arity parameter has a reifiable element type,
 * which includes primitive types, {@code Object}, and {@code String}.
 * (The unchecked warnings this annotation type suppresses already do
 * not occur for a reifiable element type.)
 *
 * <li> The body of the method or constructor declaration performs
 * potentially unsafe operations, such as an assignment to an element
 * of the variable arity parameter's array that generates an unchecked
 * warning.  Some unsafe operations do not trigger an unchecked
 * warning.  For example, the aliasing in
 *
 * <blockquote><pre>
 * &#64;SafeVarargs // Not actually safe!
 * static void m(List&lt;String&gt;... stringLists) {
 *   Object[] array = stringLists;
 *   List&lt;Integer&gt; tmpList = Arrays.asList(42);
 *   array[0] = tmpList; // Semantically invalid, but compiles without warnings
 *   String s = stringLists[0].get(0); // Oh no, ClassCastException at runtime!
 * }
 * </pre></blockquote>
 *
 * leads to a {@code ClassCastException} at runtime.
 *
 * <p>Future versions of the platform may mandate compiler errors for
 * such unsafe operations.
 *
 * </ul>
 * 可变长度的方法参数的实际值是通过数组来传递的，而数组中存储的是不可具体化的泛型类对象，
 * 自身存在类型安全问题。因此编译器会给出相应的警告信息
 *
 *
 * @since 1.7
 * @jls 4.7 Reifiable Types
 * @jls 8.4.1 Formal Parameters
 * @jls 9.6.3.7 @SafeVarargs
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface SafeVarargs {}
