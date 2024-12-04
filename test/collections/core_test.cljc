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


(ns collections.core-test
  (:require [clojure.test :refer :all]
            [collections.core :as collections]))


(defn perform-assoc-in-m-ks-test
  [m ks v expected]
  (let [result (collections/assoc-in m ks v)]
    (is (map? result))
    (is (= result expected))))


(defn perform-assoc-in-m-ks-v-coll-test
  [m ks-v-coll expected]
  (let [result (collections/assoc-in m ks-v-coll)]
    (is (map? result))
    (is (= result expected))))


(deftest assoc-in-test
  ;;
  ;; m, ks, v
  (testing "m, ks, v: add to existing structure"
    (perform-assoc-in-m-ks-test {:a {:b 1}} [:a :c] 2 {:a {:b 1 :c 2}}))
  (testing "m, ks, v: need to create structure"
    (perform-assoc-in-m-ks-test {:a {:b 1}} [:a :c :d] 2 {:a {:b 1 :c {:d 2}}}))
  ;;
  ;; m, ks-v-coll
  (testing "m, ks-v-coll: one item, add to existing structure"
    (perform-assoc-in-m-ks-v-coll-test {:a {:b 1}} [ [[:a :c] 2] ] {:a {:b 1 :c 2}}))
  (testing "m, ks-v-coll: two items, add to existing structure and create new structure"
    (perform-assoc-in-m-ks-v-coll-test {:a {:b 1}} [ [[:a :c] 2] [[:a :d :e] 3] ] {:a {:b 1 :c 2 :d {:e 3}}})))


(defn perform-dissoc-in-test
  [m ks expected]
  (let [v (collections/dissoc-in m ks)]
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
