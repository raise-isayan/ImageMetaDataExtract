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

## Command line option

 It is possible to output Image metadata from the command line.

```
java -jar ImageMetaExtract.jar -file=<ImageFileName>
```

Specify the image for which you want to output <ImageFileName> metadata.
The response will be output in tab-delimited format.

ex)
```
java -jar ImageMetaExtract.jar -file=test.jpg

JPEG    Compression Type        0xfffffffd      Baseline
JPEG    Data Precision  0x0000  8 bits
JPEG    Image Height    0x0001  1111 pixels
JPEG    Image Width     0x0003  833 pixels
JPEG    Number of Components    0x0005  3
JPEG    Component 1     0x0006  Y component: Quantization table 0, Sampling factors 2 horiz/2 vert
JPEG    Component 2     0x0007  Cb component: Quantization table 1, Sampling factors 1 horiz/1 vert
JPEG    Component 3     0x0008  Cr component: Quantization table 1, Sampling factors 1 horiz/1 vert
JFIF    Version 0x0005  1.1
JFIF    Resolution Units        0x0007  none
JFIF    X Resolution    0x0008  1 dot
JFIF    Y Resolution    0x000a  1 dot
JFIF    Thumbnail Width Pixels  0x000c  0
JFIF    Thumbnail Height Pixels 0x000d  0
Exif IFD0       Software        0x0131  Google
ICC Profile     Profile Size    0x0000  3048
ICC Profile     Version 0x0008  2.0.0
ICC Profile     Class   0x000c  Display Device
ICC Profile     Color space     0x0010  RGB
ICC Profile     Profile Connection Space        0x0014  XYZ
ICC Profile     Profile Date/Time       0x0018  2009:03:27 21:36:31
ICC Profile     Signature       0x0024  acsp
ICC Profile     Device attributes       0x0038  4294967296
ICC Profile     XYZ values      0x0044  0.964 1 0.825
ICC Profile     Tag Count       0x0080  16
(...)
```

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
