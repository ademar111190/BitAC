package ademar.bitac.test.fixture

import ademar.bitac.model.Address
import ademar.bitac.model.MultiAddress

object MultiAddressFixture {

    /**
     * Use -1 to a null list
     */
    fun makeModel(items: Int = 1) = MultiAddress().apply {
        addresses = if (items >= 0) {
            ArrayList<Address>().apply {
                for (i in 1..items) {
                    add(AddressFixture.makeModel())
                }
            }
        } else {
            null
        }
    }

}
