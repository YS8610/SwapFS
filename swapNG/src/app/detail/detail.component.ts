import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SwapService } from '../swap.service';
import { SwapReq } from '../swapReq.model';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  swapid ="";
  hasError = false;
  swapReq = <SwapReq>{};

  constructor(
    private router : Router,
    private route : ActivatedRoute,
    private swapSvc : SwapService
  ) { }

  ngOnInit(): void {
    this.swapid = this.route.snapshot.params["id"]
    this.swapSvc.getSwapByID(this.swapid).subscribe({
      next: resp=>{
        this.hasError = false;
        this.swapReq = resp;
      },
      error: error=>{
        this.hasError = true;
      }
    })
  }

  onHome(){
    this.router.navigate(['/']);
  }

  onMessage(){
    this.router.navigate(['message',this.swapid])
  }
}
