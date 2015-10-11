require Logger

defmodule KV.Bucket.Supervisor do
  use Supervisor

  def start_link(opts \\ []) do
    Logger.info "KV.Bucket.Supervisor start_link..."
    Supervisor.start_link(__MODULE__, :ok, opts)
  end

  def start_bucket(supervisor) do
    # ???
    Supervisor.start_child(supervisor, [])
  end

  def init(:ok) do
    Logger.info "KV.Bucket.Supervisor init..."
    children = [
      worker(KV.Bucket, [], restart: :temporary)
    ]

    # :simple_one_for_one, that is the perfect fit for such situations: it allows us to specify a worker template and supervise many children based on this template
    supervise(children, strategy: :simple_one_for_one)
  end
end
