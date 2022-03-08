import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '../Constants';
import { SwapService } from '../swap.service';
import { SwapReq } from '../swapReq.model';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  zoom = 15;
  options: google.maps.MapOptions = {
    mapTypeId: 'hybrid',
    zoomControl: false,
    scrollwheel: true,
    disableDoubleClickZoom: true,
    maxZoom: 25,
    minZoom: 8,
  };

  center: google.maps.LatLngLiteral = Constants.COORD_CDC;

  markerOptions: google.maps.MarkerOptions = { draggable: true };
  markerPositions: google.maps.LatLngLiteral= Constants.COORD_CDC;



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
