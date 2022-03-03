import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
// import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { AddRequestComponent } from './add-request/add-request.component';
import { HomeComponent } from './home/home.component';
import { MaterialModule } from './material.module';
import { AppDateFormats, CustomDateAdapter } from './date-format';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
// import { Platform } from '@angular/cdk/platform';
import { DetailComponent } from './detail/detail.component';
import { HeaderComponent } from './header/header.component';
import { MessageComponent } from './message/message.component';

@NgModule({
  declarations: [
    AppComponent,
    AddRequestComponent,
    HomeComponent,
    DetailComponent,
    HeaderComponent,
    MessageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule
  ],
  providers: [
    {provide: DateAdapter, useClass: CustomDateAdapter}, //, deps: [MAT_DATE_LOCALE, Platform]
    {provide: MAT_DATE_FORMATS, useValue: AppDateFormats},

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
