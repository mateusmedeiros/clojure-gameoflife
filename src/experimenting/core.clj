(ns experimenting.core 
  (:gen-class)
  (:require [experimenting.utils :refer :all])
  (:require [experimenting.config :refer :all])
  (:require [experimenting.blacken :as t])
  (:require [clojure.string :as s]))

(def grid (agent (generate-grid h w beginning)))
(def console (t/make-curses-like-term "Game of life" 40 144))

(defn iterate-game [grid] 
  (let [old-grid @grid] 
    (doseq [y (range 0 h)
            x (range 0 w)
            :let [cell (geth x (geth y old-grid))]]
      (send grid set-on-grid y x (calculate-iteration-on-cell old-grid y x cell)))))

(defn print-grid [console grid] 
  (let [i (atom 0)] 
    (doseq [v grid]
      (.write-at console  (swap! i inc) 0  (s/join  " " v)))))

(defn -main  "I forget it" []
  (do 
    (.background console "#000000") 
    (.foreground console "#FFFFFF") 
    (print-grid console @grid)
    (.refresh console) (.refresh console)
    (Thread/sleep 300))
  (def main-thread (Thread. (fn [] 
    (try 
      (loop [] 
        (iterate-game grid) 
        (.clear-console console)
        (await grid)
        (print-grid console @grid)               
        (when (Thread/interrupted) (throw (InterruptedException.)))
        (.refresh console) (.refresh console)
        (recur))
    (catch InterruptedException e (shutdown-agents) (.quit console))))))
  (do (.start main-thread) (.getch (:underlying-terminal console)) (.interrupt main-thread)))
