package ademar.bitac.test.fixture

import ademar.bitac.model.Provider

object ProviderFixture {

    const val id = "provider_id"
    const val name = "A name"

    fun makeModel() = Provider(id, name)

}
