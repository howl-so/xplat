// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/matt-ramotar/Howl/releases/assets/87157062.zip"
let remoteKotlinChecksum = "1feae8285c821b494cd9e3fc1756e4df2a07bcdb6a36471969282ab0ee385c4a"
let packageName = "StoreKit"

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: packageName,
            targets: [packageName]
        ),
    ],
    targets: [
        .binaryTarget(
            name: packageName,
            url: remoteKotlinUrl,
            checksum: remoteKotlinChecksum
        )
        ,
    ]
)