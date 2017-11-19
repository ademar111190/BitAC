package ademar.bitac.repository.datasource

import ademar.bitac.ext.getLong
import ademar.bitac.ext.getString
import ademar.bitac.model.Wallet
import ademar.bitac.sqlite.AppDbHelper
import ademar.bitac.sqlite.AppDbHelper.Companion.COLUMN_WALLET_ADDRESS
import ademar.bitac.sqlite.AppDbHelper.Companion.COLUMN_WALLET_BALANCE
import ademar.bitac.sqlite.AppDbHelper.Companion.COLUMN_WALLET_CREATION
import ademar.bitac.sqlite.AppDbHelper.Companion.COLUMN_WALLET_EDITION
import ademar.bitac.sqlite.AppDbHelper.Companion.COLUMN_WALLET_ID
import ademar.bitac.sqlite.AppDbHelper.Companion.COLUMN_WALLET_NAME
import ademar.bitac.sqlite.AppDbHelper.Companion.TABLE_WALLET
import ademar.bitac.sqlite.ContentValuesFactory
import android.database.sqlite.SQLiteDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Storage @Inject constructor(

        private val appDbHelper: AppDbHelper,
        private val contentValuesFactory: ContentValuesFactory

) {

    fun saveWallet(wallet: Wallet) {
        val time = System.nanoTime()
        val db = appDbHelper.writableDatabase
        db.insertWithOnConflict(TABLE_WALLET, null, contentValuesFactory.make(
                COLUMN_WALLET_ID to wallet.id,
                COLUMN_WALLET_ADDRESS to wallet.address,
                COLUMN_WALLET_NAME to wallet.name,
                COLUMN_WALLET_BALANCE to wallet.balance,
                COLUMN_WALLET_CREATION to time,
                COLUMN_WALLET_EDITION to time
        ), SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun deleteWallet(id: Long) {
        val db = appDbHelper.writableDatabase
        db.delete(TABLE_WALLET, "$COLUMN_WALLET_ID=?", arrayOf("$id"))
        db.close()
    }

    fun getWallets(): List<Wallet> {
        val wallets = arrayListOf<Wallet>()
        val db = appDbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_WALLET", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(COLUMN_WALLET_ID)
                val name = cursor.getString(COLUMN_WALLET_NAME)
                val address = cursor.getString(COLUMN_WALLET_ADDRESS)
                val balance = cursor.getLong(COLUMN_WALLET_BALANCE)
                val creation = cursor.getLong(COLUMN_WALLET_CREATION)
                val edition = cursor.getLong(COLUMN_WALLET_EDITION)
                wallets.add(Wallet(id, name, address, balance, creation, edition))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return wallets
    }

}
