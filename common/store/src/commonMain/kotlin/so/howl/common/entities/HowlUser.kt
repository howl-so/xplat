package so.howl.common.entities

typealias HowlUserId = String

interface HowlUser {
    val id: HowlUserId
    val name: String
    val email: String
    val avatarUrl: String
    val owns: List<HowlerId>
}