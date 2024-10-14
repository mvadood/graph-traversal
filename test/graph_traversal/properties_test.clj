(ns graph-traversal.properties-test
  (:require [clojure.test :refer [is run-tests testing is deftest]]
            [graph-traversal.properties :refer [eccentricity radius diameter]]
            [graph-traversal.graph :refer [convert-to-weighted]]))


(def weighted-graph {:1 [[:2 1] [:3 2]],
                     :2 [[:4 4]],
                     :3 [[:4 2]],
                     :4 []})

(def unweighted-graph {:1 [:2 :3],
                       :2 [:4],
                       :3 [:4],
                       :4 []})

(deftest test-eccentricity-weighted
  (testing "Eccentricity of nodes"
    (is (= (eccentricity weighted-graph :1) 4))  ; Path length from :1 -> :4 with total weight 4
    (is (= (eccentricity weighted-graph :2) 4))  ; Path length from :2 -> :4 with total weight 4
    (is (= (eccentricity weighted-graph :3) 2))  ; Path length from :3 -> :4 with total weight 2
    (is (= (eccentricity weighted-graph :4) ##Inf)))) ; Node :4 cannot reach any other nodes

(deftest test-radius-weighted
  (testing "Radius of the graph"
    (is (= (radius weighted-graph) 2))))  ; The minimum eccentricity is 2 (for node :3)

(deftest test-diameter-weighted
  (testing "Diameter of the graph"
    (is (= (diameter weighted-graph) 4))))  ; The maximum eccentricity is 4 (for nodes :1 and :2)


(deftest test-eccentricity-unweighted
  (testing "Eccentricity of nodes in unweighted graph"
    (let [g (convert-to-weighted unweighted-graph)]  ; Convert unweighted graph to weighted
      (is (= (eccentricity g :1) 2))  ; Shortest path from :1 -> :4 with 2 edges
      (is (= (eccentricity g :2) 1))  ; Shortest path from :2 -> :4 with 1 edge
      (is (= (eccentricity g :3) 1))  ; Shortest path from :3 -> :4 with 1 edge
      (is (= (eccentricity g :4) ##Inf)))))  ; Node :4 cannot reach any other nodes

(deftest test-radius-unweighted
  (testing "Radius of the unweighted graph"
    (let [g (convert-to-weighted unweighted-graph)]
      (is (= (radius g) 1)))))  ; The minimum finite eccentricity is 1 (for nodes :2 and :3)

(deftest test-diameter-unweighted
  (testing "Diameter of the unweighted graph"
    (let [g (convert-to-weighted unweighted-graph)]
      (is (= (diameter g) 2)))))  ; The maximum finite eccentricity is 2 (for node :1)



(run-tests)
