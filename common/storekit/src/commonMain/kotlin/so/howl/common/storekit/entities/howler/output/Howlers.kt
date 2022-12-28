package so.howl.common.storekit.entities.howler.output

interface Howlers {
    val howlers: List<Howler>

    companion object {
        fun from(howlers: List<Howler>): Howlers = RealHowlers(howlers)
    }
}