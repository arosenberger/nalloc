Allocation Free Options in Scala
======

The goal of this project is to provide the following constructs:
- Allocation free option types for both primitives and reference types
- Higher order functions that are inlined by a macro expansion during compilation

Its functionality is based on the excellent work Paul Phillips contributed to 2.11.0-M5 to make extractors name based.
Optional Primitives
------

Each primitive (save for Char and Boolean currently) has its own dedicated optional type. Null/empty values are represented by sentinels:
- In the case of integral types, Type.MinValue
- In the case of floating point types, NaN

Optional Reference Types
------

Reference types share an optional implementation much like Scala's `Option[A]`. The sentinel value for reference types is `null`.

Examples
------
Pattern matching is similar to that of Scala's `Option` type
```
    x match {
        case OptionalLong(value) => // no allocation or boxing, matches if x != Long.MinValue
        case _                   =>
    }

    x match {
        case Optional(value) => // no allocation, matches if x != null
        case _               =>
    }
```

####Higher Order Functions

The following functions are currently available in the master branch:

- `map[B](f: A => B): B` If `A`'s value is the sentinel for that type, the map function is not applied and instead the sentinel value for type `B` is returned
```
    // no allocation of OptionalInt
    // no boxing of x
    // no anonymous function created
    val y = OptionalInt(x).map(_ + 1)

    // no allocation of Optional
    // no anonymous function created
    // x will still be wrapped in an array due to the varargs signature of List.apply
    val y = Optional(x).map(List(_))
}
```

- `foreach[A](f: A => Unit)` If `A`'s value is the sentinel for that type, the foreach block is not executed.
```
    // no allocation of Optional
    // no anonymous function created
    Optional(x).foreach(println)

    // no allocation of OptionalInt
    // no anonymous function created
    // x will still box to Integer so it can be passed to println
    OptionalInt(x).foreach(println)
}
```

Requirements
------

The project is built against Scala 2.11.0-M7 and requires 2.11.0-M5 or greater
