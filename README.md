# clojure-collections
[![Powered by KineticFire Labs](https://img.shields.io/badge/Powered_by-KineticFire_Labs-CDA519?link=https%3A%2F%2Flabs.kineticfire.com%2F)](https://labs.kineticfire.com/)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Clojars Project](https://img.shields.io/clojars/v/com.kineticfire/collections.svg)](https://clojars.org/com.kineticfire/collections)
<p></p>

Clojure/ClojureScript utilities for collections

# Contents
1. [Motivation](#motivation)
2. [Installation](#installation)
3. [Usage](#usage)
4. [License](#license)


# Motivation

This library provides functions for operating on collections.  The library focuses at the intersection of (1) 
simplifying common, multistep operations on collections and (2) increasing code maintainability (e.g., the ease with 
which software can be understood and modified) for those operations.

A trite example: `(seq x)` is the [recommended idiom for testing that a sequence is not empty](https://clojuredocs.org/clojure.core/empty_q).
However, `(not-empty? x)` is more readable.  While senior Clojurians readily recognize `(seq x)` as asking 
if `x` is not empty, junior developers may not; in practice, most development teams do not consist of all senior-level 
Clojure experts.  Even for senior Clojurians, `(not-empty? x)` reduces the cognitive load when quickly scanning through 
code or after long hours of pouring through many lines of code. 


# Installation

## Leiningen/Boot

```
[com.kineticfire/collections "2.0.0"]
```

## Clojure CLI/deps.edn

```
com.kineticfire/collections {:mvn/version "2.0.0"}
```

## Gradle

```
implementation("com.kineticfire:collections:2.0.0")
```

## Maven

```
<dependency>
  <groupId>com.kineticfire</groupId>
  <artifactId>collections</artifactId>
  <version>2.0.0</version>
</dependency>
```

# Usage

1. [collections.collection](#collectionscollection)
   1. [not-empty?](#not-empty)
   2. [contains-value?](#contains-value)
   3. [not-contains-value?](#not-contains-value)
   3. [duplicates](#duplicates)
   4. [duplicates?](#duplicates-1)
   5. [not-duplicates?](#not-duplicates)
   5. [assoc-in](#assoc-in)
   6. [dissoc-in](#dissoc-in)
2. [collections.set](#collectionsset)
   1. [symmetric-difference](#symmetric-difference)


## collections.collection

1. [not-empty?](#not-empty)
2. [contains-value?](#contains-value)
3. [not-contains-value?](#not-contains-value)
3. [duplicates](#duplicates)
4. [duplicates?](#duplicates-1)
5. [not-duplicates?](#not-duplicates)
5. [assoc-in](#assoc-in)
6. [dissoc-in](#dissoc-in)


### not-empty?

```clojure
(not-empty? coll)
```

Returns boolean 'true' if the collection `coll` is not empty and 'false' otherwise.  Suitable for vectors, lists,
maps, and strings.  Uses an implementation with the recommended idiom `(seq coll)` but is more readable, regardless of
experience.

```clojure
(not-empty? [1])
;;=> true

(not-empty? [])
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

### contains-value?

```clojure
(contains-value? coll val) 
```

Returns boolean 'true' if the value `val` is contained in the collection `coll` and 'false' otherwise.  For a map,
searches values at the current level only.

Where `(clojure.core/contains? coll key)` checks if the key is in the collection, this function tests if a *value* is
contained in the collection.

```clojure
(contains-value? [1 2 3] 2)
;;=> true

(contains-value? [1 2 3] 4)
;;=> false

(contains-value? {:a 1} 1)
;;=> true

(contains-value? {:a 1} 2)
;;=> false

;; for a map, searches current level of keys only
(contains-value? {:a {:b 2}} 2)
;;=> false
```

### not-contains-value?

```clojure
(not-contains-value? coll val) 
```

Returns boolean 'false' if the value `val` is not contained in the collection `coll` and 'false' otherwise.  For a
map, searches values at the current level only.

Where `(not (clojure.core/contains? coll key))` checks if the *key* is not in the collection, this function tess if a
*value* is not contained in the collection.

```clojure
(not-contains-value? [1 2 3] 2)
;;=> false

(not-contains-value? [1 2 3] 4)
;;=> true

(not-contains-value? {:a 1} 1)
;;=> false

(not-contains-value? {:a 1} 2)
;;=> true

;; for a map, searches current level of keys only
(not-contains-value? {:a {:b 2}} 2)
;;=> true
```

### duplicates

```clojure
(duplicates coll)
```

Returns a vector of duplicates found in the collection `coll`.  If no duplicates, then returns an empty vector.  For
a map, searches values at the current level only.

Where `(clojure.core/distinct coll)` returns a lazy sequence of elements with duplicates removed, this function returns a
vector of the duplicates.

```clojure
(duplicates [1 2 2 3 3])
;;=> [2 3]

(duplicates [1 2 3])
;;=> []

(duplicates {:a 1 :b 2 :c 2 :d 3 :e 3})
;;=> [2 3]

(duplicates {:a 1 :b 2 :c 3})
;;=> []

;; for a map, searches current level of keys only
(duplicates {:z {:a 1 :b 2 :c 2 :d 3 :e 3}})
;;=> []
```

### duplicates?

```clojure
(duplicates? coll)
```

Returns boolean 'true' if the collection `coll` contains at least one duplicate and 'false' otherwise.  For a map,
searches values at the current level only.

Similar to `(clojure.core/distinct? coll)`, but its values are taken separately (e.g., not in a collection) while this
function operates on collections.

```clojure
(duplicates? [1 2 2 3 3])
;;=> true

(duplicates? [1 2 3])
;;=> false

(duplicates? {:a 1 :b 2 :c 2 :d 3 :e 3})
;;=> true

(duplicates? {:a 1 :b 2 :c 3})
;;=> false

;; for a map, searches current level of keys only
(duplicates? {:z {:a 1 :b 2 :c 2 :d 3 :e 3}})
;;=> false
```

### not-duplicates?

```clojure
(not-duplicates? coll)
```

Returns boolean 'true' if the collection `coll` does not contain a duplicate and 'false' otherwise.  For a map,
searches values at the current level only.

Similar to `(not (clojure.core/distinct? coll))`, but its values are taken separately (e.g., not in a collection)
while this function operates on collections.

```clojure
(not-duplicates? [1 2 2 3 3])
;;=> false

(not-duplicates? [1 2 3])
;;=> true

(not-duplicates? {:a 1 :b 2 :c 2 :d 3 :e 3})
;;=> false

(not-duplicates? {:a 1 :b 2 :c 3})
;;=> true

;; for a map, searches current level of keys only
(not-duplicates? {:z {:a 1 :b 2 :c 2 :d 3 :e 3}})
;;=> true
```


### assoc-in

```clojure
(assoc-in m ks v) (assoc-in m ks-v-seq)
```

Associates a value in a nested associative structure as with `(clojure.core/assoc-in m ks v)`, but also accepts a 
sequence of key sequence / value pairs to associate multiple values in one call.  If any levels do not exist, hash-maps 
will be created.

For signature 'm ks v': associates the value `v` in the nested associative structure `m` at key sequence `ks` exactly
as `(clojure.core/assoc-in m ks v)`.  For signature 'm ks-v-seq': behaves as above with `ks-v-seq` as a sequence with
one or more sequences of a key sequence and one value to associate multiple values in one call.

Calling `(assoc-in m ks v)` is equivalent to calling `(clojure.core/assoc-in m ks v)`.  Calling `(assoc-in m ks-v-seq)`
is equivalent calling `(clojure.core/assoc-in m ks v)` with a reduce function to accumulate the results of  associating 
multiple key sequence / value pairs.

```clojure
(def data {:a {:b 1}})

;; calling (assoc-in m ks v) calls clojure.core/assoc-in to associate one new entry
(assoc-in data [:a :c] 2)
;;=> {:a {:b 1 :c 2}}

;; calling (assoc-in m ks-v-seq) associates one or more new entries
(assoc-in data [ [[:a :c] 2] [[:a :d :e] 3] ])
;;=> {:a {:b 1 :c 2 :d {:e 3}}}
```

### dissoc-in

```clojure
(dissoc-in m ks)
```

Disassociates a value in a nested associative structure `m`, where `ks` is either a sequence of keys or a sequence
of key sequences.

Calling this function where `ks` is a sequence of keys is equivalent to removing the property at ks with a combination
of `clojure.core/update-in` and `clojure.core/dissoc`.  Calling this function with a sequence of a sequence of keys is
equivalent to using a reduce function to accumulate the results from removing multiple sequences of keys using
'update-in' and `dissoc`.

```clojure
(def data {:a {:b 1 :c 2}})

;; remove a single entry
(dissoc-in data [:a :b])
;;=> {:a {:c 2}}

;; remove multiple entries
(dissoc-in data [[:a :b] [:a :c]])
;;=> {:a {}}
```

## collections.set

1. [symmetric-difference](#symmetric-difference)

### symmetric-difference

```clojure
(symmetric-difference set1 set2)
```
Returns a set that is the symmetric difference between the first set `set1` and second set `set2`.  That is, the
returned set contains all the values that are present in one set but not the other.

```clojure
(symmetric-difference #{} #{})
;;=> #{}

(symmetric-difference #{1} #{})
;;=> #{1}

(symmetric-difference #{} #{1})
;;=> #{1}

(symmetric-difference #{1 2 3} #{1 2 4})
;;=> #{3 4}
```


# License
The *clojure-collections* project is released under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
