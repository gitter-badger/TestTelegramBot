import com.likethecolor.alchemy.api.Client
import fi.foyt.foursquare.api.FoursquareApi
import fi.foyt.foursquare.api.Result
import fi.foyt.foursquare.api.entities.CompactVenue
import fi.foyt.foursquare.api.entities.VenuesSearchResult
import org.yaml.snakeyaml.Yaml

def yaml = new Yaml().load(MaapBotConfig.getResourceAsStream("/maapbot.yaml"))

FoursquareApi foursquareApi = new FoursquareApi(
        yaml.maapbot.foursquareClientId,
        yaml.maapbot.foursquareClientSecret,
        "Callback URL");

def request = ['ll':'50.50,30.50','near':'Оболонь','section':'food','query':'мясо']

//One of food, drinks, coffee, shops, arts, outdoors, sights, trending or specials,
//nextVenues (venues frequently visited after a given venue),
//or topPicks (a mix of recommendations generated without a query from the user). Choosing one
//of these limits results to venues with the specified category or property.

Result<VenuesSearchResult> result = foursquareApi.venuesExplore(
        '50.50,30.50', null, null, null, 1000, 'food', ' пицца',10, null) as Result<VenuesSearchResult>;

if (result.getMeta().getCode() == 200) {
    for (CompactVenue venue : result.getResult().getVenues()) {
        println venue.getName()
    }
} else {
    println "Error occured: "
    println "  code: " + result.getMeta().getCode()
    println "  type: " + result.getMeta().getErrorType()
    println "  detail: " + result.getMeta().getErrorDetail()
}

