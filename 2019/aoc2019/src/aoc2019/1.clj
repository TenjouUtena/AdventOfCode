(ns aoc2019.1)




(defn fuelcost [weight]
  (- (int (Math/floor (/ weight 3))) 2))