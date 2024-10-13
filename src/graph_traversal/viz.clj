(ns graph-traversal.viz
  (:require [dorothy.core :as dot]
            [dorothy.jvm :as dot-jvm]))

(defn graph-to-dot-dorothy [g]
  (dot/digraph
   (mapcat (fn [[node neighbors]]
             (for [[neighbor weight] neighbors]
               [node neighbor {:label (str weight)}]))
           g)))

(defn save-dot-file
  "Generate and save a visual representation of the graph as a Dot file for manual testing."
  [g output-dot-file]
  (let [dot-graph (graph-to-dot-dorothy g)]
    ;; Save the Dot format
    (spit output-dot-file (dot/dot dot-graph))))
