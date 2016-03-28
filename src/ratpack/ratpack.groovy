import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.jsonNode

def logger = LoggerFactory.getLogger("ua.eshepelyuk")

ratpack {

    bindings {
        TelegramBot bot = TelegramBotAdapter.build("205365091:AAHhR6iyhWwK9pdv0FEvrKiyng0yHeI4avc")
        bindInstance TelegramBot, bot
    }

    handlers {
        all RequestLogger.ncsa()

        post("webhook") {TelegramBot telegramBot ->
            logger.info("webhook called length=${request.contentLength}, type=${request.contentType}")

            context.parse(jsonNode())
                .onError {
                    logger.error("webhook exception", it)
                    response.status(500).send(it.message)
                } then {
                    telegramBot.sendMessage(
                        it.get('message').get('chat').get('id').asLong(), "You've just said ${it.get('message').get('text').asText()}"
                    )
                    response.send("OK")
                }
        }
    }
}
