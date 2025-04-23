package com.yapp.app.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

internal class RuleProvider : RuleSetProvider {

    override val ruleSetId: String = "yapp-rule"

    override fun instance(config: Config) = RuleSet(ruleSetId, listOf())
}
