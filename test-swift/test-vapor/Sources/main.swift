import Vapor

let app = Droplet()

app.get("/") { _ in
    return "Hello"
}

app.run()
