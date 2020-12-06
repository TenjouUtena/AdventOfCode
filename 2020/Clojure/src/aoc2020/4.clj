(ns aoc2020.4
  (:require [aoc2020.core :refer :all]
            [clojure.string :as s]
            [clojure.spec.alpha :as sp]))

(def ii (map #(concat (s/split-lines %))
            (s/split (slurp "resources/Files/4/input.txt") #"\n\n")))

(def i (map #(apply concat (map (fn [x] (s/split x #" ")) %)) ii))

(def rx #"([a-z]+):([0-9a-z#]+)")

(def passports (map (fn [x] (into {} (map #(let [m (re-matches rx %)
                                                  k (keyword "aoc2020.4" (nth m 1))
                                                  v (nth m 2)]
                                              {k v}) x))) i))

(defn valid-height? [val]
  (let [m (re-matches #"^([0-9]+)(in|cm)$" val)]
    (if m (let [id (nth m 2)
                v (Integer/parseInt (nth m 1))]
            (cond
              (= id "cm")
              (<= 150 v 193)
              (= id "in")
              (<= 59 v 76)
              :default
              false))
          false)))

(sp/def ::byr #(<= 1920 (Integer/parseInt %) 2002))
(sp/def ::iyr #(<= 2010 (Integer/parseInt %) 2020))
(sp/def ::eyr #(<= 2020 (Integer/parseInt %) 2030))
(sp/def ::hgt valid-height?)
(sp/def ::hcl #(re-matches #"^#[0-9a-f]{6}$" %))
(sp/def ::ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(sp/def ::pid #(re-matches #"^[0-9]{9}$" %))
(sp/def ::cid string?)

(sp/def ::passport (sp/keys :req [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
                            :opt [::cid]))

(defn part1 []
  (+ (count (filter #(<= 8 %) (map (comp count keys) passports)))
     (count (filter #(and (= 7 (count (keys %)))
                          (not (contains? % ::cid)))
                    passports))))

(defn part2 []
  (count (filter (partial sp/valid? ::passport) passports)))