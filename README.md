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

Each higher order function offers the same functionality:
- Primitives will not box to their object counterparts
- None of the OptionalX instances will allocate, subject to the limitations described in http://docs.scala-lang.org/overviews/core/value-classes.html
- No anonymous functions / closures will be created for the lambdas or method values passed into the higher order functions

The following functions are currently available in the master branch:

- `map[B](f: A => B): OptionalTypeForB` If `A`'s value is the sentinel for that type, the function is not applied and instead the empty OptionalTypeB is returned
```
    val y = OptionalInt(x).map(_ + 1)

    val y = Optional(x).map(List(_))
```

- `flatMap[B](f: A => OptionalTypeForB): OptionalTypeForB` If `A`'s value is the sentinel for that type, the function is not applied and instead the empty OptionalTypeB is returned
```
    val y = OptionalInt(x).flatMap(x => OptionalLong(x + 1L))

    val y = Optional(x).map(x => Optional(List(x)))
```

- `foreach[A](f: A => Unit)` If `A`'s value is the sentinel for that type, the foreach block is not executed.
```
    Optional(x).foreach(println)

    OptionalInt(x).foreach(println)
```

- `exists[A](f: A => Boolean): Boolean` If `A`'s value is the sentinel for that type, returns false. Otherwise evaluates the passed in function.
```
    Optional(x).exists(_ == "foo")

    OptionalInt(x).exists(_ % 2 == 0)
```

- `filter[A](f: A => Boolean): OptionalType` If `A`'s value is the sentinel for that type or the passed in function evaluates false, returns the empty optional type. Otherwise returns the original optional type.
```
    Optional(x).filter(_ == "foo")

    OptionalInt(x).filter(_ % 2 == 0)
```

- `orElse[A](f: => A): A` If `A`'s value is the sentinel for that type, evaluates and returns the default. Otherwise returns the value held in the Optional wrapper.
```
    Optional(x).orElse("foo")

    OptionalInt(x).orElse(15)
```

Requirements
------

The project is built against Scala 2.11.0-M7 and requires 2.11.0-M5 or greater
