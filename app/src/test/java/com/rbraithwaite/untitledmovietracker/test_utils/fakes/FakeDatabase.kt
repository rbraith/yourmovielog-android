package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import kotlin.reflect.KClass

class FakeDatabase(
    entityTypes: List<KClass<out Any>>
) {
    class Table<T: Any>(
        val type: KClass<T>
    ) {
        val rows = mutableListOf<T>()
    }

    private val tables: List<Table<out Any>> = entityTypes.map { Table(it) }

    inline fun <reified T : Any, IdType: Any> insert(
        entity: T,
        idSelector: IdSelector<T, IdType>
    ): IdType {
        val table = getTableFor(T::class)

        return if (idSelector.isNewEntity(entity)) {
            val currentHighestId = if (table.rows.isEmpty()) {
                idSelector.getId(entity)
            } else {
                idSelector.getId(table.rows.last())
            }

            val newId = idSelector.incrementId(currentHighestId)
            val updatedEntity = idSelector.updateId(entity, newId)
            table.rows.add(updatedEntity)
            newId
        } else {
            table.rows.add(entity)
            idSelector.getId(entity)
        }
    }

    inline fun <reified T : Any> find(noinline where: (T.() -> Boolean)? = null): List<T> {
        val result = mutableListOf<T>()

        val table = getTableFor(T::class)

        return if (where == null) {
            result.apply { addAll(table.rows) }
        } else {
            result.apply {
                for (entity in table.rows) {
                    if (entity.where()) {
                        add(entity)
                    }
                }
            }
        }
    }

    inline fun <reified T : Any> delete(noinline where: (T.() -> Boolean)? = null) {
        val table = getTableFor(T::class)

        if (where == null) {
            table.rows.clear()
        } else {
            table.rows.removeIf { it.where() }
        }
    }

    inline fun <reified T : Any> update(newEntity: T, noinline where: (T.() -> Boolean)) {
        val table = getTableFor(T::class)

        table.rows.replaceAll {
            if (it.where()) {
                newEntity
            } else {
                it
            }
        }
    }

    inline fun <reified T: Any, IdType: Any> insertOrUpdate(
        entity: T,
        idSelector: IdSelector<T, IdType>
    ): IdType {
        val existing = find<T> { idSelector.getId(entity) == idSelector.getId(this) }

        return if (existing.isEmpty()) {
            insert(entity, idSelector)
        } else {
            update(entity) { idSelector.getId(entity) == idSelector.getId(this) }
            idSelector.getId(entity)
        }
    }

    inline fun <reified T: Any, IdType: Any> insertOrUpdateMultiple(
        entities: List<T>,
        idSelector: IdSelector<T, IdType>
    ): List<IdType> {
        return entities.map { insertOrUpdate(it, idSelector) }
    }

    fun <T : Any> getTableFor(type: KClass<T>): Table<T> {
        for (table in tables) {
            if (table.type == type) {
                return table as Table<T>
            }
        }
        throw IllegalArgumentException("No table found in the fake database for ${type.simpleName}")
    }
}

interface IdSelector<T, IdType> {
    fun getId(entity: T): IdType

    fun updateId(entity: T, newId: IdType): T

    fun isNewEntity(entity: T): Boolean

    fun incrementId(id: IdType): IdType
}

abstract class LongIdSelector<T>: IdSelector<T, Long> {
    override fun isNewEntity(entity: T): Boolean {
        return getId(entity) == 0L
    }

    override fun incrementId(id: Long): Long {
        return id + 1
    }
}