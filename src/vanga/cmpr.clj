(ns vanga.cmpr
  (:require
   [clojure.string :refer [split]]))

(defn parse
  "Splits input by semicolon and returns two integers as a list"
  [s]
  (->> (split s #":")
       (map (fn [str] (Integer. str)))))

(defn full-guess?
  [bet-home bet-guest fact-home fact-guest]
  (and (= bet-home fact-home)
       (= bet-guest fact-guest)))

(defn win-guess?
  [bet-home bet-guest fact-home fact-guest]
  (let [home-win? (and (> bet-home bet-guest)
                       (= bet-home fact-home))
        guest-win? (and (< bet-home bet-guest)
                        (= bet-guest fact-guest))
        fuzzy-win? (or (and (< bet-home fact-home)
                            (< bet-guest fact-guest)
                            (> bet-home bet-guest)
                            (> fact-home fact-guest))
                       (and (< bet-home fact-home)
                            (< bet-guest fact-guest)
                            (< bet-home bet-guest)
                            (< fact-home fact-guest))
                       (and (> bet-home fact-home)
                            (> bet-guest fact-guest)
                            (< bet-home bet-guest)
                            (< fact-home fact-guest))
                       (and (> bet-home fact-home)
                            (> bet-guest fact-guest)
                            (> bet-home bet-guest)
                            (> fact-home fact-guest))
                       (and (= bet-home fact-home)
                            (> bet-guest fact-guest)
                            (not= fact-home fact-guest))
                       (and (= bet-guest fact-guest)
                            (> bet-home fact-home)
                            (not= fact-home fact-guest))
                       (and (= bet-home fact-home)
                            (< bet-guest fact-guest)
                            (< fact-home fact-guest))
                       (and (= bet-guest fact-guest)
                            (< bet-home fact-home)
                            (> fact-home fact-guest)))]
    (or home-win?
        guest-win?
        fuzzy-win?)))

(defn draw-guess?
  [bet-home bet-guest fact-home fact-guest]
  (and (= bet-home bet-guest)
       (= fact-home fact-guest)
       (not= bet-home fact-home)
       (not= bet-guest fact-guest)))

(defn no-win?
  [bet-home bet-guest fact-home fact-guest]
  (or (and (= bet-home bet-guest)
           (< bet-home fact-home)
           (= bet-guest fact-guest))
      (and (= bet-home bet-guest)
           (= bet-home fact-home)
           (< bet-guest fact-guest))))

(defn cmpr
  "Compares user bet and game result fact.
   Представь, что ты работаешь в букмекерской конторе программистом и тебя попросили написать
   функцию, которая на вход принимает два футбольных счета - тот который загадал клиент когда
   делал ставку и реальный результат футбольного матча (то как сыграли команды на самом деле).
   На выходе нужно получить:
     2 - если клиент полностью угадал счет
     1 - если клиент угадал победившую команду (в том числе ничью)
     0 - если не угадал ничего
   Счет задается строкой вида “2:3”.
   Первое число называется счет хозяев, второе - счет гостей."
  [bet fact]
  (let [[bet-home bet-guest] (parse bet)
        [fact-home fact-guest] (parse fact)
        args [bet-home bet-guest fact-home fact-guest]]
    (cond
      (apply full-guess? args) 2
      (apply no-win? args) 0
      (apply win-guess? args) 1
      (apply draw-guess? args) 1
      :else 0)))
