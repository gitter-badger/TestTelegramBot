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
//    serverConfig {
//        ssl SSLContexts.sslContext(this.class.getResource("/maapbot.jks"), "changeit")
//    }
    handlers {
        all RequestLogger.ncsa()
        post("webhook") {
            logger.info("webhook called")
            try {
                def updates = context.parse(listOf(Update))
                logger.info("updates $updates")
            } catch (Exception e) {
                logger.error("error parsing", e)
            }
            logger.info("webhook ok")
            render "OK"
        }
        get(":name") {
            render "$pathTokens.name"
        }
    }
}
