# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require 'prey/version'

Gem::Specification.new do |spec|
  spec.name          = "prey"
  spec.version       = Prey::VERSION
  spec.authors       = ["John Nunemaker"]
  spec.email         = ["nunemaker@gmail.com"]
  spec.summary       = %q{Kestrel client gem.}
  spec.description   = %q{Kestrel client gem.}
  spec.homepage      = "https://github.com/jnunemaker/prey"
  spec.license       = "MIT"

  spec.files         = `git ls-files -z`.split("\x0")
  spec.executables   = spec.files.grep(%r{^bin/}) { |f| File.basename(f) }
  spec.test_files    = spec.files.grep(%r{^(test|spec|features)/})
  spec.require_paths = ["lib"]

  spec.add_dependency "thrift_client", "~> 0.9.2"
  spec.add_dependency "thin"
  spec.add_development_dependency "bundler", "~> 1.6"
  spec.add_development_dependency "rake"
  spec.add_development_dependency "rspec", "~> 2.14.1"
end
