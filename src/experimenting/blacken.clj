(ns experimenting.blacken
  (:require [experimenting.console :as c])
  (:import [com.googlecode.blacken 
               colors.ColorNames
               colors.ColorPalette
               swing.SwingTerminal
               terminal.BlackenKeys
               terminal.CursesLikeAPI]))

(defrecord CursesLikeTerminal [palette underlying-terminal] 
  c/BlackenConsoleAPI 
    (background [this color] (.setCurBackground underlying-terminal color))
    (foreground [this color] (.setCurForeground underlying-terminal color))
    (clear-console [this] (.clear underlying-terminal))
    (refresh [this] (.refresh underlying-terminal))
    (write-at [this line col w] (.mvputs underlying-terminal line col w))
    (quit [this] (.quit underlying-terminal)))

(defn make-curses-like-term [title lines cols] 
  (let [plt (doto (ColorPalette.) (.addAll ColorNames/XTERM_256_COLORS false))] 
    (CursesLikeTerminal. 
      plt
      (doto (CursesLikeAPI. (doto (SwingTerminal.) (.init title lines cols)))
        (.setPalette plt)
        (.getch 300)
        (.getch 300)))))
