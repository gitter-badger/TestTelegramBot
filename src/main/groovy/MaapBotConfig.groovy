import groovy.transform.ToString

/**
 * Created by Evgeny on 30.03.2016.
 */

@ToString(includeNames = true)
class MaapBotConfig {
    String telegramToken

    String botanToken

    String alchemyToken
}