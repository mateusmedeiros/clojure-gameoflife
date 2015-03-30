(ns experimenting.utils.collections (:gen-class))

(defn funcao_teste [] 
  (println "O mais engraçado é que eu nem lembro mais o que eu ia fazer nesse util. AH, LEMBREI, NTH + GET (GETH?? LOL)"))

(defn geth [i col] 
  (try 
    (or (get i col) (throw (NullPointerException.)))
  (catch Exception e 
    (nth col i))))
