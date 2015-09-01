# TestPhoenixMysql2

To start your Phoenix app:

  1. Install dependencies with `mix deps.get`
  2. Create and migrate your database with `mix ecto.create && mix ecto.migrate`
  3. Start Phoenix endpoint with `mix phoenix.server`

Now you can visit [`localhost:4000`](http://localhost:4000) from your browser.

Ready to run in production? Please [check our deployment guides](http://www.phoenixframework.org/docs/deployment).

## Run on prod mode

```
$ mix deps.get --only prod
$ MIX_ENV=prod mix compile

$ brunch build --production

$ MIX_ENV=prod mix phoenix.digest

$ MIX_ENV=prod mix ecto.create
$ MIX_ENV=prod mix ecto.migrate

$ MIX_ENV=prod PORT=4001 mix phoenix.server
$ MIX_ENV=prod PORT=4001 elixir --detached -S mix do compile, phoenix.server
```

## Learn more

  * Official website: http://www.phoenixframework.org/
  * Guides: http://phoenixframework.org/docs/overview
  * Docs: http://hexdocs.pm/phoenix
  * Mailing list: http://groups.google.com/group/phoenix-talk
  * Source: https://github.com/phoenixframework/phoenix
