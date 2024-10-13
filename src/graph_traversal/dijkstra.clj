(ns graph-traversal.dijkstra
  (:require [clojure.data.priority-map :as pmap]))

;; Dijkstra's algorithm to find the shortest path in a weighted graph
(defn shortest-path
  "Finds the shortest path from `start` to `end` in a weighted graph `g`."
  [g start end]
  (letfn [(dijkstra [frontier dist prev]
            (if (empty? frontier)
              (if (nil? (dist end))
                nil ; Return nil if there's no path to `end`
                (reverse (loop [v end path []]
                           (if (nil? v)
                             path
                             (recur (prev v) (conj path v))))))
              (let [[v d] (peek frontier) ; Get the node with the smallest distance
                    frontier (pop frontier)
                    neighbors (g v)] ; Get the neighbors of the current node
                (if (= v end)
                  (reverse (loop [v end path []]
                             (if (nil? v)
                               path
                               (recur (prev v) (conj path v)))))
                  (let [updates (for [[n weight] neighbors
                                      :let [new-dist (+ d weight)]
                                      :when (< new-dist (get dist n Double/POSITIVE_INFINITY))] ; Only update if a shorter path is found
                                  [n new-dist])]
                    (recur
                     (into frontier (for [[n new-dist] updates] [n new-dist]))
                     (merge dist (into {} updates))
                     (reduce (fn [p [n _]] (assoc p n v)) prev updates)))))))]
    (dijkstra (pmap/priority-map start 0) {start 0} {})))

;; Define a weighted graph example
(def weighted-graph
  {:1 [[:2 1] [:3 2]],
   :2 [[:4 4]],
   :3 [[:4 2]],
   :4 []})
