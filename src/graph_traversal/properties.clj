(ns graph-traversal.properties
  (:require [graph-traversal.dijkstra :refer [shortest-path]]))

;; Helper to calculate total distance (sum of weights) for a path
(defn total-weight
  "Calculates the total weight of a path from `start` to `end` in the graph."
  [g start end]
  (let [path (shortest-path g start end)]
    (reduce
     (fn [sum [from to]]
       (let [edge (first (filter #(= (first %) to) (g from)))]
         (+ sum (second edge))))
     0
     (partition 2 1 path))))

;; Eccentricity: the greatest distance from a node to any other reachable node in the graph.
(defn eccentricity
  "Calculates the eccentricity of a node `start` in graph `g`.
  The eccentricity is the greatest distance between `start` and any other node it can reach."
  [g start]
  (let [reachable-nodes (filter #(not= start %) (keys g))
        distances (keep (fn [end]
                          (try
                            (total-weight g start end)
                            (catch Exception _ nil)))
                        reachable-nodes)]
    (if (seq distances)
      (apply max distances)
      ##Inf))) ; Use Infinity to represent unreachable

;; Radius: the minimum eccentricity among all nodes with finite eccentricity.
(defn radius
  "Calculates the radius of the graph `g`.
  The radius is the minimum eccentricity among all nodes with finite eccentricity."
  [g]
  (let [eccentricities (keep (fn [node]
                               (let [e (eccentricity g node)]
                                 (when (not= e ##Inf) e)))
                             (keys g))]
    (if (seq eccentricities)
      (apply min eccentricities)
      ##Inf)))

;; Diameter: the maximum eccentricity among all nodes with finite eccentricity.
(defn diameter
  "Calculates the diameter of the graph `g`.
  The diameter is the maximum eccentricity among all nodes with finite eccentricity."
  [g]
  (let [eccentricities (keep (fn [node]
                               (let [e (eccentricity g node)]
                                 (when (not= e ##Inf) e)))
                             (keys g))]
    (if (seq eccentricities)
      (apply max eccentricities)
      0))) ; If no nodes have finite eccentricity, diameter is 0