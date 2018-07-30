package com.blackbox.onepage.cvmaker.data.repositories

import com.blackbox.onepage.cvmaker.data.local.CVData

interface CVDataSource {

    fun saveData(data: CVData)

    fun getData(id: Int): CVData?

}