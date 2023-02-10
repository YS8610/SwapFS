import { Injectable } from "@angular/core";
import { NativeDateAdapter } from "@angular/material/core";

export const AppDateFormats = {
  parse: {
    dateInput: 'DD/MM/YYYY',
  },
  display: {
    dateInput: 'DD MM YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  }
}

// AppDateFormats object will be passed to CustomDateAdapter class
@Injectable()
export class CustomDateAdapter extends NativeDateAdapter {
  override parse(value: any, parseFormat?:any): Date | null {
    // parseFormat will get input from AppDateFormats.parse ('DD/MM/YYYY')
    const valueArray:number[] = value.split("/").map( (x:string) => parseInt(x)||null)
    if (valueArray.some( x => x===null)) return null

    if (parseFormat === 'DD/MM/YYYY' && valueArray.length==3){
      console.log(new Date(valueArray[2],valueArray[1],valueArray[0]))
      return new Date(valueArray[2]>100?valueArray[2]:valueArray[2]+2000,valueArray[1]-1,valueArray[0])
    }
    return null
  }

  override format(date: Date, displayFormat: Object): string {
    // displayformat will get input from AppDateFormats.display
    const days = date.getDate();
    const months = date.toLocaleString('default', { month: 'short' }); //date.getMonth() + 1;
    const year = date.getFullYear();
    return days + '-' + months + '-' + year;
  }
}
