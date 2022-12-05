package so.howl.common.store.howler

sealed class HowlerMarketOutput {
    data class Read(val data: HowlerMarketData?) : HowlerMarketOutput()
    data class Write(
        val ok: Boolean,
        val input: HowlerMarketInput
    ) : HowlerMarketOutput()
}
