(ns graph-traversal.graph-test
  (:require [clojure.test :refer [deftest is run-tests testing]]
            [graph-traversal.graph :refer [seq-graph-dfs seq-graph-bfs]]))

;; Define a sample graph for testing
(def G {:1 [:2 :3],
        :2 [:4],
        :3 [:4],
        :4 []})

;; Test DFS traversal
(deftest test-dfs-traversal
  (testing "DFS traversal of a simple graph"
    ;; The expected order for DFS traversal starting from :1
    ;; DFS explores as deep as possible down one branch before backtracking
    (is (= (seq-graph-dfs G :1) '(:1 :3 :4 :2)))
    (is (= (seq-graph-dfs G :2) '(:2 :4)))
    (is (= (seq-graph-dfs G :3) '(:3 :4)))
    ;; When starting from :4, since it's a leaf node, the result should just be :4
    (is (= (seq-graph-dfs G :4) '(:4)))))

;; Test BFS traversal
(deftest test-bfs-traversal
  (testing "BFS traversal of a simple graph"
    ;; The expected order for BFS traversal starting from :1
    ;; BFS explores all neighbors at the present depth level before going deeper
    (is (= (seq-graph-bfs G :1) '(:1 :2 :3 :4)))
    (is (= (seq-graph-bfs G :2) '(:2 :4)))
    (is (= (seq-graph-bfs G :3) '(:3 :4)))
    ;; When starting from :4, since it's a leaf node, the result should just be :4
    (is (= (seq-graph-bfs G :4) '(:4)))))

;; Run all the tests
(run-tests)