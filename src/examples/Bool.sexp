(def-type BoolLat (variant ((:Top) (:True) (:False) (:Bot))))

(def-type A BoolLat)
(def-type B BoolLat)
(def-type R BoolLat)

(def-bot BoolLat :Bot)

(def-height BoolLat (e BoolLat)
    (match e
        (case :Top      1)
        (case :True     2)
        (case :False    2)
        (case :Bot      3)))

(def-leq BoolLat (e1 BoolLat e2 BoolLat)
    (match (e1 e2)
        (case (:Bot _)          true)
        (case (:True :True)     true)
        (case (:False :False)   true)
        (case (_ :Top)          true)))

(def-lub BoolLat (e1 BoolLat e2 BoolLat)
    (match (e1 e2)
        (case (:Bot x)          x)
        (case (x :Bot)          x)
        (case (:True :True)     :True)
        (case (:False :False)   :False)
        (case _                 :Top)))

(def-fun And (e1 BoolLat e2 BoolLat)
    (match (e1 e2)
        (case (:Bot _)          :Bot)
        (case (_ :Bot)          :Bot)
        (case (:True :True)     :True)
        (case (:True :False)    :False)
        (case (:False :True)    :False)
        (case (:False :False)   :False)
        (case (:Top _)          :Top)
        (case (_ :Top)          :Top)))

(fact (A :True))
(fact (B :False))

(rule (R x) ((A x)))
(rule (R x) ((B x)))
