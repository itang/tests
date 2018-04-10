defmodule MyApp.Auth.User do
  use Ecto.Schema
  import Ecto.Changeset

  schema "users" do
    field(:email, :string)
    field(:is_active, :boolean, default: false)
    field(:password, :string)

    timestamps()
  end

  @doc false
  def changeset(user, attrs) do
    user
    |> cast(attrs, [:email, :password, :is_active])
    |> validate_required([:email, :password, :is_active])
    |> unique_constraint(:email)
    |> hash_user_password()
  end

  defp hash_user_password(
         %Ecto.Changeset{valid?: true, changes: %{password: password}} = changeset
       ) do
    change(changeset, password: Bcrypt.hash_pwd_salt(password))
  end

  defp hash_user_password(changeset) do
    changeset
  end
end
