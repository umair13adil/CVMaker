package com.blackbox.onepage.cvmaker.data.local

import io.realm.Realm


class RealmHelper {

    private fun getRealmInstance(): Realm {
        try {
            return Realm.getDefaultInstance()
        } catch (e: Exception) {
            return Realm.getDefaultInstance()
        }
    }

    fun add(model: CVData) {
        val realm = getRealmInstance()
        realm.executeTransaction {
            it.copyToRealmOrUpdate(model)
        }
    }

    fun update(model: CVData) {
        val realm = getRealmInstance()
        realm.executeTransaction {
            it.copyToRealmOrUpdate(model)
        }
    }

    fun remove(clazz: CVData) {
        val realm = getRealmInstance()
        realm.executeTransaction {
            it.delete(clazz.javaClass)
        }
    }

    fun findAll(): List<CVData> {
        return getRealmInstance().where(CVData::class.java).findAll()
    }

    fun find(id: Int): CVData {
        return getRealmInstance().where(CVData::class.java).equalTo("id", id).findFirst()!!
    }
}