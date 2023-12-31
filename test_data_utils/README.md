# BaseBuilder

This is a utility for reducing test data builder boilerplate. It takes care of creating basic setter methods for the data's properties, and defining the `build()` method.

It's pretty restricted and ugly, right now it assumes that the builder data type will be a data class (and even then, it only pays attention to the primary constructor props).

## Usage

Create an annotated TestDataBuilder types:
```kotlin
@BaseBuilder
abstract class AbstractFooBuilder: TestDataBuilder<Foo>()
```

Create final builder type from generated Base* builder:
```kotlin
class FooBuilder: BaseAbstractFooBuilder<FooBuilder>() {
    override var data = Foo(
        bar = "default value",
        baz = 123
    )
}
```
- Yes, the `FooBuilder` type arg here is super ugly (please find a better way!!!)
  - even worse, the type param is `Any` lmao
  - but if you want things to work you should use the same derived type (e.g. FooBuilder in this case)
  - the purpose is to have the generated setter methods return the child type

the Base* builder has the setter methods that you can now use:
```kotlin
val testFoo = FooBuilder().withBar("hello").withBaz(456).build()
```
