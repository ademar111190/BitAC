package ademar.bitac.repository.datasource

import ademar.bitac.model.Wallet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Local @Inject constructor() {

    var wallets: HashMap<Long, Wallet>? = null

}
