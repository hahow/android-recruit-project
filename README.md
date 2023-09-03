
## 專案配置
- IDE：Android Preview Hedgehog | 2023.1.1 Beta 1
- Android Compiler / Target SDK Version 31 (Android 12)
- min SDK Version 21
- gradle：7.0.3

## 使用的第三方套件
- RecyclerView：Epoxy
- DI：Koin
- Image Loader：Glide

## 架構
- MVVM + 以 DI 注入 UseCase / repository 
- 異步：coroutine + Flow
- UI：Single Activity + Multi Fragment + ViewBinding

## 特別說明
- 資料層
  - 將原測試資料放於 resource/asset ，模擬為網路API回傳的資料，撰寫於 [NetworkRepositoryImpl](app/src/main/java/com/hahow/data/network/NetworkRepositoryImpl.kt)
  - [HomeViewModel](app/src/main/java/com/hahow/views/home/HomeViewModel.kt) 運用 UseCase 來取得資料，撰寫於 ，透過 Input / Output 介面來定義 ViewModel 的行為，並且透過 Koin 來注入 UseCase
  - [GetCourseListUseCase](app/src/main/java/com/hahow/useCase/GetCourseListUseCase.kt) 透過 Flow 及 Repository 取得資料，並且將事件結果分為 Success / Error 等情境，最後發送回 ViewModel 進行資料更新
  - [Repository](app/src/main/java/com/hahow/data/RepositoryImpl.kt) 是資料倉儲的主站，介面宣告於 [CourseRepository](app/src/main/java/com/hahow/data/CourseRepository.kt)，負責處理資料來源的運用，資料來源分為[**本地端 LocalRepositoryImpl**](app/src/main/java/com/hahow/data/local/LocalRepositoryImpl.kt)及[**上述的網路端 NetworkRepositoryImpl**](app/src/main/java/com/hahow/data/network/NetworkRepositoryImpl.kt)

- UI層
  - 頁面進入點為 [HomeFragment](app/src/main/java/com/hahow/views/home/HomeFragment.kt)，將初始化行為分為
    - initView ：初始化 View 相關參數
    - observeData ：監聽 ViewModel 的 DataBinding
    - initAction ：觸發第一個 action ，開始取得資料
  - 由 Epoxy 來撰寫 RecyclerView 的 Adapter，撰寫於 [CourseListAdapter](app/src/main/java/com/hahow/views/home/adapter/HomeViewController.kt)
  - 藉由 Epoxy Model 撰寫可共用的 [Course Cell](app/src/main/java/com/hahow/ui/CourseCellModel.kt)，UI 呈現邏輯則是將 Data Model 轉換成 ViewInfo ，優點可以讓 Cell 彈性用於不同的 data Model 並且能將 UI 呈現邏輯解耦，撰寫於 [CourseCellViewInfo](app/src/main/java/com/hahow/ui/CourseCellViewInfo.kt)

- 注入
  - 透過 Koin 來注入 ViewModel / UseCase / Repository ，撰寫於 [app_module](app/src/main/java/com/hahow/di/app_module.kt)