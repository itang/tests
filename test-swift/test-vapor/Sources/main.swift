import Vapor

let app = Application()

app.get("/") { req in
    return "Hello"
}

app.start(port: 3000)
