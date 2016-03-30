import com.likethecolor.alchemy.api.Client
import com.likethecolor.alchemy.api.call.AbstractCall
import com.likethecolor.alchemy.api.call.SentimentCall
import com.likethecolor.alchemy.api.call.type.CallTypeText
import com.likethecolor.alchemy.api.entity.Response
import com.likethecolor.alchemy.api.entity.SentimentAlchemyEntity

def apiKey = "e0614aa30fa5382fcead9e124974bb26941a6b7b"
def Client client = new Client(apiKey)

final AbstractCall<SentimentAlchemyEntity> sentimentCall = new SentimentCall(
    new CallTypeText("Были вдвоем, ужинали по поводу. Цена и качество-вещи не совместимые. " +
        "Интерьер, действительно выполнен необычно, идея присутствует, но.... " +
        "Усадили за столик с обратной стороны прудика, ожидали безконечно официанта. " +
        "\"Случайно\", вместо двух порций шашлыка принесли одну, постеснявшись уточнить сразу вес " +
        "порции мы дозаказали, после выясняется, что виноват повар, администратор, и.т.д. " +
        "Салаты не соответсвуют технологии . Стоимость ужина, подчеркиваю, очень скромного больше 2т.грн. " +
        "Советую посетить только в том случае, " +
        "если Вы предпочитаете совершать свои ошибки, и категорически не учитесь на чужом опыте.")
)

final Response<SentimentAlchemyEntity> sentimentResponse = client.call(sentimentCall)

println "Language: ${sentimentResponse.getLanguage()}"
println "Status: ${sentimentResponse.getStatus()}"
println "Status Info: ${sentimentResponse.getStatusInfo()}"
println "Text: ${sentimentResponse.getText()}"
println "Usage: ${sentimentResponse.getUsage()}"

sentimentResponse.iterator().each {
    println "isMixed: ${it.mixed}"
    println "Score: ${it.score}"
    println "Type: ${it.type}"
}

