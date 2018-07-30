package com.blackbox.onepage.cvmaker.data.repositories

import com.blackbox.onepage.cvmaker.data.local.CVData
import com.blackbox.onepage.cvmaker.data.local.RealmHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CVRepository @Inject constructor(private var db: RealmHelper) : CVDataSource {

    override fun saveData(data: CVData) {
        db.update(data)
    }

    override fun getData(id: Int): CVData? {
        val list = db.findAll(CVData::class.java).filter {
            it.id == id
        }

        return if (list.isNotEmpty())
            list.first()
        else
            CVData()
    }

}