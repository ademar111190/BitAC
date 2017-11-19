package ademar.bitac.model

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import java.util.*

@JsonObject
class Address {

    @JsonField(name = arrayOf("address"))
    var address: String? = null

    @JsonField(name = arrayOf("final_balance"))
    var balance: Long? = null

    override fun equals(other: Any?) = other is Address && address == other.address && balance == other.balance

    override fun hashCode() = Objects.hash(address, balance)

}
