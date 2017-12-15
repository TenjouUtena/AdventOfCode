(ns aoc2017)
(require '[clojure.string :as str])

(defn makelist [] (str/split-lines (slurp "8.txt")))

(def state (atom {}))

(def highval (atom 0))

(defn runline! [line]
  (let [mm (re-matcher #"([a-z]+) (inc|dec) ([\-0-9]+) if ([a-z]+) ([><=!]+) ([\-0-9]+)" line)
        ff (re-find mm)
        target-register (nth (re-groups mm) 1)
        operation (nth (re-groups mm) 2)
        target-value (Integer. (nth (re-groups mm) 3))
        test-register (nth (re-groups mm) 4)
        test-sig (nth (re-groups mm) 5)
        test-value (Integer. (nth (re-groups mm) 6))
        test-reg-value (get @state test-register 0)
        target-reg-value (get @state target-register 0)]
    (if (or
         (and (= test-sig "==") (= test-reg-value test-value))
         (and (= test-sig "!=") (not (= test-reg-value test-value)))
         (and (= test-sig ">=") (>= test-reg-value test-value))
         (and (= test-sig "<=") (<= test-reg-value test-value))
         (and (= test-sig "<")  (< test-reg-value test-value))
         (and (= test-sig ">")  (> test-reg-value test-value)))
      (let [vvvv (if (= operation "inc")
                   (+ target-reg-value target-value)
                   (- target-reg-value target-value))]
        (swap! state assoc target-register vvvv)
        (if (> vvvv @highval)
          (reset! highval vvvv))))))

(defn part1 []
  (do (doall (map runline! (makelist))) @state))

(defn part2 []
  (do (doall (map runline! (makelist))) @highval))

[(part2)]
