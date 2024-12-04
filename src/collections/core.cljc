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
;;	   Project site: https://github.com/kineticfire-labs/clojure-collections


(ns collections.core
  (:gen-class))



(defn assoc-in
  "Associates a value in a nested associative structure.  If any levels do not exist, hash-maps will be created.

  For signature 'm ks v': associates the value `v` in the nested associative structure `m` at key sequence `ks`.  For
  signature 'm ks-v-seq': behaves as above with `ks-v-seq` as a sequence with one or more sequences of a key sequence
  and one value.

  Calling (assoc-in m ks v) is equivalent to calling (clojure.core/assoc-in m ks v).  Calling (assoc-in m ks-v-seq) is
  equivalent to calling clojure.core/assoc-in with a reduce function to accumulate the results of associating multiple
  key sequence / value pairs."
  ([m ks-v-seq]
   (reduce (fn [acc single-ks-v]
             (let [ks (first single-ks-v)
                   v (last single-ks-v)]
               (clojure.core/assoc-in acc ks v)))
           m
           ks-v-seq))
  ([m ks v]
   (clojure.core/assoc-in m ks v)))


(defn dissoc-in
  "Disassociates a value in a nested associative structure `m`, where `ks` is either a sequence of keys or a sequence
  of key sequences.

  Calling this function where `ks` is a sequence of keys is equivalent to removing the property at ks with a combination
  of 'update-in' and 'dissoc'.  Calling this function with a sequence of a sequence of keys is equivalent to using a
  reduce function to accumulate the results  from removing multiple sequences of keys using 'update-in' and 'dissoc'."
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
