(ns experimenting.core (:gen-class)
  (use experimenting.utils.collections)
  (require [lanterna.terminal :as t]))

(def h 13)
(def w 27)
(def beginning [ 
[\_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \█ \█ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \█ \█ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \█ \█ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \█ \█ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
[\_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_ \_]  
])

(def grid (atom beginning))

(def console (t/get-terminal :swing)); :auto {:font (first (for [font (t/get-available-fonts)
                             ;                   :when (-> font (.toLowerCase) (.contains "mono"))]
                             ;               font)) } ))

(defn set-on-grid [grid y x cell]
  (assoc grid y (assoc (geth y grid) x cell)))

(defn calculate-iteration-on-cell [previous y x cell]
  (if (= cell \_) \█ \_))

(defn iterate-game [grid] 
  (doseq [y (range 0 h)
          x (range 0 w)
          :let [cell (geth x (geth y @grid))]]
    (swap! grid set-on-grid y x (calculate-iteration-on-cell @grid y x cell))))

(defn -main  "I forget it" []
  (do (t/start console) (t/set-bg-color console :black) (t/set-fg-color console :white))
  (def main-thread (Thread. (fn [] (try 
    (loop [] 
      (iterate-game grid) 
      (let [i (atom 0)] 
        (doseq [v @grid]
          (t/move-cursor console 0 (swap! i inc))
          (t/put-string console (apply str v))))
      (.flush console)
      (when (Thread/interrupted) (throw (InterruptedException.)))
      (Thread/sleep 600)
      (t/move-cursor console 0 0)
      (recur))
  (catch InterruptedException e (t/stop console))))))
  (do (.start main-thread) (t/get-key-blocking console) (.interrupt main-thread)))
