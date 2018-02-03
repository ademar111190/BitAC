package ademar.bitac.model

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import java.util.*

@JsonObject
class MultiAddress {

    @JsonField(name = ["addresses"])
    var addresses: List<Address>? = null

    override fun equals(other: Any?) = other != null && other is MultiAddress && addresses == other.addresses
    override fun hashCode() = Objects.hashCode(addresses)

}
