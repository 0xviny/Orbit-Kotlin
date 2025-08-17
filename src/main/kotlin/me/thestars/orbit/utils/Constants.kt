package me.thestars.orbit.utils

object Constants {
    const val DEFAULT_COLOR = 0x5865f2
}

object Enums {
    enum class AlertType { Reply, Backup, System, TeamInvite, Gift, Mention }
    enum class ModType { Owner, Admin }
    enum class ModerationRuleType { EXACT_WORD, REGEX, WILDCARD }
    enum class ModerationAction { DELETE_MESSAGE, WARN_USER, TIMEOUT_USER }
    enum class LogType { MessageDeleted, MessageEdited, MessageSuspect, MessageGrave, BlockedWord, MessageConfirmed, MessageRejected }
    enum class CosmeticType { BANNER, BORDER, BADGE }
}