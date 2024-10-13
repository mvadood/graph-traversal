(ns graph-traversal.dijkstra-test
  (:require [clojure.test :refer [deftest is run-tests testing]]
            [graph-traversal.dijkstra :refer [shortest-path]]))

(def G {:1 [[:2 1] [:3 2]],
        :2 [[:4 4]],
        :3 [[:4 2]],
        :4 []})

(deftest test-shortest-path
  (testing "Shortest path in a weighted graph"
    (is (= (shortest-path G :1 :4) [:1 :3 :4]))
    (is (= (shortest-path G :2 :4) [:2 :4]))
    (is (= (shortest-path G :3 :4) [:3 :4]))
    (is (= (shortest-path G :1 :3) [:1 :3]))
    (is (nil? (shortest-path G :4 :1)))))

;; Run all the tests
(run-tests)
