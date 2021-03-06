import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Constants } from '../Constants';
import { SwapService } from '../swap.service';
import { SwapReq, TelegramMsg } from '../swapReq.model';

@Component({
  selector: 'app-add-request',
  templateUrl: './add-request.component.html',
  styleUrls: ['./add-request.component.css']
})
export class AddRequestComponent implements OnInit {


  minDate !: Date;
  constant = new Constants();
  session : string[] = [];
  lesson : string[] = [];
  classTypeSelected !: string;
  lessonTypeSelected : string = 'prac';
  swapReqtoAdd :SwapReq = <SwapReq>{};

  form !:FormGroup;
  telegramLink = Constants.telegramBotLink;

  constructor(
    private fb:FormBuilder,
    private router: Router,
    private swapSvc : SwapService,
    private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.minDate = new Date();
    this.minDate.setDate(this.minDate.getDate()+1);

    for ( const [key, value] of Object.entries(this.constant.SESSION_PRAC) ){
      this.session.push(value);
    }

    this.form = this.fb.group({
      chatid : ['', [Validators.required,Validators.minLength(7), Validators.pattern("\\d+")] ],
      sch : ['cdc', Validators.required],
      lessonType : ['prac', Validators.required],
      classType : ['', Validators.required],
      lesson : ['', Validators.required],
      date : ['', Validators.required],
      time : ['',Validators.required],
      comment : ['',Validators.required]
    })
  }


  onLessontypeChange(lessonType:string){
    this.lessonTypeSelected = lessonType;
    this.session = [];
    this.form.patchValue({classType:'',lesson:'',time:''})
    if (this.lessonTypeSelected === "prac"){
      for ( const [key, value] of Object.entries(this.constant.SESSION_PRAC) ){
        this.session.push(value);
      }
    }
    else{
      for ( const [key, value] of Object.entries(this.constant.SESSION_SIM) ){
        this.session.push(value);
      }
    }
  }


  onClassTypeChange(cls:string){
    this.classTypeSelected = cls;
    this.lesson = [];
    if (this.lessonTypeSelected ==="prac"){
      if (this.classTypeSelected === "3a" || this.classTypeSelected === "3"){
        this.lesson.push("carLesson");
      }
      else if (this.classTypeSelected === "2b"){
        this.lesson = this.constant.LESSON_BIKE;
      }
      else{
        this.lesson = this.constant.LESSON_BIKE.slice(0,4);
      }
    }
    else{
      this.lesson.push("Simulator")
    }

  }



  onSubmit(){
    console.log(this.form)
    this.swapReqtoAdd.id = +1 ;
    this.swapReqtoAdd.chatid = +this.form.value.chatid;
    this.swapReqtoAdd.sch = this.form.value.sch;
    this.swapReqtoAdd.lessonType = this.form.value.lessonType;
    this.swapReqtoAdd.classType = this.form.value.classType;
    this.swapReqtoAdd.lesson = this.form.value.lesson;
    this.swapReqtoAdd.lessonTime = this.form.value.time;
    this.swapReqtoAdd.userComment = this.form.value.comment;
    this.swapReqtoAdd.lessonDate = this.form.value.date;
    this.swapReqtoAdd.lessonDate.setHours(8) //because this is GMT+8 and SB will convert it back to GMT
    this.swapSvc.postSwap(this.swapReqtoAdd).subscribe({
      next: resp =>{
        this.form.reset();
        this.router.navigate(["/"]);
      }
    });
  }

  onBack(){
    this.router.navigate(["/"]);
  }

  onTest(chatid:string){
    console.log(chatid)
    let telegramMsg : TelegramMsg = {chatid : +chatid, msg: "Verified from SwapLesson", contact : "@CdcTp_bot"}
    this.swapSvc.sendTelegramMsg(telegramMsg).subscribe(
      resp => console.log(resp)
    );
  }

  openSnackBar() {
    this._snackBar.open("msg sent. Please check telegram for msg", "close" ,{duration: 4000} );
  }
}
