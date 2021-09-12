theme: simple
background-color: #4c585a
text: #d9d9d9, alignment(left), line-height(1), text-scale(1.0), Arial
header: #d9d9d9, alignment(left), line-height(1), text-scale(1.0), Arial
link: #61c1dd, alignment(left), line-height(1), text-scale(1.0), Arial
autoscale: true

# Spring Boot mit Kotlin

## Kotlin Features

---

# Kotlin - Extention Functions

Instead of this

```kotlin
fun <T> swap(list: MutableList<T>, index1: Int, index2: Int) {
    // implementation omitted ...
}

val list = mutableListOf(1, 2, 3)

swap(list, 1, 2)
```

---

# Kotlin - Extention functions

... we can write this

```kotlin
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    // now the list is bound to 'this'
}

val list = mutableListOf(1, 2, 3)

list.swap(1,2)
```

---

# Kotlin - Reified Type Parameters

* Functions marked with `inline` will be inlined by the compiler
* In inlined generic functions, type parameters can be marked with `reified` and passed in at call side

---

# Kotlin - Reified Type Parameters

```kotlin
inline fun <reified T> TreeNode.findFirstAncestorOfTypeOrNull(): T? {
    var p = this.parent
    while (p != null && p !is T) { // no reflection!
        p = p.parent
    }
    return p as T?
}

// usage:
treeNode.findParentOfType<MyTreeNode>()
```

---

# Resources

* [kotlinlang.org](https://kotlinlang.org/docs/reference/)
* [Learn Kotlin by Example](https://play.kotlinlang.org/byExample/overview)
* [Kotlin Koans](https://kotlinlang.org/docs/koans.html)
* [Kotlin for Java Developers](https://www.coursera.org/learn/kotlin-for-java-developers) (Coursera)
* [Spring Framework Reference](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html#kotlin)
* [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.kotlin)
* [spring.io Tutorial](https://spring.io/guides/tutorials/spring-boot-kotlin/)
