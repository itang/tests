module Frost
  # :nodoc:
  VIEWS_PATH = "#{ __DIR__ }/../app/views"

  self.root = File.expand_path("..", __DIR__)
  self.environment = ENV.fetch("FROST_ENV", "development")

  module Config
    case Frost.environment
    when "development", "test"
      self.secret_key = ENV.fetch("SECRET_KEY", "ee248c9ee32a46c2988d6b89b63c78fd9344866e8b49f4b8f95b8e327a9fd3c0")
    else
      self.secret_key = ENV["SECRET_KEY"]
    end
  end
end
