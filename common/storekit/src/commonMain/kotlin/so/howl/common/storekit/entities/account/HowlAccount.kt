package so.howl.common.storekit.entities.account

interface HowlAccount {
    val id: HowlAccountId
    val plan: HowlPlan
}