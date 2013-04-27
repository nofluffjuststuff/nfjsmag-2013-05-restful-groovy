@GET @Path("{id}")
@Produces([MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML])
Response findById(@PathParam("id") long id) {
    Person p = dao.findById(id)
    getLinks(id).each { link ->
        p."${link.rel}" = link   // structural links
    }
    Response.ok(p)
        .links(getLinks(id))     // transitional links
        .build()
}

private Link[] getLinks(long id) {
    long minId = dao.minId
    long maxId = dao.maxId
    UriBuilder builder = UriBuilder.fromUri(uriInfo.requestUri)
    Link self = Link.fromUri(builder.build()).rel('self').build()
    String uri = builder.build().toString() - "/$id"
    switch (id) {
    case minId:
        Link next = Link.fromUri("${uri}/${id + 1}").rel('next').build()
        return [self, next]
        break
    case maxId:
        Link prev = Link.fromUri("${uri}/${id - 1}").rel('prev').build()
        return [prev, self]
        break
    default:
        Link next = Link.fromUri("${uri}/${id + 1}").rel('next').build()
        Link prev = Link.fromUri("${uri}/${id - 1}").rel('prev').build()
        return [prev, self, next]
    }
}
