package com.cosmic_jewelry.common.core.refactor

abstract class Registry<T> : Iterable<T>  {

    private val supliers = arrayListOf<() -> T>()
    private val items by lazy { supliers.map { it() } }

    fun T.register() { supliers += { this } }

    override fun iterator() = items.iterator()
}