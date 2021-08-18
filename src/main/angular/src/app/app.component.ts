import { Component } from '@angular/core';
import {AppService} from "./app.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss', './theme.scss']
})
export class AppComponent {
  title = 'angular-coding-task';
  type = 'JSON';
  constructor(private appService: AppService) {
  }
  download(){
    return this.appService.download(this.type).subscribe(c=>{
      const blob = new Blob([c], { type: 'application/text' });
      const url= window.URL.createObjectURL(blob);
      var anchor = document.createElement("a");
      anchor.download = "file."+this.type.toLowerCase();
      anchor.href = url;
      anchor.click();
      console.log("file downloaded");
    });
  }
  changeType(){
    if(this.type == 'JSON'){
      this.type = 'XML';
    }else{
      this.type = 'JSON';
    }
  }
}
