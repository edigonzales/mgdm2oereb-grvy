package ch.so.agi.mgdm2oereb

//@groovy.transform.CompileStatic
class App {
    String getGreeting() {
        return 'Hello World!'
    }

    static void main(String[] args) {
        println new App().greeting
    }
}
