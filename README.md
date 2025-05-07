# Screenshot

## Network avaliable
![Network avaliable](https://github.com/ladmini/RoomBookList/blob/main/screenshot/network_ok.png)

## Network Not avaliable
![Network Not avaliable](https://github.com/ladmini/RoomBookList/blob/main/screenshot/network_ng.png)

# Data Loading Flow

```mermaid
graph TD
    A[Launch Application] --> B{Check Network Connection}
    B -->|Network Available| C[Call API for Data]
    B -->|No Network| F[Load Local Database Cache]
    
    C -->|Request Success| D[Clear Local Database]
    D --> E[Save API Data to Database]
    E --> G[Load and Display Data from Database]
    
    C -->|Request Failure| F
    F --> G
```

# Application Architecture

```mermaid
graph TD
    subgraph "UI Layer"
        A[BookListActivity] --> B[BookViewModel]
        A --> C[BookListAdapter]
    end
    
    subgraph "Business Logic Layer"
        B --> D[BookRepository]
    end
    
    subgraph "Data Layer"
        D --> E[BookDao]
        D --> F[BookNetworkRepository]
        F --> G[BookApiService]
        E --> H[AppDatabase]
    end
    
    subgraph "Network Layer"
        G --> I[Retrofit]
        I --> J[API Server]
    end
    
    subgraph "Local Storage Layer"
        H --> K[Room Database]
    end
    
    %% Data Flow
    J --Network Response--> I
    I --Parse Data--> G
    G --Return Result--> F
    F --Success/Failure--> D
    K --Read Data--> H
    H --Return Entities--> E
    E --Flow<List<Book>>--> D
    D --LiveData<List<Book>>--> B
    B --Data Updates--> A
    B --Data Binding--> C
```

# The API data is used for third-party services
For example, obtaining all books from the following API:
https://run.mocky.io/v3/6be67997-b989-4864-925a-6b33c49e4848/