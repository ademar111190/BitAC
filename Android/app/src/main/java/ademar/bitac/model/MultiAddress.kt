package ademar.bitac.model

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
class MultiAddress {

    @JsonField(name = ["addresses"])
    var addresses: List<Address>? = null

}
