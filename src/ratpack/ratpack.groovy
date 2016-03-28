import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
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
        def mapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        bindInstance ObjectMapper, mapper
    }

    handlers {
        all RequestLogger.ncsa()
        post("webhook") {
            logger.info("webhook called length=${request.contentLength}, type=${request.contentType}")

            context.parse(jsonNode())
                .onError {
                    logger.error("webhook exception", it)
                    response.send("KO")
                } then {
                    logger.info("webhook parsed $it")
                    response.send("OK")
                }
        }
        get(":name") {
            render "$pathTokens.name"
        }
    }
}
