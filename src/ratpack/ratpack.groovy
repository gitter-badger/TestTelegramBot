import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
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
            logger.info("webhook called length=${request.contentLength}, type=${request.contentType}")

            context.parse(jsonNode()) onError {
                logger.error("webhook exception", it)
                response.status(500).send(it.message)
            } then {
                logger.info("webhook update=${it}")
                def message = it.get("message")

                telegramBot.sendMessage(message.get('chat').get('id').asLong(), "You've just said ${message.get('text').asText()}")

                try {
                    botan.track(message.get('from').asLong(), "message", message)
                } catch (Exception e) {
                    logger.error("Cannot track message to Botan", e)
                }

                response.send("OK")
            }
        }
    }
}
