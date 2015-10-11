require Logger

defmodule KV.Bucket do
  @doc """
  Starts a new bucket
  """
  def start_link do
    Logger.info "KV.Bucket start_link ..."
    Agent.start_link(fn -> HashDict.new end)
  end

  @doc """
  Get a value from the `bucket` by `key`.
  """
  def get(bucket, key) do
    Agent.get(bucket, fn dict -> HashDict.get(dict, key) end)
  end

  @doc """
  Puts the `value` for the given `key`.
  """
  def put(bucket, key, value) do
    Agent.update(bucket, &HashDict.put(&1, key, value))
  end

  @doc """
  Deletes `key` from `bucket`.

  Returns the current value of `key`, if `key` exists.
  """
  def delete(bucket, key) do
    Agent.get_and_update(bucket, &HashDict.pop(&1, key))
  end
end
