use sqlx::row::Row;
use std::any::Any;

//use std::env;
#[async_std::main]
async fn main() -> anyhow::Result<()> {
    //env::set_var("DATABASE_URL", "mysql://root:123456@127.0.0.1/testdb");
    let pool = sqlx::MySqlPool::new("mysql://root:123456@127.0.0.1/testdb").await?;

    let r = sqlx::query(
        "create table if not exists user ( id int auto_increment primary key, name varchar(255) not null)",
    )
    .execute(&mut &pool)
    .await?;
    println!("{}", r);

    let ir = sqlx::query("insert into user (name) values (?)")
        .bind("itang")
        .execute(&mut &pool)
        .await?;
    println!("{}", ir);

    let rows = sqlx::query("select * from user")
        .fetch_all(&mut &pool)
        .await?;
    for row in rows {
        let name: String = row.get("name");
        //let name = row.get::<&dyn Any, &str>("name") as &dyn Any;
        println!("{:?}", name);
    }

    Ok(())
}
