import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import com.pengrad.telegrambot.model.Message
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.jsonNode

import me.shib.java.lib.botan.Botan

def logger = LoggerFactory.getLogger("ua.eshepelyuk")
def botanToken = "eLCwUk8FyyShxL88OJ2RS-Q1YXgD_eHQ";
def Botan botan = new Botan(botanToken);

ratpack {

    bindings {
        TelegramBot bot = TelegramBotAdapter.build("205365091:AAHhR6iyhWwK9pdv0FEvrKiyng0yHeI4avc")
        bindInstance TelegramBot, bot
    }

    handlers {
        all RequestLogger.ncsa()

        post("webhook") { TelegramBot telegramBot ->
            logger.info("webhook called length=${request.contentLength}, type=${request.contentType}")

            context.parse(jsonNode())
                    .onError {
                logger.error("webhook exception", it)
                response.status(500).send(it.message)
            } then {
                logger.info("webhook update=${it}")
                telegramBot.sendMessage(it.get('message').get('chat').get('id').asLong(), "You've just said ${it.get('message').get('text').asText()}"
                )

                try {
                    botan.track(it.get('message').get('from').asLong(), "message", it.get('message'));
                } catch (IOException e) {
                    logger.error("Cannot track message to Botan", e)
                }

                response.send("OK")
            }
        }
    }
}
