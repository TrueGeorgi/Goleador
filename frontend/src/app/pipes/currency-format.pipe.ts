import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'currencyFormat'
})
export class CurrencyFormatPipe implements PipeTransform {
  transform(value: number | undefined): string {
    if (value === null || value === undefined) return '';
    
    return value.toLocaleString('en-US') + ' €';
  }
}
