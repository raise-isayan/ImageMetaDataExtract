Burp suite extension ImageMetaExtract
=============

Language/[Japanese](Readme-ja.md)

This tool is an extension of PortSwigger product, Burp Suite.
Supports Burp suite Professional/Community.

## Overview

This extension is a tool that allows the display of image metadata.

## About the latest version

The main repository (main) may contain code under development.
Please download the stable release version from the following.

* https://github.com/raise-isayan/ImageMetaDataExtract/releases

Please use the following versions

* Burp suite v2023.1.2 or above

## How to Use

The Burp Suite Extender can be loaded by following the steps below.

1. Click [add] on the [Extender] tab
2. Click [Select file ...] and select ImageMetaExtract.jar.
3. Click [Next], confirm that no error is occurring, and close the dialog with [Close].

### ImageMeta Tab

A tab is added if the response is in image format and contains meta information.

![MetaData Tab](/image/MetaDataTab.png)

## build

```
gradlew release
```

## Runtime environment

.Java
* JRE (JDK) 17 (Open JDK is recommended) (https://openjdk.java.net/)

.Burp suite
* v2023.1.2 or higher (http://www.portswigger.net/burp/)

## Development environment
* NetBean 21 (https://netbeans.apache.org/)
* Gradle 7.6 (https://gradle.org/)

## Required libraries
Building requires a [BurpExtensionCommons](https://github.com/raise-isayan/BurpExtensionCommons) library.
* BurpExtensionCommons v3.1.x
  * https://github.com/raise-isayan/BurpExtensionCommons

## Use Library

* google gson (https://github.com/google/gson)
  * Apache License 2.0
  * https://github.com/google/gson/blob/master/LICENSE

* Universal Chardet for java (https://code.google.com/archive/p/juniversalchardet/)
  * MPL 1.1
  * https://code.google.com/archive/p/juniversalchardet/

* Metadata extractor (https://github.com/drewnoakes/metadata-extractor)
  * Apache License 2.0
  * https://github.com/drewnoakes/metadata-extractor

Operation is confirmed with the following versions.
* Burp suite v2023.9.2

## important
This tool developed by my own personal use, PortSwigger company is not related at all. Please do not ask PortSwigger about problems, etc. caused by using this tool.
