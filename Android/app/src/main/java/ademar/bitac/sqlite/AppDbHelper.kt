package ademar.bitac.sqlite


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject constructor(context: Context) : SQLiteOpenHelper(context, "app.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create wallet table
        db.execSQL("""
        |CREATE TABLE "$TABLE_WALLET" (
        |    `$COLUMN_WALLET_ID` INTEGER NOT NULL PRIMARY KEY UNIQUE,
        |    `$COLUMN_WALLET_ADDRESS` TEXT NOT NULL,
        |    `$COLUMN_WALLET_NAME` TEXT NOT NULL,
        |    `$COLUMN_WALLET_BALANCE` INTEGER NOT NULL DEFAULT 0,
        |    `$COLUMN_WALLET_CREATION` INTEGER NOT NULL,
        |    `$COLUMN_WALLET_EDITION` INTEGER NOT NULL
        |);""".trimMargin())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {

        val TABLE_WALLET = "wallet"
        val COLUMN_WALLET_ID = "id"
        val COLUMN_WALLET_ADDRESS = "address"
        val COLUMN_WALLET_NAME = "name"
        val COLUMN_WALLET_BALANCE = "balance"
        val COLUMN_WALLET_CREATION = "creation"
        val COLUMN_WALLET_EDITION = "edition"

    }

}
