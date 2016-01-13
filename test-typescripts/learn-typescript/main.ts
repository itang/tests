declare function require(name: string);

/**
 * @see http://chaijs.com/api/assert/
 */
interface Assert {
     // Function interface
     // describe a function type with an interface
     (expression: boolean): void;
     (expression: boolean, message: string): void;

     equal(a: any, b: any): void;
     equal(a: any, b: any, message: string): void;

     notEqual(a: any, b: any): void;
     notEqual(a: any, b: any, message: string): void;

     typeOf(obj: any, type: string): void;
     typeOf(obj: any, type: string, message: String): void;
     notTypeOf(obj: any, type: string): void;
     notTypeOf(obj: any, type: string, message: String): void;

     lengthOf(a: string | Array<any>, b: number): void;
     lengthOf(a: string | Array<any>, b: number, message: string): void;

     property(obj: any, prop: string): void;
     property(obj: any, prop: string, message: string): void;

     isTrue(value: any): void;
     isTrue(value: any, message: string): void;

     isNotTrue(value: any): void;
     isNotTrue(value: any, message: string): void;

     isNull(value: any): void;
     isNull(value: any, message: string): void;

     isUndefined(value: any): void;
     isUndefined(value: any, message: string): void;

     isFunction(value: any): void;
     isFunction(value: any, message: string): void;
     isNotFunction(value: any): void;
     isNotFunction(value: any, message: string): void;

     isObject(value: any): void;
     isObject(value: any, message: string): void;
     isNotObject(value: any): void;
     isNotObject(value: any, message: string): void;

     isArray(value: any): void;
     isArray(value: any, message: string): void;
     isNotArray(value: any): void;
     isNotArray(value: any, message: string): void;

     isString(value: any): void;
     isString(value: any, message: string): void;
     isNotString(value: any): void;
     isNotString(value: any, message: string): void;

     isNumber(value: any): void;
     isNumber(value: any, message: string): void;
     isNotNumber(value: any): void;
     isNotNumber(value: any, message: string): void;

     isBoolean(value: any): void;
     isBoolean(value: any, message: string): void;
     isNotBoolean(value: any): void;
     isNotBoolean(value: any, message: string): void;

     throws(fun: Function): void;
     throws(fun: Function, message: string): void;

     sameMembers(a1: Array<any>, a2: Array<any>): void;
     sameMembers(a1: Array<any>, a2: Array<any>, message: string): void;
}

interface Chai {
    assert: Assert
}

//declare var assert: Assert;
let chai: Chai = require("chai");
let assert = chai.assert;

(function test_chai(){
    console.log("hello");
    assert(true);
    assert(true, "true");

    let name = "itang";
    assert.equal(name, "itang");
    assert.equal(name, "itang", "name equals");

    assert.typeOf(name, 'string');
    assert.typeOf(name, "string");

    assert.lengthOf(name, 5);
    assert.lengthOf([1,2,3], 3);
    let tea = {"flavors": [1,2,3,4,5]};
    assert.lengthOf(tea.flavors, 5);

    assert.isFunction(function(){});
    assert.isObject({});
    assert.isNotFunction({});
    assert.isNotObject(function(){});

    assert.throws(function(){
        throw "Error";
    });
})();

(function test_basic_types(){
    //Boolean
    (function Boolean(){
        var isDone: boolean = true;
        assert.isBoolean(isDone);
        assert.isTrue(isDone);
        isDone = false;
        assert.isNotTrue(isDone);

        //isDone = 1; // compile error
    })();

    //Number
    (function test_number(){
        // all numbers in TypeScript are floating point values. These floating point numbers get the type 'number'.
        var height: number = 100;
        assert.equal(height, 100);
    })();

    (function test_string(){
        var name: string = "bob";
        name = 'smith';
    })();

    (function test_array(){
        var list: number[] = [1, 2, 3];
        assert.isArray(list);
        var list2 = [1, 2, 3];
        assert.isArray(list2);
        assert.notEqual(list2, [1,2,3]);
        assert.sameMembers(list2, [1,2,3]);
    })();

    (function test_enum(){
        enum Color {Red, Green, Blue};
        var c: Color = Color.Green;
        assert.equal(c, Color.Green);
        assert.equal(Color[2], "Blue");

        enum Color2 {Red = 1, Green, Blue};
        var colorName: string = Color2[2];
        assert.equal(colorName, "Green");
    })();

    (function test_any(){
        var notSure: any = 4;
        assert.equal(notSure, 4);

        notSure = "maybe a string instead";
        notSure = false; // okay, definitely a boolean
        assert.isNotTrue(notSure);

        var list:any[] = [1, true, "free"];

        list[1] = 100;
        assert.isArray(list);
        assert.equal(list[1], 100);
    })();

    (function test_void(){
        var f = function(): void {
          console.log("test");
        };
        var a = f();
        assert.isUndefined(a);
    })();
})();


(function Interfaces(){
    function printLabel(labelledObj: {label: string}) {
        console.log(labelledObj.label);
    }

    var myObj = {size: 10, label: "Size 10 Object"};
    printLabel(myObj);

    interface LabelledValue {
        label: string;
    }

    function printLabel2(labelledObj: LabelledValue) {
        console.log(labelledObj.label);
    }
    printLabel2(myObj);

    interface SquareConfig {
        color?: string;
        width?: number;
    }

    function createSquare(config: SquareConfig): {color: string, area: number} {
        var newSquare = {color: "white", area: 100};
        if (config.color){
            newSquare.color = config.color;
        }
        if(config.width){
            newSquare.area = config.width;
        }
        return newSquare;
    }

    var mySquare = createSquare({color: 'black'});
    console.log(mySquare);

    // Function interface
    interface SearchFunc {
        (source: string, subString: string): boolean;
    }

    var mySearch: SearchFunc;
    mySearch = function(source: string, subString: string) {
        var result = source.search(subString);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    assert.isTrue(mySearch("hello, world", "hello"));

    // Array Types
    interface StringArray {
        [index: number]: string;
    }

    var myArray: StringArray;
    myArray = ["Bob", "Fred"];
    assert.equal(myArray[0], "Bob");

    // Class Types
    interface ClockInterface {
        currentTime: Date;
        setTime(d: Date);
    }

    class Clock implements ClockInterface  {
        currentTime: Date;
        setTime(d: Date) {
            this.currentTime = d;
        }
        constructor(h: number, m: number) { }
    }
    var clock = new Clock(12, 12);
    clock.setTime(new Date());

    interface ClockStatic {
        new (hour: number, minute: number);
    }

    var ClockClazz: ClockStatic = Clock;
    var newClock = new ClockClazz(1, 30);

    // Extending Interfaces
    interface Shape {
        color: string;
    }
    interface PenStroke {
        penWidth: number;
    }
    interface Square extends Shape, PenStroke {
        sideLength: number;
    }
    var square = <Square>{};
    square.color = "blue";
    square.sideLength = 10;
    square.penWidth = 5.0;
    assert.equal(square.color, "blue");

    // Hybrid Types
    interface Counter {
        (start: number): string;
        interval: number;
        reset():void;
    }
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
(function test_classes(){
    class Greeter {
        greeting: string;
        constructor(message: string)  {
            this.greeting = message;
        }
        greet() {
            return "Hello, " + this.greeting;
        }
    }

    var greeter = new Greeter("world");
    console.log(greeter.greet());

    // Inheritance
    class Animal {
        private name: string;
        constructor(theName: string) {
            this.name = theName;
        }
        // Public by default
        move(meters: number = 0){
            console.log(this.name + " moved " + meters + "m.");
        }
    }

    class Snake extends Animal {
        constructor(name: string) { super(name);}
        move(meters = 5){
            console.log("Slithering...");
            super.move(meters);
        }
    }

    class Horse extends Animal {
        constructor(name: string) { super(name);}
        move(meters = 45){
            console.log("Galloping...");
            super.move(meters);
        }
    }

    var sam = new Snake("Sammy the Python");
    var tom: Animal = new Horse("Tommy the Palomino");

    sam.move();
    tom.move(34);

    // The keywords 'public' and 'private' also give you a shorthand for creating and initializing members of your class, by creating parameter properties
    class Animal2 {
        constructor(private name: string) {}
        move(meters: number) {
            console.log(this.name + " moved " + meters + "m.");
        }
    }

    // Accessors
    var passcode = "secret passcode";
    class Employee {
        private _fullName: string;
        get fullName(): string {
            return this._fullName;
        }
        set fullName(newName: string) {
            if(passcode && passcode == "secret passcode") {
                this._fullName = newName;
            }else{
                alert("Error: Unauthorized update of employee!");
            }
        }
    }
    var employee = new Employee();
    employee.fullName = "Bob Smith";
    if(employee.fullName){
        console.log(employee.fullName);
    }

    // Static Properties
    class Grid {
        static origin = {x: 0, y: 0};
        calculateDistanceFromOrigin(point: {x: number; y: number;}) {
            var xDist = (point.x - Grid.origin.x);
            var yDist = (point.y - Grid.origin.y);
            return Math.sqrt(xDist * xDist + yDist * yDist) / this.scale;
        }
        constructor (public scale: number) { }
    }

    var grid1 = new Grid(1.0);  // 1x scale
    var grid2 = new Grid(5.0);  // 5x scale

    console.log(grid1.calculateDistanceFromOrigin({x: 10, y: 10}));
    console.log(grid2.calculateDistanceFromOrigin({x: 10, y: 10}));

})();