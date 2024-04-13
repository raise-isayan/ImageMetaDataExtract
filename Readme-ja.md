Burp suite 拡張 ImageMetaExtract
=============

Language/[English](Readme.md)

このツールは、PortSwigger社の製品であるBurp Suiteの拡張になります。
Burp Pro/Communityに対応しています。

## 概要

この拡張は画像のメタデータの表示を行うことが可能なツールです。

## 最新版について

メインのリポジトリ(main)には開発中のコードが含まれている場合があります。
安定したリリース版は､以下よりダウンロードしてください。

* https://github.com/raise-isayan/ImageMetaDataExtract/releases

利用するバージョンは以下のものをご利用ください

* Burp suite v2023.1.2 より後のバージョン

## 利用方法

Burp suite の Extenderは以下の手順で読み込めます。

1. [Extender]タブの[add]をクリック
2. [Select file ...]をクリックし、ImageMetaExtract.jar を選択する。
3. ｢Next｣をクリックし、エラーがでてないことを確認後、「Close」にてダイヤログを閉じる。

### ImageMeta タブ

レスポンスが画像形式でありメタ情報が含まれる場合にタブが追加されます。

![MetaData Tab](/image/MetaDataTab.png)

## コマンドラインオプション

コマンドラインから Image のメタデータを出力することが可能です。

```
java -jar ImageMetaExtract.jar -file=<ImageFileName>
```

<ImageFileName> にメタデータを出力したい画像を指定します。レスポンスはタブ区切り形式で出力されます。

例)
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

## ビルド

```
gradlew release
```

## 実行環境

.Java
* JRE (JDK) 17 (Open JDK is recommended) (https://openjdk.java.net/)

.Burp suite
* v2023.1.2 or higher (http://www.portswigger.net/burp/)

## 開発環境
* NetBean 21 (https://netbeans.apache.org/)
* Gradle 7.6 (https://gradle.org/)

## 必須ライブラリ
ビルドには別途 [BurpExtensionCommons](https://github.com/raise-isayan/BurpExtensionCommons) のライブラリを必要とします。
* BurpExtensionCommons v3.1.x
  * https://github.com/raise-isayan/BurpExtensionCommons

## 利用ライブラリ

* google gson (https://github.com/google/gson)
  * Apache License 2.0
  * https://github.com/google/gson/blob/master/LICENSE

* Universal Chardet for java (https://code.google.com/archive/p/juniversalchardet/)
  * MPL 1.1
  * https://code.google.com/archive/p/juniversalchardet/

* Metadata extractor (https://github.com/drewnoakes/metadata-extractor)
  * Apache License 2.0
  * https://github.com/drewnoakes/metadata-extractor

以下のバージョンで動作確認しています。
* Burp suite v2023.9.2

## 注意事項
このツールは、私個人が勝手に開発したもので、PortSwigger社は一切関係ありません。本ツールを使用したことによる不具合等についてPortSwiggerに問い合わせないようお願いします。
