// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/matt-ramotar/Howl/releases/assets/87155418.zip"
let remoteKotlinChecksum = "0723176a3b5f92d287f49d283a9cf93e59e77a362a0d0b9c32277cc6398faeae"
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