package so.howl.common.storekit.store.howler

import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.store.StoreData

data class PopulatedHowlerNetworkRep(
    val howler: StoreData<NetworkHowler>
)