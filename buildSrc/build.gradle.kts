plugins {
    `kotlin-dsl`
}

repositories {
    maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public/") }
    maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/jcenter") }
    maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/google") }
    maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin") }
}