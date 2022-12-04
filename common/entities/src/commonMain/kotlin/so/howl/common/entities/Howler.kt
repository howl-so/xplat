package so.howl.common.entities

typealias HowlerId = String

interface Howler {
    val id: HowlerId
    val name: String
    val avatarUrl: String
    val ownedBy: List<HowlUserId>
}