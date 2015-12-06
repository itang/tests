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
  for var i = 0; i < 4; ++i {
    secondForLoop += i
  }
  print(secondForLoop)
}

func functions_closures() {
  func greet(name: String, day: String) -> String {
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

  let stat = calculateStatistics([1,3,2,100,50])
  print(stat, stat.sum)
  print(stat.2)

  func sumOf(numbers: Int...) -> Int {
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
  func hasAnyMatches(list: [Int], condition: (Int) -> Bool) -> Bool {
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
}

simple_values()
control_flow()
functions_closures()
