require "../app/views/application_view"
require "../app/views/layouts_view"

require "../app/controllers/application_controller"
require "../app/controllers/**"
require "../app/models/**"

module Myapp
  {{ run "./routes.cr", "--codegen" }}
end

abstract class Frost::Controller
  include Myapp::NamedRoutes
end
