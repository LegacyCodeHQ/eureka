# Tumbleweed (alpha) ![CI](https://github.com/legacycodehq/tumbleweed/actions/workflows/jvm-tests.yml/badge.svg) ![GitHub release (latest by date)](https://img.shields.io/github/v/release/legacycodehq/tumbleweed)

Understand Kotlin and Java classes with ease 😎

## Is this for me?

- You have joined a new team or an organization and want to understand the codebase.
- You want to contribute to an open-source project to build your portfolio.
- You want to break down a large class.
- You want to bring a function under test and want to break dependencies and see them in real-time.

## Installation

```bash
brew install legacycodehq/tap/twd
```

## Updates

```bash
brew upgrade twd
```

## Live demo

You can try this [sample interactive graph](https://redgreenio.github.io/) for Signal Android
App's [StoryViewerPageFragment](https://github.com/signalapp/Signal-Android/blob/ff8f9ca81ae6a25e1e946612c817206b9410d9a1/app/src/main/java/org/thoughtcrime/securesms/stories/viewer/page/StoryViewerPageFragment.kt)
class.

## Features

### 1. Class visualization

You must first build the project and then run the tool.

The command will start a web server on port 7070. Go to `localhost:7070` in your browser to see the diagram. The diagram
updates in real-time as you make changes to the source code and compile the project.

For more options, run `twd watch --help`.

![Edge bundling graph](docs/images/watch.png)

#### 1.1 Android support (experimental)

```bash
twd watch -x android MediaPreviewActivity
```

Read [this blog post](https://legacycode.com/android-support) to learn how to use this feature when examining Android
classes.

#### 1.2 Without Android support

```bash
twd watch MediaPreviewActivity
```

### 2. File ownership (experimental)

![Treemap](docs/images/ownership.png)

#### Usage

```bash
twd ownership --repo <path-to-git-repo>
```

The command will start a web server on port 7080. Visit the app on `localhost:7080`.

### 3. Module dependency diagram (experimental)

![PlantUML component diagram](docs/images/modules.png)

#### Usage

```bash
twd modules ~/GitHubProjects/Signal-Android
```

The command will print a Graphviz directed graph DSL.

1. Copy the DSL and paste it into the [online Graphviz tool](https://dreampuf.github.io/GraphvizOnline).
2. The tool selects the **dot** engine by default, for best results use the **circo** engine from the engine dropdown.

## Licenses

```
Copyright (c) 2022-Present, Ragunath Jawahar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

```
Copyright 2018–2020 Observable, Inc.

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
```
