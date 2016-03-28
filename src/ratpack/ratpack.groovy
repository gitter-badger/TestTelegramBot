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
    handlers {
        all RequestLogger.ncsa()
        post("webhook") {
            logger.info("webhook called length=${request.contentLength}, type=${request.contentType}")

            context.parse(fromJson(Update))

            request.getBody()
                .onError {
                    logger.error("webhook exception", it)
                    response.send("KO")
                } then {
                    logger.info("webhook parsed $it.text")
                    response.send("OK")
                }
        }
        get(":name") {
            render "$pathTokens.name"
        }
    }
}
