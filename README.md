# github-user
Github user exercise
### How to build
- IDE: android studio 3.5.1
- Import project
- Build variant
  - develop: build for debug
  - staging: build for unit and ui testing
  - release: build for release version
### Device support
- From API 21
- Both portrait and landscape mode
### Architecture
MVVM (android arch component) + Clean Architecture

```
└── githubuser
    ├── application
    │   └── GithubUserApplication.kt
    ├── data
    │   ├── entity
    │   │   ├── User.kt
    │   │   └── etc...
    │   └── network
    │       ├── UserApi.kt
    │       └── etc...
    ├── di
    │   ├── component
    │   ├── module
    │   └── scope
    ├── domain
    │   ├── usecase
    │   └── repository
    ├── ext
    ├── presentation
    │   ├── contract
    │   ├── presenter
    │   └── ui
    │       ├── acitivity
    │       ├── fragment
    │       └── view
    └── utils
        ├── network
        └── scheduler
 ```
 ### Testing
 - Unit testing: target for presenter package to guarantee business
 - UI testing: targer for MainActivity
 ### Noted
 - api: https://api.github.com/users?&since=x&per_page=y is not work correctly for number of return items. 
