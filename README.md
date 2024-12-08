# clojure-collections
[![Powered by KineticFire Labs](https://img.shields.io/badge/Powered_by-KineticFire_Labs-CDA519?link=https%3A%2F%2Flabs.kineticfire.com%2F)](https://labs.kineticfire.com/)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Clojars Project](https://img.shields.io/clojars/v/com.kineticfire/collections.svg)](https://clojars.org/com.kineticfire/collections)
<p></p>

Clojure/ClojureScript utilities for collections

# Contents
1. [Installation](#installation)
2. [Usage](#usage)
3. [License](#license)


# Installation

## Leiningen/Boot

```
[com.kineticfire/collections "1.0.0"]
```

## Clojure CLI/deps.edn

```
com.kineticfire/collections {:mvn/version "1.0.0"}
```

## Gradle

```
implementation("com.kineticfire:collections:1.0.0")
```

## Maven

```
<dependency>
  <groupId>com.kineticfire</groupId>
  <artifactId>collections</artifactId>
  <version>1.0.0</version>
</dependency>
```

# Usage

1. [collections.collection](#collectionscollection)
   1. [not-empty?](#not-empty)
1. [collections.set](#collectionsset)
   1. [symmetric-difference](#symmetric-difference)


## collections.collection

### not-empty?
```
(not-empty? [1])
;;=> true

(not-empty? [])
;;=> false

(not-empty? '(1))
;;=> true

(not-empty? '())
;;=> false

(not-empty? {:a 1})
;;=> true

(not-empty? {})
;;=> false

(not-empty? "hello")
;;=> true

(not-empty? "")
;;=> false
```

## collections.set

1. [symmetric-difference](#symmetric-difference)

### symmetric-difference

```
(symmetric-difference #{} #{}
;;=> #{}

(symmetric-difference #{1} #{}
;;=> #{1}

(symmetric-difference #{} #{1}
;;=> #{1}

(symmetric-difference #{1 2 3} #{ 1 2 4}
;;=> #{3 4}

```


# License
The *clojure-collections* project is released under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
