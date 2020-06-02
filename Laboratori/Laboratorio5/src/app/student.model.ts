export class Student{

  constructor(theId: number, theSerial: string, theName: string, theFirstName: string, theCourseId: number, theGroupId: number) {
    this.id = theId;
    this.serial = theSerial;
    this.name = theName;
    this.firstName = theFirstName;
    this.checked = false;
    this.courseId = theCourseId;
    this.groupId= theGroupId;
  };

  id: number;
  serial: string;
  name: string;
  firstName: string;
  checked?: boolean;
  courseId: number;
  groupId: number;

  toggleSelect(): void {
    this.checked = this.checked !== true;
  }

  toString():string{
    return this.firstName+" "+this.name+" - "+this.serial;
  }
}
