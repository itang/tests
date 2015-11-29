
def run_nimbus
  #system "cd /home/itang/dev-env/storm;bin/storm nimbus > /dev/null 2>&1 &"
  system "cd /home/itang/dev-env/storm;bin/storm nimbus"
end

def run_supervisor
  system "cd /home/itang/dev-env/storm;bin/storm supervisor"
end

def run_ui
  system "cd /home/itang/dev-env/storm;bin/storm ui"
end

def submit_hello_topology
  system "cd ../storm-in-action;sbt assembly;storm jar target/scala-2.11/storm-in-action-assembly-1.0-SNAPSHOT.jar Main hello true"
end

def stop_test
  system "storm kill hello"
end
################################################
if $0 == __FILE__
  task = ARGV[0] || "nimbus"
  case task
  when "nimbus"
    run_nimbus
  when "supervisor"
    run_supervisor
  when "ui"
    run_ui
  when "test"
    submit_hello_topology
  when "stop_test"
    stop_test
  else
    puts "unknown task: #{task}"
  end
end
