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


(ns kineticfire.collections.collection-test
  (:require [clojure.test                       :refer :all]
            [kineticfire.collections.collection :as kf-coll]))



(defn perform-not-empty?-test
  [coll fn-expected]
  (let [v (kf-coll/not-empty? coll)]
    (is (boolean? v))
    (is (fn-expected v))))


(deftest not-empty?-test
  ;;
  ;; vector
  (testing "vector, empty"
    (perform-not-empty?-test [] false?))
  (testing "vector, not empty"
    (perform-not-empty?-test [1] true?))
  ;;
  ;; list
  (testing "list, empty"
    (perform-not-empty?-test '() false?))
  (testing "list, not empty"
    (perform-not-empty?-test '(1) true?))
  ;;
  ;; set
  (testing "set, empty"
    (perform-not-empty?-test #{} false?))
  (testing "set, not empty"
    (perform-not-empty?-test #{1} true?))
  ;;
  ;; map
  (testing "map, empty"
    (perform-not-empty?-test {} false?))
  (testing "map, not empty"
    (perform-not-empty?-test {:a 1} true?))
  ;;
  ;; string
  (testing "string, empty"
    (perform-not-empty?-test "" false?))
  (testing "string, not empty"
    (perform-not-empty?-test "test" true?)))


(defn perform-contains?-test
  [coll key-or-seq fn-expected]
  (let [v (kf-coll/contains? coll key-or-seq)]
    (is (boolean? v))
    (is (fn-expected v))))


(deftest contains?-test
  ;;
  ;; map
  (testing "map: key not coll, first level, found"
    (perform-contains?-test {:a {:b 1}} :a true?))
  (testing "map: key not coll, first level, not found"
    (perform-contains?-test {:a {:b 1}} :b false?))
  (testing "map: key coll, first level, found"
    (perform-contains?-test {:a {:b 1}} [:a] true?))
  (testing "map: key coll, first level, not found"
    (perform-contains?-test {:a {:b 1}} [:c] false?))
  (testing "map: key coll, second level, found"
    (perform-contains?-test {:a {:b 1}} [:a :b] true?))
  (testing "map: key coll, second level, not found"
    (perform-contains?-test {:a {:b 1}} [:a :c] false?))
  (testing "map: key coll, third level, found"
    (perform-contains?-test {:a {:b {:c 1}}} [:a :b :c] true?))
  (testing "map: key coll, third level, not found"
    (perform-contains?-test {:a {:b {:c 1}}} [:a :b :d] false?))
  ;;
  ;; vector
  (testing "vector: key not coll, first level, found"
    (perform-contains?-test ["a" "b" "c"] 0 true?))
  (testing "vector: key not coll, first level, not found"
    (perform-contains?-test ["a" "b" "c"] 3 false?))
  (testing "vector: key coll, first level, found"
    (perform-contains?-test ["a" "b" "c"] [0] true?))
  (testing "vector: key coll, first level, not found"
    (perform-contains?-test ["a" "b" "c"] [3] false?))
  (testing "vector: key not coll, first level, found"
    (perform-contains?-test [["a" "b"] ["c" "d"]] 0 true?))
  (testing "vector: key not coll, first level, not found"
    (perform-contains?-test [["a" "b"] ["c" "d"]] 2 false?))
  (testing "vector: key coll, first level, found"
    (perform-contains?-test [["a" "b"] ["c" "d"]] [0] true?))
  (testing "vector: key coll, first level, not found"
    (perform-contains?-test [["a" "b"] ["c" "d"]] [2] false?))
  (testing "vector: key coll, second level, found"
    (perform-contains?-test [["a" "b"] ["c" "d"]] [0 0] true?))
  (testing "vector: key coll, second level, not found"
    (perform-contains?-test [["a" "b"] ["c" "d"]] [0 2] false?))
  ;;
  ;; set
  (testing "set: key not coll, first level, found"
    (perform-contains?-test #{"a" "b" "c"} "a" true?))
  (testing "set: key not coll, first level, not found"
    (perform-contains?-test #{"a" "b" "c"} "d" false?))
  (testing "set: key coll, first level, found"
    (perform-contains?-test #{"a" "b" "c"} ["a"] true?))
  (testing "set: key coll, first level, not found"
    (perform-contains?-test #{"a" "b" "c"} ["d"] false?))
  ;
  ; string
  (testing "string: key not coll, first level, found"
    (perform-contains?-test "hello" 0 true?))
  (testing "string: key not coll, first level, not found"
    (perform-contains?-test "hello" 5 false?))
  ;;
  ;; mix
  (testing "mix: found first item available of each collection"
    (perform-contains?-test {:a {:b [1 #{"alpha" "bravo"}]}} [:a :b 1 "alpha" 0] true?))
  (testing "mix: found last item available of each collection"
    (perform-contains?-test {:a {:b [1 #{"alpha" "bravo"}]}} [:a :b 1 "alpha" 4] true?))
  (testing "mix: not found, last item available of each collection"
    (perform-contains?-test {:a {:b [1 #{"alpha" "bravo"}]}} [:a :b 1 "alpha" 5] false?)))


(defn perform-not-contains?-test
  [coll key-or-seq fn-expected]
  (let [v (kf-coll/not-contains? coll key-or-seq)]
    (is (boolean? v))
    (is (fn-expected v))))


(deftest not-contains?-test
  ;;
  ;; map
  (testing "map: key not coll, first level, found"
    (perform-not-contains?-test {:a {:b 1}} :a false?))
  (testing "map: key not coll, first level, not found"
    (perform-not-contains?-test {:a {:b 1}} :b true?))
  (testing "map: key coll, first level, found"
    (perform-not-contains?-test {:a {:b 1}} [:a] false?))
  (testing "map: key coll, first level, not found"
    (perform-not-contains?-test {:a {:b 1}} [:c] true?))
  (testing "map: key coll, second level, found"
    (perform-not-contains?-test {:a {:b 1}} [:a :b] false?))
  (testing "map: key coll, second level, not found"
    (perform-not-contains?-test {:a {:b 1}} [:a :c] true?))
  (testing "map: key coll, third level, found"
    (perform-not-contains?-test {:a {:b {:c 1}}} [:a :b :c] false?))
  (testing "map: key coll, third level, not found"
    (perform-not-contains?-test {:a {:b {:c 1}}} [:a :b :d] true?))
  ;;
  ;; vector
  (testing "vector: key not coll, first level, found"
    (perform-not-contains?-test ["a" "b" "c"] 0 false?))
  (testing "vector: key not coll, first level, not found"
    (perform-not-contains?-test ["a" "b" "c"] 3 true?))
  (testing "vector: key coll, first level, found"
    (perform-not-contains?-test ["a" "b" "c"] [0] false?))
  (testing "vector: key coll, first level, not found"
    (perform-not-contains?-test ["a" "b" "c"] [3] true?))
  (testing "vector: key not coll, first level, found"
    (perform-not-contains?-test [["a" "b"] ["c" "d"]] 0 false?))
  (testing "vector: key not coll, first level, not found"
    (perform-not-contains?-test [["a" "b"] ["c" "d"]] 2 true?))
  (testing "vector: key coll, first level, found"
    (perform-not-contains?-test [["a" "b"] ["c" "d"]] [0] false?))
  (testing "vector: key coll, first level, not found"
    (perform-not-contains?-test [["a" "b"] ["c" "d"]] [2] true?))
  (testing "vector: key coll, second level, found"
    (perform-not-contains?-test [["a" "b"] ["c" "d"]] [0 0] false?))
  (testing "vector: key coll, second level, not found"
    (perform-not-contains?-test [["a" "b"] ["c" "d"]] [0 2] true?))
  ;;
  ;; set
  (testing "set: key not coll, first level, found"
    (perform-not-contains?-test #{"a" "b" "c"} "a" false?))
  (testing "set: key not coll, first level, not found"
    (perform-not-contains?-test #{"a" "b" "c"} "d" true?))
  (testing "set: key coll, first level, found"
    (perform-not-contains?-test #{"a" "b" "c"} ["a"] false?))
  (testing "set: key coll, first level, not found"
    (perform-not-contains?-test #{"a" "b" "c"} ["d"] true?))
  ;
  ; string
  (testing "string: key not coll, first level, found"
    (perform-not-contains?-test "hello" 0 false?))
  (testing "string: key not coll, first level, not found"
    (perform-not-contains?-test "hello" 5 true?))
  ;;
  ;; mix
  (testing "mix: found first item available of each collection"
    (perform-not-contains?-test {:a {:b [1 #{"alpha" "bravo"}]}} [:a :b 1 "alpha" 0] false?))
  (testing "mix: found last item available of each collection"
    (perform-not-contains?-test {:a {:b [1 #{"alpha" "bravo"}]}} [:a :b 1 "alpha" 4] false?))
  (testing "mix: not found, last item available of each collection"
    (perform-not-contains?-test {:a {:b [1 #{"alpha" "bravo"}]}} [:a :b 1 "alpha" 5] true?)))


(defn perform-contains-value-test
  [coll search fn-expected]
  (let [v (kf-coll/contains-value? coll search)]
    (is (boolean? v))
    (is (fn-expected v))))


(deftest contains-value-test
  ;;
  ;; vector
  (testing "vector: contains"
    (perform-contains-value-test [1 2 3] 2 true?))
  (testing "vector: does not contain"
    (perform-contains-value-test [1 2 3] 0 false?))
  (testing "vector: empty"
    (perform-contains-value-test [] 2 false?))
  ;;
  ;; list
  (testing "list: contains"
    (perform-contains-value-test '(1 2 3) 2 true?))
  (testing "list: does not contain"
    (perform-contains-value-test '(1 2 3) 0 false?))
  (testing "list: empty"
    (perform-contains-value-test '() 2 false?))
  ;;
  ;; set
  (testing "set: contains"
    (perform-contains-value-test #{1 2 3} 2 true?))
  (testing "set: does not contain"
    (perform-contains-value-test #{1 2 3} 0 false?))
  (testing "set: empty"
    (perform-contains-value-test #{} 2 false?))
  (testing "map: search on key is'false'"
    (perform-contains-value-test {:a 1 :b 2} :a false?))
  (testing "map: contains value"
    (perform-contains-value-test {:a 1 :b 2} 2 true?))
  (testing "map: does not contain value"
    (perform-contains-value-test {:a 1 :b 2} 3 false?)))


(defn perform-not-contains-value-test
  [coll search fn-expected]
  (let [v (kf-coll/not-contains-value? coll search)]
    (is (boolean? v))
    (is (fn-expected v))))


(deftest contains-value-test
  ;;
  ;; vector
  (testing "vector: contains"
    (perform-not-contains-value-test [1 2 3] 2 false?))
  (testing "vector: does not contain"
    (perform-not-contains-value-test [1 2 3] 0 true?))
  (testing "vector: empty"
    (perform-not-contains-value-test [] 2 true?))
  ;;
  ;; list
  (testing "list: contains"
    (perform-not-contains-value-test '(1 2 3) 2 false?))
  (testing "list: does not contain"
    (perform-not-contains-value-test '(1 2 3) 0 true?))
  (testing "list: empty"
    (perform-not-contains-value-test '() 2 true?))
  ;;
  ;; set
  (testing "set: contains"
    (perform-not-contains-value-test #{1 2 3} 2 false?))
  (testing "set: does not contain"
    (perform-not-contains-value-test #{1 2 3} 0 true?))
  (testing "set: empty"
    (perform-not-contains-value-test #{} 2 true?))
  (testing "map: search on key is'false'"
    (perform-not-contains-value-test {:a 1 :b 2} :a true?))
  (testing "map: contains value"
    (perform-not-contains-value-test {:a 1 :b 2} 2 false?))
  (testing "map: does not contain value"
    (perform-not-contains-value-test {:a 1 :b 2} 3 true?)))


(defn perform-duplicates-test
  [coll expected]
  (let [v (kf-coll/duplicates coll)]
    (is (vector? v))
    (is (= (sort v) (sort expected)))))


(deftest duplicates-test
  ;;
  ;; vector
  (testing "vector, no duplicates, empty vector"
    (perform-duplicates-test [] []))
  (testing "vector, no duplicates, integer: populated vector"
    (perform-duplicates-test [1 2 3 10 4] []))
  (testing "vector, no duplicates, string: populated vector"
    (perform-duplicates-test ["alpha" "charlie" "bravo" "foxtrot" "kilo"] []))
  (testing "vector, one duplicate, integer"
    (perform-duplicates-test [1 2 3 10 3] [3]))
  (testing "vector, one duplicate, string"
    (perform-duplicates-test ["alpha" "charlie" "bravo" "alpha" "kilo"] ["alpha"]))
  (testing "vector, three duplicates, integer"
    (perform-duplicates-test [1 2 3 10 3 1 8 2] [3 1 2]))
  (testing "vector, three duplicates, string"
    (perform-duplicates-test ["alpha" "charlie" "bravo" "alpha" "kilo" "charlie" "bravo"] ["alpha" "charlie" "bravo"]))
  ;;
  ;; list
  (testing "list, no duplicates, empty vector"
    (perform-duplicates-test '() []))
  (testing "list, no duplicates, integer: populated list"
    (perform-duplicates-test '(1 2 3 10 4) []))
  (testing "list, no duplicates, string: populated list"
    (perform-duplicates-test '("alpha" "charlie" "bravo" "foxtrot" "kilo") []))
  (testing "list, one duplicate, integer"
    (perform-duplicates-test '(1 2 3 10 3) [3]))
  (testing "list, one duplicate, string"
    (perform-duplicates-test '("alpha" "charlie" "bravo" "alpha" "kilo") ["alpha"]))
  (testing "list, three duplicates, integer"
    (perform-duplicates-test [1 2 3 10 3 1 8 2] [3 1 2]))
  (testing "list, three duplicates, string"
    (perform-duplicates-test '("alpha" "charlie" "bravo" "alpha" "kilo" "charlie" "bravo") ["alpha" "charlie" "bravo"]))
  ;;
  ;; set - won't have duplicates, but the function still works
  (testing "set, no duplicates, empty vector"
    (perform-duplicates-test #{} []))
  (testing "set, no duplicates, integer: populated vector"
    (perform-duplicates-test #{1 2 3 10 4} []))
  ;;
  ;; map
  (testing "map, no duplicates, empty vector"
    (perform-duplicates-test {} []))
  (testing "map, no duplicates, integer: populated map"
    (perform-duplicates-test {:a 1, :b 2, :c 3, :d 4} []))
  (testing "map, no duplicates, string: populated vector"
    (perform-duplicates-test {:a "alpha", :b "charlie", :c "bravo", :d "foxtrot", :e "kilo"} []))
  (testing "map, one duplicate, integer"
    (perform-duplicates-test {:a 3, :b 2, :c 3, :d 4} [3]))
  (testing "map, one duplicate, string"
    (perform-duplicates-test {:a "alpha", :b "charlie", :c "bravo", :d "alpha", :e "kilo"} ["alpha"]))
  (testing "map, three duplicates, integer"
    (perform-duplicates-test {:a 1, :b 2, :c 3, :d 1, :e 2, :f 3, :g 0} [3 1 2]))
  (testing "map, three duplicates, string"
    (perform-duplicates-test {:a "alpha", :b "charlie", :c "bravo", :d "foxtrot", :e "alpha", :f "bravo", :g "golf", :h "charlie"} ["alpha" "charlie" "bravo"])))


(defn perform-duplicates?-test
  [coll fn-expected]
  (let [v (kf-coll/duplicates? coll)]
    (is (boolean? v))
    (is (fn-expected v))))


(deftest duplicates?-test
  ;;
  ;; vector
  (testing "vector, no duplicates: empty vector"
    (perform-duplicates?-test [] false?))
  (testing "vector, no duplicates, integer: populated vector"
    (perform-duplicates?-test [1 2 3 10 4] false?))
  (testing "vector, no duplicates, string: populated vector"
    (perform-duplicates?-test ["alpha" "charlie" "bravo" "foxtrot" "kilo"] false?))
  (testing "vector, one duplicate, integer"
    (perform-duplicates?-test [1 2 3 10 3] true?))
  (testing "vector, one duplicate, string"
    (perform-duplicates?-test ["alpha" "charlie" "bravo" "alpha" "kilo"] true?))
  (testing "vector, three duplicates, integer"
    (perform-duplicates?-test [1 2 3 10 3 1 8 2] true?))
  (testing "vector, three duplicates, string"
    (perform-duplicates?-test ["alpha" "charlie" "bravo" "alpha" "kilo" "charlie" "bravo"] true?))
  ;;
  ;; list
  (testing "list, no duplicates, empty vector"
    (perform-duplicates?-test '() false?))
  (testing "list, no duplicates, integer: populated list"
    (perform-duplicates?-test '(1 2 3 10 4) false?))
  (testing "list, no duplicates, string: populated list"
    (perform-duplicates?-test '("alpha" "charlie" "bravo" "foxtrot" "kilo") false?))
  (testing "list, one duplicate, integer"
    (perform-duplicates?-test '(1 2 3 10 3) true?))
  (testing "list, one duplicate, string"
    (perform-duplicates?-test '("alpha" "charlie" "bravo" "alpha" "kilo") true?))
  (testing "list, three duplicates, integer"
    (perform-duplicates?-test [1 2 3 10 3 1 8 2] true?))
  (testing "list, three duplicates, string"
    (perform-duplicates?-test '("alpha" "charlie" "bravo" "alpha" "kilo" "charlie" "bravo") true?))
  ;;
  ;; set - won't have duplicates, but the function still works
  (testing "set, no duplicates, empty vector"
    (perform-duplicates?-test #{} false?))
  (testing "set, no duplicates, integer: populated vector"
    (perform-duplicates?-test #{1 2 3 10 4} false?))
  ;;
  ;; map
  (testing "map, no duplicates, empty vector"
    (perform-duplicates?-test {} false?))
  (testing "map, no duplicates, integer: populated map"
    (perform-duplicates?-test {:a 1, :b 2, :c 3, :d 4} false?))
  (testing "map, no duplicates, string: populated vector"
    (perform-duplicates?-test {:a "alpha", :b "charlie", :c "bravo", :d "foxtrot", :e "kilo"} false?))
  (testing "map, one duplicate, integer"
    (perform-duplicates?-test {:a 3, :b 2, :c 3, :d 4} true?))
  (testing "map, one duplicate, string"
    (perform-duplicates?-test {:a "alpha", :b "charlie", :c "bravo", :d "alpha", :e "kilo"} true?))
  (testing "map, three duplicates, integer"
    (perform-duplicates?-test {:a 1, :b 2, :c 3, :d 1, :e 2, :f 3, :g 0} true?))
  (testing "map, three duplicates, string"
    (perform-duplicates?-test {:a "alpha", :b "charlie", :c "bravo", :d "foxtrot", :e "alpha", :f "bravo", :g "golf", :h "charlie"} true?)))


(defn perform-not-duplicates?-test
  [coll fn-expected]
  (let [v (kf-coll/not-duplicates? coll)]
    (is (boolean? v))
    (is (fn-expected v))))


(deftest not-duplicates?-test
  ;;
  ;; vector
  (testing "vector, no duplicates: empty vector"
    (perform-not-duplicates?-test [] true?))
  (testing "vector, no duplicates, integer: populated vector"
    (perform-not-duplicates?-test [1 2 3 10 4] true?))
  (testing "vector, no duplicates, string: populated vector"
    (perform-not-duplicates?-test ["alpha" "charlie" "bravo" "foxtrot" "kilo"] true?))
  (testing "vector, one duplicate, integer"
    (perform-not-duplicates?-test [1 2 3 10 3] false?))
  (testing "vector, one duplicate, string"
    (perform-not-duplicates?-test ["alpha" "charlie" "bravo" "alpha" "kilo"] false?))
  (testing "vector, three duplicates, integer"
    (perform-not-duplicates?-test [1 2 3 10 3 1 8 2] false?))
  (testing "vector, three duplicates, string"
    (perform-not-duplicates?-test ["alpha" "charlie" "bravo" "alpha" "kilo" "charlie" "bravo"] false?))
  ;;
  ;; list
  (testing "list, no duplicates, empty vector"
    (perform-not-duplicates?-test '() true?))
  (testing "list, no duplicates, integer: populated list"
    (perform-not-duplicates?-test '(1 2 3 10 4) true?))
  (testing "list, no duplicates, string: populated list"
    (perform-not-duplicates?-test '("alpha" "charlie" "bravo" "foxtrot" "kilo") true?))
  (testing "list, one duplicate, integer"
    (perform-not-duplicates?-test '(1 2 3 10 3) false?))
  (testing "list, one duplicate, string"
    (perform-not-duplicates?-test '("alpha" "charlie" "bravo" "alpha" "kilo") false?))
  (testing "list, three duplicates, integer"
    (perform-not-duplicates?-test [1 2 3 10 3 1 8 2] false?))
  (testing "list, three duplicates, string"
    (perform-not-duplicates?-test '("alpha" "charlie" "bravo" "alpha" "kilo" "charlie" "bravo") false?))
  ;;
  ;; set - won't have duplicates, but the function still works
  (testing "set, no duplicates, empty vector"
    (perform-not-duplicates?-test #{} true?))
  (testing "set, no duplicates, integer: populated vector"
    (perform-not-duplicates?-test #{1 2 3 10 4} true?))
  ;;
  ;; map
  (testing "map, no duplicates, empty vector"
    (perform-not-duplicates?-test {} true?))
  (testing "map, no duplicates, integer: populated map"
    (perform-not-duplicates?-test {:a 1, :b 2, :c 3, :d 4} true?))
  (testing "map, no duplicates, string: populated vector"
    (perform-not-duplicates?-test {:a "alpha", :b "charlie", :c "bravo", :d "foxtrot", :e "kilo"} true?))
  (testing "map, one duplicate, integer"
    (perform-not-duplicates?-test {:a 3, :b 2, :c 3, :d 4} false?))
  (testing "map, one duplicate, string"
    (perform-not-duplicates?-test {:a "alpha", :b "charlie", :c "bravo", :d "alpha", :e "kilo"} false?))
  (testing "map, three duplicates, integer"
    (perform-not-duplicates?-test {:a 1, :b 2, :c 3, :d 1, :e 2, :f 3, :g 0} false?))
  (testing "map, three duplicates, string"
    (perform-not-duplicates?-test {:a "alpha", :b "charlie", :c "bravo", :d "foxtrot", :e "alpha", :f "bravo", :g "golf", :h "charlie"} false?)))


(defn perform-assoc-in-m-ks-test
  [m ks v expected]
  (let [result (kf-coll/assoc-in m ks v)]
    (is (map? result))
    (is (= result expected))))


(defn perform-assoc-in-m-ks-v-coll-test
  [m ks-v-coll expected]
  (let [result (kf-coll/assoc-in m ks-v-coll)]
    (is (map? result))
    (is (= result expected))))


(deftest assoc-in-test
  ;;
  ;; m, ks, v
  (testing "m, ks as vector, v: add to existing structure"
    (perform-assoc-in-m-ks-test {:a {:b 1}} [:a :c] 2 {:a {:b 1 :c 2}}))
  (testing "m, ks as vector, v: need to create structure"
    (perform-assoc-in-m-ks-test {:a {:b 1}} [:a :c :d] 2 {:a {:b 1 :c {:d 2}}}))
  (testing "m, ks as list, v: add to existing structure"
    (perform-assoc-in-m-ks-test {:a {:b 1}} '(:a :c) 2 {:a {:b 1 :c 2}}))
  (testing "m, ks as list, v: need to create structure"
    (perform-assoc-in-m-ks-test {:a {:b 1}} '(:a :c :d) 2 {:a {:b 1 :c {:d 2}}}))
  ;;
  ;; m, ks-v-coll
  (testing "m, ks-v-coll as vectors: one item, add to existing structure"
    (perform-assoc-in-m-ks-v-coll-test {:a {:b 1}} [ [[:a :c] 2] ] {:a {:b 1 :c 2}}))
  (testing "m, ks-v-coll as vectors: two items, add to existing structure and create new structure"
    (perform-assoc-in-m-ks-v-coll-test {:a {:b 1}} [ [[:a :c] 2] [[:a :d :e] 3] ] {:a {:b 1 :c 2 :d {:e 3}}}))
  (testing "m, ks-v-coll as lists: one item, add to existing structure"
    (perform-assoc-in-m-ks-v-coll-test {:a {:b 1}} (list (list (list :a :c) 2) ) {:a {:b 1 :c 2}}))
  (testing "m, ks-v-coll as lists: two items, add to existing structure and create new structure"
    (perform-assoc-in-m-ks-v-coll-test {:a {:b 1}} (list (list (list :a :c) 2) (list (list :a :d :e) 3) ) {:a {:b 1 :c 2 :d {:e 3}}})))


(defn perform-dissoc-in-test
  [m ks expected]
  (let [v (kf-coll/dissoc-in m ks)]
    (is (map? v))
    (is (= v expected))))


(deftest dissoc-in-test
  (testing "ks is vector of keywords: key doesn't exist"
    (perform-dissoc-in-test {:a {:b 1}} [:a :c] {:a {:b 1}}))
  (testing "ks is vector of keywords: remove top-level key"
    (perform-dissoc-in-test {:a {:b 1}} [:a] {}))
  (testing "ks is vector of keywords: remove only key at that level"
    (perform-dissoc-in-test {:a {:b 1}} [:a :b] {:a {}}))
  (testing "ks is vector of keywords: remove 1 of 2 keys at that level"
    (perform-dissoc-in-test {:a {:b 1 :c 2}} [:a :c] {:a {:b 1}}))
  (testing "ks is vector of vector of keywords of seq: remove 2 keys"
    (perform-dissoc-in-test {:a {:b 1 :c 2}} [[:a :b] [:a :c]] {:a {}}))
  ;;
  (testing "ks is list of strings: key doesn't exist"
    (perform-dissoc-in-test {"a" {"b" 1}} `("a" "c") {"a" {"b" 1}}))
  (testing "ks is list of strings: remove top-level key"
    (perform-dissoc-in-test {"a" {"b" 1}} `("a") {}))
  (testing "ks is list of strings: remove only key at that level"
    (perform-dissoc-in-test {"a" {"b" 1}} '("a" "b") {"a" {}}))
  (testing "ks is list of strings: remove 1 of 2 keys at that level"
    (perform-dissoc-in-test {"a" {"b" 1 "c" 2}} '("a" "c") {"a" {"b" 1}}))
  (testing "ks is list of list of strings of seq: remove 2 keys"
    (perform-dissoc-in-test {"a" {"b" 1 "c" 2}} (list (list "a" "b") (list "a" "c")) {"a" {}})))
