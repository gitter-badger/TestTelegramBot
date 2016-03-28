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

        post("webhook") {TelegramBot telegramBot ->
            logger.info("webhook called length=${request.contentLength}, type=${request.contentType}")

            context.parse(jsonNode())
                .onError {
                    logger.error("webhook exception", it)
                    response.status(500).send(it.message)
                } then {
                    Message message = it.get('message') as Message;
                    logger.info("webhook message=${message.get('text').asText()} from=${message.get('from')}")
                    telegramBot.sendMessage(
                        message.get('chat').get('id').asLong(), "You've just said ${message.get('text').asText()}"
                    )

                    try {
                        botan.track(message.get('from'), "message", message);
                    } catch (IOException e) {
                        logger.error("Cannot track message to Botan", e)
                    }
                    response.send("OK")
                }
        }
    }
}
