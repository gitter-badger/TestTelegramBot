import fi.foyt.foursquare.api.FoursquareApi
import fi.foyt.foursquare.api.Result
import fi.foyt.foursquare.api.entities.CompactVenue
import fi.foyt.foursquare.api.entities.VenuesSearchResult

FoursquareApi foursquareApi = new FoursquareApi("Client ID", "Client Secret", "Callback URL");

Result<VenuesSearchResult> result = foursquareApi.venuesSearch("Obolon", null, null, null, null, null, null, null, null, null, null, null, null);

if (result.getMeta().getCode() == 200) {
    for (CompactVenue venue : result.getResult().getVenues()) {
        System.out.println(venue.getName());
    }
} else {
    System.out.println("Error occured: ");
    System.out.println("  code: " + result.getMeta().getCode());
    System.out.println("  type: " + result.getMeta().getErrorType());
    System.out.println("  detail: " + result.getMeta().getErrorDetail());
}

