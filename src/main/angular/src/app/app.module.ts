import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon'
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatListModule} from "@angular/material/list";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FlexLayoutModule} from "@angular/flex-layout";
import { HttpClientModule } from '@angular/common/http';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';





@NgModule({
  imports: [BrowserModule, BrowserAnimationsModule, FormsModule, MatInputModule, MatButtonModule, MatIconModule, MatSidenavModule, MatToolbarModule, MatListModule, FlexLayoutModule, HttpClientModule, MatSlideToggleModule],
  declarations: [ AppComponent ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
