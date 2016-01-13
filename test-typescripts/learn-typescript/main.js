var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
//declare var assert: Assert;
var chai = require("chai");
var assert = chai.assert;
(function test_chai() {
    console.log("hello");
    assert(true);
    assert(true, "true");
    var name = "itang";
    assert.equal(name, "itang");
    assert.equal(name, "itang", "name equals");
    assert.typeOf(name, 'string');
    assert.typeOf(name, "string");
    assert.lengthOf(name, 5);
    assert.lengthOf([1, 2, 3], 3);
    var tea = { "flavors": [1, 2, 3, 4, 5] };
    assert.lengthOf(tea.flavors, 5);
    assert.isFunction(function () { });
    assert.isObject({});
    assert.isNotFunction({});
    assert.isNotObject(function () { });
    assert.throws(function () {
        throw "Error";
    });
})();
(function test_basic_types() {
    //Boolean
    (function Boolean() {
        var isDone = true;
        assert.isBoolean(isDone);
        assert.isTrue(isDone);
        isDone = false;
        assert.isNotTrue(isDone);
        //isDone = 1; // compile error
    })();
    //Number
    (function test_number() {
        // all numbers in TypeScript are floating point values. These floating point numbers get the type 'number'.
        var height = 100;
        assert.equal(height, 100);
    })();
    (function test_string() {
        var name = "bob";
        name = 'smith';
    })();
    (function test_array() {
        var list = [1, 2, 3];
        assert.isArray(list);
        var list2 = [1, 2, 3];
        assert.isArray(list2);
        assert.notEqual(list2, [1, 2, 3]);
        assert.sameMembers(list2, [1, 2, 3]);
    })();
    (function test_enum() {
        var Color;
        (function (Color) {
            Color[Color["Red"] = 0] = "Red";
            Color[Color["Green"] = 1] = "Green";
            Color[Color["Blue"] = 2] = "Blue";
        })(Color || (Color = {}));
        ;
        var c = Color.Green;
        assert.equal(c, Color.Green);
        assert.equal(Color[2], "Blue");
        var Color2;
        (function (Color2) {
            Color2[Color2["Red"] = 1] = "Red";
            Color2[Color2["Green"] = 2] = "Green";
            Color2[Color2["Blue"] = 3] = "Blue";
        })(Color2 || (Color2 = {}));
        ;
        var colorName = Color2[2];
        assert.equal(colorName, "Green");
    })();
    (function test_any() {
        var notSure = 4;
        assert.equal(notSure, 4);
        notSure = "maybe a string instead";
        notSure = false; // okay, definitely a boolean
        assert.isNotTrue(notSure);
        var list = [1, true, "free"];
        list[1] = 100;
        assert.isArray(list);
        assert.equal(list[1], 100);
    })();
    (function test_void() {
        var f = function () {
            console.log("test");
        };
        var a = f();
        assert.isUndefined(a);
    })();
})();
(function Interfaces() {
    function printLabel(labelledObj) {
        console.log(labelledObj.label);
    }
    var myObj = { size: 10, label: "Size 10 Object" };
    printLabel(myObj);
    function printLabel2(labelledObj) {
        console.log(labelledObj.label);
    }
    printLabel2(myObj);
    function createSquare(config) {
        var newSquare = { color: "white", area: 100 };
        if (config.color) {
            newSquare.color = config.color;
        }
        if (config.width) {
            newSquare.area = config.width;
        }
        return newSquare;
    }
    var mySquare = createSquare({ color: 'black' });
    console.log(mySquare);
    var mySearch;
    mySearch = function (source, subString) {
        var result = source.search(subString);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    };
    assert.isTrue(mySearch("hello, world", "hello"));
    var myArray;
    myArray = ["Bob", "Fred"];
    assert.equal(myArray[0], "Bob");
    var Clock = (function () {
        function Clock(h, m) {
        }
        Clock.prototype.setTime = function (d) {
            this.currentTime = d;
        };
        return Clock;
    })();
    var clock = new Clock(12, 12);
    clock.setTime(new Date());
    var ClockClazz = Clock;
    var newClock = new ClockClazz(1, 30);
    var square = {};
    square.color = "blue";
    square.sideLength = 10;
    square.penWidth = 5.0;
    assert.equal(square.color, "blue");
    /*class CounterImpl implements Counter {
         (start:number): string {
            return "";
        }
        interval: number = 10;
        reset(): void {

        }
    }

    var c: Counter;
    c(10);
    c.reset();
    c.interval = 5.0;
    */
})();
// Classes.
(function test_classes() {
    var Greeter = (function () {
        function Greeter(message) {
            this.greeting = message;
        }
        Greeter.prototype.greet = function () {
            return "Hello, " + this.greeting;
        };
        return Greeter;
    })();
    var greeter = new Greeter("world");
    console.log(greeter.greet());
    // Inheritance
    var Animal = (function () {
        function Animal(theName) {
            this.name = theName;
        }
        // Public by default
        Animal.prototype.move = function (meters) {
            if (meters === void 0) { meters = 0; }
            console.log(this.name + " moved " + meters + "m.");
        };
        return Animal;
    })();
    var Snake = (function (_super) {
        __extends(Snake, _super);
        function Snake(name) {
            _super.call(this, name);
        }
        Snake.prototype.move = function (meters) {
            if (meters === void 0) { meters = 5; }
            console.log("Slithering...");
            _super.prototype.move.call(this, meters);
        };
        return Snake;
    })(Animal);
    var Horse = (function (_super) {
        __extends(Horse, _super);
        function Horse(name) {
            _super.call(this, name);
        }
        Horse.prototype.move = function (meters) {
            if (meters === void 0) { meters = 45; }
            console.log("Galloping...");
            _super.prototype.move.call(this, meters);
        };
        return Horse;
    })(Animal);
    var sam = new Snake("Sammy the Python");
    var tom = new Horse("Tommy the Palomino");
    sam.move();
    tom.move(34);
    // The keywords 'public' and 'private' also give you a shorthand for creating and initializing members of your class, by creating parameter properties
    var Animal2 = (function () {
        function Animal2(name) {
            this.name = name;
        }
        Animal2.prototype.move = function (meters) {
            console.log(this.name + " moved " + meters + "m.");
        };
        return Animal2;
    })();
    // Accessors
    var passcode = "secret passcode";
    var Employee = (function () {
        function Employee() {
        }
        Object.defineProperty(Employee.prototype, "fullName", {
            get: function () {
                return this._fullName;
            },
            set: function (newName) {
                if (passcode && passcode == "secret passcode") {
                    this._fullName = newName;
                }
                else {
                    alert("Error: Unauthorized update of employee!");
                }
            },
            enumerable: true,
            configurable: true
        });
        return Employee;
    })();
    var employee = new Employee();
    employee.fullName = "Bob Smith";
    if (employee.fullName) {
        console.log(employee.fullName);
    }
    // Static Properties
    var Grid = (function () {
        function Grid(scale) {
            this.scale = scale;
        }
        Grid.prototype.calculateDistanceFromOrigin = function (point) {
            var xDist = (point.x - Grid.origin.x);
            var yDist = (point.y - Grid.origin.y);
            return Math.sqrt(xDist * xDist + yDist * yDist) / this.scale;
        };
        Grid.origin = { x: 0, y: 0 };
        return Grid;
    })();
    var grid1 = new Grid(1.0); // 1x scale
    var grid2 = new Grid(5.0); // 5x scale
    console.log(grid1.calculateDistanceFromOrigin({ x: 10, y: 10 }));
    console.log(grid2.calculateDistanceFromOrigin({ x: 10, y: 10 }));
    assert.equal(Grid.origin.x, 0);
    var GridMarker = Grid;
    assert.equal(GridMarker.origin.y, 0);
    var grid3 = new GridMarker(10);
    console.log(grid3.calculateDistanceFromOrigin({ x: 10, y: 10 }));
})();
