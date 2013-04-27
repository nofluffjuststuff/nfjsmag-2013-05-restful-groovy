def 'structural and transitional links for kirk are correct'() {
    when:
    def response = client.get(path: 'people/3')
    
    then:
    'James Kirk' == "$response.data.first $response.data.last"
    response.getHeaders('Link').each { println it }
    assert response.data.prev.href == 'http://localhost:1234/people/2'
    assert response.data.self.href == 'http://localhost:1234/people/3'
    assert response.data.next.href == 'http://localhost:1234/people/4'
}
