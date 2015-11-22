namespace java hello

struct User {
    1: i64 id,
    2: string name,
}

service UserService {

   User getById(1: i64 id)
  
}
