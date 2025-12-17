package com.empresaurios.stylist.controller;

import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import com.empresaurios.stylist.bean.PaginatedResponse;
import com.empresaurios.stylist.bean.Users;
import com.empresaurios.stylist.service.UserService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userSService;

     public UserController(UserService userSService) {
        this.userSService = userSService;
    }

    @GET
    @Path("/listAll")
    public PaginatedResponse<Users> listAll(@QueryParam ("page") @DefaultValue("1") int  page) {
        return userSService.listAll(page);
    }

    @GET
    @Path("/{email}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Get user by email",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = Users.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "No user found for email provided",
                            content = @Content(mediaType = "application/json")),
            }
    )
    public Response getById(@PathParam("email") String userEmail) {
        Optional<Users> optional = userSService.findByEmail(userEmail);
        return !optional.isEmpty() ? Response.ok(optional.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "user Created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = Users.class))),
                    @APIResponse(
                            responseCode = "400",
                            description = "user already exists for customerId",
                            content = @Content(mediaType = "application/json")),
            }
    )
    public Response post(@Valid Users users) {
        final Users saved = userSService.createUser(users);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "user updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = Users.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "No user found for email provided",
                            content = @Content(mediaType = "application/json")),
            }
    )
    public Response put(@Valid Users user,@Valid String email) {
        final Users saved = userSService.updateUsers(email,user);
        return Response.ok(saved).build();
    }
}
