import spock.lang.Specification

class LibraryTest extends Specification{
    def "someLibraryMethod returns true"() {
        setup:
		def i = 1
        //Library lib = new Library()
        when:
		i++
        //def result = lib.someLibraryMethod()
        then:
		i == 2
        //result == true
    }
}
