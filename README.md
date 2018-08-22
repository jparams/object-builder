# Object Builder

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.jparams/object-builder/badge.svg)](https://search.maven.org/search?q=g:com.jparams%20AND%20a:object-builder)
 [![Build Status](https://travis-ci.org/jparams/object-builder.svg?branch=master)](https://travis-ci.org/jparams/jparams-junit4)

## Getting Started

### Get Object Builder

Maven:
```
<dependency>
    <groupId>com.jparams</groupId>
    <artifactId>object-builder</artifactId>
    <version>1.x.x</version>
</dependency>
```

Gradle:
```
compile 'com.jparams:object-builder:1.x.x'
```

### What is Object Builder?
Object Builder is a highly configurable and robust library for building instances of objects. Just supply a class and Object Builder will supply an 
instance.

Object Builder works using a combination of constructor and field injection.


### Build an Object
Building an object is easy, just configure the builder and let it do the job! Here is very simple example:

```java
public class Application {
    public static void main(String[] args) {
        final ObjectBuilder objectBuilder = ObjectBuilder.withDefaultConfiguration(InjectionStrategy.FIELD_INJECTION);
        final Person person = objectBuilder.buildInstanceOf(Person.class).get();
    }
    
    public static class Person {
        private String name;
        private LocalDate dob;
        private List<Address> addresses;
    }
    
    public static class Address {
        private String line1;
        private String line2;
        private String city;
    }
}
```

## Compatibility
This library is compatible with Java 8 and above.

## License
[MIT License](http://www.opensource.org/licenses/mit-license.php)

Copyright 2017 JParams

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
