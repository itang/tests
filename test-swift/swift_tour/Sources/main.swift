/**
 * swift tour.
 *
 */
 /// for String.hasSuffix
 import Foundation
 //import Swift

print("Hello, world!")

func simple_values() {
  var myVariable = 42
  myVariable = 50
  print(myVariable)

  let myConstant = 42
  print(myConstant)

  let implicitInteger = 70
  let implicitDouble = 70.0
  let explicitDouble: Double = 70
  print(implicitInteger, implicitDouble, explicitDouble)


  let explicitFloat: Float = 5
  print(explicitFloat)

  let label = "The width is "
  let width = 94
  //let widthLabel = label + " " + width
  let widthLabel = label + " " + String(width)
  print(widthLabel)

  let apples = 3
  let oranges = 5
  let applySummary = "I have \(apples) apples"
  let fruitSummary = "I have \(apples + oranges) pieces of fruit"
  print(applySummary)
  print(fruitSummary)

  var shoppingList = ["catfish", "water", "tuplips", "blue paint"]
  shoppingList[1] = "bottle of water"
  print(shoppingList)

  //print(shoppingList[4])


  var occupations = [
    "Malconlm" : "Captain",
    "Kaylee" : "Mechanic",
  ]

  occupations["Jayne"] = "Public Relations"
  print(occupations)

  //To create an empty array or dictionary, use the initializer syntax.
  let emptyArray = [String]()
  print(emptyArray)
  print("emptyArray length :\(emptyArray.count)")

  let emptyDictionary = [String:Float]()
  print(emptyDictionary)
  print("emptyDictionary length: \(emptyDictionary.count)")

  var emptyArray2 : [String]
  emptyArray2 = []
  print(emptyArray2)

  var emptyDictionary2 : [String: String]
  emptyDictionary2 = [:]
  print(emptyDictionary2)
  emptyDictionary2["name"] = "itang"
  print(emptyDictionary2["name"])
}

func control_flow() {
  // if
  let individualScores = [75, 43, 103, 87, 12]
  var teamScore = 0
  for score in individualScores {
    if score > 50 {
      teamScore += 3
    } else {
      teamScore += 1
    }
  }
  print("teamScore: \(teamScore)")

  // if let
  // You can use if and let together to work with values that might be missing.
  let optionalString: String? = "hello"
  print(optionalString == nil)

  let optionalName: String? = "John Appleseed"
  var greeting = "hello!"
  if let name = optionalName {
    greeting = "Hello, \(name)"
  }
  print("greeting: \(greeting)")

  let nickName: String? = nil
  let fullName: String = "John Appleseed"
  let informalGreeting = "Hi \(nickName ?? fullName)"
  print(informalGreeting)

  // switch
  let vegetable = "red pepper"
  switch vegetable {
    case "celery":
       print("Add some raisins and make ants on a log.")
    case "cucumber", "watercress":
      print("That would make a good tea sandwich.")
    case let x where x.hasSuffix("pepper"):
      print("Is is a spicy \(x)?")
    default:
      print("Everything tastes good in soup.")
  }

  // for-in
  let interestingNumbers = [
    "Prime": [2, 3, 5, 7, 11, 13],
    "Fibonacci": [1, 1, 2, 3, 5, 8],
    "Square": [1, 4, 9, 16, 25],
  ]

  var largest = 0

  for (_, numbers) in interestingNumbers {
    for number in numbers {
      if number > largest {
        largest = number
      }
    }
  }

  print("largest:\(largest)")

  // while
  var n = 2
  while n < 100 {
    n = n * 2
  }
  print(n)

  var m = 2
  repeat {
    m = m * 2
  } while m < 100
  print(m)

  // fo-in ranges
  var firstForLoop = 0
  for i in 0..<4 {
    firstForLoop += i
  }
  print(firstForLoop)

  var secondForLoop = 0
  for i in 0..<4{
    secondForLoop += i
  }
  print(secondForLoop)

  for i in 0..<4 {
    print("i: \(i)")
  }
}

func functions_closures() {
  func greet(_ name: String, day: String) -> String {
    return "Hello \(name), taday is \(day)"
  }

  let ret = greet("Bob", day: "Tuesday")
  print(ret)

  func calculateStatistics(scores: [Int]) -> (min: Int, max: Int, sum: Int) {
    var min = scores[0]
    var max = scores[0]
    var sum = 0

    for score in scores {
      if score > max {
        max = score
      } else if score < min {
        min = score
      }

      sum += score
    }
    return (min, max, sum)
  }

  let stat = calculateStatistics(scores: [1,3,2,100,50])
  print(stat, stat.sum)
  print(stat.2)

  func sumOf(_ numbers: Int...) -> Int {
    var sum = 0
    for number in numbers {
      sum += number
    }
    return sum
  }

  print(sumOf())
  print(sumOf(42,213,111))

  //nested
  func returnFifteen() -> Int {
    var y = 10
    func add() {
      y += 5
    }
    add()
    return y
  }
  print(returnFifteen())

  // Functions are a first-class type
  func makeIncrement() -> ((Int) -> Int) {
    func addOne(number: Int) -> Int {
      return 1 + number
    }
    return addOne
  }
  let f = makeIncrement()
  print(f(100))

  // A function can take another function as one of its arguments.
  func hasAnyMatches(_ list: [Int], condition: (Int) -> Bool) -> Bool {
    for item in list {
      if condition(item) {
        return true
      }
    }
    return false
  }

  func lessThanTen(number: Int) -> Bool {
    return number < 10
  }

  let numbers = [20, 19, 7]
  //print(hasAnyMatches(numbers, lessThanTen)) // missing argument label 'condition'
  print(hasAnyMatches(numbers, condition: lessThanTen))


  let r1 = numbers.map({
    (number: Int) -> Int in
    let result = 3 * number
    return result
  })
  print(r1)

  let mappedNumbers = numbers.map({ number in 3 * number})
  print(mappedNumbers)
  let mappedNumbers2 = numbers.map { number in 3 * number }
  print(mappedNumbers2)
  print(numbers.map{ it in 3 * it })

  let sortedNumbers = numbers.sorted { $0 > $1 }
  print(sortedNumbers)

  func foo(action: () -> ()) {
    return  action()
  }

  foo {
    print("****************************")
  }
}

func objects_classes() {
  class Shape {
    var numberOfSides = 0
    func simpleDescription() -> String {
      return "A shape with \(numberOfSides) sides."
    }
  }

  let shape = Shape()
  print(shape.simpleDescription())

  shape.numberOfSides = 7
  let shapeDescription = shape.simpleDescription()
  print(shapeDescription)


  class NamedShape {
    var numberOfSides: Int = 0
    var name: String

    init(name: String) {
      self.name = name
    }

    func simpleDescription() -> String {
      return "A shape \(name) with \(numberOfSides) slides."
    }
  }

  let namedShape = NamedShape(name: "tang")
  print(namedShape.simpleDescription())

  class Square: NamedShape {
    var sideLength: Double

    init(sideLength: Double, name: String) {
      self.sideLength = sideLength
      super.init(name: name)
      numberOfSides = 4
    }

    func area() -> Double {
      return sideLength * sideLength
    }

    override func simpleDescription() -> String {
      return "A square with sides of length \(sideLength)."
    }
  }

  let test = Square(sideLength: 5.2, name: "my test square")
  let area = test.area()
  let desc = test.simpleDescription()
  print(area, desc)

  class EquilateralTriangle: NamedShape {
    var sideLength: Double = 0.0

    init(sideLength: Double, name: String) {
      self.sideLength = sideLength
      super.init(name: name)
      numberOfSides = 3
    }

    var perimeter: Double {
      get {
        return 3.0 * sideLength
      }

      set {
        sideLength = newValue / 3.0
      }
    }

    override func simpleDescription() -> String {
      return "An equilateral triangle with sides of length \(sideLength)."
    }
  }

  let triangle = EquilateralTriangle(sideLength: 3.1, name: "a triangle")
  print(triangle.perimeter)
  triangle.perimeter = 9.9
  print(triangle.sideLength)

  class TriangleAndSquare {
    var triangle: EquilateralTriangle {
      willSet {
        print("triangle willSet \(newValue)")
        square.sideLength = newValue.sideLength
      }
    }

    var square: Square {
      willSet {
        print("square willSet \(newValue)")
        triangle.sideLength = newValue.sideLength
      }
    }

    init(size: Double, name: String) {
      square = Square(sideLength: size, name: name)
      triangle = EquilateralTriangle(sideLength: size, name: name)
    }
  }

  print("////////////////////")
  let triangleAndSquare = TriangleAndSquare(size: 10, name: "another test shape")
  print(triangleAndSquare.square.sideLength)
  print(triangleAndSquare.triangle.sideLength)
  triangleAndSquare.square = Square(sideLength: 50, name: "larger square")
  print(triangleAndSquare.triangle.sideLength)

  let optionalSquare: Square? = Square(sideLength: 2.5, name: "optional square")
  let sideLength = optionalSquare?.sideLength
  print(sideLength)
}

func enumerations_and_structures() {
  enum Rank: Int {
    case Ace = 1
    case Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten
    case Jack, Queen, King

    func simpleDescription() -> String {
      switch self {
        case .Ace:
          return "ace"
        case .Jack:
          return "jack"
        case .Queen:
          return "queen"
        case .King:
          return "kind"
        default:
          return String(self.rawValue)
      }
    }
  }

  let ace = Rank.Ace
  let aceRawValue = ace.rawValue
  print(aceRawValue)

  if let convertedRank = Rank(rawValue: 3) {
    let threeDescription = convertedRank.simpleDescription()
    print(threeDescription)
  }

  enum Suit {
    case Spades, Hearts, Diamonds, Clubs
    func simpleDescription() -> String {
      switch self {
        case .Spades:
          return "spades"
        case .Hearts:
          return "hearts"
        case .Diamonds:
          return "diamonds"
        case .Clubs:
          return "clubs"
      }
    }
  }

  let hearts = Suit.Hearts
  print(hearts.simpleDescription())

  // One of the most important differences between structures and classes is that structures are always copied when they are passed around in your code,
  // but classes are passed by reference
  struct Card {
    var rank: Rank
    var suit: Suit
    func simpleDescription() -> String {
      return "The \(rank.simpleDescription()) of \(suit.simpleDescription())"
    }
  }

  let threeOfSpades = Card(rank: .Three, suit: .Spades)
  print(threeOfSpades.simpleDescription())

  enum ServerResponse {
    case Result(String, String)
    case Error(String)
  }

  let success = ServerResponse.Result("6:00 am", "8:09 pm")
  let failure = ServerResponse.Error("Out of cheese.")

  func print_result(_ resp: ServerResponse) {
    switch resp {
      case let .Result(sunrise, sunset):
         print("Sunrise is at \(sunrise) and sunset is at \(sunset).")
      case let .Error(error):
        print("Failture... \(error)")
    }
  }

  print_result(success)
  print("/////")
  print_result(failure)
}

protocol ExampleProtocol {
  var simpleDescription: String { get }
  mutating func adjust()
}

extension Int: ExampleProtocol {
  var simpleDescription: String {
    return "The number \(self)"
  }

  mutating func adjust(){
    self += 42
  }

  func times(block: (Int) -> Void) -> Void {
    for i in 0..<self {
      block(i)
    }
  }
}

func protocols_and_extensions() {
  class SimpleClass: ExampleProtocol {
    var simpleDescription: String = "A very simple class."
    var anotherProperty: Int = 69105
    func adjust() {
      simpleDescription += "  Now 100% adjusted."
    }
  }

  let a = SimpleClass()
  a.adjust()
  let aDescription = a.simpleDescription
  print(aDescription)

  struct SimpleStructure: ExampleProtocol {
    var simpleDescription: String = "A simple structure"
    mutating func adjust() {
      simpleDescription += " (adjusted)"
    }
  }

  var b = SimpleStructure()
  b.adjust()

  let bDescription = b.simpleDescription
  print(bDescription)

  print(7.simpleDescription)

  var i = 7
  i.adjust()
  print(i.simpleDescription)

  let protocolValue: ExampleProtocol = i
  print(protocolValue.simpleDescription)

  10.times { x in
    print(x)
  }

  5.times { print($0) }
}

func error_handle() {
    enum PrinterError: ErrorProtocol {
        case outOfPager
        case noToner
        case onFire
    }

    func send(job: Int, toPrinter printerName: String) throws -> String {
        if printerName == "Never Has Toner" {
            throw PrinterError.noToner
        }
        return "Job sent"
    }

    do {
        let printerResponse = try send(job: 1440, toPrinter: "Gutenberg")
        print(printerResponse)
    } catch PrinterError.onFire {
        print("I'll just put this over here, with the rest of the fire")
    } catch let printerError as PrinterError{
        print("Printer error: \(printerError).")
    } catch {
        print(error)
    }

    let printerFailure = try? send(job: 1885, toPrinter: "Never Has Toner")
    print("printerFailure: \(printerFailure)")

    var fridgeIsOpen = false
    let fridgeContent = ["milk", "eggs", "leftovers"]

    func fridgeContains(_ food: String) -> Bool {
        fridgeIsOpen = true
        defer {
            fridgeIsOpen = false
        }

        let result = fridgeContent.contains(food)
        return result
    }

    let _ = fridgeContains("banana")
    print(fridgeIsOpen)
}

func generics() {
  func repeatItem<Item>(_ item: Item, numberOfTimes: Int) -> [Item] {
    var result = [Item]()
    for _ in 0..<numberOfTimes {
      result.append(item)
    }
    return result
  }

  let items = repeatItem("knock", numberOfTimes: 4)
  print(items)

  enum OptionalValue<Wrapper> {
    case None
    case Some(Wrapper)
  }

  var possibleInteger: OptionalValue<Int> = .None
  print(possibleInteger)

  possibleInteger = .Some(100)
  print(possibleInteger)


  func anyCommonElements<T: Sequence, U: Sequence where T.Iterator.Element: Equatable,
    T.Iterator.Element == U.Iterator.Element> (_ lhs: T, _ rhs: U) -> Bool {
    for lhsItem in lhs {
      for rhsItem in rhs {
        if lhsItem == rhsItem {
          return true
        }
      }
    }
    return false
  }

  let r = anyCommonElements([1, 2, 3], [3])
  print(r)
}

simple_values()
control_flow()
functions_closures()
objects_classes()
enumerations_and_structures()
protocols_and_extensions()
error_handle()
generics()
