# Common Android Library. [![](https://jitpack.io/v/kibotu/common.android.utils.svg)](https://jitpack.io/#kibotu/common.android.utils) [![Build Status](https://travis-ci.org/kibotu/common.android.utils.svg)](https://travis-ci.org/kibotu/common.android.utils)  [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)  [![Gradle Version](https://img.shields.io/badge/gradle-2.14.1-green.svg)](https://docs.gradle.org/current/release-notes)  [![Gradle Version](https://img.shields.io/badge/gradle-plugin-2.1.2-green.svg)](https://developer.android.com/studio/releases/gradle-plugin.html#updating-plugin) [![Licence](https://img.shields.io/badge/licence-Apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Retrolambda](https://img.shields.io/badge/java-8-green.svg)](https://github.com/evant/gradle-retrolambda) [![Javadoc](https://img.shields.io/badge/javadoc-green.svg)](https://jitpack.io/com/github/kibotu/common.android.utils/develop-SNAPSHOT/javadoc/index.html)

A collection of commonly used static android helper methods.

### How to install
	
	repositories {
	    maven {
	        url "https://jitpack.io"
	    }
	}
		
	dependencies {
            compile 'com.github.kibotu:common.android.utils:-SNAPSHOT'
    }
    

### Features

#### Extensions
    
    View

#### Input
#### GeoIp

    RequestProvider.freeGeoIpService().getGeoIp() // http://freegeoip.net/json/{$ip} 10k requests per hour
    RequestProvider.ipInfoIoService().getGeoIp()  // http://ipinfo.io/{$ip}/json 1k per day
    RequestProvider.ipApiComService().getGeoIp()  // http://ip-api.com/json/{$ip} 150 per minute

#### Sound
#### Storage

CachedHawkValue

#### Menu
    
### Simple Debug Logger