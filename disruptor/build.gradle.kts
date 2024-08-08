plugins {
    id("org.iproute.commons-dep")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // https://mvnrepository.com/artifact/com.lmax/disruptor
    implementation("com.lmax:disruptor:3.4.2")

}
