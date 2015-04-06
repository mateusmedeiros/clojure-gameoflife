(ns experimenting.utils)

(defn get-neighbors [y x] 
  [[ (dec y),  (inc x)]
   [ (dec y),       x ]
   [      y,   (dec x)]
   [ (dec y),  (dec x)]
   [ (inc y),  (dec x)]
   [ (inc y),       x ]
   [      y,   (inc x)]
   [ (inc y),  (inc x)]])

(defn is-living-cell [y x grid] 
  (let [cell (get (get grid y) x)]
    (not (or (nil? cell) (= cell \_)))))

(defn count-neighbors [[acc grid] [y x]] 
  (if (is-living-cell y x grid) 
    [(inc acc) grid] 
    [acc grid]))

(defn calculate-iteration-on-cell [grid y x cell]
  (let [[living-neighbors] (reduce count-neighbors [0 grid] (get-neighbors y x))]
    (if (= cell \_)
      (cond (= living-neighbors 3) \u2588 :else \_)
      (cond 
        (< living-neighbors 2) \_
        (or (= living-neighbors 2) (= living-neighbors 3)) \u2588
        (> living-neighbors 3) \_))))

(defn geth [i col] 
  (try 
    (or (get i col) (throw (NullPointerException.)))
  (catch Exception e 
    (nth col i))))

(defn set-on-grid [grid y x cell]
  (assoc grid y (assoc (geth y grid) x cell)))

(defn generate-grid 
  ([lines cols] 
    (generate-grid lines cols []))
  ([lines cols living-cells] 
    (generate-grid lines cols living-cells (take lines  (repeat  (vec (take cols  (repeat \_)))))))
  ([lines cols [[y x] & rest] current] 
    (if (nil? y) 
      current
      (recur lines cols rest (set-on-grid (vec current) (- y 1) (- x 1) \u2588)))))
