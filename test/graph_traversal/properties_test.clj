(ns graph-traversal.properties-test
  (:require [clojure.test :refer [is run-tests testing is deftest]]
            [graph-traversal.properties :refer [eccentricity radius diameter]]))

(def G {:1 [[:2 1] [:3 2]],
        :2 [[:4 4]],
        :3 [[:4 2]],
        :4 []})

(deftest test-eccentricity
  (testing "Eccentricity of nodes"
    (is (= (eccentricity G :1) 4))  ; Path length from :1 -> :4 with total weight 4
    (is (= (eccentricity G :2) 4))  ; Path length from :2 -> :4 with total weight 4
    (is (= (eccentricity G :3) 2))  ; Path length from :3 -> :4 with total weight 2
    (is (= (eccentricity G :4) ##Inf)))) ; Node :4 cannot reach any other nodes

(deftest test-radius
  (testing "Radius of the graph"
    (is (= (radius G) 2))))  ; The minimum eccentricity is 2 (for node :3)

(deftest test-diameter
  (testing "Diameter of the graph"
    (is (= (diameter G) 4))))  ; The maximum eccentricity is 4 (for nodes :1 and :2)

(run-tests)
