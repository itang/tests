defmodule KV.RegistryTest do
  use ExUnit.Case, async: true

  defmodule Forwarder do
    use GenEvent

    def handle_event(event, parent) do
      send parent, event
      {:ok, parent}
    end
  end

  setup do
    {:ok, sup} = KV.Bucket.Supervisor.start_link
    {:ok, manager} = GenEvent.start_link
    {:ok, registry}  = KV.Registry.start_link(manager, sup)

    GenEvent.add_mon_handler(manager, Forwarder, self())
    {:ok, registry: registry}
  end

  test "spawns buckets", %{registry: registry} do
    assert KV.Registry.lookup(registry, "shopping") == :error

    KV.Registry.create(registry, "shopping")
    assert {:ok, bucket} = KV.Registry.lookup(registry, "shopping")
    KV.Bucket.put(bucket, "milk", 1)
    assert KV.Bucket.get(bucket, "milk") == 1

    send registry, {:msg, "Hello, world!", self()}
    receive do
      {:reply, msg, from} -> IO.puts "#{msg} from #{inspect from}"
    end
  end

  test "sends events on create and crash", %{registry: registry} do
    KV.Registry.create(registry, "shopping")
    {:ok, bucket} = KV.Registry.lookup(registry, "shopping")
    assert_receive {:create, "shopping", ^bucket}

    Agent.stop(bucket)
    assert_receive {:exit, "shopping", ^bucket}
  end

  test "removes bucket on crash", %{registry: registry} do
    KV.Registry.create(registry, "shopping")
    {:ok, bucket} = KV.Registry.lookup(registry, "shopping")

    # Kill the bucket and wait for the notification
    Process.exit(bucket, :shutdown)
    assert_receive {:exit, "shopping", ^bucket}
    assert KV.Registry.lookup(registry, "shopping") == :error
  end
end
