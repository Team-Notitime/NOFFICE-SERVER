# Noffice-mermaid ERD

```mermaid
---
title : NOFFICE-ERD (ver 1.0.0)
---
erDiagram
    MEMBER {
        bigint id PK
        varchar name
        varchar alias
        text profile_image
        varchar email
        varchar serial_id
        SocialAuthProvider social_auth_provider
    }

    ORGANIZATION {
        bigint id PK
        varchar name
        timestamp end_at
        varchar profile_image
    }

    ORGANIZATION ||--|{ ORGANIZATION_CATEGORY: has
    CATEGORY ||--|{ ORGANIZATION_CATEGORY: categorized
    ORGANIZATION_CATEGORY {
        bigint id PK
        bigint organization_id FK
        bigint category_id FK
    }

    CATEGORY {
        bigint id PK
        varchar name
    }
    ORGANIZATION_PROMOTION {
        bigint id PK
        bigint organization_id FK
        bigint promotion_id FK
        timestamp start_date
        timestamp end_date
    }

    MEMBER ||--|{ ORGANIZATION_MEMBER: belongs
    ORGANIZATION ||--|{ ORGANIZATION_MEMBER: belongs
    ORGANIZATION_MEMBER {
        bigint id PK
        bigint organization_id FK
        bigint member_id FK
        OragnizationRole role
    }

    FCM_NOTIFICATION_TOKEN }o--|| MEMBER: "has"
    FCM_NOTIFICATION_TOKEN {
        bigint id PK
        bigint member_id FK
        varchar device_id
        varchar fcm_token
    }

    MEMBER ||--|| REFRESH_TOKEN: authenticate
    REFRESH_TOKEN {
        varchar id PK
        bigint member_id FK
        varchar refresh_token
        SocialAuthProvider auth_provider
        timestamp expired_date_time
    }

%%    MEMBER ||--o{ MEMBER_READ_STATUS: checks
%%    MEMBER_READ_STATUS {
%%        bigint id PK
%%        bigint announce_id FK
%%        bigint member_id FK
%%        boolean checked
%%    }
    ORGANIZATION ||--o{ ANNOUNCEMENT: "has"
    ANNOUNCEMENT {
        bigint id PK
        bigint creator_id FK
        bigint organization_id FK
        varchar title
        text content
        varchar cover_image
        varchar place_link_title
        varchar place_link_url
        timestamp start_at
        timestamp end_at
        timestamp notice_at
    }
    ORGANIZATION ||--o{ ORGANIZATION_PROMOTION: "promote event"
    PROMOTION ||--o{ ORGANIZATION_PROMOTION: ""
    PROMOTION {
        bigint id PK
        varchar code
        boolean used
    }

    ANNOUNCEMENT ||--o{ TASK: has
    TASK {
        bigint id PK
        bigint announce_id FK
        varchar content
    }
    
    MEMBER ||--o{ TASK_STATUS: records
    TASK ||--o{ TASK_STATUS: updates
    TASK_STATUS {
        bigint id PK
        bigint task_id FK
        bigint member_id FK
        boolean checked
        timestamp checked_at
    }

    ANNOUNCEMENT ||--|{ ANNOUNCEMENT_IMAGE: "has"
    ANNOUNCEMENT_IMAGE {
        bigint id PK
        bigint announcement_id FK
        varchar image_url
        boolean is_promotion
    }

    PROMOTION_IMAGE {
        bigint id PK
        text image_url
    }

    BASETIMEENTITY {
        timestamp created_at
        timestamp updated_at
    }
```