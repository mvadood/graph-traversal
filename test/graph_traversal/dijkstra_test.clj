(ns graph-traversal.dijkstra-test
  (:require [clojure.test :refer [deftest is run-tests testing]]
            [graph-traversal.dijkstra :refer [shortest-path]]))

(def G {:1 [[:2 1] [:3 2]],
        :2 [[:4 4]],
        :3 [[:4 2]],
        :4 []})

(deftest test-shortest-path
  (testing "Find shortest path when it exists"
    ;; Test valid paths
    (is (= (shortest-path G :1 :4) [:1 :3 :4]))
    (is (= (shortest-path G :2 :4) [:2 :4]))
    (is (= (shortest-path G :1 :3) [:1 :3]))))

(deftest test-no-path
  (testing "Throws exception when no path is found"
    ;; No path from :4 to :1
    (is (thrown? Exception (shortest-path G :4 :1)))
    ;; No path from :3 to :2 (as it’s a directed graph)
    (is (thrown? Exception (shortest-path G :3 :2)))))

(run-tests)
