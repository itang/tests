# Prey

Kestrel client gem that uses the thrift interface.

## Installation

Not released yet.

## Usage

```ruby
client = Prey::Client.new
client.put("events", "something")
items = client.get("events")
items.each do |item|
  p item.data
end
```

## Contributing

1. Fork it ( https://github.com/[my-github-username]/prey/fork )
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create a new Pull Request
