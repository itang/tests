import PackageDescription

let package = Package(
    name: "test-vapor",
    dependencies: [
        .Package(url: "https://github.com/qutheory/vapor.git", majorVersion: 0)
        //.Package(url: "https://github.com/apple/example-package-deckofplayingcards.git", majorVersion: 1),
    ]
)

