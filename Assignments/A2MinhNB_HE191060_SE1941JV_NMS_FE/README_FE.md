# FUNews FE (React)

This front-end is a React + Vite app for the FUNewsManagementSystem assignment. It expects the backend API to run at `http://localhost:8080` by default.

## Quick start

1. Install dependencies

```bash
npm install
```

2. Start the dev server

```bash
npm run dev
```

3. Open the app at `http://localhost:5173` (Vite default).

## Backend

Start the Spring Boot backend (provided by the BE project) and ensure it's listening on `http://localhost:8080`.

Default test accounts (created by the BE on first run):

- Admin: `admin@funews.com` / `admin123`
- Staff: `staff@funews.com` / `staff123`

## Notes

- The FE reads the API base URL from `VITE_API_URL` (e.g., `http://localhost:8080`). If not set, it defaults to `http://localhost:8080`.
- Login will store JWT token in `localStorage` under `funews_token` and account info under `funews_account`.
- If you encounter CORS or 401 issues, check the backend logs and `application.yaml`.
