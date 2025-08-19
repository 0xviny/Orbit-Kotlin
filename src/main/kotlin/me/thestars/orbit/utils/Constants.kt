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

enum class OrbitConnectionFlag(val value: Int) {
    Frozen(1 shl 1),
    AllowFiles(1 shl 2),
    AllowInvites(1 shl 3),
    AllowLinks(1 shl 4),
    NoIdentification(1 shl 5),
    AllowOrigin(1 shl 6),
    AllowWebhooks(1 shl 7),
    AllowEmojis(1 shl 8),
    CompactMode(1 shl 9),
    ConfirmActions(1 shl 10),
    AutoTranslate(1 shl 11),
    Inactive(1 shl 12),
    AllowMentions(1 shl 13),
    AllowWallOfText(1 shl 14),
    AutoModIntelligence(1 shl 15),
    DisableReactionAndSystemMessages(1 shl 16),
    EnableModeTosco(1 shl 17),
    CleanMessage(1 shl 18),
    UseComponentsV2(1 shl 19),
    NameServerWebhook(1 shl 20);
}