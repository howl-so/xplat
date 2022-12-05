package so.howl.common.store.howl_user

sealed class HowlUserMarketOutput {
    data class Read(val data: HowlUserMarketData?) : HowlUserMarketOutput()
    data class Write(
        val ok: Boolean,
        val input: HowlUserMarketInput
    ) : HowlUserMarketOutput()
}
