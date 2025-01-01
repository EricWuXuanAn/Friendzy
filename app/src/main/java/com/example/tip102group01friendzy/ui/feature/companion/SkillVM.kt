package com.example.tip102group01friendzy.ui.feature.companion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//專長VM
class SkillVM : ViewModel() {
    private val _skillState = MutableStateFlow(emptyList<Skill>())
    val skillState = _skillState.asStateFlow()

    init {
        _skillState.value = fetchSkill()
    }


    private fun fetchSkill(): List<Skill> {
        return listOf(
            Skill("01", "籃球"),
            Skill("02", "跑步"),
            Skill("03", "健身"),
            Skill("04", "照護"),
            Skill("05", "攝影"),
        )
    }
}
//專長類別
data class Skill(var skillNo: String = "", var skillName: String = "") {
    override fun equals(other: Any?): Boolean {
        return this.skillNo == (other as Skill).skillNo
    }

    override fun hashCode(): Int {
        return skillNo.hashCode()
    }

}