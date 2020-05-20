export class Student{

  constructor(theId: string, theName: string, theFirstName: string) {
    this.id = theId;
    this.name = theName;
    this.firstName = theFirstName;
    this.checked = false;
  };

  id: string;
  name: string;
  firstName: string;
  checked?: boolean;

  toggleSelect() {
    if (this.checked === true){
      this.checked = false;
    } else this.checked = true;
  }

  toString():string{
    return this.name+" "+this.firstName+" - "+this.id;
  }
}
