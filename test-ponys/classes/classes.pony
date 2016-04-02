actor Main
  let mascot: Mascot
  
  new create(env: Env) =>
    mascot = Mascot("Go gopher")
    env.out.print(mascot.message())

class Mascot
  let _name: String
  
  new create(name: String) =>
    _name = name

  fun message(): String =>
    "The " + _name + " is the best mascot!!"
