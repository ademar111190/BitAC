package ademar.bitac.repository.datasource

import ademar.bitac.model.MultiAddress
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Cloud {

    @GET("/multiaddr")
    fun getAddressBalances(
            @Query("active") address: String
    ): Call<MultiAddress>

}
