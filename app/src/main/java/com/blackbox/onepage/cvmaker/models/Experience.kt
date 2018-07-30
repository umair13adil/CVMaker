package com.blackbox.onepage.cvmaker.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Experience(@PrimaryKey var designation: String = "",
                      var organization: String = "",
                      var timePeriod: String = "",
                      var locaton: String = "",
                      var points: RealmList<BulletPoints> = RealmList<BulletPoints>()) : RealmObject(){


}