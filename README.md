```
+---------------------------------+
|   __   _____   _   __  ___      |
|   \ \ / / _ \ | | / / / _ \     |
|    \ V / /_\ \| |/ / / /_\ \    |
|     \ /|  _  ||    \ |  _  |    |
|     | || | | || |\  \| | | |    |
|     \_/\_| |_/\_| \_/\_| |_/    |
|                                 |
|  Yet Another Kotlin Assertions  |
|                                 |
+---------------------------------+
```


#### Why yet another Kotlin assertions library

1. Concise and clear assertion code on call site,
   using infix notation and not overcomplicated.
   
2. Clear fail reports (not like “expected true but was false”),
   to facilitate fail investigetion.
   
3. Separate test assertions library from test runners library
   and allowing to use assertions with different runners.
   

#### Platforms

The version 1 is being developed for JVM (version 11+).

The version 2 is planned to be multi-platform.


#### Status

Under construction. 


#### Build

During development, I use IntelliJ Idea to code and build.

Additionally, Gradle is used to build and deploy artifacts.
In order to use Gradle wrapper just prepare it by the existing gradle
```bash
   gradle wrapper
```
or add the additional repository:
```bash
   git clone git@github.com:leo-from-spb/gradle.git
```                                                     
