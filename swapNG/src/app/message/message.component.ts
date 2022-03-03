import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControlName, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '../Constants';
import { SwapService } from '../swap.service';
import { SwapReq, TelegramMsg } from '../swapReq.model';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  telegramLink = Constants.telegramBotLink;
  swapId!:string;
  swapReq : SwapReq = <SwapReq>{};
  msgForm !: FormGroup

  constructor(
    private router : Router,
    private route : ActivatedRoute,
    private swapSvc : SwapService,
    private fb : FormBuilder) { }

  ngOnInit(): void {
    this.swapId = this.route.snapshot.params["id"];
    this.swapSvc.getSwapByID(this.swapId).subscribe(
      resp=> {
        this.swapReq = resp
        let templatemsg = "For class "+this.swapReq.classType+", "+this.swapReq.lesson+" @"+this.swapReq.lessonDate+ " "+this.swapReq.lessonTime+"\n";
        this.msgForm = this.fb.group({
          contact:[ "",[Validators.required,Validators.minLength(1)] ],
          msg:[ templatemsg ,[Validators.required,Validators.minLength(5)] ]
        })
      })
    }

    onHome(){
      this.router.navigate(["/"]);
    }

    onSend(){
      // console.log(this.msgForm)
      // console.log(this.msgForm.value.msg)
      let telegramMsg : TelegramMsg = {chatid : this.swapReq.chatid, msg: this.msgForm.value.msg, contact : this.msgForm.value.contact}
      this.swapSvc.sendTelegramMsg(telegramMsg).subscribe(
        resp => {
          let templatemsg = "For class "+this.swapReq.classType+", "+this.swapReq.lesson+" @"+this.swapReq.lessonDate+ " "+this.swapReq.lessonTime+"\n";
          this.msgForm = this.fb.group({
            msg:[ templatemsg ,[Validators.required,Validators.minLength(5)] ]
          })
      }
    )
  }
}
