import com.google.common.io.Resources
import com.likethecolor.alchemy.api.Client
import com.likethecolor.alchemy.api.call.AbstractCall
import com.likethecolor.alchemy.api.call.RankedNamedEntitiesCall
import com.likethecolor.alchemy.api.call.SentimentCall
import com.likethecolor.alchemy.api.call.type.CallTypeText
import com.likethecolor.alchemy.api.entity.NamedEntityAlchemyEntity
import com.likethecolor.alchemy.api.entity.QuotationAlchemyEntity
import com.likethecolor.alchemy.api.entity.Response
import com.likethecolor.alchemy.api.entity.SentimentAlchemyEntity
import org.yaml.snakeyaml.Yaml

def yaml = new Yaml().load(MaapBotConfig.getResourceAsStream("/maapbot.yaml"))
def Client client = new Client(yaml.maapbot.alchemyToken)

def call = new RankedNamedEntitiesCall(new CallTypeText("встать в 8 утра"))
Response<NamedEntityAlchemyEntity> response = client.call(call)

println "Language: ${response.language}"
println "Status: ${response.status}"
println "Status Info: ${response.statusInfo}"
println "Text: ${response.text}"

response.iterator().each {
    println "Geo: ${it.geo}"
    println "Geonames: ${it.geonames}"
    println "Name: ${it.name}"
    println "Dis: ${it.disambiguatedAlchemyEntity}"

    println "Type: ${it.type}"
    println "Text: ${it.text}"

    it.quotationIterator().each {QuotationAlchemyEntity quotationAlchemyEntity ->
        println "${quotationAlchemyEntity.quotation}"
    }
}
