import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup
import me.shib.java.lib.botan.Botan
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.jsonNode

def logger = LoggerFactory.getLogger("ua.eshepelyuk")

ratpack {

    bindings {
        TelegramBot bot = TelegramBotAdapter.build("205365091:AAHhR6iyhWwK9pdv0FEvrKiyng0yHeI4avc")
        bindInstance TelegramBot, bot

        Botan botan = new Botan("AvVoNrWQ5PQgC-FxDCeoXf_ytf7m3_mJ")
        bindInstance(botan)
    }

    handlers {
        all RequestLogger.ncsa()

        post("webhook") { TelegramBot telegramBot, Botan botan ->
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
