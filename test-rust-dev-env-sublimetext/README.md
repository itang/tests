Rust开发环境搭建(SublimeText)
---------------

## 安装 Rust和Cargo

$curl -sf -L https://static.rust-lang.org/rustup.sh | sudo sh

## 下载rust 源码
$ git clone git@github.com:rust-lang/rust.git​ --depth 1

## 安装Racer

### 下载源码构建

$ git clone git@github.com:phildawes/racer.git

$ cd racer; cargo build --release

### 设置~/.profile

```
export RUST_SRC_PATH=RUST_SRC_HOMEXXXXXXXXXXX

export PATH=RACER_SRC_HOME/target/release/racer
```

$ source ~./profile

## 安装Sublime Text 3

### 安装Rust插件

### 安装RustAutoComplete插件

设置:

Package setting-> RustAutoComplete user settings

```
// Copy this and place into your Packages/User directory.
{
  // The full path to the racer binary. If racer is already
  // in your system path, then this default will be fine.
  "racer": "racer",
  
  // A list of search paths. This should generally just
  // be the path to the rust compiler src/ directory.
  "search_paths": [
            "/home/itang/sources/rust/src"
  ]
}
```


### 安装SublimeLinter-contrib-rustc插件

先安装sublimelinter


