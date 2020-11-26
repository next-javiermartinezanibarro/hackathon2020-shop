package com.bbva.hackathon.bbvakids.shop.client;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/profiles")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface ProfileService {

    @GET
    @Path("/{id}")
    Profile getProfile(@PathParam("id") Long id);

    @PUT
    @Path("/{id}")
    Profile purchaseItem(@PathParam("id") Long id, @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Profile.class))) Profile profile);

    @POST
    @Path("/{id}/inventory")
    Item addItem(@PathParam("id") Long id, @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Item.class))) Item item);
}
