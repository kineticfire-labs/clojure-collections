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
            [kineticfire.collections.collection :as kf-collection]))



(defn perform-not-empty?-test
  [col fn-expected]
  (let [v (kf-collection/not-empty? col)]
    (is (boolean? v))
    (is (fn-expected v))))


(deftest not-empty?-test
  ;;
  ;; vector
  (testing "vector, empty"
    (perform-not-empty?-test [] true?))
  (testing "vector, not empty"
    (perform-not-empty?-test [1] false?))
  ;;
  ;; list
  (testing "list, empty"
    (perform-not-empty?-test '() true?))
  (testing "list, not empty"
    (perform-not-empty?-test '(1) false?))
  ;;
  ;; set
  (testing "set, empty"
    (perform-not-empty?-test #{} true?))
  (testing "set, not empty"
    (perform-not-empty?-test #{1} false?))
  ;;
  ;; map
  (testing "map, empty"
    (perform-not-empty?-test {} true?))
  (testing "map, not empty"
    (perform-not-empty?-test {:a 1} false?))
  ;;
  ;; string
  (testing "string, empty"
    (perform-not-empty?-test "" true?))
  (testing "string, not empty"
    (perform-not-empty?-test "test" false?)))


(defn perform-contains-value-test
  [col search fn-expected]
  (let [v (kf-collection/contains-value? col search)]
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


(defn perform-find-duplicates-test
  [col expected]
  (let [v (kf-collection/find-duplicates col)]
    (is (vector? v))
    (is (= (sort v) (sort expected)))))


(deftest find-duplicates-test
  ;;
  ;; vector
  (testing "vector, no duplicates, empty vector"
    (perform-find-duplicates-test [] []))
  (testing "vector, no duplicates, integer: populated vector"
    (perform-find-duplicates-test [1 2 3 10 4] []))
  (testing "vector, no duplicates, string: populated vector"
    (perform-find-duplicates-test ["alpha" "charlie" "bravo" "foxtrot" "kilo"] []))
  (testing "vector, one duplicate, integer"
    (perform-find-duplicates-test [1 2 3 10 3] [3]))
  (testing "vector, one duplicate, string"
    (perform-find-duplicates-test ["alpha" "charlie" "bravo" "alpha" "kilo"] ["alpha"]))
  (testing "vector, three duplicates, integer"
    (perform-find-duplicates-test [1 2 3 10 3 1 8 2] [3 1 2]))
  (testing "vector, three duplicates, string"
    (perform-find-duplicates-test ["alpha" "charlie" "bravo" "alpha" "kilo" "charlie" "bravo"] ["alpha" "charlie" "bravo"]))
  ;;
  ;; list
  (testing "list, no duplicates, empty vector"
    (perform-find-duplicates-test '() []))
  (testing "list, no duplicates, integer: populated list"
    (perform-find-duplicates-test '(1 2 3 10 4) []))
  (testing "list, no duplicates, string: populated list"
    (perform-find-duplicates-test '("alpha" "charlie" "bravo" "foxtrot" "kilo") []))
  (testing "list, one duplicate, integer"
    (perform-find-duplicates-test '(1 2 3 10 3) [3]))
  (testing "list, one duplicate, string"
    (perform-find-duplicates-test '("alpha" "charlie" "bravo" "alpha" "kilo") ["alpha"]))
  (testing "list, three duplicates, integer"
    (perform-find-duplicates-test [1 2 3 10 3 1 8 2] [3 1 2]))
  (testing "list, three duplicates, string"
    (perform-find-duplicates-test '("alpha" "charlie" "bravo" "alpha" "kilo" "charlie" "bravo") ["alpha" "charlie" "bravo"]))
  ;;
  ;; set - won't have duplicates, but the function still works
  (testing "set, no duplicates, empty vector"
    (perform-find-duplicates-test #{} []))
  (testing "set, no duplicates, integer: populated vector"
    (perform-find-duplicates-test #{1 2 3 10 4} []))
  ;;
  ;; map
  (testing "map, no duplicates, empty vector"
    (perform-find-duplicates-test {} []))
  (testing "map, no duplicates, integer: populated map"
    (perform-find-duplicates-test {:a 1, :b 2, :c 3, :d 4} []))
  (testing "map, no duplicates, string: populated vector"
    (perform-find-duplicates-test {:a "alpha", :b "charlie", :c "bravo", :d "foxtrot", :e "kilo"} []))
  (testing "map, one duplicate, integer"
    (perform-find-duplicates-test {:a 3, :b 2, :c 3, :d 4} [3]))
  (testing "map, one duplicate, string"
    (perform-find-duplicates-test {:a "alpha", :b "charlie", :c "bravo", :d "alpha", :e "kilo"} ["alpha"]))
  (testing "map, three duplicates, integer"
    (perform-find-duplicates-test {:a 1, :b 2, :c 3, :d 1, :e 2, :f 3, :g 0} [3 1 2]))
  (testing "map, three duplicates, string"
    (perform-find-duplicates-test {:a "alpha", :b "charlie", :c "bravo", :d "foxtrot", :e "alpha", :f "bravo", :g "golf", :h "charlie"} ["alpha" "charlie" "bravo"])))


(defn perform-duplicates?-test
  [col fn-expected]
  (let [v (kf-collection/duplicates? col)]
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


(defn perform-assoc-in-m-ks-test
  [m ks v expected]
  (let [result (kf-collection/assoc-in m ks v)]
    (is (map? result))
    (is (= result expected))))


(defn perform-assoc-in-m-ks-v-coll-test
  [m ks-v-coll expected]
  (let [result (kf-collection/assoc-in m ks-v-coll)]
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
  (let [v (kf-collection/dissoc-in m ks)]
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
