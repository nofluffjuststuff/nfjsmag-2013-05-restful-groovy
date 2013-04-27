@Test
void testNextAndPreviousHeaders() {
    Client cl = ClientBuilder.newClient()
    int id = 1
    WebTarget target = cl.target("http://localhost:1234/people/$id")
    def resp = target.request().get(Response.class)
    def next = resp.getLink('next').uri
    assert next.toString()[-1] == (++id).toString()
    println 'following next links...'
    while (next) {
        println "Accessing $next"
        target = cl.target(next)
        resp = target.request().get(Response.class)
        next = resp.getLink('next')?.uri
        if (next)
            assert next.toString()[-1] == (++id).toString()
    }
    println 'following prev links...'
    def prev = resp.getLink('prev').uri
    assert prev.toString()[-1] == (--id).toString()
    while (prev) {
        println "Accessing $prev"
        target = cl.target(prev)
        resp = target.request().get(Response.class)
        prev = resp.getLink('prev')?.uri
        if (prev)
            assert prev.toString()[-1] == (--id).toString()
    }
    cl.close()
}
