package so.howl.common.storekit.entities.howler.common

interface Howlers {
    val howlers: List<CommonHowler>

    companion object {
        fun from(howlers: List<CommonHowler>): Howlers = RealHowlers(howlers)
    }
}