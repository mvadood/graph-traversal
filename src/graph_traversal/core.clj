(ns graph-traversal.core
  (:gen-class))



(def G {:1 #{:2 :3},  ; Node :1 points to nodes :2 and :3
        :2 #{:4},     ; Node :2 points to node :4
        :3 #{:4},     ; Node :3 also points to node :4
        :4 #{}})      ; Node :4 points to no other nodes


(defn seq-graph [d g s]
  ((fn rec-seq [explored frontier]
     (lazy-seq
      (if (empty? frontier)
        nil
        (let [v (peek frontier)
              neighbors (g v)]
          (cons v (rec-seq
                   (into explored neighbors)
                   (into (pop frontier) (remove explored neighbors))))))))
   #{s} (conj d s)))

(def seq-graph-dfs (partial seq-graph []))
(def seq-graph-bfs (partial seq-graph clojure.lang.PersistentQueue/EMPTY))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println G)
  (println (seq-graph-dfs G :1))
  (println (seq-graph-bfs G :1)))
