package com.blackbox.onepage.cvmaker.data.local

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CVData(@PrimaryKey var id: Int? = null,
                  var fullName: String? = "[Enter Name Here]",
                  var headline: String? = "[Enter Headline Here]",
                  var aboutMe: String? = "[Enter AboutMe Here]",
                  var email: String? = "[Enter Email Here]",
                  var address: String? = "[Enter Address Here]",
                  var phone: String? = "[Enter Phone Here]",
                  var social: String? = "[Enter Twitter Here]",
                  var themeColor: Int? = null
) : RealmObject() {

    public fun CVData() {

    }
}