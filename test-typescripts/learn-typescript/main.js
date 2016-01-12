//declare var assert: Assert;
var chai = require("chai");
var A = chai.assert;
var assert = chai.assert;
(function test1() {
    console.log("hello");
    assert(true);
    assert(true, "true");
    var name = "itang";
    A.equal(name, "itang");
    A.equal(name, "itang", "name equals");
    A.typeOf(name, 'string');
    A.typeOf(name, "string");
    A.lengthOf(name, 5);
    A.lengthOf([1, 2, 3], 3);
    var tea = { "flavors": [1, 2, 3, 4, 5] };
    A.lengthOf(tea.flavors, 5);
    A.isFunction(function () { });
    A.isObject({});
    A.isNotFunction({});
    A.isNotObject(function () { });
    A.throws(function () {
        throw "Error";
    });
})();
(function BasicTypes() {
    //Boolean
    (function Boolean() {
        var isDone = true;
        A.isBoolean(isDone);
        A.isTrue(isDone);
        isDone = false;
        A.isNotTrue(isDone);
        //isDone = 1; // compile error
    })();
    //Number
    (function number() {
        // all numbers in TypeScript are floating point values. These floating point numbers get the type 'number'.
        var height = 100;
        A.equal(height, 100);
    })();
    (function String() {
        var name = "bob";
        name = 'smith';
    })();
    (function test_array() {
        var list = [1, 2, 3];
        A.isArray(list);
        var list2 = [1, 2, 3];
        A.isArray(list2);
        A.notEqual(list2, [1, 2, 3]);
        A.sameMembers(list2, [1, 2, 3]);
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
        A.equal(c, Color.Green);
        A.equal(Color[2], "Blue");
        var Color2;
        (function (Color2) {
            Color2[Color2["Red"] = 1] = "Red";
            Color2[Color2["Green"] = 2] = "Green";
            Color2[Color2["Blue"] = 3] = "Blue";
        })(Color2 || (Color2 = {}));
        ;
        var colorName = Color2[2];
        A.equal(colorName, "Green");
    })();
    (function test_any() {
        var notSure = 4;
        A.equal(notSure, 4);
        notSure = "maybe a string instead";
        notSure = false; // okay, definitely a boolean
        A.isNotTrue(notSure);
        var list = [1, true, "free"];
        list[1] = 100;
        A.isArray(list);
        A.equal(list[1], 100);
    })();
    (function test_void() {
        var f = function () {
            console.log("test");
        };
        var a = f();
        A.isUndefined(a);
    })();
})();
