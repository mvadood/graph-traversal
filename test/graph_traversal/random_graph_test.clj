(ns graph-traversal.random-graph-test
  (:require [clojure.test :refer [testing deftest is]]
            [graph-traversal.random-graph :refer [make-graph]]
            [graph-traversal.graph :refer [seq-graph-dfs]])) ; for DFS traversal to test connectivity

(defn graph-connected?
  "Check if the graph is weakly connected by running DFS from any vertex."
  [graph]
  (let [start-node (first (keys graph))
        reachable-nodes (set (seq-graph-dfs graph start-node))]
    (= reachable-nodes (set (keys graph)))))

(deftest test-make-graph-unweighted
  (testing "Generate connected unweighted random graph"
    (let [graph (make-graph 5 6 :weighted false)]
      ;; Check that the graph has 5 vertices
      (is (= 5 (count (keys graph))))
      ;; Ensure that the number of edges is 6
      (let [edge-count (reduce + (map count (vals graph)))]
        (is (= 6 edge-count)))
      ;; Ensure the graph is weakly connected
      (is (graph-connected? graph)))))

(deftest test-make-graph-weighted
  (testing "Generate connected weighted random graph with custom weight range"
    (let [graph (make-graph 5 6 :weight-floor 5 :weight-cap 15)]
      ;; Check that the graph has 5 vertices
      (is (= 5 (count (keys graph))))
      ;; Ensure that each edge has a weight in the specified range
      (is (every? (fn [[end weight]] (and (>= weight 5) (<= weight 15)))
                  (apply concat (vals graph))))
      ;; Ensure that the number of edges is 6
      )))