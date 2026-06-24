```mermaid
erDiagram
USERS {
Long id PK
String username
String email
String password
String role
boolean enabled
Long profile_id FK
}

    USER_PROFILES {
        Long id PK
        String first_name
        String last_name
        String phone_number
        String address
    }

    GAMES {
        Long id PK
        String title
        String description
        double daily_rent_price
        int stock
        Long console_id FK
    }

    CONSOLES {
        Long id PK
        String name
        String manufacturer
    }

    CATEGORIES {
        Long id PK
        String name
    }

    RENTALS {
        Long id PK
        LocalDate rental_date
        LocalDate return_date
        LocalDate actual_return_date
        double total_price
        Long user_id FK
        Long game_id FK
    }

    %% Relațiile dintre tabele
    USERS |o--|| USER_PROFILES : "has_profile (1:1)"
    USERS ||--o{ RENTALS : "makes (1:N)"
    GAMES ||--o{ RENTALS : "is_rented (1:N)"
    CONSOLES ||--o{ GAMES : "contains (1:N)"
    GAMES }o--o{ CATEGORIES : "game_categories (M:N)"