(ns vanga.cmpr-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [vanga.cmpr :as sut]))

(deftest parse-test
  (testing "Testing game results parse function"
    (is (= '(2 3) (sut/parse "2:3")))
    (is (= '(3 1) (sut/parse "3:1")))
    (is (= '(0 0) (sut/parse "0:0")))))

(deftest cmpr-test
  (testing "Testing bet to fact with full-guess rules"
    (is (= 2 (sut/cmpr "2:3" "2:3")))
    (is (= 2 (sut/cmpr "3:2" "3:2")))
    (is (= 2 (sut/cmpr "3:3" "3:3")))
    (is (= 2 (sut/cmpr "0:0" "0:0"))))

  (testing "Testing bet to fact with win-guess rules"
    (is (= 1 (sut/cmpr "3:0" "3:3")))
    (is (= 1 (sut/cmpr "0:3" "3:3")))
    (is (= 1 (sut/cmpr "3:3" "3:0")))
    (is (= 1 (sut/cmpr "3:3" "0:3")))
    (is (= 1 (sut/cmpr "3:0" "3:1")))
    (is (= 1 (sut/cmpr "3:1" "3:0")))
    (is (= 1 (sut/cmpr "0:3" "1:3")))
    (is (= 1 (sut/cmpr "1:3" "0:3")))
    (is (= 1 (sut/cmpr "2:3" "1:3")))
    (is (= 1 (sut/cmpr "1:3" "2:3")))
    (is (= 1 (sut/cmpr "3:2" "3:1")))
    (is (= 1 (sut/cmpr "3:1" "3:2"))))

(testing "Testing win-guess inspite of exact values"
    (is (= 1 (sut/cmpr "2:1" "3:2")))
    (is (= 1 (sut/cmpr "1:2" "2:3")))
    (is (= 1 (sut/cmpr "3:2" "2:1")))
    (is (= 1 (sut/cmpr "2:3" "1:2")))
    (is (= 1 (sut/cmpr "2:3" "4:5")))
    (is (= 1 (sut/cmpr "3:2" "5:4")))
    (is (= 1 (sut/cmpr "0:3" "0:2")))
    (is (= 1 (sut/cmpr "3:0" "2:0")))
    (is (= 1 (sut/cmpr "0:2" "0:3")))
    (is (= 1 (sut/cmpr "2:0" "3:0"))))

  (testing "Testing draw rules"
    (is (= 1 (sut/cmpr "1:1" "2:2")))
    (is (= 1 (sut/cmpr "2:2" "1:1"))))

  (testing "Testing bet to fact with no-guess rules"
    (is (= 0 (sut/cmpr "2:3" "2:2")))
    (is (= 0 (sut/cmpr "3:2" "2:2")))
    (is (= 0 (sut/cmpr "3:3" "0:2")))
    (is (= 0 (sut/cmpr "3:3" "2:0")))
    (is (= 0 (sut/cmpr "2:2" "2:3")))
    (is (= 0 (sut/cmpr "2:2" "3:2")))
    (is (= 0 (sut/cmpr "0:2" "3:3")))
    (is (= 0 (sut/cmpr "2:0" "3:3")))))
