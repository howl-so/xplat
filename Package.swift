// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/matt-ramotar/Howl/releases/assets/87141104.zip"
let remoteKotlinChecksum = "85df2aab692acfd4057dbf767836477b7834fbd20ad3f16bdfd5997b51daaa5f"
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