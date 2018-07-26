package com.blackbox.onepage.cvmaker.data.repositories.cv

import com.blackbox.onepage.cvmaker.data.local.RealmHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CVRepository @Inject constructor(private var db: RealmHelper) : CVDataSource {


}