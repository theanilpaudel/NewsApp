# NewsApp

A modern news application built using **Jetpack Compose** and **MVVM architecture with Use Cases**.  
This app fetches news from **NewsAPI**, allows users to save favorite articles, and manage news sources with persistence.

---

## Table of Contents
- [App Architecture](#app-architecture)
- [Navigation](#navigation)
- [Screens](#screens)
- [API Integration](#api-integration)
- [Persistence](#persistence)
- [Dependency Injection](#dependency-injection)
- [Key Components](#key-components)
- [Use Cases](#use-cases)
- [Testing](#testing)
- [Design Choices](#design-choices)
- [Future Improvements](#future-improvements)
- [Contact](#contact)

---

## App Architecture

The app follows a **Clean MVVM (Model-View-ViewModel)** approach with **Use Cases** for business logic:

- **data**
  - Data classes.
  - Repositories handle fetching data from **API**, **Room**, or **DataStore**.

- **domain**
  - Encapsulate a **single unit of business logic**.
  - Examples: fetching headlines, saving an article, retrieving persisted sources.
  - Keeps ViewModels slim and ensures testability.
  - DTOs

- **ViewModel**
  - Consumes **use cases**.
  - Orchestrates business logic and exposes reactive **StateFlow** to the UI.

- **View (Composable)**
  - Built entirely with **Jetpack Compose**.
  - Observes ViewModel state and renders UI reactively.

Other architectural highlights:
- **Coroutines** for async work.
- **StateFlow** for reactive data flow.
- **Hilt** for DI across layers.

---

## Navigation

- Powered by **Jetpack Navigation Component for Compose**.
- **Bottom Navigation Bar** with three tabs:
  - **Headlines:** Top news headlines.
  - **Sources:** Source selection screen.
  - **Saved:** Saved articles.

- **WebView:** Opens news articles inside the app.

---

## Screens

### Headlines
- Displays a list of news articles with:
  - Title, description, author, and thumbnail (loaded with **Coil**).
- Tapping opens the article in WebView.
- Articles can be **saved for later reading**.

### Sources
- Displays available news sources.
- Users can select multiple sources.
- Persisted via **DataStore**.

### Saved
- Displays all saved articles.
- Allows deletion.
- Persistence via **Room database**.

---

## API Integration

- **NewsAPI** for fetching news & sources.
- **Retrofit + OkHttp + Kotlinx Serialization** for API calls & JSON parsing.
- API responses wrapped in **DataState** → `Loading`, `Success`, `Error`.

---

## Persistence

### Room Database
- Stores saved articles.
- Handles insert, delete, and query operations.

### DataStore
- Persists selected news sources.
- Retained across sessions.

---

## Dependency Injection

- **Hilt** manages DI across:
  - Retrofit service
  - Repositories
  - Use cases
  - ViewModels
- Promotes modularity and easier testing.

---

## Key Components

- **Composable Components:**
  - `NewsScreen`, `SourceScreen`, `SavedScreen`
  - `ArticleCard`, `SourceSingleItem` (reusable UI)

- **ViewModels:**
  - `NewsViewModel`, `SourceViewModel`, `SavedViewModel`

- **Repositories:**
  - `NewsRepo` → handles API + local data access

- **DataState:**
  - Wrapper for loading/error/success states

---

## Use Cases

Use cases act as the **business logic layer** between ViewModels and repositories.

Examples in this project:
- **GetNewsListUC** → Fetches news from NewsAPI. Default all news in `en` language. Later on from Saved Sources
- **GetSourceListUC** → Retrieves available news sources.
- **SaveArticleUC** → Saves article to Room.
- **GetSavedSourceListUC** → Loads all saved sources.
- **SaveSourceListUC** → Saves selected sources in DataStore.

✅ **Benefits of use cases:**
- Keeps ViewModels **focused on UI state only**.
- Each use case is **testable in isolation**.
- Enforces **single responsibility principle**.
- Scales better when adding new business logic.

---

## Design Choices

1. **Jetpack Compose** for declarative UI.
2. **MVVM + Use Cases** for clean separation of concerns.
3. **StateFlow** for reactive state updates.
4. **Room + DataStore** for persistence.
5. **Coil** for image loading with placeholder & error support.
6. **Hilt** for dependency injection.
7. **WebView** for seamless article browsing.
8. **Reusable components** for maintainable code.
9. **Coroutines** for async operations.

---

## Future Improvements
- UI fixes for selected sources in **SourcesScreen**
- Display selected sources in **NewsScreen** with option to deselect
- Writing Test cases
- Utilizing Hilt to install/uninstall test modules and increasing test coverage

---

## Contact

Developed by **Anil Paudel** ✨  
