(ns experimenting.console)

(defprotocol BlackenConsoleAPI 
  (background [this ^String color])
  (foreground [this ^String color])
  (clear-console [this])
  (refresh [this])
  (write-at [this line col w])
  (quit [this]))
