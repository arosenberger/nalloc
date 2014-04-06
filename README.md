Allocation Free Options in Scala
======

The goal of this project is to provide the following constructs:
- Allocation free option types for both primitives and reference types
- Higher order functions that are inlined by a macro expansion during compilation

Its functionality is based in part on the excellent work Paul Phillips contributed to 2.11.0-M5 to make extractors name based.
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

####Factory Methods

Each primitive type has a direct constructor, e.g. `OptionalInt(5)`. For convenience, there are overloaded factory methods in the `Optional` companion object to allow for construction of the appropriate type. For example
```
scala> import org.nalloc.bitb.kcits.optional._
import org.nalloc.bitb.kcits.optional._

scala> val b = Optional(5.toByte)
b: org.nalloc.bitb.kcits.optional.OptionalByte = 5

scala> val l = Optional(5L)
l: org.nalloc.bitb.kcits.optional.OptionalLong = 5
```

####Higher Order Functions

Each higher order function offers the same characteristics:
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

    val y = Optional(x).flatMap(x => Optional(List(x)))
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

- `getOrElse[A](f: => A): A` If `A`'s value is the sentinel for that type, evaluates and returns the default. Otherwise returns the value held in the Optional wrapper.
```
    Optional(x).getOrElse("foo")

    OptionalInt(x).getOrElse(15)
```

- `orElse[A](f: => OptionalTypeForA): OptionalTypeForA` If `A`'s value is the sentinel for that type, evaluates and returns the default. Otherwise returns the original Optional wrapper.
```
    Optional(x).orElse(Optional("foo"))

    OptionalInt(x).orElse(OptionalInt(15))
```

- `fold[B](ifEmpty: => B)(f: A => B): A` If `A`'s value is the sentinel for that type, evaluates and returns the default. Otherwise applies `f` to the underlying value.
```
    Optional(x).fold("foo")(_ + "bar)

    OptionalInt(x).fold(15)(x => x + 1)
```

- `forAll[A](f: A => Boolean): Boolean` If `A`'s value is the sentinel for that type, returns true. Otherwise evaluates the passed in function.
```
    Optional(x).forAll(_ == "foo")

    OptionalInt(x).forAll(_ % 2 == 0)
```

- `collect[B](pf: PartialFunction[A, B]): OptionalTypeForB` If `A`'s value is the sentinel for that type, the function is not applied and instead the empty OptionalTypeB is returned. Otherwise evaluates the passed in partial function, defaulting to the empty OptionalTypeB if an explicit default was not provided.
```
    Optional(x).collect {
    	case x if x.length % 2 == 0 => x
    	case _ => x.substring(1) // can supply an explicit default
    }

    OptionalInt(x).collect{
    	case x if x % 2 == 0 => x // OK not to supply explicit default, OptionalInt.empty will be added as a default
    }
```

Requirements
------

The project is built against Scala 2.11.0-RC1 and uses Name Based Extractors which appeared in 2.11.0-M5.

SBT Dependency
------

libraryDependencies += "org.nalloc" %% "optional" % "0.1.0-SNAPSHOT"

Getting Started
------

import org.nalloc.bitb.kcits.optional._

Remarks
------

Comments, feedback, bugs and feature suggestions are all welcome. I am considering adding additional classes with unsigned semantics and some collection support to get around the issue with using value classes in e.g. Arrays causing them to be allocated.
