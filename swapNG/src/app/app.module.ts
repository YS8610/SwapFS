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
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { HowtouseComponent } from './howtouse/howtouse.component';
import { MapComponent } from './map/map.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { FeedbackComponent } from './feedback/feedback.component'

@NgModule({
  declarations: [
    AppComponent,
    AddRequestComponent,
    HomeComponent,
    DetailComponent,
    HeaderComponent,
    MessageComponent,
    HowtouseComponent,
    MapComponent,
    FeedbackComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: environment.production,
      // Register the ServiceWorker as soon as the app is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }),
    GoogleMapsModule
  ],
  providers: [
    {provide: DateAdapter, useClass: CustomDateAdapter}, //, deps: [MAT_DATE_LOCALE, Platform]
    {provide: MAT_DATE_FORMATS, useValue: AppDateFormats},

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
