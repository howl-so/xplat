// swift-tools-version:5.3
import PackageDescription

let remoteKotlinUrl = "https://api.github.com/repos/matt-ramotar/Howl/releases/assets/87153208.zip"
let remoteKotlinChecksum = "5a47653eaf969efa10fe742f972414c2a0e92fd135275177730015c6181abc07"
let packageName = "HowlApi"

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