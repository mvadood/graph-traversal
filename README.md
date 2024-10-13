# Audience Republic:: Graph Traversal Coding Assignment


# Directory Structure

```
.
├── src
│   └── graph_traversal
│       ├── core.clj            # Main entry point with a few examples
│       ├── graph.clj           # Graph traversal functions
│       ├── random_graph.clj    # Random graph generation
│       ├── dijkstra.clj        # Dijkstra's shortest path
│       ├── properties.clj      # Graph properties (eccentricity, radius, diameter)
│       └── viz.clj             # Graph visualisation
├── test                        # Unit tests
└── project.clj                 # Leiningen project file

```

# Installation
## Prerequisites

* [Clojure](https://clojure.org/) and [Leiningen](https://leiningen.org/) for running Clojure programs.
* [Graphviz](https://graphviz.org/) for graph visualisation (ensure dot is in your system's `PATH`).

## Project Setup
1. Clone the repository:
   ```
   git clone ...
   cd ...
   ```
2. Install dependencies
   ```
   lein deps
   ```
3. Ensure [Graphviz](https://graphviz.org/) is installed
   ```
   dot -V  # Check Graphviz installation
   ```

# Usage
First of all, start `REPL`
```
lein repl
```

## Graph Traversal (DFS and BFS)
```
(require '[graph-traversal.graph :refer [seq-graph-dfs seq-graph-bfs]])

(def g {:1 [:2 :3], :2 [:4], :3 [:4], :4 []})

;; Perform Depth-First Search
(seq-graph-dfs g :1)  ; => (:1 :3 :4 :2)

;; Perform Breadth-First Search
(seq-graph-bfs g :1)  ; => (:1 :2 :3 :4)

```

## Random Graph Generation
```
(require '[graph-traversal.random-graph :refer [make-graph]])

;; Generate a weakly-connected directed random graph with 10 vertices and 10 edges
(def random-graph (make-graph 10 10))

;; This will throw an exception (not enough edges for the graph to be connected)
(def random-graph (make-graph 10 8))

;; This will throw an exception (Too many edges for the graph for it not to have parallel edges)
(def random-graph (make-graph 10 91))

;; Generate a non-weighted connected random graph with 10 vertices and 10 edges
(def random-graph (make-graph 10 10 :weighted false))

;; You can use graph-traversal.graph/convert-to-weighted to convert a non-weighted graph to a weighted one (with weights = 1)
(def converted (convert-to-weighted random-graph))

;; You can also specify a floor and cap for the weights on each edge
(def random-graph (make-graph 10 10 :weight_floor 12 :weight_cap 100))

```

## Graph visualisation
It is useful to visualise the generated graph to be able to visually cross-check the output of Dijkstra or the properties.

1. **Generate DOT File**:

```
(require '[graph-traversal.viz :refer [save-dot-file]])

;; Save the DOT file for manual visualisation with Graphviz
(save-dot-file random-graph "graph.dot")

```
2. **Render Graph**: Open another terminal and type in the following:

```
dot -Tpng graph.dot -o graph.png
```

Result of `(make-graph 10 90)` / Fully connected 10-node graph
![Fully connected 10-node graph](example_graph.png)

## Shortest Path Calculation (Dijkstra)
```
(require '[graph-traversal.dijkstra :refer [shortest-path]])

;; or pass a random-graph instead
(def g {:1 [[:2 1] [:3 2]], :2 [[:4 4]], :3 [[:4 2]], :4 []})

;; Find the shortest path from node :1 to node :4
(shortest-path g :1 :4)  ; => (:1 :3 :4)
```
## Graph Properties
```
(require '[graph-traversal.properties :refer [eccentricity radius diameter]])

;; or pass a random-graph instead
(def g {:1 [[:2 1] [:3 2]], :2 [[:4 4]], :3 [[:4 2]], :4 []})

;; Calculate eccentricity for node :1
(eccentricity g :1)  ; => 4

;; Calculate the radius of the graph
(radius g)  ; => 2

;; Calculate the diameter of the graph
(diameter g)  ; => 4
```

# Testing
Run the tests using Leiningen:
```
lein test
```