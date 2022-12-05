package so.howl.common.entities

typealias HowlAccountId = String

interface HowlAccount {
    val id: HowlAccountId
    val plan: HowlPlan
}