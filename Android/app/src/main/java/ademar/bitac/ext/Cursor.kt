package ademar.bitac.ext

import android.database.Cursor

fun Cursor.getString(column: String): String = getString(getColumnIndex(column))

fun Cursor.getLong(column: String): Long = getLong(getColumnIndex(column))
