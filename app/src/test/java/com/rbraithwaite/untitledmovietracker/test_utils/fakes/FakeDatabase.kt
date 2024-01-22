package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.data.database.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.MediaReviewEntity
import kotlin.reflect.KClass

class FakeDatabase(
    entityTypes: List<KClass<out Any>>
) {
    companion object {
        const val ID_ORIGINAL = -1L
        const val ID_FAILED = -2L
    }

    class Table<T: Any>(
        val type: KClass<T>
    ) {
        val rows = mutableListOf<T>()
    }

    private val tables: List<Table<out Any>> = entityTypes.map { Table(it) }

    inline fun <reified T : Any> insert(entity: T, noinline idUpdateBlock: (T.(Long) -> T)? = null): Long {
        val table = getTableFor(T::class) ?: return ID_FAILED

        return if (idUpdateBlock == null) {
            table.rows.add(entity)
            ID_ORIGINAL
        } else {
            val newId = (table.rows.size + 1).toLong()
            val updatedEntity = entity.idUpdateBlock(newId)
            table.rows.add(updatedEntity)
            newId
        }
    }

    inline fun <reified T : Any> find(noinline where: (T.() -> Boolean)? = null): List<T> {
        val result = mutableListOf<T>()

        val table = getTableFor(T::class) ?: return result

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
        val table = getTableFor(T::class) ?: return

        if (where == null) {
            table.rows.clear()
        } else {
            table.rows.removeIf { it.where() }
        }
    }

    inline fun <reified T : Any> update(newEntity: T, noinline where: (T.() -> Boolean)) {
        val table = getTableFor(T::class) ?: return

        table.rows.replaceAll {
            if (it.where()) {
                newEntity
            } else {
                it
            }
        }
    }

    fun <T : Any> getTableFor(type: KClass<T>): Table<T>? {
        for (table in tables) {
            if (table.type == type) {
                return table as Table<T>
            }
        }
        return null
    }
}