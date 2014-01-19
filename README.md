nalloc: Allocation Free Option Types in Scala
------

The goal of this project is to provide the following constructs:
- allocation free option types for both primitives and reference types
- higher order functions that are inlined by a macro expansion during compilation

Optional Primitives
------

Each primitive (save for Char and Boolean currently) has its own dedicated optional type. Null values are represented by sentinels:
- In the case of integral types, Type.MinValue
- In the case of floating point types, NaN

Higher Order Functions
------

The following functions are currently available in the master branch:
- `map(f: A => B)` If `A`'s value in the sentinel for that type, the map function is not applied and instead the sentinel value for type `B` is returned

Example Usages:
```
def doWork(x: Int) {
    x match {
        case OptionalLong(value) => // no allocation or boxing
        case _                   =>
    }

    val y = x.map(_ + 1) // no allocation or boxing
}
```

Requirements
------

The project is built against Scala 2.11.0-M7 and requires 2.11.0-M5 or greater
