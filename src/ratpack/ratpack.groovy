import com.google.common.io.Resources
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup
import me.shib.java.lib.botan.Botan
import me.shib.java.lib.common.utils.JsonLib
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger

import static ratpack.func.Action.throwException
import static ratpack.groovy.Groovy.ratpack

def logger = LoggerFactory.getLogger("ua.eshepelyuk")

ratpack {
    serverConfig {
        onError(throwException()).yaml(Resources.getResource("maapbot.yaml")).require("/maapbot", MaapBotConfig)
    }

    bindings {
        def maapBotConfig = serverConfig.get("/maapbot", MaapBotConfig)
        bindInstance(maapBotConfig)

        bindInstance(TelegramBotAdapter.build(maapBotConfig.telegramToken))

        bindInstance(new Botan(maapBotConfig.botanToken))

        bindInstance(new JsonLib())
    }

    handlers {
        all RequestLogger.ncsa()
        get("config") {MaapBotConfig config ->
            render config.toString()
        }
        post("webhook") { TelegramBot telegramBot, Botan botan, JsonLib jsonLib ->
            context.parse(Map) onError {
                logger.error("exception", it)
                response.status(500).send(it.message)
            } then {
                logger.info("update=$it")
                def message = it['message']

                telegramBot.sendMessage(
                    message['chat']['id'] as Long, "You've reacted with *${message['text']}*. What else do you think ?",
                    ParseMode.Markdown, false, null,
                    new ReplyKeyboardMarkup([message['text']] as String[], ["Cool", "It sucks"] as String[])
                )

                try {
                    logger.info("encoded json for botan: ${jsonLib.toJson(it)}")
                    def botanResp = botan.track(message['from']['id'] as Long, "message", it)
                    logger.info("botan response isAccepted=${botanResp.accepted}, info=${botanResp.info}, status=${botanResp.status}")
                } catch (Exception e) {
                    logger.error("Cannot track message to Botan", e)
                }

                response.send("OK")
            }
        }
    }
}
