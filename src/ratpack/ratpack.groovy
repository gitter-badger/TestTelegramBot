import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import com.pengrad.telegrambot.model.Update
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger
import ratpack.ssl.SSLContexts
import static ratpack.util.Types.listOf;
import static ratpack.jackson.Jackson.jsonNode
import static ratpack.jackson.Jackson.fromJson

import static ratpack.groovy.Groovy.ratpack

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
                    logger.info("webhook parsed message=${it.get('message').get('text').asText()}")
                    telegramBot.sendMessage(
                        it.get('message').get('chat').get('id').asLong(), "You've just said ${it.get('message').get('text').asText()}"
                    )
                    response.send("OK")
                }
        }
        get(":name") {
            render "$pathTokens.name"
        }
    }
}
