import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddRequestComponent } from './add-request/add-request.component';
import { DetailComponent } from './detail/detail.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { HomeComponent } from './home/home.component';
import { HowtouseComponent } from './howtouse/howtouse.component';
import { MapComponent } from './map/map.component';
import { MessageComponent } from './message/message.component';

const routes: Routes = [
  {path:"add",component:AddRequestComponent},
  {path:"map/:sch",component:MapComponent},
  {path:"swap/:id",component:DetailComponent},
  {path:"message/:id",component:MessageComponent},
  {path:"how",component:HowtouseComponent},
  {path:"feedback",component:FeedbackComponent},
  {path:"",component:HomeComponent},
  {path:"**",redirectTo:""}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{useHash:true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
