import { Injectable } from "@angular/core";
import { NativeDateAdapter } from "@angular/material/core";

export const AppDateFormats = {
  parse: {
    dateInput: 'DD.MM.YYYY',
  },
  display: {
    dateInput: 'DD.MM.YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  }
}

@Injectable()
export class CustomDateAdapter extends NativeDateAdapter {
  override format(date: Date, displayFormat: any): string {
    const days = date.getDate();
    const months = date.toLocaleString('default', { month: 'short' }); //date.getMonth() + 1;
    const year = date.getFullYear();
    return days + '-' + months + '-' + year;
  }
}
