import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SwapService } from '../swap.service';
import { SwapReq } from '../swapReq.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  swapReqList : SwapReq[] = [];

  constructor(private swapSvc : SwapService, private router: Router) { }

  ngOnInit(): void {
    this.swapSvc.getAllSwaps().subscribe(
      resp =>{ this.swapReqList = resp}
    )
  }

  onSwapReq(id :number){
    this.router.navigate(['swap',id])
  }

  onMessage(id:number){
    this.router.navigate(['message',id])
  }
}
