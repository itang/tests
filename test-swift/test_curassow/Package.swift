import PackageDescription

let package = Package(
    name: "test_curassow",
    dependencies: [
        .Package(url: "https://github.com/kylef/Curassow.git", majorVersion: 0, minor: 1),
    ]
)
