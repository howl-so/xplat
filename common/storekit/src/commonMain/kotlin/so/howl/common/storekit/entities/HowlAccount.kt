package so.howl.common.storekit.entities

typealias HowlAccountId = String

interface HowlAccount {
    val id: HowlAccountId
    val plan: HowlPlan
}