declare function require(name: string);

/**
 * @see http://chaijs.com/api/assert/
 */
interface Assert {
     //assert(expression: boolean): void;
     //assert(expression: boolean, message: string): void;

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


//declare var assert: Assert;
let chai = require("chai");
let A: Assert /*| ((expr: boolean) => void) | ((expr: boolean, message: string) => void) */ = chai.assert;
let assert = chai.assert;

(function test1(){
    console.log("hello");
    assert(true);
    assert(true, "true");

    let name = "itang";
    A.equal(name, "itang");
    A.equal(name, "itang", "name equals");

    A.typeOf(name, 'string');
    A.typeOf(name, "string");

    A.lengthOf(name, 5);
    A.lengthOf([1,2,3], 3);
    let tea = {"flavors": [1,2,3,4,5]};
    A.lengthOf(tea.flavors, 5);

    A.isFunction(function(){});
    A.isObject({});
    A.isNotFunction({});
    A.isNotObject(function(){});

    A.throws(function(){
        throw "Error";
    });
})();

(function BasicTypes(){
    //Boolean
    (function Boolean(){
        var isDone: boolean = true;
        A.isBoolean(isDone);
        A.isTrue(isDone);
        isDone = false;
        A.isNotTrue(isDone);

        //isDone = 1; // compile error
    })();

    //Number
    (function number(){
        // all numbers in TypeScript are floating point values. These floating point numbers get the type 'number'.
        var height: number = 100;
        A.equal(height, 100);
    })();

    (function String(){
        var name: string = "bob";
        name = 'smith';
    })();

    (function test_array(){
        var list: number[] = [1, 2, 3];
        A.isArray(list);
        var list2 = [1, 2, 3];
        A.isArray(list2);
        A.notEqual(list2, [1,2,3]);
        A.sameMembers(list2, [1,2,3]);
    })();

    (function test_enum(){
        enum Color {Red, Green, Blue};
        var c: Color = Color.Green;
        A.equal(c, Color.Green);
        A.equal(Color[2], "Blue");

        enum Color2 {Red = 1, Green, Blue};
        var colorName: string = Color2[2];
        A.equal(colorName, "Green");
    })();

    (function test_any(){
        var notSure: any = 4;
        A.equal(notSure, 4);

        notSure = "maybe a string instead";
        notSure = false; // okay, definitely a boolean
        A.isNotTrue(notSure);

        var list:any[] = [1, true, "free"];

        list[1] = 100;
        A.isArray(list);
        A.equal(list[1], 100);
    })();

    (function test_void(){
        var f = function(): void {
          console.log("test");
        };
        var a = f();
        A.isUndefined(a);
    })();
})();
