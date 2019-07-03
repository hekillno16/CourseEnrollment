import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmitterService {

  public onSearch: EventEmitter<any> = new EventEmitter<any>();
  public onLogin: EventEmitter<any> = new EventEmitter<any>();

  constructor() { }

  public emitSearch(id: any, text: string) {
    this.onSearch.emit({
      id, text
    });
  }

  public emitLogin(message: string) {
    this.onLogin.emit({message});
  }
}


