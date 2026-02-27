# FU News Management System - REST API

Base URL: `http://localhost:8080` (default)

## Authentication
- **Login**: `POST /api/auth/login`  
  Body: `{ "email": "...", "password": "..." }`  
  Response: `{ "token": "JWT...", "account": { accountId, accountName, accountEmail, accountRole } }`
- **Current user**: `GET /api/auth/me`  
  Header: `Authorization: Bearer <token>`

## Public (no token)
- `GET /api/news` – list active news (query: keyword, page, size, sortBy, sortDir)
- `GET /api/news/{id}` – get one active news by id

## Admin (role = 1, header: Authorization: Bearer &lt;token&gt;)
- **Accounts**: CRUD + search  
  - `GET /api/accounts` – search (keyword, page, size, sortBy, sortDir)  
  - `GET /api/accounts/{id}`  
  - `POST /api/accounts` – create  
  - `PUT /api/accounts/{id}` – update  
  - `DELETE /api/accounts/{id}` – delete only if account has no news articles  

## Staff (role = 2) & Admin
- **Profile**
  - `GET /api/accounts/me` – current user
  - `PUT /api/accounts/me` – update profile (name, email, password)
- **Categories**: CRUD + search  
  - `GET /api/categories` – search (keyword, isActive, page, size, sortBy, sortDir)  
  - `GET /api/categories/{id}`  
  - `POST /api/categories`  
  - `PUT /api/categories/{id}`  
  - `DELETE /api/categories/{id}` – delete only if category has no news  
- **News (manage)**
  - `GET /api/my/news` – news created by current user (keyword, page, size, sortBy, sortDir)
  - `GET /api/news/{id}/edit` – get one article (any status) for edit
  - `POST /api/news` – create (body: NewsArticleRequest, createdBy = current user)
  - `PUT /api/news/{id}` – update
  - `DELETE /api/news/{id}`
- **Tags**: CRUD + search  
  - `GET /api/tags` – search (keyword, page, size, sortBy, sortDir)  
  - `GET /api/tags/{id}`  
  - `POST /api/tags`  
  - `PUT /api/tags/{id}`  
  - `DELETE /api/tags/{id}`  

## Default accounts (created on first run)
- Admin: `admin@funews.com` / `admin123`
- Staff: `staff@funews.com` / `staff123`

## Configuration
- Database: MS SQL Server – set `spring.datasource.url`, `username`, `password` in `application.yaml`
- JWT: `app.jwt.secret`, `app.jwt.expiration-ms` in `application.yaml`
