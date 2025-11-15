## Typing Game 

#### プロジェクト概要

これは、独自で開発を行なった`Java`2Dタイピングゲームである。

プレイヤーは、飛び交うリングの中に表示される英単語をタイピングすることで、勝利を手にするミッションが与えられる。

#### プログラムの役割一覧

`WordLoader.java`

- 英単語リストを辞書ファイルから読み込む。
- ランダムに英単語を選択する。

`ColorfulCircle.java`

- リングの各データを網羅的に保存する。
- 自分を画面に表示させる。

`HelpWindow.java`

- ゲームの全体的なルールを示したウィンドウを開く。

`StartPanel.java`

- スタート待ち画面を表示する。

`GamingPanel.java`

- ゲーム実行中の画面を表示する。

`GameLauncher.java`

- メイン函数を格納する。
- パネルの制御を支配する。

#### ゲームプレイの手順

1. `javac GameLauncher.java` でコンパイルを行う。
2. `java GameLauncher`をターミナルで実行するまたは`GameLauncher.class`フィアルをダブルクリックすると、ゲームウィンドウが開かれます。
3. `New Game`ボタンをクリックすると、ゲームが即座に開始します。
4. ゲームが実行されているうちに、いつでも`Pause`(一時停止)、`Resume`(再生)、`Help`(ルール説明)、及び`Quit`(ゲーム中止)ボタンの操作が可能です。
5. ゲームで勝利または失敗した場合、それぞれのゲーム結果が表示されます。

#### プレイデモ動画

YouTube URL: [Click me](https://youtu.be/a0q2GQDPtf4)
