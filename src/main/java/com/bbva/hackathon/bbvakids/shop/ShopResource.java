package com.bbva.hackathon.bbvakids.shop;

import com.bbva.hackathon.bbvakids.shop.client.Profile;
import com.bbva.hackathon.bbvakids.shop.client.ProfileService;
import com.bbva.hackathon.bbvakids.shop.item.Item;
import com.bbva.hackathon.bbvakids.shop.item.ItemService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/shop")
@Produces(APPLICATION_JSON)
public class ShopResource {

    private static final Logger LOGGER = Logger.getLogger(ShopResource.class);

    @Inject
    ItemService itemService;

    @Inject
    @RestClient
    ProfileService profileService;

    @Operation(summary = "Returns all the items from the database")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Item.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No items")
    @GET
    public Response getAllItems() {
        List<Item> items = itemService.findAllItems();
        return Response.ok(items).build();
    }

    @Operation(summary = "Returns a item for a given identifier")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Item.class)))
    @APIResponse(responseCode = "204", description = "The Profile is not found for a given identifier")
    @GET
    @Path("/{id}")
    public Response getItem(
            @Parameter(description = "Item identifier", required = true)
            @PathParam("id") Long id) {
        Item item = itemService.findItemById(id);
        if (item != null) {
            LOGGER.debug("Found item " + item);
            return Response.ok(item).build();
        } else {
            LOGGER.debug("No iten found with id " + id);
            return Response.noContent().build();
        }
    }

    @POST
    @Path("/purchase")
    public Response purchaseItem(
            @Parameter(description = "Item identifier", required = true)
            @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Item.class))) Item item, @HeaderParam("profileId") Long profileId) {
        item = itemService.findItemById(item.id);
        if (item == null) {
            return Response.status(404).build();
        }

        Profile profile = profileService.getProfile(profileId);

        if (profile.level < item.requiredLevel) {
            return Response.status(404).build();
        }

        if (profile.gems < item.requiredGems) {
            return Response.status(404).build();
        }

        profile.gems -= item.requiredGems;

        profileService.purchaseItem(profileId, profile);
        com.bbva.hackathon.bbvakids.shop.client.Item apiItem = new com.bbva.hackathon.bbvakids.shop.client.Item();
        apiItem.id = item.id;
        apiItem.name = item.name;
        apiItem.type = item.type;
        apiItem.requiredLevel = item.requiredLevel;
        profileService.addItem(profileId, apiItem);

        return Response.accepted().build();
    }
}