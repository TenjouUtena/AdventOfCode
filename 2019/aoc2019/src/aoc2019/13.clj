(ns aoc2019.13
  (:require [aoc2019.machine :refer :all]
            [clojure.string :as s]
            [lanterna.screen :as scr]))

(def input (s/trim (slurp "resources/13.txt")))

(def machine (create-machine-spec input))

(defn main1 []
  (run-machine machine))


                                        ; (frequencies (map #(nth % 2) (partition 3 screen)))


(defn main2 []
  (let [sc (scr/get-screen :swing)
        paddle (atom [0 0])
        ball (atom [0 0])]
    (scr/start sc)
    (loop [state (run-machine-with-input (assoc machine 0 2) [])]
      (if (:halt state)
        (do (scr/stop sc)
            state)
        (do
                                        ;(scr/clear sc)
          (println (apply str (:outputstream state)))
          (doall (map #(cond
                         (= (nth % 0) -1)
                         (scr/put-string sc 40 5 (str (nth % 2)))
                         (= (nth % 2) 1)
                         (scr/put-string sc (nth % 0) (nth % 1) " " {:bg :white})
                         (= (nth % 2) 2)
                         (scr/put-string sc (nth % 0) (nth % 1) " " {:bg :yellow})
                         (= (nth % 2) 3)
                         (do
                           (scr/put-string sc (nth % 0) (nth % 1) "~" {:bg :grey :fb :white})
                           (reset! paddle [(nth % 0) (nth % 1)])
                           )
                         (= (nth % 2) 4)
                         (do
                           (scr/put-string sc (nth % 0) (nth % 1) "*" {:fg :red})
                           (reset! ball [(nth % 0) (nth % 1)]))
                         (= (nth % 2) 0)
                         (scr/put-string sc (nth % 0) (nth % 1) " ")
                         :else
                         (identity "5"))
                      (partition 3 (:outputstream state))))
          (scr/redraw sc)
          (recur (run-machine-base (-> state
                                       (assoc :inputwait false)
                                       (assoc :inputstream [(cond (> (first @paddle) (first @ball))
                                                                  -1
                                                                  (< (first @paddle) (first @ball))
                                                                  1
                                                                  :else
                                                                  0
                                                              )])
                                       (assoc :outputstream [])))))))))
;
;(assoc :inputstream [(case (scr/get-key-blocking sc)
;                       :left -2
;                       :right 2
;                       0)])
