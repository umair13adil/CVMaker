package com.blackbox.onepage.cvmaker.data.repositories

import com.blackbox.onepage.cvmaker.data.local.CVData
import com.blackbox.onepage.cvmaker.data.local.RealmHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CVRepository @Inject constructor(private var db: RealmHelper) : CVDataSource {

    override fun saveData(data: CVData) {
        db.add(data)
    }

    override fun getData(id: Int): CVData {
        return db.find(id)
    }

}