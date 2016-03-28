import ratpack.handling.RequestLogger
import ratpack.ssl.SSLContexts

import static ratpack.groovy.Groovy.ratpack

ratpack {
//    serverConfig {
//        ssl SSLContexts.sslContext(this.class.getResource("/maapbot.jks"), "changeit")
//    }
    handlers {
        all RequestLogger.ncsa()
        get("webhook") {
            render "GET webhook"
        }
        post("webhook") {
            byContent {
                json {
                    render "POST webhook 2"
                }
            }
        }
        get(":name") {
            render "$pathTokens.name"
        }
    }
}
