# sell-plugin

PaperMC サーバー向けのシンプルな売却プラグインです。  
プレイヤーは `/sell` コマンドを使って、手に持っているアイテムをサーバーに売却できます。  
売却価格や実行コマンドは `config.yml` で自由に設定できます。

---

## ✨ 主な機能

- `/sell` コマンドで手持ちアイテムを売却
- `config.yml` でアイテムごとの売却価格を設定可能
- 売却時に任意のコマンドを実行（例：経済プラグインの pay コマンド）
- PaperMC（Java）向けの軽量プラグイン

---

## 📦 対応環境

- **Minecraft**: PaperMC 1.xx  
- **Java**: 17 以上（Paper の推奨に合わせて変更可）

---

## 🔧 インストール方法

1. `sell-plugin.jar` をダウンロード  
2. Paper サーバーの `plugins` フォルダに配置  
3. サーバーを起動すると `plugins/sell-plugin/config.yml` が生成されます  
4. `config.yml` を編集して価格やコマンドを設定

---

## ⚙️ config.yml の仕様

```yaml
# 価格設定
prices:
  DIAMOND: 1
  IRON_INGOT: 50
  APPLE: 10
  stone: 0

# 売却時に実行されるコマンド
# %user%  → 売却したプレイヤー名
# %money% → 売却額
pay: pay %user% %money%
```

### 🔍 説明

#### **prices**
- キー：Minecraft のアイテムID  
  - 例：`DIAMOND`, `IRON_INGOT`, `APPLE`, `STONE` など  
  - 大文字小文字はプラグイン側で処理されるため、`stone` のような小文字でも可
- 値：売却価格（整数）

#### **pay**
売却時にサーバー側で実行されるコマンド。  
経済プラグインと連携するために使用します。

利用可能な変数：

| 変数 | 内容 |
|------|------|
| `%user%` | 売却したプレイヤー名 |
| `%money%` | 売却額 |

例：

```yaml
pay: pay %user% %money%
```

---

## 🕹️ コマンド

### `/sell`
手に持っているアイテムを売却します。  
売却額は `config.yml` の設定に基づきます。

---

## 📄 ライセンス

MIT（変更可能）

---

## 👤 作者

（必要ならここに名前やリンクを記載）
