# Kong docker-compose

## Setup

```bash
docker-compose up kong-database

fab migrate

docker-compose up kong
```

test:

`$ http http://localhost:8001/apis/`
