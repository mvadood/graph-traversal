(ns graph-traversal.graph)

;; Abstracted graph traversal function for both DFS and BFS
(defn seq-graph
  "Performs graph traversal on graph `g` starting from node `s` using `d` as the initial data structure for the frontier (stack or queue)."
  [d g s]
  ((fn rec-seq [explored frontier]
     (lazy-seq
      (if (empty? frontier)
        nil
        (let [v (peek frontier)              ; Get the current node from the frontier
              neighbors (g v)]               ; Get the neighbors of the current node
          (cons v (rec-seq
                   (into explored neighbors) ; Mark neighbors as explored
                   (into (pop frontier)      ; Add unexplored neighbors to the frontier
                         (remove explored neighbors))))))))
   #{s} (conj d s)))  ; Initialize with explored set and frontier

;; Define DFS and BFS as special cases of seq-graph
(def seq-graph-dfs
  "Returns a lazy sequence of nodes in Depth-First Search (DFS) order starting from `s`."
  (partial seq-graph []))  ; DFS uses a stack (vector)

(def seq-graph-bfs
  "Returns a lazy sequence of nodes in Breadth-First Search (BFS) order starting from `s`."
  (partial seq-graph clojure.lang.PersistentQueue/EMPTY))  ; BFS uses a queue
