;; (c) Copyright 2023-2024 KineticFire. All rights reserved.
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;;     http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.


;; KineticFire Labs: https://labs.kineticfire.com
;;	   project site: https://github.com/kineticfire-labs/clojure-collections


(ns kineticfire.collections.collection
  (:gen-class))


(defn not-empty?
  "Returns boolean 'true' if the collection `col` is not empty and 'false' otherwise.  Suitable for vectors, lists,
  maps, and strings.  Uses an implementation with the recommended idiom 'seq col' but is more readable, regardless of
  experience."
  [col]
  (not (boolean (seq col))))


(defn contains-value?
  "Returns boolean 'true' if the value `val` is contained in the collection `col` and 'false' otherwise.  For a map,
  searches values at the current level only."
  [col val]
  (if (map? col)
    (boolean (some #(= % val) (vals col)))
    (boolean (some #(= % val) col))))


(defn find-duplicates
  "Returns a vector of duplicates found in the collection 'col'.  If no duplicates, then returns an empty vector.  For
  a map, searches values at the current level only."
  [col]
  (if (map? col)
    (let [vals (vals col)
          freqs (frequencies vals)]
      (->> freqs
           (filter #(> (val %) 1))
           (map key)
           (into [])))
    (let [freqs (frequencies col)]
      (->> freqs
           (filter #(> (val %) 1))
           (map key)
           (into [])))))


(defn duplicates?
  "Returns boolean 'true' if the collection `col` contains at least one duplicate and 'false' otherwise.  For a map,
  searches values at the current level only.

  Similar to 'clojure.core/distinct?', but its values are taken separately (e.g., not in a collection) while this
  function operates on collections."
  [col]
  (not (empty? (find-duplicates col))))


(defn assoc-in
  "Associates a value in a nested associative structure as with 'clojure.core/assoc-in', but also accepts a sequence of
  key sequence / value pairs to associate multiple values in one call.  If any levels do not exist, hash-maps will be
  created.

  For signature 'm ks v': associates the value `v` in the nested associative structure `m` at key sequence `ks` exactly
  as 'clojure.core/assoc-in'.  For signature 'm ks-v-seq': behaves as above with `ks-v-seq` as a sequence with one or
  more sequences of a key sequence and one value to associate multiple values in one call.

  Calling (assoc-in m ks v) is equivalent to calling (clojure.core/assoc-in m ks v).  Calling (assoc-in m ks-v-seq) is
  equivalent to calling clojure.core/assoc-in with a reduce function to accumulate the results of associating multiple
  key sequence / value pairs."
  ([m ks v]
   (clojure.core/assoc-in m ks v))
  ([m ks-v-seq]
   (reduce (fn [acc single-ks-v]
             (let [ks (first single-ks-v)
                   v (last single-ks-v)]
               (clojure.core/assoc-in acc ks v)))
           m
           ks-v-seq)))


(defn dissoc-in
  "Disassociates a value in a nested associative structure `m`, where `ks` is either a sequence of keys or a sequence
  of key sequences.

  Calling this function where `ks` is a sequence of keys is equivalent to removing the property at ks with a combination
  of 'update-in' and 'dissoc'.  Calling this function with a sequence of a sequence of keys is equivalent to using a
  reduce function to accumulate the results from removing multiple sequences of keys using 'update-in' and 'dissoc'."
  [m ks]
  (if (empty? ks)
    (dissoc m)
    (if (coll? (first ks))
      (reduce (fn [acc single-key-seq]
                (dissoc-in acc single-key-seq))
              m
              ks)
      (if (= (count ks) 1)
        (dissoc m (first ks))
        (update-in m (butlast ks) dissoc (last ks))))))
