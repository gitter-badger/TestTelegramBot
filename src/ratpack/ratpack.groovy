import ratpack.handling.RequestLogger
import ratpack.ssl.SSLContexts

import static ratpack.groovy.Groovy.ratpack

ratpack {
//    serverConfig {
//        ssl SSLContexts.sslContext(this.class.getResource("/maapbot.jks"), "changeit")
//    }
    handlers {
        all RequestLogger.ncsa()
        get(":name") {
            render "$pathTokens.name"
        }
    }
}
