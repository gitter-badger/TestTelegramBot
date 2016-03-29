import com.likethecolor.alchemy.api.*
import com.likethecolor.alchemy.api.call.AbstractCall
import com.likethecolor.alchemy.api.call.SentimentCall
import com.likethecolor.alchemy.api.call.type.CallTypeText
import com.likethecolor.alchemy.api.call.type.CallTypeUrl
import com.likethecolor.alchemy.api.entity.Response
import com.likethecolor.alchemy.api.entity.SentimentAlchemyEntity

def apiKey = "e0614aa30fa5382fcead9e124974bb26941a6b7b";
def Client client = new Client(apiKey);

/*
final AbstractCall<SentimentAlchemyEntity> sentimentCall = new SentimentCall(
        new CallTypeUrl("https://foursquare.com/v/вареничная-катюша/4fae1d2de4b085f6b283ae5e")
);
*/

final AbstractCall<SentimentAlchemyEntity> sentimentCall = new SentimentCall(
        new CallTypeText("Были вдвоем, ужинали по поводу. Цена и качество-вещи не совместимые. " +
                "Интерьер, действительно выполнен необычно, идея присутствует, но.... " +
                "Усадили за столик с обратной стороны прудика, ожидали безконечно официанта. " +
                "\"Случайно\", вместо двух порций шашлыка принесли одну, постеснявшись уточнить сразу вес " +
                "порции мы дозаказали, после выясняется, что виноват повар, администратор, и.т.д. " +
                "Салаты не соответсвуют технологии . Стоимость ужина, подчеркиваю, очень скромного больше 2т.грн. " +
                "Советую посетить только в том случае, " +
                "если Вы предпочитаете совершать свои ошибки, и категорически не учитесь на чужом опыте.")
);

final Response<SentimentAlchemyEntity> sentimentResponse = client.call(sentimentCall);

System.out.println("Language: " + sentimentResponse.getLanguage());
System.out.println("Status: " + sentimentResponse.getStatus());
System.out.println("Status Info: " + sentimentResponse.getStatusInfo());
System.out.println("Text: " + sentimentResponse.getText());
System.out.println("Usage: " + sentimentResponse.getUsage());
System.out.println("URL: " + sentimentResponse.getURL());

SentimentAlchemyEntity entity;
final Iterator<SentimentAlchemyEntity> iter = sentimentResponse.iterator();
while (iter.hasNext()) {
    entity = iter.next();
    System.out.println("isMixed: " + (entity.isMixed() ? "true" : "false"));
    System.out.println("Score: " + entity.getScore());
    System.out.println("Type: " + entity.getType());
}

