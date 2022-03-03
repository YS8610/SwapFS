import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddRequestComponent } from './add-request/add-request.component';
import { DetailComponent } from './detail/detail.component';
import { HomeComponent } from './home/home.component';
import { MessageComponent } from './message/message.component';

const routes: Routes = [
  {path:"add",component:AddRequestComponent},
  {path:"swap/:id",component:DetailComponent},
  {path:"message/:id",component:MessageComponent},
  {path:"",component:HomeComponent},
  {path:"**",redirectTo:""}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{useHash:true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
