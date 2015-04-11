require "json"

module API
  class Person
    json_mapping({
      first_name: {type: String},
      last_name: {type: String},
    })

    def_equals last_name, last_name

    def initialize(@first_name : String, @last_name: String)
    end
  end
end
