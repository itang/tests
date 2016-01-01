interface Person {
    firstname: string;
    lastname: string;
}

class Student {
    fullname: string;
    constructor(public firstname,public middleinitial, public lastname){
        this.fullname = firstname + " " + middleinitial + " " + lastname;
    }
}

function greeter(person: Person) {
    return "Hello, " + person.firstname + " " + person.lastname + ` at: ${new Date()}`;
}

var user = new Student("CH", "M." , "itang");

document.body.innerHTML = greeter(user);
