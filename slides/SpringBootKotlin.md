theme: simple
background-color: #4c585a
text: #d9d9d9, alignment(left), line-height(1), text-scale(1.0), Arial
header: #d9d9d9, alignment(left), line-height(1), text-scale(1.0), Arial
link: #61c1dd, alignment(left), line-height(1), text-scale(1.0), Arial
autoscale: true

# Spring Boot mit Kotlin

## Stefan Scheidt, REWE digital

---

[.build-lists: true]

# Agenda

* About me & REWE digital
* Spring Framework & Spring Boot
* Kotlin
* Spring Boot & Kotlin
* Experiences

---

# About me

* Software Engineer at REWE digital in Cologne/Germany
* 20+ years of software development in enterprise context
* Current focus: Microservices with Spring Boot and Java/Kotlin
* [Twitter](https://twitter.com/stefanscheidt)/[GitHub](https://github.com/stefanscheidt): `stefanscheidt`

---

# REWE digital

![inline](img/Bierdeckel.png)

---

# Kotlin @ REWE digital

*   Android
    *   REWE Angebote & Lieferservice (since 2016!)
    *   PENNY Coupons & Angebote
    *   5+ Apps in Fulfillment

*   Spring Boot
    *   10+ Microservices (eCom, Fulfillment)

---

# Spring Framework

> "The Spring Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications - on any kind of deployment platform."
-- [spring.io](https://spring.io/projects/spring-framework)

---

# Spring Framework

* "An inversion of control container and an application framework for the Java Platform"[^1]
* 16+ years old
* Many [modules](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/) and [side projects](https://spring.io/projects)

[^1]: [Wikipedia](https://en.wikipedia.org/wiki/Spring_Framework)

---

# Spring Boot

> "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can 'just run'."
-- [spring.io](https://spring.io/projects/spring-boot)

---

# Spring Boot - Features

[.build-lists: true]

* Create stand-alone Spring applications
* Embed Tomcat, Jetty or Undertow directly
* Provide opinionated 'starter' dependencies to simplify build configuration
* Automatically configure Spring and 3rd party libraries whenever possible
* Provide production-ready features such as metrics, health checks and externalized configuration

---

# Kotlin

[.build-lists: true]

* Programming language for JVM, Android, Browser, Native
* Concise, safe, interoperable, tool-friendly
* Developed by [JetBrains](https://www.jetbrains.com/)
* Open source since 2012
* Version 1.0 in Feb. 2016
* 2017: Google announced first class support for Kotlin on Android
* 2019: Kotlin is preferred language for Android development

---

# Kotlin by Example

* [Hello, World!](https://play.kotlinlang.org/byExample/01_introduction/01_Hello%20world)
* [Variables](https://play.kotlinlang.org/byExample/01_introduction/03_Variables)
* [Null Safety](https://play.kotlinlang.org/byExample/01_introduction/04_Null%20Safety)
* [Classes](https://play.kotlinlang.org/byExample/01_introduction/05_Classes), [Data Classes](https://play.kotlinlang.org/byExample/03_special_classes/01_Data%20classes)
* [Functions](https://play.kotlinlang.org/byExample/01_introduction/02_Functions), [Extention Functions](https://play.kotlinlang.org/byExample/04_functional/03_extensionFunctions)

---

# Kotlin - Extention Functions

> "Kotlin provides the ability to extend a class with new functionality without having to inherit from the class or use any type of design pattern such as Decorator."
-- [Kotlin Reference](https://kotlinlang.org/docs/reference/extensions.html)

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
    // same implementation as before ...
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
inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) { // no reflection!
        p = p.parent
    }
    return p as T?
}

// usage:
treeNode.findParentOfType<MyTreeNode>()
```

---

# Spring Boot with Kotlin

* [Official Support](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-kotlin) since Spring Boot 2.x
    *   Spring Boot 2.3.x supports Kotlin 1.3.x
    *   Spring Boot 2.4.x will support Kotlin 1.4.x
* Initial Project Setup via [Spring Initializr](https://start.spring.io/)

---

# Dedicated Support for Kotlin in Spring (Boot)

[.build-lists: true]

* [Null Safety](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html#kotlin-null-safety)
* [Extention Functions](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html#kotlin-extensions)
* [Bean Definition DSL](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html#kotlin-bean-definition-dsl)
* [Web Router DSL](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html#router-dsl)
* [Mock Web MVC DSL](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html#router-dsl)
* [Coroutines](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html#coroutines)

---

# [Demo](https://start.spring.io/)

---

# Spring Fu
## Incubator for Java and Kotlin Configuration DSL

> "designed to configure Spring Boot explicitly with code<br>in a declarative way with great discoverability thanks to<br>auto-completion"
-- [Spring Fu](https://github.com/spring-projects-experimental/spring-fu)

---

# Resources - Kotlin

* [kotlinlang.org](https://kotlinlang.org/docs/reference/)
* [Learn Kotlin by Example](https://play.kotlinlang.org/byExample/overview)
* [Kotlin for Java Developers](https://www.coursera.org/learn/kotlin-for-java-developers) (Coursera)
* [Kotlin in Action](https://www.manning.com/books/kotlin-in-action) (Manning)

---

# Resources - Spring Boot with Kotlin

* [Spring Framework Reference](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html#kotlin)
* [spring.io Tutorial](https://spring.io/guides/tutorials/spring-boot-kotlin/)
* [The State of Kotlin Support in Spring](https://youtu.be/j2OEtSO2gvM) (YouTube)

---

# Slides

![inline](img/QR-Speakerdeck.png)

### <https://speakerdeck.com/stefanscheidt/spring-boot-with-kotlin>

---

# Sources

![inline](img/QR-GitHub.png)

### <https://github.com/stefanscheidt/spring-boot-kotlin>

---

# Thank you!

### Contact: `stefanscheidt` on [Twitter](https://twitter.com/stefanscheidt) and [GitHub](https://github.com/stefanscheidt)
