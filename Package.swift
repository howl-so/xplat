// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/matt-ramotar/Howl/releases/assets/87393572.zip"
let remoteKotlinChecksum = "8551c3a889deb5f6d8898a7ba42120a0c303d93eb1237e43d3de01cde60c1cbc"
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