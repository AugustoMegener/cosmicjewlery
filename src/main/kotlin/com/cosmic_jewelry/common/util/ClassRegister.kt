package com.cosmic_jewelry.common.util

abstract class ClassRegister<T> {
    protected val all = arrayListOf<T>()
    val register get() = ArrayList(all)
}