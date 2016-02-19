# db/migrations/000001_create_posts.cr

class CreatePosts < Frost::Record::Migration
  set_version {{ __FILE__ }}

  up do
    create_table :posts do |t|
      t.string :title
      t.text   :body
      t.timestamps
    end
  end

  down do
    drop_table :posts
  end
end
