# SafeRent Docker Notes

Docker files and local service notes live here.

Step 1.1 uses the root `compose.yaml` file to run PostgreSQL:

```bash
docker compose up -d postgres
```

The PostgreSQL data is stored in the named Docker volume `saferent_postgres_data`.
