defmodule KV.Registry do
  use GenServer

  ## Client API
  @doc """
  Starts the registry.
  """
  def start_link(event_manager, opts \\ []) do
    # GenServer.start_link(__MODULE__, :ok, opts)
    GenServer.start_link(__MODULE__, event_manager, opts)
  end

  @doc """
  Looks up the bucket pid for `name` stored in `server`.

  Returns `{:ok, pid}` if the bucket exists, `:error` otherwise.
  """
  def lookup(server, name) do
    GenServer.call(server, {:lookup, name})
  end

  @doc """
  Ensures there is a bucket associated to the given `name` in `server`.
  """
  def create(server, name) do
    GenServer.cast(server, {:create, name})
  end

  @doc """
  Stops the registry.
  """
  def stop(server) do
    GenServer.call(server, :stop)
  end

  ## Server Callbacks
  def init(events) do
    names = HashDict.new
    refs = HashDict.new
    {:ok, %{names: names, refs: refs, events: events}}
  end

  ### calls are synchronous and the server must send a response back to such requests.
  ### params: request, _from - the process from which we received the request, names - the current server state.
  ### returns: a tuple in the format {:reply, reply, new_state}
  def handle_call({:lookup, name}, _from, state) do
    {:reply, HashDict.fetch(state.names, name), state}
  end

  def handle_call(:stop, _from, state) do
    {:stop, :normal, :ok, state}
  end

  ### Casts are asynchronous and the server won't send a reqsponse back.
  ### params: request, the current server state
  ### returns: {:noreply, new_state}
  def handle_cast({:create, name}, state) do
    if HashDict.has_key?(state.names, name) do
      {:noreply, state}
    else
      {:ok, pid} = KV.Bucket.start_link()
      ref = Process.monitor(pid)
      refs = HashDict.put(state.refs, ref, name)
      names = HashDict.put(state.names, name, pid)

      # 3. Push a notification to the event manager on create
      GenEvent.sync_notify(state.events, {:create, name, pid})

      {:noreply, %{state| refs: refs, names: names}}
    end
  end

  def handle_info({:DOWN, ref, :process, pid, _reason}, state) do
    {name, refs}  = HashDict.pop(state.refs, ref)
    names = HashDict.delete(state.names, name)

    # 4. Push a notification to the event manager on exit
    GenEvent.sync_notify(state.events, {:exit, name, pid})
    {:noreply, %{state | names: names, refs: refs}}
  end

  def handle_info(msg, state) do
    case msg do
      {:msg, m, from} ->
        IO.puts "#{m} from #{inspect from}"
        send from, {:reply, m, self}
      _ ->
    end

    {:noreply, state}
  end
end
