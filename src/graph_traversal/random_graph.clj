(ns graph-traversal.random-graph)

;; Default spanning tree logic: simple linear connection
(defn default-spanning-tree
  "Generates a default spanning tree for a graph with `N` vertices to ensure weak connectivity."
  [N weighted weight-floor weight-cap]
  (let [vertices (range 1 (inc N))] ; Vertices numbered from 1 to N
    (map (fn [i]
           (let [start (nth vertices (dec i))
                 end (nth vertices i)
                 weight (if weighted (+ weight-floor (rand-int (inc (- weight-cap weight-floor)))) 1)] ; Random weight within limits
             (if weighted
               [start end weight]   ; If weighted, include the weight
               [start end])))
         (range 1 N)))) ; Create a tree with N - 1 edges

(defn generate-random-edges
  "Generates `remaining-edges` random edges for a graph with `N` vertices.
  Ensures no duplicates or self-loops."
  [N remaining-edges weighted existing-edges weight-floor weight-cap]
  (let [vertices (range 1 (inc N))]
    (take remaining-edges
          (remove existing-edges
                  (repeatedly
                   (fn []
                     (let [start (rand-nth vertices)
                           end (rand-nth (remove #{start} vertices)) ; Avoid self-loops
                           weight (if weighted (+ weight-floor (rand-int (inc (- weight-cap weight-floor)))) 1)] ; Random weight within limits
                       (if weighted
                         [start end weight]
                         [start end]))))))))

(defn add-edges-to-graph
  "Adds edges to a graph. If weighted, edges will have weights."
  [edges weighted]
  (reduce (fn [graph [start end & weight]]
            (update graph start (fnil conj []) (if weighted [end (first weight)] end)))
          {}
          edges))

(defn ensure-all-vertices
  "Ensures all vertices from 1 to N are present in the graph, even if they have no outgoing edges."
  [graph N]
  (reduce (fn [g v]
            (if (contains? g v)
              g
              (assoc g v [])))
          graph
          (range 1 (inc N))))

(defn make-graph
  "Generates a connected random directed graph with `N` vertices and `S` edges.
  Optionally adds weights if `weighted` is true. Throws an exception if `S` is less than N-1 or more than N(N-1).
  Ensures no duplicate edges or self-loops."
  [N S & {:keys [weighted weight-floor weight-cap spanning-tree-fn]
          :or {weighted false
               weight-floor 1
               weight-cap 10
               spanning-tree-fn default-spanning-tree}}]

  ;; Check if the number of edges is valid
  (let [min-edges (dec N)
        max-edges (* N (dec N))]
    (when (or (< S min-edges) (> S max-edges))
      (throw (Exception. (str "Invalid number of edges. Must be between " min-edges " and " max-edges ".")))))

  ;; Generate the spanning tree first
  (let [spanning-tree (spanning-tree-fn N weighted weight-floor weight-cap)
        remaining-edges (- S (dec N)) ; Calculate how many edges are left to generate
        random-edges (generate-random-edges N remaining-edges weighted (set spanning-tree) weight-floor weight-cap)]
    (-> (add-edges-to-graph (concat spanning-tree random-edges) weighted)
        (ensure-all-vertices N))))
