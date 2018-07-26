package com.blackbox.onepage.cvmaker.data.local

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CVData(@PrimaryKey var id: Int? = null, var name: String? = null) : RealmObject() {

    public fun MovieGenre() {

    }
}