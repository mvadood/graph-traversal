(ns graph-traversal.core
  (:require [graph-traversal.graph :refer [seq-graph-dfs seq-graph-bfs convert-to-weighted]]
            [graph-traversal.random-graph :refer [make-graph]]
            [graph-traversal.dijkstra :refer [shortest-path]]
            [graph-traversal.properties :refer [eccentricity radius diameter]]))

(defn example-weighted-graph []
  {:1 [[:2 1] [:3 2]]
   :2 [[:4 4]]
   :3 [[:4 2]]
   :4 []})

(defn example-unweighted-graph []
  {:1 [:2 :3]
   :2 [:4]
   :3 [:4]
   :4 []})

(defn run-graph-traversal []
  (let [g (example-unweighted-graph)]
    (println "DFS Traversal:" (seq-graph-dfs g :1))
    (println "BFS Traversal:" (seq-graph-bfs g :1))))

(defn run-dijkstra []
  (let [g (example-weighted-graph)]
    (println "Shortest path from :1 to :4:" (shortest-path g :1 :4))
    (try
      (println "Shortest path from :4 to :1:" (shortest-path g :4 :1))
      (catch Exception e
        (println (.getMessage e))))))

(defn run-random-graph []
  ;; Generate a random graph with 5 vertices, 6 edges, weighted
  (let [g (make-graph 5 6 :weighted true)]
    (println "Random generated graph (weighted):" g)))

(defn run-graph-properties []
  (let [g (example-weighted-graph)
        unweighted-g (convert-to-weighted (example-unweighted-graph))]
    ;; For the weighted graph
    (println "Eccentricity of node :1 (weighted graph):" (eccentricity g :1))
    (println "Radius of weighted graph:" (radius g))
    (println "Diameter of weighted graph:" (diameter g))

    ;; For the unweighted graph
    (println "Eccentricity of node :1 (unweighted graph):" (eccentricity unweighted-g :1))
    (println "Radius of unweighted graph:" (radius unweighted-g))
    (println "Diameter of unweighted graph:" (diameter unweighted-g))))

(defn -main []
  (println "Running graph traversal (DFS and BFS)...")
  (run-graph-traversal)

  (println "\nRunning Dijkstra's algorithm...")
  (run-dijkstra)

  (println "\nGenerating a random graph...")
  (run-random-graph)

  (println "\nCalculating graph properties (eccentricity, radius, diameter)...")
  (run-graph-properties))

;; Entry point for running the program
;; (main) can be used to run the entire flow.
