import com.pengrad.telegrambot.model.Update
import ratpack.handling.RequestLogger
import ratpack.ssl.SSLContexts
import static ratpack.util.Types.listOf;
import static ratpack.jackson.Jackson.jsonNode
import static ratpack.jackson.Jackson.fromJson

import static ratpack.groovy.Groovy.ratpack

ratpack {
//    serverConfig {
//        ssl SSLContexts.sslContext(this.class.getResource("/maapbot.jks"), "changeit")
//    }
    handlers {
        all RequestLogger.ncsa()
        post("webhook") {
            println "webhook ${request.getContentType()}"
            context.parse(listOf(Update))
            render "POST webhook 2"
        }
        get(":name") {
            render "$pathTokens.name"
        }
    }
}
