(ns aoc2019.machine
  (:require [clojure.string :as str]
            [clojure.core.async :as a]))

(defn op_PLUS [ms params]
  (let [{:keys [ip memory]} ms]
    (-> ms
        (assoc-in [:memory (memory (+ ip 3))] (+ (nth params 0) (nth params 1)))
        (assoc :ip (+ ip 4)))))

(defn op_MUL [ms params]
  (let [{:keys [ip memory]} ms]
    (-> ms
        (assoc-in [:memory (memory (+ ip 3))] (* (nth params 0) (nth params 1)))
        (assoc :ip (+ ip 4)))))

(defn op_INPUT [ms params]
  (let [{:keys [ip memory]} ms]
    (-> ms
        (assoc-in [:memory (memory (+ ip 1))] (first (:inputstream ms)))
        (assoc :inputstream (rest (:inputstream ms)))
        (assoc :ip (+ ip 2)))))

(defn op_OUTPUT [ms params]
  (-> ms
      (assoc :outputstream (conj (:outputstream ms) (nth params 0)))
      (assoc :ip (+ (:ip ms) 2))))

(defn op_NOOP [ms params]
  (println params)
  (assoc ms :ip (+ (:ip ms) 2)))

(defn op_JEZ [ms params]
  (assoc ms :ip
         (if (zero? (nth params 0))
           (nth params 1)
           (+ (:ip ms) 3))))

(defn op_JNZ [ms params]
  (assoc ms :ip
         (if (zero? (nth params 0))
           (+ (:ip ms) 3)
           (nth params 1))))

(defn op_EQ [ms params]
  (let [{:keys [ip memory]} ms
        out (if (= (nth params 0) (nth params 1)) 1 0)]
    (-> ms
        (assoc-in [:memory (memory (+ ip 3))] out)
        (assoc :ip (+ ip 4)))))

(defn op_LT [ms params]
  (let [{:keys [ip memory]} ms
        out (if (< (nth params 0) (nth params 1)) 1 0)]
    (-> ms
        (assoc-in [:memory (memory (+ ip 3))] out)
        (assoc :ip (+ ip 4)))))

(defn op_HALT [ms params]
  (assoc ms :halt true))

(defn op_INPUT_CHAN [ms params]
  (let [{:keys [ip memory]} ms]
    (-> ms
        (assoc-in [:memory (memory (+ ip 1))] (a/<!! (:inputstream ms)))
        (assoc :ip (+ ip 2)))))

(defn op_OUTPUT_CHAN [ms params]
  (a/>!! (:outputstream ms) (nth params 0))
  (assoc ms :ip (+ (:ip ms) 2)))

(defn op_HALT_CHAN [ms params]
  (a/close! (:outputstream ms))
  (assoc ms :halt true))






(defn op-lookup-normal [opcode]
  (case opcode
    "01" op_PLUS
    "02" op_MUL
    "03" op_INPUT
    "04" op_OUTPUT ;Ouput
    "05" op_JNZ
    "06" op_JEZ
    "07" op_LT
    "08" op_EQ
    "99" op_HALT

    op_NOOP))

(defn op-lookup-channel [opcode]
  (case opcode
    "01" op_PLUS
    "02" op_MUL
    "03" op_INPUT_CHAN
    "04" op_OUTPUT_CHAN ;Ouput
    "05" op_JNZ
    "06" op_JEZ
    "07" op_LT
    "08" op_EQ
    "99" op_HALT_CHAN

    op_NOOP))


(def op-lookup op-lookup-channel)

(def op-len {"01" 3
             "02" 3
             "03" 1
             "04" 1
             "05" 2
             "06" 2
             "07" 3
             "08" 3
             "99" 0})

(defn gather-param [indicator mem v]
  (case indicator
    \0 (mem v)
    \1 v))

(defn decode-op [opcode mem ip]
  (let [fullop (str "00000000" opcode)
        op (apply str (take-last 2 fullop))
        params (for [x (range 1 (inc (get op-len op)))]
                 (gather-param (last (drop-last (inc x) fullop)) mem (mem (+ ip x))))]
    [op params]))

(def trace true)

(defn run-one-step [machine-state]
  (let [{:keys [ip memory]} machine-state
        [op params] (decode-op (memory ip) memory ip)]
        ((op-lookup op) machine-state params)))

(defn create-state-from-mem [mem]
  {:ip 0
   :histmem []
   :memory mem
   :halt false
   :inputstream []
   :outputstream []})

(defn run-machine-base [state]
  (loop [ms state]
    (if (:halt ms)
      ms
      (recur (run-one-step ms)))))

(defn run-machine
  ([machine-spec noun verb]
   (run-machine (assoc (assoc machine-spec 1 noun) 2 verb)))
  ([machine-spec]
   (run-machine-base (create-state-from-mem machine-spec))))

(defn run-machine-with-input [memory input]
  (run-machine-base (assoc (create-state-from-mem memory) :inputstream input)))


(defn run-machine-with-chan [memory inchan outchan]
  (let [
        mac (-> (create-state-from-mem memory)
                (assoc :inputstream inchan)
                (assoc :outputstream outchan))]
    (run-machine-base mac)
    ))


(defn create-machine-spec [s]
  (vec (map #(Integer/parseInt %) (vec (str/split (str/trim s) #",")))))

