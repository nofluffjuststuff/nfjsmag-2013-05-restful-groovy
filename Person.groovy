import groovy.transform.EqualsAndHashCode
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@EqualsAndHashCode
class Person {
    Long id
    String first
    String last

    String toString() { "$first $last" }
}
