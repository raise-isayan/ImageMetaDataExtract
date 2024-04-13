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
