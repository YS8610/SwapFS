import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Feedback, SwapReq, TelegramMsg } from './swapReq.model';

@Injectable({
  providedIn: 'root'
})
export class SwapService {


  constructor(private http:HttpClient) { }

  getAllSwaps(){
    // return this.http.get<SwapReq[]>("http://localhost:8080/api/swaps");
    return this.http.get<SwapReq[]>("api/swaps");
  }

  getSwapByID(id:string){
    const urlid = "api/swap/" + id +"/";
    return this.http.get<SwapReq>(urlid);
  }

  postSwap(swap:SwapReq){
    const urlPost = "api/add";
    return this.http.post<SwapReq>(urlPost,swap);
  }

  sendTelegramMsg(telegramMsg : TelegramMsg){
    const urlTelegram = "api/send";
    return this.http.post<TelegramMsg>(urlTelegram,telegramMsg);
  }

  sendFeedback(feedback:Feedback){
    const urlFeedback = "api/feedback"
    return this.http.post<Feedback>(urlFeedback,feedback);
  }
}

