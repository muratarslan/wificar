(ns wificar.core
  (:import (javax.swing JFrame JPanel KeyStroke Action 
                        AbstractAction)
           (java.awt.event ActionEvent ActionListener)
           (java.awt Graphics Dimension Color)
           (java.awt.image BufferedImage)
           (java.io BufferedReader InputStreamReader OutputStream PrintWriter)
           (java.net Socket)))

(def socket nil)
(defn listensocket []
	(try
	  (let [socket (Socket. "192.168.1.1" 1500)
                in     (BufferedReader. 
                       (InputStreamReader. (.getInputStream socket)))
                out    (PrintWriter. (.getOutputStream socket))] [socket in out] )
          (catch Exception e (println e "Unknown Host"))))

(def up-key
  (proxy [AbstractAction ActionListener] []
    (actionPerformed [e] (println "UP!!!")
      (let [[_ in out] socket] 
        (.print out (char 9))
        (.print out (char 0))
       ))))

(def down-key
  (proxy [AbstractAction ActionListener] []
    (actionPerformed [e] (println "DOWN!!!") 
      (let [[_ in out] socket]
        (.print out (char 33))
        (.print out (char 0))
        (.flush out)))))

(def left-key
  (proxy [AbstractAction ActionListener] []
    (actionPerformed [e] (println "LEFT!!!")
      (let [[_ in out] socket] 
        (.print out (char 5))
        (.print out (char 0))
        (.flush out)))))

(def right-key
  (proxy [AbstractAction ActionListener] []
    (actionPerformed [e] (println "RİGHT!!!")
      (let [[_ in out] socket]
        (.print out (char 3))
        (.print out (char 0))
        (.flush out)))))

(def connect
  (proxy [AbstractAction ActionListener] []
    (actionPerformed [e] (println "CONNECT!!!") 
      (def socket (listensocket)) 
      )))

(def disconnect
  (proxy [AbstractAction ActionListener] []
    (actionPerformed [e] (println "DISCONNECT!!!"))))



(def panel
  (doto
      (proxy [JPanel] []
        (paint [g] nil))
    (.setPreferredSize (new Dimension 200 200))
    (.. (getInputMap)  (put (KeyStroke/getKeyStroke "UP") "up"))
    (.. (getActionMap) (put "up" up-key))
    (.. (getInputMap)  (put (KeyStroke/getKeyStroke "DOWN") "down"))
    (.. (getActionMap) (put "down" down-key))
    (.. (getInputMap)  (put (KeyStroke/getKeyStroke "RIGHT") "right"))
    (.. (getActionMap) (put "right" right-key))
    (.. (getInputMap)  (put (KeyStroke/getKeyStroke "LEFT") "left"))
    (.. (getActionMap) (put "left" left-key))
    (.. (getInputMap)  (put (KeyStroke/getKeyStroke \c) "con"))
    (.. (getActionMap) (put "con" connect))
    (.. (getInputMap)  (put (KeyStroke/getKeyStroke \d) "dis"))
    (.. (getActionMap) (put "dis" disconnect))))

(def frame
  (doto
      (JFrame.)
    (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
    (.add panel)
    (.pack)
    (.setVisible true)))

(println "Let's Drive")


