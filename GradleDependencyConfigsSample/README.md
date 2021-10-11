# Gradle Dependency Configurations

Android sample app to learn about the gradle dependency configurations `compileonly` and `runtimeonly` (`implementation` and `api` are pretty common, so I didn't touch on them in this sample).

`compileonly` is used for dependencies that are required only at compile time, but not at runtime.
`runtimeonly` is used for dependencies that are required only at runtime, but not at compile time.

In this sample, I take logging as an example. The highlevel API of a [`Logger`](https://github.com/husaynhakeem/android-playground/blob/master/GradleDependencyConfigsSample/compileonly/src/main/java/com/husaynhakeem/compileonly/Logger.kt) is defined in the module `compileonly` (quite original name!), and two implementations of this logging class are defined in two other separate modules: `runtimeonly` and `otherruntimeonly`. The main module of the sample app uses an instance of this `Logger`.

The main module uses the Gradle dependency configuration `compileonly` with the `compileonly` module, as it only requires the `Logger`'s API to compile. At runtime, one of the implementation modules provide the concrete implementation of the `Logger`, thus the main module uses one of these modules with the Gradle dependency configuration `runtimeonly`.
