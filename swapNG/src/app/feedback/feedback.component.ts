import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { SwapService } from '../swap.service';
import { Feedback } from '../swapReq.model';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit {



  constructor(
    private swapSvc : SwapService,
    private router: Router
    ) { }

  ngOnInit(): void {
  }



  onSubmit(form:NgForm){
    const fbString = form.value.feedback

    console.log(fbString)
    let feedback : Feedback = {feedback : fbString}
    this.swapSvc.sendFeedback(feedback).subscribe(
      resp => console.log(resp)
    )

    this.router.navigate(["/"])

  }
}
