task :default do
  sh 'rake -T'
end

desc "clean"
task :clean do
  sh 'mvn clean'
end

desc "run (on dev mode)"
task :run do
  sh 'mvn spring-boot:run -Drun.jvmArguments="-Xmx512m -Drun.mode=dev -Dspring.profiles.active=dev" -Drun.arguments="--spring.profiles.active=dev"'
end

desc "run (on prod mode)"
task :start do
  sh 'mvn spring-boot:run -Drun.jvmArguments="-Xmx1g -Drun.mode=prod -Dspring.profiles.active=prod"'
end

desc "test"
task :test => %w[clean] do
  sh 'mvn test'
end

desc "guard"
task :guard do
  sh 'guard'
end

namespace :deps do
  desc "check deps update"
  task "check-update" do
    sh 'mvn versions:display-property-updates'
  end
  
  desc "deps tree"
  task :tree do
    sh 'mvn dependency:tree'
  end
end