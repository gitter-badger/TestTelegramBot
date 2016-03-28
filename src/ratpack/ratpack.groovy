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
            logger.info("webhook called")
            context.parse(listOf(Update))
                .onError {
                    logger.error("webhook exception", it)
                } then {
                    logger.info("webhook parsed $it")
                    //updates.each { logger.info("webhook update=$it") }
                }

            render "OK"
        }
        get(":name") {
            render "$pathTokens.name"
        }
    }
}
